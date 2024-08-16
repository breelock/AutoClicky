package com.breelock.autoclicky.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class TooltipCheckboxWidget extends CheckboxWidget {

    private final String tooltipText;

    public TooltipCheckboxWidget(int x, int y, int width, int height, LiteralText message, boolean checked, String tooltipText) {
        super(x, y, width, height, message, checked);
        this.tooltipText = tooltipText;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        if (isHovered() && MinecraftClient.getInstance().currentScreen != null && tooltipText != null) {
            MinecraftClient.getInstance().currentScreen.renderTooltip(matrices, new LiteralText(tooltipText), mouseX, mouseY);
        }
    }
}

