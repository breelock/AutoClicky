package com.breelock.autoclicky.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public abstract class TooltipSliderWidget extends SliderWidget {

    private final String tooltipText;

    public TooltipSliderWidget(int x, int y, int width, int height, Text text, double value, String tooltipText) {
        super(x, y, width, height, text, value);
        this.tooltipText = tooltipText;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        if (isHovered() && MinecraftClient.getInstance().currentScreen != null && tooltipText != null) {
            MinecraftClient.getInstance().currentScreen.renderTooltip(matrices, Text.literal(tooltipText), mouseX, mouseY);
        }
    }
}

