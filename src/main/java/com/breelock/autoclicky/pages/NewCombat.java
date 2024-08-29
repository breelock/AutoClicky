package com.breelock.autoclicky.pages;

import com.breelock.autoclicky.ModConfig;
import com.breelock.autoclicky.widgets.TooltipSliderWidget;

import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;

public class NewCombat extends OldCombat {
    @Override
    public void init() {
        // this.client.keyboard.setRepeatEvents(true);

        int centerX = this.width / 2;
        int startY = this.height / 4;
        int spacing = 30;
        int columnSpacing = 10;
        int widgetWidth = 95;
        int buttonWidth = 95;
        int checkboxWidth = 95;

        // Select PvP system button
        addDrawableChild(ButtonWidget.builder(Text.literal(Text.translatable(ModConfig.pvpSystems.get(1)).getString()), (button) -> {
            save(true);
            ModConfig.selectedPvp = (ModConfig.PvP.values()[(ModConfig.selectedPvp.ordinal() + 1) % ModConfig.pvpSystems.size()]);
            this.client.setScreen(new OldCombat());
        }).dimensions(this.width / 2 - 50, startY - spacing + 7, 100, 20).build());

        // Left click - minimum click delay slider
        leftMinDelaySlider = new TooltipSliderWidget(centerX - widgetWidth - columnSpacing / 2, startY + spacing, widgetWidth, 20, Text.literal(Text.translatable("gui.autoclicky.minDelay").getString() + ": " + ModConfig.NewPvP.leftMinDelay), ModConfig.NewPvP.leftMinDelay / 100.0, Text.translatable("gui.autoclicky.delayTooltip").getString()) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal(Text.translatable("gui.autoclicky.minDelay").getString() + ": " + (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                ModConfig.NewPvP.leftMinDelay = (int)(this.value * 100);
            }
        };
        addDrawableChild(leftMinDelaySlider);

        // Left click - maximum click delay slider
        leftMaxDelaySlider = new TooltipSliderWidget(centerX - widgetWidth - columnSpacing / 2, startY + 2 * spacing, widgetWidth, 20, Text.literal(Text.translatable("gui.autoclicky.maxDelay").getString() + ": " + ModConfig.NewPvP.leftMaxDelay), ModConfig.NewPvP.leftMaxDelay / 100.0, Text.translatable("gui.autoclicky.delayTooltip").getString()) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal(Text.translatable("gui.autoclicky.maxDelay").getString() + ": " + (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                ModConfig.NewPvP.leftMaxDelay = (int)(this.value * 100);
            }
        };
        addDrawableChild(leftMaxDelaySlider);

        // Right click - minimum click delay slider
        rightMinDelaySlider = new TooltipSliderWidget(centerX + columnSpacing / 2, startY + spacing, widgetWidth, 20, Text.literal(Text.translatable("gui.autoclicky.minDelay").getString() + ": " + ModConfig.NewPvP.rightMinDelay), ModConfig.NewPvP.rightMinDelay / 100.0, Text.translatable("gui.autoclicky.delayTooltip").getString()) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal(Text.translatable("gui.autoclicky.minDelay").getString() + ": " + (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                ModConfig.NewPvP.rightMinDelay = (int)(this.value * 100);
            }
        };
        addDrawableChild(rightMinDelaySlider);

        // Right click - maximum click delay slider
        rightMaxDelaySlider = new TooltipSliderWidget(centerX + columnSpacing / 2, startY + 2 * spacing, widgetWidth, 20, Text.literal(Text.translatable("gui.autoclicky.maxDelay").getString() + ": " + ModConfig.NewPvP.rightMaxDelay), ModConfig.NewPvP.rightMaxDelay / 100.0, Text.translatable("gui.autoclicky.delayTooltip").getString()) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal(Text.translatable("gui.autoclicky.maxDelay").getString() + ": " + (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                ModConfig.NewPvP.rightMaxDelay = (int)(this.value * 100);
            }
        };
        addDrawableChild(rightMaxDelaySlider);

        // First click is instant checkbox
        interruptCheckbox = CheckboxWidget.builder(Text.translatable("gui.autoclicky.interrupt"), this.textRenderer).pos(centerX + columnSpacing / 2, startY + 3 * spacing).tooltip(Tooltip.of(Text.translatable("gui.autoclicky.interrupt.tooltip"))).checked(ModConfig.NewPvP.interrupt).build();
        addDrawableChild(interruptCheckbox);

        // Show message checkbox
        showMessageCheckbox = CheckboxWidget.builder(Text.translatable("gui.autoclicky.messages"), this.textRenderer).pos(centerX - checkboxWidth - columnSpacing / 2, startY + 3 * spacing).tooltip(Tooltip.of(Text.translatable("gui.autoclicky.messages.tooltip"))).checked(ModConfig.NewPvP.showMessage).build();
        addDrawableChild(showMessageCheckbox);

        // Auto jump checkbox
        autoJumpCheckbox = CheckboxWidget.builder(Text.translatable("gui.autoclicky.autoJump"), this.textRenderer).pos(centerX - checkboxWidth - columnSpacing / 2, startY + 4 * spacing).tooltip(Tooltip.of(Text.translatable("gui.autoclicky.autoJump.tooltip"))).checked(ModConfig.NewPvP.autoJump).build();
        addDrawableChild(autoJumpCheckbox);

        // Attack only when targeting an entity checkbox
        onlyEntityCheckbox = CheckboxWidget.builder(Text.translatable("gui.autoclicky.onlyEntity"), this.textRenderer).pos(centerX + columnSpacing / 2, startY + 4 * spacing).tooltip(Tooltip.of(Text.translatable("gui.autoclicky.onlyEntity.tooltip"))).checked(ModConfig.NewPvP.onlyEntity).build();
        addDrawableChild(onlyEntityCheckbox);

        // Save button
        addDrawableChild(ButtonWidget.builder(Text.literal(Text.translatable("gui.autoclicky.save").getString()), (button) -> {
            save(true);
            this.client.setScreen(null); // Close the screen
        }).dimensions(centerX + 5, startY + 5 * spacing, buttonWidth, 20).build());

        // Exit without saving button
        addDrawableChild(ButtonWidget.builder(Text.literal(Text.translatable("gui.autoclicky.cancel").getString()), (button) -> {
            this.client.setScreen(null); // Close the screen
        }).dimensions(centerX - buttonWidth - 5, startY + 5 * spacing, buttonWidth, 20).build());
    }
}
