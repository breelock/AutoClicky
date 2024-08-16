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
import net.minecraft.util.Formatting;

import org.lwjgl.glfw.GLFW;

public class OldCombat extends Screen {
    protected TooltipSliderWidget leftMinDelaySlider;
    protected TooltipSliderWidget leftMaxDelaySlider;
    protected TooltipSliderWidget rightMinDelaySlider;
    protected TooltipSliderWidget rightMaxDelaySlider;
    protected TooltipCheckboxWidget firstClickIsInstantCheckbox;
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
        this.addButton(new ButtonWidget(this.width / 2 - 50, startY - spacing + 7, 100, 20, new LiteralText(ModConfig.pvpSystems.get(0)), button -> {
            save(false);
            ModConfig.selectedPvp = (ModConfig.PvP.values()[(ModConfig.selectedPvp.ordinal() + 1) % ModConfig.pvpSystems.size()]);
            this.client.openScreen(new NewCombat());
        }));

        // Left click - minimum click delay slider
        leftMinDelaySlider = new TooltipSliderWidget(centerX - widgetWidth - columnSpacing / 2, startY + spacing, widgetWidth, 20, new LiteralText("Min Delay: " + ModConfig.OldPvP.leftMinDelay), ModConfig.OldPvP.leftMinDelay / 100.0, "Delay between clicks in ticks (1 tick = 50 ms)") {
            @Override
            protected void updateMessage() {
                this.setMessage(new LiteralText("Min Delay: " + (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                ModConfig.OldPvP.leftMinDelay = (int)(this.value * 100);
            }
        };
        this.children.add(leftMinDelaySlider);

        // Left click - maximum click delay slider
        leftMaxDelaySlider = new TooltipSliderWidget(centerX - widgetWidth - columnSpacing / 2, startY + 2 * spacing, widgetWidth, 20, new LiteralText("Max Delay: " + ModConfig.OldPvP.leftMaxDelay), ModConfig.OldPvP.leftMaxDelay / 100.0, "Delay between clicks in ticks (1 tick = 50 ms)") {
            @Override
            protected void updateMessage() {
                this.setMessage(new LiteralText("Max Delay: " + (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                ModConfig.OldPvP.leftMaxDelay = (int)(this.value * 100);
            }
        };
        this.children.add(leftMaxDelaySlider);

        // Right click - minimum click delay slider
        rightMinDelaySlider = new TooltipSliderWidget(centerX + columnSpacing / 2, startY + spacing, widgetWidth, 20, new LiteralText("Min Delay: " + ModConfig.OldPvP.rightMinDelay), ModConfig.OldPvP.rightMinDelay / 100.0, "Delay between clicks in ticks (1 tick = 50 ms)") {
            @Override
            protected void updateMessage() {
                this.setMessage(new LiteralText("Min Delay: " + (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                ModConfig.OldPvP.rightMinDelay = (int)(this.value * 100);
            }
        };
        this.children.add(rightMinDelaySlider);

        // Right click - maximum click delay slider
        rightMaxDelaySlider = new TooltipSliderWidget(centerX + columnSpacing / 2, startY + 2 * spacing, widgetWidth, 20, new LiteralText("Max Delay: " + ModConfig.OldPvP.rightMaxDelay), ModConfig.OldPvP.rightMaxDelay / 100.0, "Delay between clicks in ticks (1 tick = 50 ms)") {
            @Override
            protected void updateMessage() {
                this.setMessage(new LiteralText("Max Delay: " + (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                ModConfig.OldPvP.rightMaxDelay = (int)(this.value * 100);
            }
        };
        this.children.add(rightMaxDelaySlider);

        // First click is instant checkbox
        firstClickIsInstantCheckbox = new TooltipCheckboxWidget(centerX + columnSpacing / 2, startY + 3 * spacing, 85, 20, new LiteralText("First instant"), ModConfig.OldPvP.firstClickIsInstant, "The first click is instant, will not wait for a delay");
        this.children.add(firstClickIsInstantCheckbox);

        // Show message checkbox
        showMessageCheckbox = new TooltipCheckboxWidget(centerX - checkboxWidth - columnSpacing / 2, startY + 3 * spacing, 73, 20, new LiteralText("Messages"), ModConfig.OldPvP.showMessage, "Show autoclicky messages in action bar");
        this.children.add(showMessageCheckbox);

        // Auto jump checkbox
        autoJumpCheckbox = new TooltipCheckboxWidget(centerX - checkboxWidth - columnSpacing / 2, startY + 4 * spacing, 74, 20, new LiteralText("Auto jump"), ModConfig.OldPvP.autoJump, "Autoclicky will jump when targeting an entity");
        this.children.add(autoJumpCheckbox);

        // Attack only when targeting an entity checkbox
        onlyEntityCheckbox = new TooltipCheckboxWidget(centerX + columnSpacing / 2, startY + 4 * spacing, 78, 20, new LiteralText("Only entity"), ModConfig.OldPvP.onlyEntity, "Autoclicky will only attack when targeting an entity");
        this.children.add(onlyEntityCheckbox);

        // Save button
        this.addButton(new ButtonWidget(centerX + 5, startY + 5 * spacing, buttonWidth, 20, new LiteralText("Save"), button -> {
            save(false);
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
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, this.height / 4 - 50, 16777215);
        drawCenteredText(matrices, this.textRenderer, new LiteralText("by breelock").formatted(Formatting.GRAY), this.width / 2, this.height / 4 - 50 + 10, 16777215);

        // Draw labels
        this.textRenderer.draw(matrices, new LiteralText("Left click"), (float) this.width / 2 - 95 - 10f / 2 + 2, (float) this.height / 4 + 10, 16777215);
        this.textRenderer.draw(matrices, new LiteralText("Right click"), (float) this.width / 2 + 10f / 2 + 2, (float) this.height / 4 + 10, 16777215);

        // Draw sliders
        leftMinDelaySlider.render(matrices, mouseX, mouseY, delta);
        leftMaxDelaySlider.render(matrices, mouseX, mouseY, delta);
        rightMinDelaySlider.render(matrices, mouseX, mouseY, delta);
        rightMaxDelaySlider.render(matrices, mouseX, mouseY, delta);

        // Draw checkboxes
        firstClickIsInstantCheckbox.render(matrices, mouseX, mouseY, delta);
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

    public void save(boolean isNewPvP) {
        if (isNewPvP) {
            ModConfig.NewPvP.firstClickIsInstant = firstClickIsInstantCheckbox.isChecked();
            ModConfig.NewPvP.showMessage = showMessageCheckbox.isChecked();
            ModConfig.NewPvP.autoJump = autoJumpCheckbox.isChecked();
            ModConfig.NewPvP.onlyEntity = onlyEntityCheckbox.isChecked();
        } else {
            ModConfig.OldPvP.firstClickIsInstant = firstClickIsInstantCheckbox.isChecked();
            ModConfig.OldPvP.showMessage = showMessageCheckbox.isChecked();
            ModConfig.OldPvP.autoJump = autoJumpCheckbox.isChecked();
            ModConfig.OldPvP.onlyEntity = onlyEntityCheckbox.isChecked();
        }

        ModConfig.save();
    }
}
