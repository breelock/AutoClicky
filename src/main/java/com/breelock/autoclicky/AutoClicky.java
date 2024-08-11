package com.breelock.autoclicky;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

import org.lwjgl.glfw.GLFW;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AutoClicky implements ModInitializer {
	public static final String MOD_ID = "autoclicky";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	private static KeyBinding leftKeyBinding;
	private static KeyBinding rightKeyBinding;
	private static KeyBinding configScreenKeyBinding;

	private static boolean leftIsNowEnabled = false;
	private static boolean rightIsNowEnabled = false;
	private static int currentDelay = 0;

	// Global settings
	public static boolean leftEnabled = true;
	public static int leftMinDelay = 0;
	public static int leftMaxDelay = 1;

	public static boolean rightEnabled = true;
	public static int rightMinDelay = 0;
	public static int rightMaxDelay = 1;

	public static boolean firstClickIsInstant = true;
	public static boolean showMessage = true;

	private static final Random random = new Random();

	@Override
	public void onInitialize() {
		// Load configuration
		ModConfig.loadConfig();

		// Register key binds
		leftKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"Left click",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_R,
				"AutoClicky"
		));

		rightKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"Right click",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_V,
				"AutoClicky"
		));

		configScreenKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"Settings",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_N,
				"AutoClicky"
		));

		// Called every tick
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			// Left mosue button clicker
			if (leftEnabled && leftKeyBinding.wasPressed()) {
				leftIsNowEnabled = !leftIsNowEnabled;
				rightIsNowEnabled = false;
				if (leftIsNowEnabled) {
					if (firstClickIsInstant == false) {
						currentDelay = RandomDelay(leftMinDelay, leftMaxDelay);
					}
					if (showMessage) {
						client.player.sendMessage(new LiteralText("[Left mouse button] AutoClicky activated"), true);
					}
				} else {
					if (showMessage) {
						client.player.sendMessage(new LiteralText("[Left mouse button] AutoClicky deactivated"), true);
					}
				}
			}

			// Right mouse button clicker
			else if (rightEnabled && rightKeyBinding.wasPressed()) {
				rightIsNowEnabled = !rightIsNowEnabled;
				leftIsNowEnabled = false;
				if (rightIsNowEnabled) {
					if (firstClickIsInstant == false) {
						currentDelay = RandomDelay(rightMinDelay, rightMaxDelay);
					}
					if (showMessage) {
						client.player.sendMessage(new LiteralText("[Right mouse button] AutoClicky activated"), true);
					}
				} else {
					if (showMessage) {
						client.player.sendMessage(new LiteralText("[Right mouse button] AutoClicky deactivated"), true);
					}
				}
			}

			// Open config screen
			else if (configScreenKeyBinding.wasPressed()) {
				client.openScreen(new ConfigScreen());
			}

			// Left mouse button clicking
			if (leftEnabled && leftIsNowEnabled && client.player != null && client.currentScreen == null) {
				if (currentDelay <= 0) {
					LeftClick(client);
					currentDelay = RandomDelay(leftMinDelay, leftMaxDelay);
				} else {
					currentDelay--;
				}
			}

			// Right mouse button clicking
			else if (rightEnabled && rightIsNowEnabled && client.player != null && client.currentScreen == null) {
				if (currentDelay <= 0) {
					RightClick(client);
					currentDelay = RandomDelay(rightMinDelay, rightMaxDelay);
				} else {
					currentDelay--;
				}
			}
		});
	}

	private void LeftClick(MinecraftClient client) {
		if (client.player != null && client.crosshairTarget != null) {
			HitResult hitResult = client.crosshairTarget;
			switch (hitResult.getType()) {
				case ENTITY:
					EntityHitResult entityHitResult = (EntityHitResult) hitResult;
					client.interactionManager.attackEntity(client.player, entityHitResult.getEntity());
					break;
				case BLOCK:
					BlockHitResult blockHitResult = (BlockHitResult) hitResult;
					client.interactionManager.updateBlockBreakingProgress(blockHitResult.getBlockPos(), blockHitResult.getSide());
					break;
				default:
					break;
			}
			client.player.swingHand(Hand.MAIN_HAND);
		}
	}

	private void RightClick(MinecraftClient client) {
		if (client.player != null && client.crosshairTarget != null) {
			HitResult hitResult = client.crosshairTarget;
			switch (hitResult.getType()) {
				case ENTITY:
					EntityHitResult entityHitResult = (EntityHitResult) hitResult;
					if (client.interactionManager.interactEntity(client.player, entityHitResult.getEntity(), Hand.MAIN_HAND) == ActionResult.SUCCESS) {
						client.player.swingHand(Hand.MAIN_HAND);
					}
					break;
				case BLOCK:
					BlockHitResult blockHitResult = (BlockHitResult) hitResult;
					ActionResult result = client.interactionManager.interactBlock(client.player, client.world, Hand.MAIN_HAND, blockHitResult);
					if (result == ActionResult.SUCCESS) {
						client.player.swingHand(Hand.MAIN_HAND);
					}
					break;
				default:
					break;
			}
		}
	}


	private int RandomDelay(int min, int max) {
		if (min == max) {
			return min;
		}

		if (min > max) {
			min = min + max;
			max = min - max;
			min = min - max;
		}

		return random.nextInt(max - min + 1) + min;
	}
}
