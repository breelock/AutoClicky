package com.breelock.autoclicky;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

public class ConfigScreen extends Screen {
    private CheckboxWidget leftEnableCheckbox;
    private CheckboxWidget rightEnableCheckbox;
    private SliderWidget leftMinDelaySlider;
    private SliderWidget leftMaxDelaySlider;
    private SliderWidget rightMinDelaySlider;
    private SliderWidget rightMaxDelaySlider;
    private CheckboxWidget showMessageCheckbox;
    private CheckboxWidget firstClickIsInstantCheckbox;
    private final MinecraftClient client;

    protected ConfigScreen() {
        super(new LiteralText("AutoClicky"));
        this.client = MinecraftClient.getInstance();
    }

    @Override
    protected void init() {
        this.client.keyboard.setRepeatEvents(true);

        int centerX = this.width / 2;
        int startY = this.height / 4;
        int spacing = 30;
        int columnSpacing = 10;
        int widgetWidth = 95;
        int buttonWidth = 95;
        int checkboxWidth = 95;

        // Left click - enable checkbox
        leftEnableCheckbox = new CheckboxWidget(centerX - checkboxWidth - columnSpacing / 2, startY + spacing, 60, 20, new LiteralText("Enable"), AutoClicky.leftEnabled);
        this.children.add(leftEnableCheckbox);

        // Right click - enable checkbox
        rightEnableCheckbox = new CheckboxWidget(centerX + columnSpacing / 2, startY + spacing, 60, 20, new LiteralText("Enable"), AutoClicky.rightEnabled);
        this.children.add(rightEnableCheckbox);

        // Left click - minimum click delay slider
        leftMinDelaySlider = new SliderWidget(centerX - widgetWidth - columnSpacing / 2, startY + 2 * spacing, widgetWidth, 20, new LiteralText("Min Delay: " + AutoClicky.leftMinDelay), AutoClicky.leftMinDelay / 100.0) {
            @Override
            protected void updateMessage() {
                this.setMessage(new LiteralText("Min Delay: " + (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                AutoClicky.leftMinDelay = (int)(this.value * 100);
            }
        };
        this.children.add(leftMinDelaySlider);

        // Left click - maximum click delay slider
        leftMaxDelaySlider = new SliderWidget(centerX - widgetWidth - columnSpacing / 2, startY + 3 * spacing, widgetWidth, 20, new LiteralText("Max Delay: " + AutoClicky.leftMaxDelay), AutoClicky.leftMaxDelay / 100.0) {
            @Override
            protected void updateMessage() {
                this.setMessage(new LiteralText("Max Delay: " + (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                AutoClicky.leftMaxDelay = (int)(this.value * 100);
            }
        };
        this.children.add(leftMaxDelaySlider);

        // Right click - minimum click delay slider
        rightMinDelaySlider = new SliderWidget(centerX + columnSpacing / 2, startY + 2 * spacing, widgetWidth, 20, new LiteralText("Min Delay: " + AutoClicky.rightMinDelay), AutoClicky.rightMinDelay / 100.0) {
            @Override
            protected void updateMessage() {
                this.setMessage(new LiteralText("Min Delay: " + (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                AutoClicky.rightMinDelay = (int)(this.value * 100);
            }
        };
        this.children.add(rightMinDelaySlider);

        // Right click - maximum click delay slider
        rightMaxDelaySlider = new SliderWidget(centerX + columnSpacing / 2, startY + 3 * spacing, widgetWidth, 20, new LiteralText("Max Delay: " + AutoClicky.rightMaxDelay), AutoClicky.rightMaxDelay / 100.0) {
            @Override
            protected void updateMessage() {
                this.setMessage(new LiteralText("Max Delay: " + (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                AutoClicky.rightMaxDelay = (int)(this.value * 100);
            }
        };
        this.children.add(rightMaxDelaySlider);

        // Show message checkbox
        showMessageCheckbox = new CheckboxWidget(centerX - checkboxWidth - columnSpacing / 2, startY + 4 * spacing, 73, 20, new LiteralText("Messages"), AutoClicky.showMessage);
        this.children.add(showMessageCheckbox);

        // First click is instant checkbox
        firstClickIsInstantCheckbox = new CheckboxWidget(centerX + columnSpacing / 2, startY + 4 * spacing, 85, 20, new LiteralText("First instant"), AutoClicky.firstClickIsInstant);
        this.children.add(firstClickIsInstantCheckbox);

        // Save button
        this.addButton(new ButtonWidget(centerX + 5, startY + 5 * spacing, buttonWidth, 20, new LiteralText("Save"), button -> {
            AutoClicky.leftEnabled = leftEnableCheckbox.isChecked();
            AutoClicky.rightEnabled = rightEnableCheckbox.isChecked();
            AutoClicky.showMessage = showMessageCheckbox.isChecked();
            AutoClicky.firstClickIsInstant = firstClickIsInstantCheckbox.isChecked();
            ModConfig.saveConfig();
            this.client.openScreen(null); // Close the screen
        }));

        // Exit without saving button
        this.addButton(new ButtonWidget(centerX - buttonWidth - 5, startY + 5 * spacing, buttonWidth, 20, new LiteralText("Cancel"), button -> {
            this.client.openScreen(null); // Close the screen without saving
        }));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);

        // Draw title
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, this.height / 4 - 40, 16777215);
        drawCenteredText(matrices, this.textRenderer, new LiteralText("by breelock").formatted(Formatting.GRAY), this.width / 2, this.height / 4 - 40 + 10, 16777215);

        // Draw Labels
        this.textRenderer.draw(matrices, new LiteralText("Left click"), this.width / 2 - 95 - 10 / 2 + 2, this.height / 4 + 10, 16777215);
        this.textRenderer.draw(matrices, new LiteralText("Right click"), this.width / 2 + 10 / 2 + 2, this.height / 4 + 10, 16777215);

        // Draw sliders
        leftMinDelaySlider.render(matrices, mouseX, mouseY, delta);
        leftMaxDelaySlider.render(matrices, mouseX, mouseY, delta);
        rightMinDelaySlider.render(matrices, mouseX, mouseY, delta);
        rightMaxDelaySlider.render(matrices, mouseX, mouseY, delta);

        // Draw checkboxes
        leftEnableCheckbox.render(matrices, mouseX, mouseY, delta);
        rightEnableCheckbox.render(matrices, mouseX, mouseY, delta);
        showMessageCheckbox.render(matrices, mouseX, mouseY, delta);
        firstClickIsInstantCheckbox.render(matrices, mouseX, mouseY, delta);

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        this.client.keyboard.setRepeatEvents(false);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.client.openScreen(null); // Close the screen on ESC key press
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
