package com.schokobaer.battleofgods.mechanics.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import javax.annotation.Nullable;
import java.util.List;

public class ItemClass extends Item {
    private final String name;

    /**
     *The Main ItemClass where every Item belongs to<br>
     * For example: Melee, Ranged, Magic, Summoning, etc.
     * @param properties The properties of the item
     * @param name Name of the ItemClass
     */
    public ItemClass(Properties properties, @NotBlank String name) {
        super(properties);
        this.name = name;
    }

    public String getName() {
        return name;
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.literal(this.name).setStyle(Style.EMPTY.withBold(true)));
    }
}
