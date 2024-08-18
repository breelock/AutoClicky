package com.breelock.autoclicky.pages;

import com.breelock.autoclicky.ModConfig;
import com.breelock.autoclicky.Utils;
import com.breelock.autoclicky.widgets.TooltipCheckboxWidget;
import com.breelock.autoclicky.widgets.TooltipSliderWidget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import org.lwjgl.glfw.GLFW;

public class OldCombat extends Screen {
    protected TooltipSliderWidget leftMinDelaySlider;
    protected TooltipSliderWidget leftMaxDelaySlider;
    protected TooltipSliderWidget rightMinDelaySlider;
    protected TooltipSliderWidget rightMaxDelaySlider;
    protected TooltipCheckboxWidget interruptCheckbox;
    protected TooltipCheckboxWidget showMessageCheckbox;
    protected TooltipCheckboxWidget autoJumpCheckbox;
    protected TooltipCheckboxWidget onlyEntityCheckbox;
    protected final MinecraftClient client;

    public OldCombat() {
        super(new LiteralText("AutoClicky"));
        this.client = MinecraftClient.getInstance();
    }

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
        addDrawableChild(new ButtonWidget(this.width / 2 - 50, startY - spacing + 7, 100, 20, new LiteralText(new TranslatableText(ModConfig.pvpSystems.get(0)).getString()), button -> {
            save(false);
            ModConfig.selectedPvp = (ModConfig.PvP.values()[(ModConfig.selectedPvp.ordinal() + 1) % ModConfig.pvpSystems.size()]);
            this.client.setScreen(new NewCombat());
        }));

