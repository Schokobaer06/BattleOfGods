package com.schokobaer.battleofgods.mechanics.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import javax.annotation.Nullable;
import java.util.List;

public class ItemClass extends Item {
    private final String name;
    private final TagKey<Item> tag;

    /**
     *The Main ItemClass where every Item belongs to<br>
     * For example: Melee, Ranged, Magic, Summoning, etc.
     * @param properties The properties of the itemClass
     * @param name Name of the ItemClass
     */
    public ItemClass(Properties properties, @NotBlank String name, @NotBlank TagKey<Item> tag) {
        super(properties);
        this.name = name;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }
    public TagKey<Item> getTag() {
        return tag;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.literal(this.name).setStyle(Style.EMPTY.withBold(true)));
    }
}
