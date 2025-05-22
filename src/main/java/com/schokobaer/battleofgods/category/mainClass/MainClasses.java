package com.schokobaer.battleofgods.category.mainClass;

public enum MainClasses implements MainClass{
    MISC("misc"),
    MELEE("melee"),
    RANGED("ranged"),
    MAGIC("magic"),
    SUMMONING("summoning"),
    ROGUE("rogue"),
    HEALING("healing"),
    BARD("bard"),
    TOOL("tool"),
    ARMOR("armor");

    private final String name;

    MainClasses(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}