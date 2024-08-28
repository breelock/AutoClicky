package com.breelock.autoclicky;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class PlayerMethods {
    public static void attack(MinecraftClient client, boolean isNewPvP) {
        if (client.player != null && client.crosshairTarget != null && !client.player.isSpectator() && client.interactionManager != null && client.world != null) {
            boolean isInLava = client.world.getBlockState(new BlockPos(client.player.getX(), client.player.getY(), client.player.getZ())).getBlock() == Blocks.LAVA || client.player.isSubmergedIn(FluidTags.LAVA);
            boolean isOnGround = client.player.isOnGround() && !client.player.isTouchingWater() && !isInLava;

            float jumpCooldown = 400f;
            float cooldownTime = getAttackSpeedInTicks(client.player) * 50;
            float attackCooldown = getAttackCooldownInTicks(client.player) * 50;

            if (isNewPvP) {
                if (ModConfig.NewPvP.onlyEntity) {
                    if (client.crosshairTarget.getType() == HitResult.Type.ENTITY) {
                        if (!targetIsProtectedByShield(client, ((EntityHitResult) client.crosshairTarget).getEntity())) {
                            if (attackCooldown >= cooldownTime - jumpCooldown && ModConfig.NewPvP.autoJump && client.player.isOnGround() && !client.player.isTouchingWater() && !isInLava)
                                client.player.jump();

                            if (attackCooldown >= cooldownTime) {
                                if (!isOnGround && client.player.getVelocity().y < -0.1 || client.player.isOnGround() || client.player.abilities.flying || client.player.isTouchingWater() || isInLava) {
                                    if (interrupt(client, true)) return;
                                    PlayerMethods.attackEntity(client);
                                }
                            }
                        }
                    }
                }
                else {
                    if (client.crosshairTarget.getType() == HitResult.Type.ENTITY && attackCooldown >= cooldownTime - jumpCooldown && ModConfig.NewPvP.autoJump && client.player.isOnGround() && !client.player.isTouchingWater() && !isInLava) {
                        if (!targetIsProtectedByShield(client, ((EntityHitResult) client.crosshairTarget).getEntity()))
                            client.player.jump();
                    }

                    if (attackCooldown >= cooldownTime) {
                        if (interrupt(client, true)) return;

                        if (client.crosshairTarget.getType() == HitResult.Type.ENTITY) {
                            if (!targetIsProtectedByShield(client, ((EntityHitResult) client.crosshairTarget).getEntity())) {
                                if (!isOnGround && client.player.getVelocity().y < -0.1 || client.player.isOnGround() || client.player.abilities.flying || client.player.isTouchingWater() || isInLava)
                                    PlayerMethods.attackEntity(client);
                            }
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
                if (client.crosshairTarget.getType() == HitResult.Type.ENTITY && ModConfig.OldPvP.autoJump && client.player.isOnGround() && !client.player.isTouchingWater() && !isInLava)
                    client.player.jump();

                if (ModConfig.OldPvP.onlyEntity) {
                    if (client.crosshairTarget.getType() == HitResult.Type.ENTITY) {
                        if (interrupt(client, false)) return;
                        PlayerMethods.attackEntity(client);
                    }
                }
                else {
                    if (interrupt(client, false)) return;

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
        if (client.player != null && !client.player.isSpectator() && client.interactionManager != null && !client.interactionManager.isBreakingBlock() && !client.player.isRiding()) {
            for (Hand hand : Hand.values()) {
                if (client.crosshairTarget != null) {
                    if (client.crosshairTarget.getType() == HitResult.Type.ENTITY) {
                        if (PlayerMethods.interactEntity(client, hand)) return;
                    }
                    else if (client.crosshairTarget.getType() == HitResult.Type.BLOCK) {
                        if (PlayerMethods.interactBlock(client, hand)) return;
                    }

                }
                interactItem(client, hand);
            }
        }
    }

    private static boolean interrupt(MinecraftClient client, boolean isNewPvP) {
        if (client != null && client.player != null && client.interactionManager != null && client.player.isUsingItem()) {
            if (isNewPvP && ModConfig.NewPvP.interrupt || !isNewPvP && ModConfig.OldPvP.interrupt)
                client.interactionManager.stopUsingItem(client.player);
            else
                return true;
        }

        return false;
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

    private static boolean targetIsProtectedByShield(MinecraftClient client, Entity targetEntity) {
        if (client.player != null) {
            if (targetEntity instanceof PlayerEntity) {
                PlayerEntity targetPlayer = (PlayerEntity) targetEntity;
                ItemStack heldItem = client.player.getMainHandStack();
                if (heldItem.getItem() instanceof AxeItem)
                    return false;

                return targetPlayer.isUsingItem() && targetPlayer.getActiveItem().getItem() == Items.SHIELD;
            }
        }
        return false;
    }

    private static int getAttackCooldownInTicks(PlayerEntity player) {
        float cooldown = player.getAttackCooldownProgress(0.0f);
        return MathHelper.ceil(cooldown * getAttackSpeedInTicks(player));
    }

    private static float getAttackSpeedInTicks(PlayerEntity player) {
        float attackSpeed = (float) player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED);
        return (1.0f / attackSpeed) * 20.0f;
    }

    private static void breakBlock(MinecraftClient client) {
        if (client != null && client.interactionManager != null && client.player != null && client.world != null) {
            BlockHitResult blockHitResult = (BlockHitResult) client.crosshairTarget;
            if (blockHitResult != null) {
                BlockPos blockPos = blockHitResult.getBlockPos();

                if (!client.world.getBlockState(blockPos).isAir())
                    client.interactionManager.attackBlock(blockPos, blockHitResult.getSide());

                client.player.swingHand(Hand.MAIN_HAND);
            }
        }
    }

    private static boolean interactEntity(MinecraftClient client, Hand hand) {
        if (client != null && client.interactionManager != null && client.player != null) {
            EntityHitResult entityHitResult = (EntityHitResult) client.crosshairTarget;
            if (entityHitResult != null) {
                Entity entity = entityHitResult.getEntity();
                ActionResult actionResult = client.interactionManager.interactEntityAtLocation(client.player, entity, entityHitResult, hand);
                if (!actionResult.isAccepted()) {
                    actionResult = client.interactionManager.interactEntity(client.player, entity, hand);
                }

                if (actionResult.isAccepted()) {
                    if (actionResult.shouldSwingHand()) {
                        client.player.swingHand(hand);
                    }

                    return true;
                }
            }
        }

        return false;
    }

    private static boolean interactBlock(MinecraftClient client, Hand hand) {
        if (client != null && client.interactionManager != null && client.player != null) {
            ItemStack itemStack = client.player.getStackInHand(hand);
            BlockHitResult blockHitResult = (BlockHitResult)client.crosshairTarget;
            int i = itemStack.getCount();
            ActionResult actionResult = client.interactionManager.interactBlock(client.player, client.world, hand, blockHitResult);
            if (actionResult.isAccepted()) {
                if (actionResult.shouldSwingHand()) {
                    client.player.swingHand(hand);
                    if (!itemStack.isEmpty() && (itemStack.getCount() != i || client.interactionManager.hasCreativeInventory())) {
                        client.gameRenderer.firstPersonRenderer.resetEquipProgress(hand);
                    }
                }

                return true;
            }

            return actionResult == ActionResult.FAIL;
        }

        return false;
    }

    private static void interactItem(MinecraftClient client, Hand hand) {
        if (client != null && client.interactionManager != null && client.player != null) {
            ItemStack itemStack = client.player.getStackInHand(hand);
            if (!itemStack.isEmpty()) {
                ActionResult actionResult = client.interactionManager.interactItem(client.player, client.world, hand);
                if (actionResult.isAccepted()) {
                    if (actionResult.shouldSwingHand()) {
                        client.player.swingHand(hand);
                    }

                    client.gameRenderer.firstPersonRenderer.resetEquipProgress(hand);
                }
            }
        }
    }
}
