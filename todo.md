
# TODO:
## Inhaltsangabe
>[Layouts](#layouts)
>>[Ore](#ore)
>>[Class Setup](#class-setups)
>>[Mechanics](#mechanics)
>>[Progression](#progression)
>
>[Pre Hardmode](#pre-hardmode)
>>[Tier 1](#tier-1)
>>[Tier 2](#tier-2)
>>[Tier 3](#tier-3)
>>[Tier 4](#tier-4)
>
>[Hardmode](#hardmode)
>>[Tier 5](#tier-5)
>>[Tier 6](#tier-6)
>>[Tier 7](#tier-7)
>>[Tier 8](#tier-8)
>>[Tier 9](#tier-9)
>>[Tier 10](#tier-10)
>
>[Post-MoonLord](#post-moonlord)
>>[Tier 11](#tier-11)
>>[Tier 12](#tier-12)
>>[Tier 13](#tier-13)
>>[Tier 14](#tier-14)

## Layouts
### Ore
Ingot
>Raw Ore Item
>Raw Ore Block
>Ore Block
>Ingot Block
>Weapon/Tools
>Armor
>>Head
>>Body
>>Leggings
>>Boots
### Class Setups

#### All Classes
>Weapons
>Ammo/Buffs/Potions
>Armor
>Accessories
#### Melee
>Weapons
>> Shortswords
>>Broadswords
>>Yoyos
>>Spears
>>Boomerangs
>>Flails
>>Other
>
>Ammo/Buffs/Potions
>Armor
>Accessories
#### Ranged
>Weapons
>>Bow
>>Gun
>>Repeater
>>Consumables
>>Launchers
>>Others
>
>Ammo/Buffs/Potions
>Armor
>Accessories
#### Magic
>Weapons
>>Wands
>>Magic Guns
>Spell Books
>Others
>
>Ammo/Buffs/Potions
>Armor
>Accessories
#### Summoner
>Weapons
>>Minions
>>Sentries
>>Whips
>
>Ammo/Buffs/Potions
>Armor
>Accessories
#### Rogue
>Weapons
>>Bombs
>>Boomerangs
>>Daggers
>>Javelins
>>Spiky Balls
>>Others
>
>Ammo/Buffs/Potions
>Armor
>Accessories
#### Healer
>Weapons
>>Healing Spells
>>Support Spells
>>Scythes
>>Maces
>>Other
>
>Ammo/Buffs/Potions
>Armor
>Accessories
#### Bard
>Weapons
>>Brass Instruments
>>Percussion Instruments
>>String Instruments
>>Wind Instruments
>>Electronic Instruments
>
>Ammo/Buffs/Potions
>Armor
>Accessories
### Mechanics
#### Rarity
#### NPC
#### Difficulty
>##### Normal Mode
>##### Revengeance Mode
>##### Death Mode
>##### Eternity Mode
### Progression


```mermaid
graph TD
classDef boss fill:#ff5555,stroke:#aa0000,stroke-width:3px,color:black,rx:40,ry:20;
classDef biome fill:#008000,stroke:#164e00,stroke-width:3px,color:black,rx:20,ry:40;
classDef misc fill:#8f8f8f,stroke:#000000,stroke-width:2px,color:black,rx:5,ry:5;
classDef event fill:#04bdff,stroke:#0069b6,stroke-width:3px,color:black;
classDef start fill:#fff1cf,stroke:#ffb90f,stroke-width:2px,color:black;
classDef struct fill:#9541ff,stroke:#51005e,stroke-width:3px,color:black;
classDef miniboss fill:#ff9900,stroke:#cc6600,stroke-width:3px,color:black,rx:30,ry:15;
classDef npc fill:#66cc66,stroke:#336633,stroke-width:2px,color:black,rx:10,ry:10;
classDef invisible fill:#ffffff,stroke:#ffffff,stroke-width:0px,opacity:0;


subgraph legend [Legende]
direction LR
	start0@{ shape: circle, label: "Start"}
	bo0("Boss")
	mbo0("Miniboss")
	bi0("Biome")
	mis0("Misc")
	npc0("NPC")
	ev0("Event")
	str0("Struct")
	inv0-->arrow0@{shape: text, label: "Default Progression"}
	inv0==>arrow1@{shape: text, label: "Main Progression"}
	inv0-.->arrow2@{shape: text, label: "Optional Progression"}
end

subgraph prehardmode [Pre-Hardmode]
	subgraph t1 [Tier 1]
		start1@{ shape: circle, label: "Start"} --> bi1
		bi1(**Forest**) -.-> bo1
		bo1(**Trojan Squirrel**) --> npc1
		npc1("Squirrel")
		start1-->bi2
		bi2(Caverns)
		bi3(Desert) -->bo2
		bo2(**Grand Thunderbird**) --> npc2
		npc2(Desert Acolyte)
		bi2-->mis1
		mis1(Mine **Live Crystals**) --> ev1
		ev1(**Slime Rain**<br>200HP needed)
		ev1-.->bo3
		bo3(**King Slime**)
		bi2-->mbo1
		mbo1(Torch God)
		bo2-->bo3
		bo3(**Desert Scourge**)-->ev2 & mbo2
		ev2(Sandstorm)
		mbo2(Giant Clam)
		start1-->bi3 & bi4
		bi4(Sunken Sea)-->mbo2 & str1
		str1(SS Bio-Lab)
		mis1==>bo4
		bo4(**Eye of Cthulhu**<br>250HP & 10Def needed,4 NPCs must be present)	
		mis1-->npc3
		npc3(Nurse)
		start1-->bi5
		bi5(Ocean)-->npc4
		npc4(Angler)
		mbo2-->npc5
		npc5(Sea King)
		bo3-->npc6
		npc6(Cook)
	end
	subgraph t2 [Tier 2]
		bo4-->npc7 & npc8 & ev4
		npc7(Blacksmith)
		npc8(Tracker)
		bo4 & mis1-->ev3
		ev3(**Blood Moon**<br>140HP)--> mbo3
		mbo3(Patchwerk)-->npc9
		npc9(Confused Zombie)
		ev4(**Acid Rain**)
		bi2-->bi6
		bi6(Mushroom Biome)-->bo5
		bo5(**Crabulon**)
		start1-->bi7
		bi7(Evil Biome) & bo4==>bo6 & bo7
		bo6(Brain of Cthulu)
		bo7(Eater of Worlds)



	end
	subgraph t3 [Tier 3]
	end
	subgraph t4 [Tier 4]
	end
	%%t1 ==> t2
	t2 ==> t3
	t3 ==> t4
end
subgraph hardmode [Hardmode]
	subgraph t5 [Tier 5]

	end
	subgraph t6 [Tier 6]

	end
	subgraph t7 [Tier 7]

	end
	subgraph t8 [Tier 8]

	end
	subgraph t9 [Tier 9]

	end
	subgraph t10 [Tier 10]

	end
	t5 ==> t6
	t6 ==> t7
	t7 ==> t8
	t8 ==> t9
	t9 ==> t10
end
subgraph postmoonlord [PostMoonlord]
	subgraph t11 [Tier 11]

	end
	subgraph t12 [Tier 12]

	end
	subgraph t13 [Tier 13]

	end
	subgraph t14 [Tier 14]

	end
	t11 ==> t12
	t12 ==> t13
	t13 ==> t14
end
%%legend ~~~ prehardmode
prehardmode ==> hardmode
hardmode ==> postmoonlord
class bo0,bo1,bo2,bo3,bo4,bo5,bo6,bo7,bo8,bo9,bo10 boss
class bi0,bi1,bi2,bi3,bi4,bi5,bi6,bi7,bi8,bi9,bi10 biome
class mis0,mis1,mis2,mis3,mis4,mis5,mis6,mis7,mis8,mis9,mis10 misc
class ev0,ev1,ev2,ev3,ev5,ev6,ev7,ev8,ev9,ev10 event
class str0,str1,str2,str3,str4,str5,str6,str7,str8,str9,str10 struct
class start0,start1 start
class mbo0,mbo1,mbo2,mbo3,mbo4,mbo5,mbo6,mbo7,mbo8,mbo9,mbo10 miniboss
class npc0,npc1,npc2,npc3,npc4,npc5,npc6,npc7,npc8,npc9,npc10 npc
class inv0,inv1,inv2 invisible
```
## Pre-Hardmode
### Tier 1
![tier1-img](img/tiers_template/tier1.png)

- [ ] Start
	- [ ] Starter Items
		- [x] Copper Shortsword [`Me`](#melee)
		- [x] Copper Broadsword  [`Me`](#melee)
		- [ ] Copper Bow  [`Ra`](#ranged)
		- [ ] Copper Pickaxe
		- [ ] Copper Axe
		- [ ] Guidebook &#8594; with [Patchouli](https://github.com/VazkiiMods/Patchouli) ([Guide](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/getting-started/))
			- [ ] Entries
				- [ ] Bestiary
				- [ ] Progression Path
				- [ ] NPCs
				- [ ] Rarity Explained
				- [ ] Difficulty
		- [ ] Amethyst Staff [`Ma`](#magic)
	- [ ] Environment
		- [ ] Copper Ore
		- [ ] Tin Ore 
		- [ ] Amethyst
		- [ ] Topaz
	- [ ] Mobs
		- [ ] Slime
		- [ ] Squirrel
		- [ ] NPC
			- [ ] Guide
			- [ ] Merchant
	- [ ] Items
		- [ ] Tin Weapons & Tools
		- [ ] Copper Weapons & Tools
		- [ ] Bug Net
	- [ ] Mechanics
		- [ ] NPC
			- [ ] House System
			- [ ] Dialog System
			- [ ] Shop System
			- [ ] Happiness System
		- [ ] Money System
	- [ ] Advancements
		- [ ] Start
- [ ] Trojan Squirrel
	- [ ] Environment
		- [ ] Platin Ore
		- [ ] Gold Ore (Upgrade)
		- [ ] Thorium Ore
- [ ] Grand Thunder Bird
- [ ] King Slime
- [ ] The Torch God
- [ ] Desert Scourge
- [ ] Giant Clam
- [ ] Eye of Cthulu

### Tier 2
![tier2-img](img/tiers_template/tier2.png)

### Tier 3
![tier3-img](img/tiers_template/tier3.png)

### Tier 4
![tier4-img](img/tiers_template/tier4.png)

## Hardmode
### Tier 5
![tier5-img](img/tiers_template/tier5.png)

### Tier 6
![tier6-img](img/tiers_template/tier6.png)

### Tier 7
![tier7-img](img/tiers_template/tier7.png)

### Tier 8
![tier8-img](img/tiers_template/tier8.png)

### Tier 9
![tier9-img](img/tiers_template/tier9.png)

### Tier 10
![tier10-img](img/tiers_template/tier10.png)

## Post MoonLord
### Tier 11
![tier11-img](img/tiers_template/tier11.png)

### Tier 12
![tier12-img](img/tiers_template/tier12.png)

### Tier 13
![tier13-img](img/tiers_template/tier13.png)

### Tier 14
![tier14-img](img/tiers_template/tier14.png)
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTg4MzU0MTM0NiwxODgzNTQxMzQ2LDMyOD
IzMDkzMiwtMTI4NDU3NTQxLDEyNDM3NTI0NDcsLTYwNzY3OTcw
LDE2ODgxOTU2MDUsLTE5MjQ4OTg2NzMsLTEyOTgyNTgyNDksLT
E3MDg1OTUyNTMsLTY0MjM5MTAyMSwxNzAyNTgxMTMyLC0xMTkx
OTgxMzE2LDY5MTM5ODQ3MSw1MzEzNzE1OTEsMTA1NjEzMzg4Mi
wxMTM0OTMxOTM5LC05MzIwODU1MDAsLTI2NDk1OTQ5NCwtNjM1
NTAzMjEyXX0=
-->