package com.breelock.autoclicky.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public abstract class TooltipSliderWidget extends SliderWidget {
    private final String tooltipText;

    public TooltipSliderWidget(int x, int y, int width, int height, Text text, double value, String tooltipText) {
        super(x, y, width, height, text, value);
        this.tooltipText = tooltipText;
        this.updateMessage();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        // Check if the mouse is hovering over the slider
        if (this.isMouseOver(mouseX, mouseY) && this.tooltipText != null && !this.tooltipText.trim().isEmpty()) {
            renderTooltip(context, mouseX, mouseY);
        }
    }

    private void renderTooltip(DrawContext context, int mouseX, int mouseY) {
        context.drawTooltip(MinecraftClient.getInstance().textRenderer, Text.of(tooltipText), mouseX, mouseY);
    }
}