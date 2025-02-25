## Recipe Template:

```json 
{
  "type": "battleofgods:default_recipe",
  "group": "recipe_group",
  "category": "recipe_category",
  "items": [
    {
      "item": "recipe_name",
      "count": null
    },
    {
      "tag": "modid:tag",
      "count": 1
    },
    {
      "item": "recipe_name",
        "count": 15
    }
  ],
  "output": {
    "item": "recipe_name",
    "count": 1
  }
}
```
>`type` - The type of the recipe. This should always be `battleofgods:default_recipe`.<br>
 
>`group` - The group of the recipe. This is used to group recipes together in the recipe book.<br>
>>usable types:<br>`workbench` 
 
>`category` - The category of the recipe. This is used to group recipes together in the recipe book. Default is `misc`.<br>
>>usable types:<br>`misc`,`accessories`, `armor`, `blocks`, `consumables`, `tools`, `weapons`,`potions`,`boss_summonings`,`pets`<br>
 
>`items` - An array of items required to craft the recipe. Each item should have an `item`/`tag` and `count` property.<br>
> 
>> `item` - The item that is required to craft the recipe.<br>
>>> `modid:name`<br>
>> 
>> `tag` - Item tag that is required to craft the recipe
>>> `modid:tag` 
>> 
>> `count` - The number of items required to craft the recipe.<br>
>>> `int`- type; value should be `1 <= value <= 9999`<br>anything different will be ignored

>`output` - The item that is crafted by the recipe. This should have an `item` and `count` property. The `count` property is the number of items that are crafted by the recipe.<br>
 





## Crafting GUI
**Here is how it should look:<br>**
![GUI](./gui.png)<br>
**Here is how it should look in JEI:<br>**