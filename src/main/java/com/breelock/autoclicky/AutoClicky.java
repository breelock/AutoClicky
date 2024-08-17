package com.breelock.autoclicky;

import com.breelock.autoclicky.pages.NewCombat;
import com.breelock.autoclicky.pages.OldCombat;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;

import org.lwjgl.glfw.GLFW;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AutoClicky implements ModInitializer {
	public static final String MOD_ID = "autoclicky";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	private static KeyBinding leftBind;
	private static KeyBinding rightBind;
	private static KeyBinding settingsBind;
	private static KeyBinding switchPvPSystemBind;

	private static boolean leftIsNowEnabled = false;
	private static boolean rightIsNowEnabled = false;
	private static int currentDelay = 0;

	@Override
	public void onInitialize() {
		// Load configuration
		ModConfig.load();

		// Register key binds
		leftBind = KeyBindingHelper.registerKeyBinding(new KeyBinding("Left click", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "AutoClicky"));
		rightBind = KeyBindingHelper.registerKeyBinding(new KeyBinding("Right click", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "AutoClicky"));
		settingsBind = KeyBindingHelper.registerKeyBinding(new KeyBinding("Settings", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_N, "AutoClicky"));
		switchPvPSystemBind = KeyBindingHelper.registerKeyBinding(new KeyBinding("Switch PvP system", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "AutoClicky"));

		// Called every tick
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			boolean isNewPvp = ModConfig.selectedPvp == ModConfig.PvP.New;

			if (client.player != null) {
				// Left mouse button activation
				if (leftBind.wasPressed()) {
					autoclickyActivation(client, true, isNewPvp);
                }

				// Right mouse button activation
                else if (rightBind.wasPressed()) {
					autoclickyActivation(client, false, isNewPvp);
                }

				// Open config screen
				else if (settingsBind.wasPressed())
					client.setScreen(isNewPvp ? new NewCombat() : new OldCombat());

				// Switch PvP system to new
				else if (switchPvPSystemBind.wasPressed()) {
					ModConfig.selectedPvp = isNewPvp ? ModConfig.PvP.Old : ModConfig.PvP.New;
					isNewPvp = ModConfig.selectedPvp == ModConfig.PvP.New;

					if (isNewPvp && ModConfig.NewPvP.showMessage || !isNewPvp && ModConfig.OldPvP.showMessage)
						client.player.sendMessage(new LiteralText(String.format("AutoClicky PvP switched to %s", isNewPvp ? "new" : "old")), true);

					ModConfig.save();
				}

				// Left mouse button click
                if (leftIsNowEnabled) {
                    if (client.player != null && client.currentScreen == null) {
						if (currentDelay <= 0) {
							PlayerMethods.attack(client, isNewPvp);
							if (isNewPvp)
								currentDelay = Utils.randint(ModConfig.NewPvP.leftMinDelay, ModConfig.NewPvP.leftMaxDelay);
							else
								currentDelay = Utils.randint(ModConfig.OldPvP.leftMinDelay, ModConfig.OldPvP.leftMaxDelay);
						}
						else
							currentDelay--;
                    }
                }

				// Right mouse button click
                else if (rightIsNowEnabled) {
                    if (client.player != null && client.currentScreen == null) {
                        if (currentDelay <= 0) {
                            PlayerMethods.interact(client);
                            if (isNewPvp)
                                currentDelay = Utils.randint(ModConfig.NewPvP.rightMinDelay, ModConfig.NewPvP.rightMaxDelay);
                            else
                                currentDelay = Utils.randint(ModConfig.OldPvP.rightMinDelay, ModConfig.OldPvP.rightMaxDelay);
                        }
                        else
                            currentDelay--;
                    }
                }
			}
		});
	}

	private void autoclickyActivation(MinecraftClient client, boolean isLeft, boolean isNewPvP) {
		if (client != null && client.player != null) {
			boolean btnIsNowEnabled;

			if (isLeft) { // left mouse button
				leftIsNowEnabled = !leftIsNowEnabled;
				rightIsNowEnabled = false;
				btnIsNowEnabled = leftIsNowEnabled;
			}

			else { // right mouse button
				rightIsNowEnabled = !rightIsNowEnabled;
				leftIsNowEnabled = false;
				btnIsNowEnabled = rightIsNowEnabled;
			}

			if (isNewPvP && !ModConfig.NewPvP.firstClickIsInstant && btnIsNowEnabled)
				currentDelay = Utils.randint(ModConfig.NewPvP.leftMinDelay, ModConfig.NewPvP.leftMaxDelay);
            else if (!isNewPvP && !ModConfig.OldPvP.firstClickIsInstant && btnIsNowEnabled)
                currentDelay = Utils.randint(ModConfig.OldPvP.leftMinDelay, ModConfig.OldPvP.leftMaxDelay);
			else
				currentDelay = 0;

			if (isNewPvP && ModConfig.NewPvP.showMessage || !isNewPvP && ModConfig.OldPvP.showMessage)
				client.player.sendMessage(new LiteralText(String.format("[%s mouse button] AutoClicky %sactivated%s", isLeft ? "Left" : "Right", btnIsNowEnabled ? "" : "de", isLeft ? isNewPvP ? " [New PvP]" : " [Old PvP]" : "")), true);
		}
	}
}
