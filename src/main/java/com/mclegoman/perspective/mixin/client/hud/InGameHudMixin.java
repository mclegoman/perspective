/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.hud;

import com.llamalad7.mixinextras.sugar.Local;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.hud.HUDHelper;
import com.mclegoman.perspective.client.hud.Overlays;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = InGameHud.class)
public abstract class InGameHudMixin {
	@Shadow @Nullable protected abstract PlayerEntity getCameraPlayer();
	@Shadow protected abstract void renderHotbarItem(DrawContext context, int x, int y, RenderTickCounter tickCounter, PlayerEntity player, ItemStack stack, int seed);
	@Inject(at = @At("HEAD"), method = "render", cancellable = true)
	private void perspective$render(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
		if (HUDHelper.shouldHideHUD()) ci.cancel();
	}
	@Inject(at = @At("RETURN"), method = "render")
	private void perspective$renderOverlays(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
		Overlays.renderOverlays(context);
	}
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;getAttackIndicator()Lnet/minecraft/client/option/SimpleOption;"), method = "renderHotbar")
	private void perspective$renderArmorHud(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci, @Local int l) {
		if (PerspectiveConfig.config.armorOverlay.value()) {
			PlayerEntity player = this.getCameraPlayer();
			if (player != null) {
				context.getMatrices().push();
				context.getMatrices().translate(0.0F, 0.0F, -90.0F);
				int x = (context.getScaledWindowWidth() / 2) + (player.getMainArm().getOpposite().equals(Arm.LEFT) ? 98 : -180);
				int y = context.getScaledWindowHeight() - 22;
				context.drawGuiTexture(RenderLayer::getGuiTextured, Identifier.of(Data.getVersion().getID(), "hud/armor"), x, y, 82, 22);
				if (player.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) context.drawGuiTexture(RenderLayer::getGuiTextured, Identifier.of("container/slot/helmet"), x + 3, y + 3, 16, 16);
				else this.renderHotbarItem(context, x + 3, y + 3, tickCounter, player, player.getEquippedStack(EquipmentSlot.HEAD), l++);
				if (player.getEquippedStack(EquipmentSlot.CHEST).isEmpty()) context.drawGuiTexture(RenderLayer::getGuiTextured, Identifier.of("container/slot/chestplate"), x + 23, y + 3, 16, 16);
				else this.renderHotbarItem(context, x + 23, y + 3, tickCounter, player, player.getEquippedStack(EquipmentSlot.CHEST), l++);
				if (player.getEquippedStack(EquipmentSlot.LEGS).isEmpty()) context.drawGuiTexture(RenderLayer::getGuiTextured, Identifier.of("container/slot/leggings"), x + 43, y + 3, 16, 16);
				else this.renderHotbarItem(context, x + 43, y + 3, tickCounter, player, player.getEquippedStack(EquipmentSlot.LEGS), l++);
				if (player.getEquippedStack(EquipmentSlot.FEET).isEmpty()) context.drawGuiTexture(RenderLayer::getGuiTextured, Identifier.of("container/slot/boots"), x + 63, y + 3, 16, 16);
				else this.renderHotbarItem(context, x + 63, y + 3, tickCounter, player, player.getEquippedStack(EquipmentSlot.FEET), l++);
				context.getMatrices().pop();
			}
		}
	}
}