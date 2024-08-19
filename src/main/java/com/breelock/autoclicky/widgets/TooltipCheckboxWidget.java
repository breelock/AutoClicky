package com.breelock.autoclicky.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;

public class TooltipCheckboxWidget extends CheckboxWidget {
    private final String tooltipText;

    public TooltipCheckboxWidget(int x, int y, int width, int height, Text message, boolean checked, String tooltipText) {
        super(x, y, width, height, message, checked);
        this.tooltipText = tooltipText;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        // Check if the mouse is hovering over the checkbox
        if (this.isMouseOver(mouseX, mouseY) && this.tooltipText != null && !this.tooltipText.trim().isEmpty()) {
            renderTooltip(context, mouseX, mouseY);
        }
    }

    private void renderTooltip(DrawContext context, int mouseX, int mouseY) {
        context.drawTooltip(MinecraftClient.getInstance().textRenderer, Text.of(tooltipText), mouseX, mouseY);
    }
}
