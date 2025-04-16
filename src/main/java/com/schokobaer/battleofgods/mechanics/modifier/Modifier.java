package com.schokobaer.battleofgods.mechanics.modifier;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public abstract class Modifier {
    private final Attribute attribute;
    private final UUID uuid;
    private final String name;

    protected Modifier(Attribute attribute, UUID uuid, String name) {
        this.attribute = attribute;
        this.uuid = uuid;
        this.name = name;
    }

    public abstract AttributeModifier createModifier(double value);

    public Attribute getAttribute() {
        return attribute;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }
}