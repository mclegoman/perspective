/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.perspective;

import com.mclegoman.perspective.client.config.PerspectiveDefaultConfig;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.keybindings.Keybindings;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import net.minecraft.util.math.MathHelper;

public class Perspective {
	private static boolean holdThirdPersonBackLock;
	private static net.minecraft.client.option.Perspective holdThirdPersonBackPrev;
	private static boolean holdThirdPersonFrontLock;
	private static net.minecraft.client.option.Perspective holdThirdPersonFrontPrev;
	public static boolean isHoldingAdjust() {
		return Keybindings.adjustHoldPerspective.isPressed();
	}
	public static boolean isHoldingPerspective() {
		return isHoldingPerspectiveBack() || isHoldingPerspectiveFront();
	}
	public static boolean isHoldingPerspectiveBack() {
		return holdThirdPersonBackLock ;
	}
	public static boolean isHoldingPerspectiveFront() {
		return holdThirdPersonFrontLock;
	}
	public static net.minecraft.client.option.Perspective getPerspective() {
		return ClientData.minecraft.options.getPerspective();
	}
	public static float getHoldPerspectiveBackMultiplier() {
		return PerspectiveConfig.config.holdPerspectiveBackMultiplier.value();
	}
	public static float getHoldPerspectiveFrontMultiplier() {
		return PerspectiveConfig.config.holdPerspectiveFrontMultiplier.value();
	}
	public static void tick() {
		if (Keybindings.setPerspectiveFirstPerson.wasPressed())
			setPerspective(net.minecraft.client.option.Perspective.FIRST_PERSON);
		if (Keybindings.setPerspectiveThirdPersonBack.wasPressed())
			setPerspective(net.minecraft.client.option.Perspective.THIRD_PERSON_BACK);
		if (Keybindings.setPerspectiveThirdPersonFront.wasPressed())
			setPerspective(net.minecraft.client.option.Perspective.THIRD_PERSON_FRONT);
		getHoldAll();
		if (!(isHoldingPerspective() && isHoldingAdjust()) && hasUpdated) {
			PerspectiveConfig.config.holdPerspectiveBackMultiplier.serializeAndInvokeCallbacks();
			PerspectiveConfig.config.holdPerspectiveFrontMultiplier.serializeAndInvokeCallbacks();
		}
	}
	private static void setThirdPersonFront(net.minecraft.client.option.Perspective perspective) {
		if (!Keybindings.holdPerspectiveThirdPersonBack.isPressed() && !holdThirdPersonBackLock) {
			if (!holdThirdPersonFrontLock) {
				holdThirdPersonFrontPrev = perspective;
				if (ClientData.minecraft.options.getPerspective().equals(net.minecraft.client.option.Perspective.THIRD_PERSON_FRONT))
					setPerspective(net.minecraft.client.option.Perspective.FIRST_PERSON);
				else
					setPerspective(net.minecraft.client.option.Perspective.THIRD_PERSON_FRONT);
			}
			holdThirdPersonFrontLock = true;
		}
	}
	private static void setThirdPersonBack(net.minecraft.client.option.Perspective perspective) {
		if (!Keybindings.holdPerspectiveThirdPersonFront.isPressed() && !holdThirdPersonFrontLock) {
			if (!holdThirdPersonBackLock) {
				ClientData.minecraft.worldRenderer.scheduleTerrainUpdate();
				holdThirdPersonBackPrev = perspective;
				if (ClientData.minecraft.options.getPerspective().equals(net.minecraft.client.option.Perspective.THIRD_PERSON_BACK))
					setPerspective(net.minecraft.client.option.Perspective.FIRST_PERSON);
				else
					setPerspective(net.minecraft.client.option.Perspective.THIRD_PERSON_BACK);
			}
			holdThirdPersonBackLock = true;
		}
	}
	private static void getHoldAll() {
		getHoldFront(ClientData.minecraft.options.getPerspective());
		getHoldBack(ClientData.minecraft.options.getPerspective());
	}
	private static boolean isHoldFront() {
		return Keybindings.holdPerspectiveThirdPersonFront.isPressed();
	}
	private static void getHoldFront(net.minecraft.client.option.Perspective perspective) {
		if (isHoldFront())
			setThirdPersonFront(perspective);
		clearHoldFront();
	}
	private static boolean isHoldBack() {
		return Keybindings.holdPerspectiveThirdPersonBack.isPressed();
	}
	private static void getHoldBack(net.minecraft.client.option.Perspective perspective) {
		if (isHoldBack())
			setThirdPersonBack(perspective);
		clearHoldBack();
	}
	private static void clearHoldFront() {
		if (!Keybindings.holdPerspectiveThirdPersonFront.isPressed() && holdThirdPersonFrontLock) {
			holdThirdPersonFrontLock = false;
			if (isHoldBack()) getHoldBack(holdThirdPersonFrontPrev);
			else setPerspective(holdThirdPersonFrontPrev);
		}
	}
	private static void clearHoldBack() {
		if (!Keybindings.holdPerspectiveThirdPersonBack.isPressed() && holdThirdPersonBackLock) {
			holdThirdPersonBackLock = false;
			if (isHoldFront()) getHoldFront(holdThirdPersonBackPrev);
			else setPerspective(holdThirdPersonBackPrev);
		}
	}
	public static void setPerspective(net.minecraft.client.option.Perspective perspective) {
		ClientData.minecraft.worldRenderer.scheduleTerrainUpdate();
		ClientData.minecraft.options.setPerspective(perspective);
	}





	private static boolean hasUpdated;

	public static float getMultiplier() {
		if (isHoldingPerspective()) {
			if (isHoldBack()) return PerspectiveConfig.config.holdPerspectiveBackMultiplier.value();
			else if (isHoldFront()) return PerspectiveConfig.config.holdPerspectiveFrontMultiplier.value();
		}
		return 1.0F;
	}
	public static void adjust(float amount, int multiplier) {
		if (isHoldingPerspective()) {
			for (int i = 0; i < multiplier; i++) {
				if (!(getMultiplier() <= 0.5F) || !(getMultiplier() >= 16.0F)) {
					if (isHoldBack()) {
						PerspectiveConfig.config.holdPerspectiveBackMultiplier.setValue(MathHelper.clamp(getMultiplier() + amount, 0.5F, 16.0F), false);
					} else if (isHoldFront()) {
						PerspectiveConfig.config.holdPerspectiveFrontMultiplier.setValue(MathHelper.clamp(getMultiplier() + amount, 0.5F, 16.0F), false);
					}
					hasUpdated = true;
				}
			}
		}
	}
	public static void reset() {
		if (isHoldingPerspective()) {
			if (isHoldBack()) {
				PerspectiveConfig.config.holdPerspectiveBackMultiplier.setValue(MathHelper.clamp(PerspectiveDefaultConfig.config.holdPerspectiveBackMultiplier.value(), 0.5F, 16.0F), false);
			} else if (isHoldFront()) {
				PerspectiveConfig.config.holdPerspectiveFrontMultiplier.setValue(MathHelper.clamp(PerspectiveDefaultConfig.config.holdPerspectiveFrontMultiplier.value(), 0.5F, 16.0F), false);
			}
			hasUpdated = true;
		}
	}
}