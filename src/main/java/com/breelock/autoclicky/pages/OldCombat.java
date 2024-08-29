package com.breelock.autoclicky.pages;

import com.breelock.autoclicky.ModConfig;
import com.breelock.autoclicky.Utils;
import com.breelock.autoclicky.widgets.TooltipSliderWidget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import org.lwjgl.glfw.GLFW;

public class OldCombat extends Screen {
    protected TooltipSliderWidget leftMinDelaySlider;
    protected TooltipSliderWidget leftMaxDelaySlider;
    protected TooltipSliderWidget rightMinDelaySlider;
    protected TooltipSliderWidget rightMaxDelaySlider;
    protected CheckboxWidget interruptCheckbox;
    protected CheckboxWidget showMessageCheckbox;
    protected CheckboxWidget autoJumpCheckbox;
    protected CheckboxWidget onlyEntityCheckbox;
    protected final MinecraftClient client;

    public OldCombat() {
        super(Text.literal("AutoClicky"));
        this.client = MinecraftClient.getInstance();
    }

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
        addDrawableChild(ButtonWidget.builder(Text.literal(Text.translatable(ModConfig.pvpSystems.get(0)).getString()), (button) -> {
            save(false);
            ModConfig.selectedPvp = (ModConfig.PvP.values()[(ModConfig.selectedPvp.ordinal() + 1) % ModConfig.pvpSystems.size()]);
            this.client.setScreen(new NewCombat());
        }).dimensions(this.width / 2 - 50, startY - spacing + 7, 100, 20).build());

        // Left click - minimum click delay slider
        leftMinDelaySlider = new TooltipSliderWidget(centerX - widgetWidth - columnSpacing / 2, startY + spacing, widgetWidth, 20, Text.literal(Text.translatable("gui.autoclicky.minDelay").getString() + ": " +  ModConfig.OldPvP.leftMinDelay), ModConfig.OldPvP.leftMinDelay / 100.0, Text.translatable("gui.autoclicky.delayTooltip").getString()) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal(Text.translatable("gui.autoclicky.minDelay").getString() + ": " +  (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                ModConfig.OldPvP.leftMinDelay = (int)(this.value * 100);
            }
        };
        addDrawableChild(leftMinDelaySlider);

        // Left click - maximum click delay slider
        leftMaxDelaySlider = new TooltipSliderWidget(centerX - widgetWidth - columnSpacing / 2, startY + 2 * spacing, widgetWidth, 20, Text.literal(Text.translatable("gui.autoclicky.maxDelay").getString() + ": " +  ModConfig.OldPvP.leftMaxDelay), ModConfig.OldPvP.leftMaxDelay / 100.0, Text.translatable("gui.autoclicky.delayTooltip").getString()) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal(Text.translatable("gui.autoclicky.maxDelay").getString() + ": " +  (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                ModConfig.OldPvP.leftMaxDelay = (int)(this.value * 100);
            }
        };
        addDrawableChild(leftMaxDelaySlider);

        // Right click - minimum click delay slider
        rightMinDelaySlider = new TooltipSliderWidget(centerX + columnSpacing / 2, startY + spacing, widgetWidth, 20, Text.literal(Text.translatable("gui.autoclicky.minDelay").getString() + ": " +  ModConfig.OldPvP.rightMinDelay), ModConfig.OldPvP.rightMinDelay / 100.0, Text.translatable("gui.autoclicky.delayTooltip").getString()) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal(Text.translatable("gui.autoclicky.minDelay").getString() + ": " +  (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                ModConfig.OldPvP.rightMinDelay = (int)(this.value * 100);
            }
        };
        addDrawableChild(rightMinDelaySlider);

