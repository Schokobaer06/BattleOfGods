package com.schokobaer.battleofgods.category.rarity;

import com.schokobaer.battleofgods.BattleOfGods;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;


@Mod.EventBusSubscriber(modid = BattleOfGods.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Rarities {
    public static Rarity GRAY;
    public static Rarity WHITE;
    public static Rarity BLUE;
    public static Rarity GREEN;
    public static Rarity ORANGE;
    public static Rarity LIGHT_RED;
    public static Rarity PINK;
    public static Rarity LIGHT_PURPLE;
    public static Rarity LIME;
    public static Rarity YELLOW;
    public static Rarity CYAN;
    public static Rarity RED;
    public static Rarity PURPLE;
    public static Rarity TURQUOISE;
    public static Rarity PURE_GREEN;
    public static Rarity DARK_BLUE;
    public static Rarity VIOLET;
    public static Rarity HOT_PINK;
    public static Rarity CALAMITY_RED;
    public static Rarity AMBER;
    public static Rarity DARK_ORANGE;
    public static Rarity RAINBOW;
    public static Rarity FIERY_RED;
    public static Rarity TEAL;

    static com.schokobaer.battleofgods.category.rarity.Rarity rainbow = new com.schokobaer.battleofgods.category.rarity.Rarity("Revengeance Mode", new ResourceLocation("battleofgods:textures/rarity/rainbow.png"), 0.25f);
    static com.schokobaer.battleofgods.category.rarity.Rarity fiery_red = new com.schokobaer.battleofgods.category.rarity.Rarity("Death Mode", new ResourceLocation("battleofgods:textures/rarity/fiery_red.png"));
    static com.schokobaer.battleofgods.category.rarity.Rarity teal = new com.schokobaer.battleofgods.category.rarity.Rarity("Eternity Mode", new ResourceLocation("battleofgods:textures/rarity/teal.png"), 0.5f);

    public static com.schokobaer.battleofgods.category.rarity.Rarity asRarity(Rarity rarity) {
        Style style = (Style) rarity.getStyleModifier();
        if (style.getColor() == null) return null;
        return new com.schokobaer.battleofgods.category.rarity.Rarity(rarity.name(), style.getColor().getValue());
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        GRAY = Rarity.create("gray", style -> style.withColor(TextColor.fromRgb(0x808080)));
        WHITE = Rarity.create("white", style -> style.withColor(TextColor.fromRgb(0xFFFFFF)));
        BLUE = Rarity.create("blue", style -> style.withColor(TextColor.fromRgb(0x9696FF)));
        GREEN = Rarity.create("green", style -> style.withColor(TextColor.fromRgb(0x96FF96)));
        ORANGE = Rarity.create("orange", style -> style.withColor(TextColor.fromRgb(0xFFC896)));
        LIGHT_RED = Rarity.create("light_red", style -> style.withColor(TextColor.fromRgb(0xFF9696)));
        PINK = Rarity.create("pink", style -> style.withColor(TextColor.fromRgb(0xFF96FF)));
        LIGHT_PURPLE = Rarity.create("light_purple", style -> style.withColor(TextColor.fromRgb(0xD2A0FF)));
        LIME = Rarity.create("lime", style -> style.withColor(TextColor.fromRgb(0x96FF0A)));
        YELLOW = Rarity.create("yellow", style -> style.withColor(TextColor.fromRgb(0xFFFF0A)));
        CYAN = Rarity.create("cyan", style -> style.withColor(TextColor.fromRgb(0x05C8FF)));
        RED = Rarity.create("red", style -> style.withColor(TextColor.fromRgb(0xFF2864)));
        PURPLE = Rarity.create("purple", style -> style.withColor(TextColor.fromRgb(0xB428FF)));
        TURQUOISE = Rarity.create("turquoise", style -> style.withColor(TextColor.fromRgb(0x00FFC8)));
        PURE_GREEN = Rarity.create("pure_green", style -> style.withColor(TextColor.fromRgb(0x00FF00)));
        DARK_BLUE = Rarity.create("dark_blue", style -> style.withColor(TextColor.fromRgb(0x2B60DE)));
        VIOLET = Rarity.create("violet", style -> style.withColor(TextColor.fromRgb(0x6C2DC7)));
        HOT_PINK = Rarity.create("hot_pink", style -> style.withColor(TextColor.fromRgb(0xFF00FF)));
        CALAMITY_RED = Rarity.create("calamity_red", style -> style.withColor(TextColor.fromRgb(0xA3191A)));
        AMBER = Rarity.create("amber", style -> style.withColor(TextColor.fromRgb(0xFFAF00)));
        DARK_ORANGE = Rarity.create("dark_orange", style -> style.withColor(TextColor.fromRgb(0xCC4723)));

        RAINBOW = Rarity.create("rainbow", style -> style.withColor(TextColor.fromRgb(rainbow.getColor())));
        FIERY_RED = Rarity.create("fiery_red", style -> style.withColor(TextColor.fromRgb(fiery_red.getColor())));
        TEAL = Rarity.create("teal", style -> style.withColor(TextColor.fromRgb(teal.getColor())));
    }

}