package com.schokobaer.battleofgods.client.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import com.schokobaer.battleofgods.recipe.RecipeHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class MaterialWidget extends AbstractWidget {
    private final RecipeHandler.BattleRecipe.IngredientEntry entry;
    private final Player player;
    private static final int ITEM_SIZE = 16;
    private final List<ItemStack> cachedItems;
    private static final long ROTATION_INTERVAL = 15; // Zeit in ticks für den Wechsel


    public MaterialWidget(int x, int y, RecipeHandler.BattleRecipe.IngredientEntry entry, Player player) {
        super(x, y, 100, 20, Component.empty());
        this.entry = entry;
        this.player = player;
        this.cachedItems = List.of(entry.ingredient().getItems());
    }



    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        assert Minecraft.getInstance().level != null;
        // 1. Rendere Item-Icon
        if(!cachedItems.isEmpty()) {
            // Zyklisch durch die Items im Tag wechseln
            int index = (int) ((System.currentTimeMillis() / (ROTATION_INTERVAL* 50L)) % cachedItems.size()); // Alle 500ms wechseln
            ItemStack currentItem = cachedItems.get(index);
            guiGraphics.renderFakeItem(currentItem, getX(), getY());


            // 2. Rendere Text (Anzahl)
            int available = getAvailableCount();
            int required = entry.count();
            ChatFormatting color = (available >= required) ? ChatFormatting.GREEN : ChatFormatting.RED;
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            poseStack.translate(getX() + ITEM_SIZE - 8, getY() + ITEM_SIZE -5, 200); // Position neben dem Item
            poseStack.scale(0.7f, 0.7f, 1);
            guiGraphics.drawString(
                    Minecraft.getInstance().font,
                    Component.literal(String.valueOf(available)).withStyle(color)
                            .append(Component.literal("/").withStyle(ChatFormatting.WHITE)
                            .append(Component.literal(String.valueOf(required)).withStyle(ChatFormatting.WHITE))),
                    0, 0,
                    0xFFFFFF,
                    false
            );

            poseStack.popPose();

            // 3. Rendere Tooltip
            if (isMouseOver(mouseX, mouseY)) {
                guiGraphics.renderTooltip(Minecraft.getInstance().font, currentItem, mouseX, mouseY);
            }
        }
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= getX() && mouseX <= getX() + ITEM_SIZE
                && mouseY >= getY() && mouseY <= getY() + ITEM_SIZE;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        defaultButtonNarrationText(output);
    }

    private int getAvailableCount() {
        int count = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (entry.ingredient().test(stack)) {
                count += stack.getCount();
                //if (count >= entry.count()) break;
            }
        }
        return count;
    }
    public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        assert Minecraft.getInstance().level != null;
        guiGraphics.renderTooltip(
                Minecraft.getInstance().font,
                entry.ingredient().getItems()[0],
                mouseX, mouseY
        );
    }
}
