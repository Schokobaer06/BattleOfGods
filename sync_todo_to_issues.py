#!/usr/bin/env python3
import os
import re
import requests

# GitHub-Konfiguration aus Umgebungsvariablen
GITHUB_REPOSITORY = os.getenv("GITHUB_REPOSITORY")  # Format: owner/repo
GITHUB_TOKEN      = os.getenv("GITHUB_TOKEN")
OWNER, REPO       = GITHUB_REPOSITORY.split("/")
API_URL           = f"https://api.github.com/repos/{OWNER}/{REPO}"
HEADERS           = {
    "Authorization": f"Bearer {GITHUB_TOKEN}",
    "Accept":        "application/vnd.github+json"
}

def read_tasks(filename="todo.md"):
    pattern = re.compile(r"^(?P<indent>\s*)- \[(?P<check>[ xX])\] (?P<title>.+)$", re.MULTILINE)
    tasks = []
    with open(filename, "r", encoding="utf-8") as f:
        content = f.read()
    for m in pattern.finditer(content):
        level   = len(m.group("indent")) // 2
        checked = m.group("check").lower() == "x"
        title   = m.group("title").strip()
        tasks.append({"title": title, "checked": checked, "indent": level})
    return tasks

def get_existing_issues():
    issues = {}
    page = 1
    while True:
        r = requests.get(f"{API_URL}/issues?state=all&per_page=100&page={page}", headers=HEADERS)
        r.raise_for_status()
        batch = r.json()
        if not batch:
            break
        for issue in batch:
            issues[issue["title"]] = issue
        page += 1
    return issues

def create_issue(title, status, body):
    payload = {"title": title, "body": body, "labels": [status]}
    r = requests.post(f"{API_URL}/issues", headers=HEADERS, json=payload)
    r.raise_for_status()
    return r.json()

def update_issue(issue_number, status):
    state   = "closed" if status == "Done" else "open"
    payload = {"state": state, "labels": [status]}
    r = requests.patch(f"{API_URL}/issues/{issue_number}", headers=HEADERS, json=payload)
    r.raise_for_status()
    return r.json()

def update_issue_body(issue_number, new_body):
    r = requests.patch(f"{API_URL}/issues/{issue_number}", headers=HEADERS, json={"body": new_body})
    r.raise_for_status()
    return r.json()

def build_tree(tasks):
    nodes = [dict(t, children=[]) for t in tasks]
    roots = []
    stack = []
    for node in nodes:
        while stack and stack[-1]["indent"] >= node["indent"]:
            stack.pop()
        if stack:
            stack[-1]["children"].append(node)
        else:
            roots.append(node)
        stack.append(node)
    return roots

def generate_body(node, status, issues_map):
    lines = [f"Imported from `todo.md`.", f"Status: **{status}**"]
    if node["children"]:
        lines.append("\n**Subtasks:**")
        for child in node["children"]:
            if child["title"] in issues_map:
                url      = issues_map[child["title"]]["html_url"]
                checkbox = "[x]" if child["checked"] else "[ ]"
                lines.append(f"- {checkbox} [{child['title']}]({url})")
    return "\n".join(lines)

def main():
    tasks    = read_tasks()
    existing = get_existing_issues()

    done_list  = [t["title"] for t in tasks if t["checked"]]
    open_list  = [t["title"] for t in tasks if not t["checked"]]

    in_progress = open_list[0]    if open_list else None
    todo_list   = open_list[1:4]
    backlog     = open_list[4:]

    status_map = {}
    for t in done_list:      status_map[t] = "Done"
    if in_progress:          status_map[in_progress] = "In Progress"
    for t in todo_list:      status_map[t] = "Todo"
    for t in backlog:        status_map[t] = "Backlog"

    issues_map = {}
    for task in tasks:
        title  = task["title"]
        status = status_map.get(title, "Backlog")
        simple_body = f"Imported from `todo.md`.\n\nStatus: **{status}**"

        if title in existing:
            issue = existing[title]
            labels = [l["name"] for l in issue["labels"]]
            if status not in labels or (issue["state"] == "closed" and status != "Done"):
                issue = update_issue(issue["number"], status)
            issues_map[title] = issue
        else:
            issue = create_issue(title, status, simple_body)
            issues_map[title] = issue

    tree = build_tree(tasks)
    def recurse(node):
        if node["children"]:
            issue = issues_map[node["title"]]
            status = status_map.get(node["title"], "Backlog")
            body   = generate_body(node, status, issues_map)
            update_issue_body(issue["number"], body)
            for c in node["children"]:
                recurse(c)
    for root in tree:
        recurse(root)

if __name__ == "__main__":
    main()
