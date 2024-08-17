package com.breelock.autoclicky.pages;

import com.breelock.autoclicky.ModConfig;
import com.breelock.autoclicky.widgets.TooltipCheckboxWidget;
import com.breelock.autoclicky.widgets.TooltipSliderWidget;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class NewCombat extends OldCombat {
    @Override
    public void init() {
        this.client.keyboard.setRepeatEvents(true);

        int centerX = this.width / 2;
        int startY = this.height / 4;
        int spacing = 30;
        int columnSpacing = 10;
        int widgetWidth = 95;
        int buttonWidth = 95;
        int checkboxWidth = 95;

        // Select PvP system button
        this.addButton(new ButtonWidget(this.width / 2 - 50, startY - spacing + 7, 100, 20, new LiteralText(new TranslatableText(ModConfig.pvpSystems.get(1)).getString()), button -> {
            save(true);
            ModConfig.selectedPvp = (ModConfig.PvP.values()[(ModConfig.selectedPvp.ordinal() + 1) % ModConfig.pvpSystems.size()]);
            this.client.openScreen(new OldCombat());
        }));

        // Left click - minimum click delay slider
        leftMinDelaySlider = new TooltipSliderWidget(centerX - widgetWidth - columnSpacing / 2, startY + spacing, widgetWidth, 20, new LiteralText(new TranslatableText("gui.autoclicky.minDelay").getString() + ": " + ModConfig.NewPvP.leftMinDelay), ModConfig.NewPvP.leftMinDelay / 100.0, new TranslatableText("gui.autoclicky.delayTooltip").getString()) {
            @Override
            protected void updateMessage() {
                this.setMessage(new LiteralText(new TranslatableText("gui.autoclicky.minDelay").getString() + ": " + (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                ModConfig.NewPvP.leftMinDelay = (int)(this.value * 100);
            }
        };
        this.children.add(leftMinDelaySlider);

        // Left click - maximum click delay slider
        leftMaxDelaySlider = new TooltipSliderWidget(centerX - widgetWidth - columnSpacing / 2, startY + 2 * spacing, widgetWidth, 20, new LiteralText(new TranslatableText("gui.autoclicky.maxDelay").getString() + ": " + ModConfig.NewPvP.leftMaxDelay), ModConfig.NewPvP.leftMaxDelay / 100.0, new TranslatableText("gui.autoclicky.delayTooltip").getString()) {
            @Override
            protected void updateMessage() {
                this.setMessage(new LiteralText(new TranslatableText("gui.autoclicky.maxDelay").getString() + ": " + (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                ModConfig.NewPvP.leftMaxDelay = (int)(this.value * 100);
            }
        };
        this.children.add(leftMaxDelaySlider);

        // Right click - minimum click delay slider
        rightMinDelaySlider = new TooltipSliderWidget(centerX + columnSpacing / 2, startY + spacing, widgetWidth, 20, new LiteralText(new TranslatableText("gui.autoclicky.minDelay").getString() + ": " + ModConfig.NewPvP.rightMinDelay), ModConfig.NewPvP.rightMinDelay / 100.0, new TranslatableText("gui.autoclicky.delayTooltip").getString()) {
            @Override
            protected void updateMessage() {
                this.setMessage(new LiteralText(new TranslatableText("gui.autoclicky.minDelay").getString() + ": " + (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                ModConfig.NewPvP.rightMinDelay = (int)(this.value * 100);
            }
        };
        this.children.add(rightMinDelaySlider);

        // Right click - maximum click delay slider
        rightMaxDelaySlider = new TooltipSliderWidget(centerX + columnSpacing / 2, startY + 2 * spacing, widgetWidth, 20, new LiteralText(new TranslatableText("gui.autoclicky.maxDelay").getString() + ": " + ModConfig.NewPvP.rightMaxDelay), ModConfig.NewPvP.rightMaxDelay / 100.0, new TranslatableText("gui.autoclicky.delayTooltip").getString()) {
            @Override
            protected void updateMessage() {
                this.setMessage(new LiteralText(new TranslatableText("gui.autoclicky.maxDelay").getString() + ": " + (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                ModConfig.NewPvP.rightMaxDelay = (int)(this.value * 100);
            }
        };
        this.children.add(rightMaxDelaySlider);

        // First click is instant checkbox
        interruptCheckbox = new TooltipCheckboxWidget(centerX + columnSpacing / 2, startY + 3 * spacing, checkboxWidth, 20, new LiteralText(new TranslatableText("gui.autoclicky.interrupt").getString()), ModConfig.NewPvP.interrupt, new TranslatableText("gui.autoclicky.interrupt.tooltip").getString());
        this.children.add(interruptCheckbox);

        // Show message checkbox
        showMessageCheckbox = new TooltipCheckboxWidget(centerX - checkboxWidth - columnSpacing / 2, startY + 3 * spacing, checkboxWidth, 20, new LiteralText(new TranslatableText("gui.autoclicky.messages").getString()), ModConfig.NewPvP.showMessage, new TranslatableText("gui.autoclicky.messages.tooltip").getString());
        this.children.add(showMessageCheckbox);

        // Auto jump checkbox
        autoJumpCheckbox = new TooltipCheckboxWidget(centerX - checkboxWidth - columnSpacing / 2, startY + 4 * spacing, checkboxWidth, 20, new LiteralText(new TranslatableText("gui.autoclicky.autoJump").getString()), ModConfig.NewPvP.autoJump, new TranslatableText("gui.autoclicky.autoJump.tooltip").getString());
        this.children.add(autoJumpCheckbox);

        // Attack only when targeting an entity checkbox
        onlyEntityCheckbox = new TooltipCheckboxWidget(centerX + columnSpacing / 2, startY + 4 * spacing, checkboxWidth, 20, new LiteralText(new TranslatableText("gui.autoclicky.onlyEntity").getString()), ModConfig.NewPvP.onlyEntity, new TranslatableText("gui.autoclicky.onlyEntity.tooltip").getString());
        this.children.add(onlyEntityCheckbox);

        // Save button
        this.addButton(new ButtonWidget(centerX + 5, startY + 5 * spacing, buttonWidth, 20, new LiteralText(new TranslatableText("gui.autoclicky.save").getString()), button -> {
            save(true);
            this.client.openScreen(null); // Close the screen
        }));

        // Exit without saving button
        this.addButton(new ButtonWidget(centerX - buttonWidth - 5, startY + 5 * spacing, buttonWidth, 20, new LiteralText(new TranslatableText("gui.autoclicky.cancel").getString()), button -> {
            this.client.openScreen(null); // Close the screen without saving
        }));
    }
}
