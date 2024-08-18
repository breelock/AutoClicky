package com.breelock.autoclicky.pages;

import com.breelock.autoclicky.ModConfig;
import com.breelock.autoclicky.widgets.TooltipCheckboxWidget;
import com.breelock.autoclicky.widgets.TooltipSliderWidget;

import net.minecraft.client.gui.widget.ButtonWidget;
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
        interruptCheckbox = new TooltipCheckboxWidget(centerX + columnSpacing / 2, startY + 3 * spacing, checkboxWidth, 20, Text.literal(Text.translatable("gui.autoclicky.interrupt").getString()), ModConfig.NewPvP.interrupt, Text.translatable("gui.autoclicky.interrupt.tooltip").getString());
        addDrawableChild(interruptCheckbox);

        // Show message checkbox
        showMessageCheckbox = new TooltipCheckboxWidget(centerX - checkboxWidth - columnSpacing / 2, startY + 3 * spacing, checkboxWidth, 20, Text.literal(Text.translatable("gui.autoclicky.messages").getString()), ModConfig.NewPvP.showMessage, Text.translatable("gui.autoclicky.messages.tooltip").getString());
        addDrawableChild(showMessageCheckbox);

        // Auto jump checkbox
        autoJumpCheckbox = new TooltipCheckboxWidget(centerX - checkboxWidth - columnSpacing / 2, startY + 4 * spacing, checkboxWidth, 20, Text.literal(Text.translatable("gui.autoclicky.autoJump").getString()), ModConfig.NewPvP.autoJump, Text.translatable("gui.autoclicky.autoJump.tooltip").getString());
        addDrawableChild(autoJumpCheckbox);

        // Attack only when targeting an entity checkbox
        onlyEntityCheckbox = new TooltipCheckboxWidget(centerX + columnSpacing / 2, startY + 4 * spacing, checkboxWidth, 20, Text.literal(Text.translatable("gui.autoclicky.onlyEntity").getString()), ModConfig.NewPvP.onlyEntity, Text.translatable("gui.autoclicky.onlyEntity.tooltip").getString());
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
