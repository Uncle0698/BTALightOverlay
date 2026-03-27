package lunaticuncle.btalightoverlay.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.input.InputDevice;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static lunaticuncle.btalightoverlay.BTALightOverlayInit.modSettings;
import static lunaticuncle.btalightoverlay.BTALightOverlayInit.overlayRenderer;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftMixin {
	@Shadow
	public WorldClient currentWorld;
	@Shadow
	private static Minecraft INSTANCE;

	@Inject(method = "checkBoundInputs", at = @At("HEAD"), cancellable = true)
	private void checkModdedBoundInputs(InputDevice currentInputDevice, CallbackInfoReturnable<Boolean> cir) {
		if(modSettings.bTALightOverlay$getKeyToggleLightOverlay().isPressEvent(currentInputDevice)) {
			overlayRenderer.toggleRender();
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "runTick", at = @At(value = "INVOKE", target = "Ljava/lang/System;currentTimeMillis()J", ordinal = 2, shift = At.Shift.BEFORE))
	private void BTALightOverlayUpdate(CallbackInfo ci) {
		if(currentWorld != null) {
			overlayRenderer.update(INSTANCE);
		}
	}

}
