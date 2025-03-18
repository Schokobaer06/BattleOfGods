package com.schokobaer.battleofgods.mechanics.item;
import com.schokobaer.battleofgods.mechanics.rarity.Rarity;
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

public class ItemSubClass extends ItemClass {
    private final String displayName;
    private final ItemClass itemClass;
    private Rarity rarity;
    private final TagKey<Item> tag;
    /**
     *The SubClass all items belong to<br>
     * Every ItemSubClass belongs to an ItemClass<br>
     * For example: Broadsword (Melee), Whip (Summoning), Magic Gun (Magic), Bow (Ranged), etc.
     * @param properties The properties of the ItemSubClass
     * @param itemClass  The ItemClass the Item belongs to
     * @param name       Name of the SubClass
     */
    public ItemSubClass(Properties properties, ItemClass itemClass, @NotBlank String name, TagKey<Item> tag) {
        super(properties, itemClass.getName(), itemClass.getTag());
        this.itemClass = itemClass;
        this.displayName = name;
        this.tag = tag;
    }

    public String GetName() {
        return displayName;
    }

    public ItemClass getItemClass() {
        return itemClass;
    }
    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }
    public Rarity getRarity() {
        return rarity;
    }
    public TagKey<Item> getTag() {
        return tag;
    }
    @Override
    public Component getName(ItemStack stack) {
        Component name = super.getName(stack);
        if (rarity != null)
            return name.copy().withStyle(Style.EMPTY.withBold(true).withColor(rarity.getArgbColor()));
        return name;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        if (this.rarity != null)
            tooltip.add(Component.literal(this.rarity.getDisplayName()).setStyle(Style.EMPTY.withBold(true).withColor(rarity.getArgbColor()).withItalic(true)));

    }
}
