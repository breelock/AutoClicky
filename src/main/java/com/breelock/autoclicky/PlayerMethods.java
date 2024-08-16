package com.breelock.autoclicky;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class PlayerMethods {
    public static void attack(MinecraftClient client, boolean isNewPvP) {
        if (client.player != null && client.crosshairTarget != null && !client.player.isSpectator()) {
            if (isNewPvP) {
                if (ModConfig.NewPvP.onlyEntity) {
                    if (client.crosshairTarget.getType() == HitResult.Type.ENTITY) {
                        if (client.player.getAttackCooldownProgress(0.0F) >= 0.7F && ModConfig.NewPvP.autoJump && client.player.isOnGround())
                            client.player.jump();

                        if (client.player.getAttackCooldownProgress(0.0F) >= 1.0F) {
                            if (!client.player.isOnGround() && client.player.getVelocity().y < -0.1 || client.player.isOnGround() || client.player.abilities.flying)
                                PlayerMethods.attackEntity(client);
                        }
                    }
                }
                else {
                    if (client.crosshairTarget.getType() == HitResult.Type.ENTITY && client.player.getAttackCooldownProgress(0.0F) >= 0.7F && ModConfig.NewPvP.autoJump && client.player.isOnGround())
                        client.player.jump();

                    if (client.player.getAttackCooldownProgress(0.0F) >= 1.0F) {
                        if (client.crosshairTarget.getType() == HitResult.Type.ENTITY) {
                            if (!client.player.isOnGround() && client.player.getVelocity().y < -0.1 || client.player.isOnGround() || client.player.abilities.flying)
                                PlayerMethods.attackEntity(client);
                        }
                        else if (client.crosshairTarget.getType() == HitResult.Type.BLOCK)
                            PlayerMethods.breakBlock(client);
                        else if (client.crosshairTarget.getType() == HitResult.Type.MISS) {
                            client.player.swingHand(Hand.MAIN_HAND);
                            resetAttackCooldown(client);
                        }
                    }
                }
            }
            else {
                if (client.crosshairTarget.getType() == HitResult.Type.ENTITY && ModConfig.OldPvP.autoJump && client.player.isOnGround())
                    client.player.jump();

                if (ModConfig.OldPvP.onlyEntity) {
                    if (client.crosshairTarget.getType() == HitResult.Type.ENTITY)
                        PlayerMethods.attackEntity(client);
                }
                else {
                    if (client.crosshairTarget.getType() == HitResult.Type.ENTITY)
                        PlayerMethods.attackEntity(client);
                    else if (client.crosshairTarget.getType() == HitResult.Type.BLOCK)
                        PlayerMethods.breakBlock(client);
                    else if (client.crosshairTarget.getType() == HitResult.Type.MISS) {
                        client.player.swingHand(Hand.MAIN_HAND);
                        resetAttackCooldown(client);
                    }
                }
            }
        }
    }

    public static void interact(MinecraftClient client) {
        if (client.player != null && client.crosshairTarget != null && !client.player.isSpectator()) {
            if (client.crosshairTarget.getType() == HitResult.Type.ENTITY)
                PlayerMethods.interactEntity(client);
            else if (client.crosshairTarget.getType() == HitResult.Type.BLOCK)
                PlayerMethods.interactBlock(client);
        }
    }

    private static void resetAttackCooldown(MinecraftClient client) {
        if (client.player != null) {
            client.player.resetLastAttackedTicks();
        }
    }

    private static void attackEntity(MinecraftClient client) {
        if (client != null && client.interactionManager != null && client.player != null) {
            EntityHitResult entityHitResult = (EntityHitResult) client.crosshairTarget;
            if (entityHitResult != null)
                client.interactionManager.attackEntity(client.player, entityHitResult.getEntity());

            client.player.swingHand(Hand.MAIN_HAND);
        }
    }

    private static void breakBlock(MinecraftClient client) {
        if (client != null && client.interactionManager != null && client.player != null) {
            BlockHitResult blockHitResult = (BlockHitResult) client.crosshairTarget;
            if (blockHitResult != null)
                client.interactionManager.updateBlockBreakingProgress(blockHitResult.getBlockPos(), blockHitResult.getSide());

            client.player.swingHand(Hand.MAIN_HAND);
        }
    }

    private static void interactEntity(MinecraftClient client) {
        if (client != null && client.interactionManager != null && client.player != null) {
            EntityHitResult entityHitResult = (EntityHitResult) client.crosshairTarget;
            if (entityHitResult != null && client.interactionManager.interactEntity(client.player, entityHitResult.getEntity(), Hand.MAIN_HAND) == ActionResult.SUCCESS)
                client.player.swingHand(Hand.MAIN_HAND);
        }
    }

    private static void interactBlock(MinecraftClient client) {
        if (client != null && client.interactionManager != null && client.player != null) {
            if (client.interactionManager.interactBlock(client.player, client.world, Hand.MAIN_HAND, (BlockHitResult) client.crosshairTarget) == ActionResult.SUCCESS)
                client.player.swingHand(Hand.MAIN_HAND);
        }
    }
}