        // Right click - maximum click delay slider
        rightMaxDelaySlider = new TooltipSliderWidget(centerX + columnSpacing / 2, startY + 2 * spacing, widgetWidth, 20, Text.literal(Text.translatable("gui.autoclicky.maxDelay").getString() + ": " +  ModConfig.OldPvP.rightMaxDelay), ModConfig.OldPvP.rightMaxDelay / 100.0, Text.translatable("gui.autoclicky.delayTooltip").getString()) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal(Text.translatable("gui.autoclicky.maxDelay").getString() + ": " +  (int)(this.value * 100)));
            }

            @Override
            protected void applyValue() {
                ModConfig.OldPvP.rightMaxDelay = (int)(this.value * 100);
            }
        };
        addDrawableChild(rightMaxDelaySlider);

        // Interrupt the item use when left click
        interruptCheckbox = CheckboxWidget.builder(Text.translatable("gui.autoclicky.interrupt"), this.textRenderer).pos(centerX + columnSpacing / 2, startY + 3 * spacing).tooltip(Tooltip.of(Text.translatable("gui.autoclicky.interrupt.tooltip"))).checked(ModConfig.OldPvP.interrupt).build();
        addDrawableChild(interruptCheckbox);

        // Show message checkbox
        showMessageCheckbox = CheckboxWidget.builder(Text.translatable("gui.autoclicky.messages"), this.textRenderer).pos(centerX - checkboxWidth - columnSpacing / 2, startY + 3 * spacing).tooltip(Tooltip.of(Text.translatable("gui.autoclicky.messages.tooltip"))).checked(ModConfig.OldPvP.showMessage).build();
        addDrawableChild(showMessageCheckbox);

        // Auto jump checkbox
        autoJumpCheckbox = CheckboxWidget.builder(Text.translatable("gui.autoclicky.autoJump"), this.textRenderer).pos(centerX - checkboxWidth - columnSpacing / 2, startY + 4 * spacing).tooltip(Tooltip.of(Text.translatable("gui.autoclicky.autoJump.tooltip"))).checked(ModConfig.OldPvP.autoJump).build();
        addDrawableChild(autoJumpCheckbox);

        // Attack only when targeting an entity checkbox
        onlyEntityCheckbox = CheckboxWidget.builder(Text.translatable("gui.autoclicky.onlyEntity"), this.textRenderer).pos(centerX + columnSpacing / 2, startY + 4 * spacing).tooltip(Tooltip.of(Text.translatable("gui.autoclicky.onlyEntity.tooltip"))).checked(ModConfig.OldPvP.onlyEntity).build();
        addDrawableChild(onlyEntityCheckbox);

        // Save button
        addDrawableChild(ButtonWidget.builder(Text.literal(Text.translatable("gui.autoclicky.save").getString()), (button) -> {
            save(false);
            this.client.setScreen(null); // Close the screen
        }).dimensions(centerX + 5, startY + 5 * spacing, buttonWidth, 20).build());

        // Exit without saving button
        addDrawableChild(ButtonWidget.builder(Text.literal(Text.translatable("gui.autoclicky.cancel").getString()), (button) -> {
            this.client.setScreen(null); // Close the screen
        }).dimensions(centerX - buttonWidth - 5, startY + 5 * spacing, buttonWidth, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        // Draw title
        context.drawText(this.textRenderer, this.title, (this.width - this.textRenderer.getWidth(this.title)) / 2, this.height / 4 - 50, 16777215, true);
        context.drawText(this.textRenderer, Text.literal("by breelock").formatted(Formatting.GRAY), (this.width - this.textRenderer.getWidth("by breelock")) / 2, this.height / 4 - 50 + 10, 16777215, true);

        // Draw labels
        context.drawText(this.textRenderer, Text.translatable("gui.autoclicky.leftBind"), Math.round((float) this.width / 2 - 95 - 10f / 2 + 2), this.height / 4 + 10, 16777215, true);
        context.drawText(this.textRenderer, Text.translatable("gui.autoclicky.rightBind"), Math.round((float) this.width / 2 + 10f / 2 + 2), this.height / 4 + 10, 16777215, true);

        // Draw sliders
        leftMinDelaySlider.render(context, mouseX, mouseY, delta);
        leftMaxDelaySlider.render(context, mouseX, mouseY, delta);
        rightMinDelaySlider.render(context, mouseX, mouseY, delta);
        rightMaxDelaySlider.render(context, mouseX, mouseY, delta);

        // Draw checkboxes
        interruptCheckbox.render(context, mouseX, mouseY, delta);
        showMessageCheckbox.render(context, mouseX, mouseY, delta);
        autoJumpCheckbox.render(context, mouseX, mouseY, delta);
        onlyEntityCheckbox.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) { // Left-click
            int titleWidth = this.textRenderer.getWidth(this.title);
            int titleX = (this.width / 2) - (titleWidth / 2);
            int titleY = this.height / 4 - 50;

            int authorWidth = this.textRenderer.getWidth(Text.literal("by breelock").formatted(Formatting.GRAY));
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

    /*
    @Override
    public void close() {
        this.client.keyboard.setRepeatEvents(false);
    }
    */

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
