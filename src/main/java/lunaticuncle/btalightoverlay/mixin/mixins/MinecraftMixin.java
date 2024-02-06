package lunaticuncle.btalightoverlay.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.option.InputDevice;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static lunaticuncle.btalightoverlay.BTALightOverlayInit.modSettings;
import static lunaticuncle.btalightoverlay.BTALightOverlayInit.overlayRenderer;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftMixin {

	@Inject(method = "checkBoundInputs", at = @At("HEAD"), cancellable = true)
	private void checkModdedBoundInputs(InputDevice currentInputDevice, CallbackInfoReturnable<Boolean> cir) {
		if(modSettings.bTALightOverlay$getKeyToggleLightOverlay().isPressEvent(currentInputDevice)) {
			overlayRenderer.toggleRender();
			cir.setReturnValue(true);
		}
	}

}