        // Left click - minimum click delay slider
        leftMinDelaySlider = new TooltipSliderWidget(centerX - widgetWidth - columnSpacing / 2, startY + spacing, widgetWidth, 20, new LiteralText(new TranslatableText("gui.autoclicky.minDelay").getString() + ": " +  ModConfig.OldPvP.leftMinDelay), ModConfig.OldPvP.leftMinDelay / 100.0, new TranslatableText("gui.autoclicky.delayTooltip").getString()) {
            @Override
            protected void updateMessage() {
                this.setMessage(new LiteralText(new TranslatableText("gui.autoclicky.minDelay").getString() + ": " +  (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                ModConfig.OldPvP.leftMinDelay = (int)(this.value * 100);
            }
        };
        addDrawableChild(leftMinDelaySlider);

        // Left click - maximum click delay slider
        leftMaxDelaySlider = new TooltipSliderWidget(centerX - widgetWidth - columnSpacing / 2, startY + 2 * spacing, widgetWidth, 20, new LiteralText(new TranslatableText("gui.autoclicky.maxDelay").getString() + ": " +  ModConfig.OldPvP.leftMaxDelay), ModConfig.OldPvP.leftMaxDelay / 100.0, new TranslatableText("gui.autoclicky.delayTooltip").getString()) {
            @Override
            protected void updateMessage() {
                this.setMessage(new LiteralText(new TranslatableText("gui.autoclicky.maxDelay").getString() + ": " +  (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                ModConfig.OldPvP.leftMaxDelay = (int)(this.value * 100);
            }
        };
        addDrawableChild(leftMaxDelaySlider);

        // Right click - minimum click delay slider
        rightMinDelaySlider = new TooltipSliderWidget(centerX + columnSpacing / 2, startY + spacing, widgetWidth, 20, new LiteralText(new TranslatableText("gui.autoclicky.minDelay").getString() + ": " +  ModConfig.OldPvP.rightMinDelay), ModConfig.OldPvP.rightMinDelay / 100.0, new TranslatableText("gui.autoclicky.delayTooltip").getString()) {
            @Override
            protected void updateMessage() {
                this.setMessage(new LiteralText(new TranslatableText("gui.autoclicky.minDelay").getString() + ": " +  (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                ModConfig.OldPvP.rightMinDelay = (int)(this.value * 100);
            }
        };
        addDrawableChild(rightMinDelaySlider);

        // Right click - maximum click delay slider
        rightMaxDelaySlider = new TooltipSliderWidget(centerX + columnSpacing / 2, startY + 2 * spacing, widgetWidth, 20, new LiteralText(new TranslatableText("gui.autoclicky.maxDelay").getString() + ": " +  ModConfig.OldPvP.rightMaxDelay), ModConfig.OldPvP.rightMaxDelay / 100.0, new TranslatableText("gui.autoclicky.delayTooltip").getString()) {
            @Override
            protected void updateMessage() {
                this.setMessage(new LiteralText(new TranslatableText("gui.autoclicky.maxDelay").getString() + ": " +  (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                ModConfig.OldPvP.rightMaxDelay = (int)(this.value * 100);
            }
        };
        addDrawableChild(rightMaxDelaySlider);

        // Interrupt the item use when left click
        interruptCheckbox = new TooltipCheckboxWidget(centerX + columnSpacing / 2, startY + 3 * spacing, checkboxWidth, 20, new LiteralText(new TranslatableText("gui.autoclicky.interrupt").getString()), ModConfig.OldPvP.interrupt, new TranslatableText("gui.autoclicky.interrupt.tooltip").getString());
        addDrawableChild(interruptCheckbox);

        // Show message checkbox
        showMessageCheckbox = new TooltipCheckboxWidget(centerX - checkboxWidth - columnSpacing / 2, startY + 3 * spacing, checkboxWidth, 20, new LiteralText(new TranslatableText("gui.autoclicky.messages").getString()), ModConfig.OldPvP.showMessage, new TranslatableText("gui.autoclicky.messages.tooltip").getString());
        addDrawableChild(showMessageCheckbox);

        // Auto jump checkbox
        autoJumpCheckbox = new TooltipCheckboxWidget(centerX - checkboxWidth - columnSpacing / 2, startY + 4 * spacing, checkboxWidth, 20, new LiteralText(new TranslatableText("gui.autoclicky.autoJump").getString()), ModConfig.OldPvP.autoJump, new TranslatableText("gui.autoclicky.autoJump.tooltip").getString());
        addDrawableChild(autoJumpCheckbox);

        // Attack only when targeting an entity checkbox
        onlyEntityCheckbox = new TooltipCheckboxWidget(centerX + columnSpacing / 2, startY + 4 * spacing, checkboxWidth, 20, new LiteralText(new TranslatableText("gui.autoclicky.onlyEntity").getString()), ModConfig.OldPvP.onlyEntity, new TranslatableText("gui.autoclicky.onlyEntity.tooltip").getString());
        addDrawableChild(onlyEntityCheckbox);

        // Save button
        addDrawableChild(new ButtonWidget(centerX + 5, startY + 5 * spacing, buttonWidth, 20, new LiteralText(new TranslatableText("gui.autoclicky.save").getString()), button -> {
            save(false);
            this.client.setScreen(null); // Close the screen
        }));

        // Exit without saving button
        addDrawableChild(new ButtonWidget(centerX - buttonWidth - 5, startY + 5 * spacing, buttonWidth, 20, new LiteralText(new TranslatableText("gui.autoclicky.cancel").getString()), button -> {
            this.client.setScreen(null); // Close the screen
        }));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);

        // Draw title
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, this.height / 4 - 50, 16777215);
        drawCenteredText(matrices, this.textRenderer, new LiteralText("by breelock").formatted(Formatting.GRAY), this.width / 2, this.height / 4 - 50 + 10, 16777215);

        // Draw labels
        this.textRenderer.draw(matrices, new LiteralText(new TranslatableText("gui.autoclicky.leftBind").getString()), (float) this.width / 2 - 95 - 10f / 2 + 2, (float) this.height / 4 + 10, 16777215);
        this.textRenderer.draw(matrices, new LiteralText(new TranslatableText("gui.autoclicky.rightBind").getString()), (float) this.width / 2 + 10f / 2 + 2, (float) this.height / 4 + 10, 16777215);

        // Draw sliders
        leftMinDelaySlider.render(matrices, mouseX, mouseY, delta);
        leftMaxDelaySlider.render(matrices, mouseX, mouseY, delta);
        rightMinDelaySlider.render(matrices, mouseX, mouseY, delta);
        rightMaxDelaySlider.render(matrices, mouseX, mouseY, delta);

        // Draw checkboxes
        interruptCheckbox.render(matrices, mouseX, mouseY, delta);
        showMessageCheckbox.render(matrices, mouseX, mouseY, delta);
        autoJumpCheckbox.render(matrices, mouseX, mouseY, delta);
        onlyEntityCheckbox.render(matrices, mouseX, mouseY, delta);

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) { // Left-click
            int titleWidth = this.textRenderer.getWidth(this.title);
            int titleX = (this.width / 2) - (titleWidth / 2);
            int titleY = this.height / 4 - 50;

            int authorWidth = this.textRenderer.getWidth(new LiteralText("by breelock").formatted(Formatting.GRAY));
            int authorX = (this.width / 2) - (authorWidth / 2);
            int authorY = this.height / 4 - 50 + 10;

            if (mouseX >= titleX && mouseX <= titleX + titleWidth && mouseY >= titleY && mouseY <= titleY + 10 ||
                    mouseX >= authorX && mouseX <= authorX + authorWidth && mouseY >= authorY && mouseY <= authorY + 10 ) {
                Utils.openLink("https://github.com/breelock/AutoClicky");
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void close() {
        this.client.keyboard.setRepeatEvents(false);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.client.setScreen(null); // Close the screen on ESC key press
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void save(boolean isNewPvP) {
        if (isNewPvP) {
            ModConfig.NewPvP.interrupt = interruptCheckbox.isChecked();
            ModConfig.NewPvP.showMessage = showMessageCheckbox.isChecked();
            ModConfig.NewPvP.autoJump = autoJumpCheckbox.isChecked();
            ModConfig.NewPvP.onlyEntity = onlyEntityCheckbox.isChecked();
        } else {
            ModConfig.OldPvP.interrupt = interruptCheckbox.isChecked();
            ModConfig.OldPvP.showMessage = showMessageCheckbox.isChecked();
            ModConfig.OldPvP.autoJump = autoJumpCheckbox.isChecked();
            ModConfig.OldPvP.onlyEntity = onlyEntityCheckbox.isChecked();
        }

        ModConfig.save();
    }
}
