package com.schokobaer.battleofgods.utils.tier;

public enum GameTiers implements GameTier{
    TIER_1(),
    TIER_2(),
    TIER_3(),
    TIER_4(),
    TIER_5();

    private final String name;

    GameTiers() {
        this.name = "tier_" + ordinal();
    }
    GameTiers(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
