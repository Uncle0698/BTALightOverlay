package lunaticuncle.btalightoverlay.mixin.mixins;

import lunaticuncle.btalightoverlay.OverlayRenderer;
import lunaticuncle.btalightoverlay.mixin.interfaces.IOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.WorldRenderer;

import net.minecraft.client.render.camera.ICamera;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static lunaticuncle.btalightoverlay.BTALightOverlayInit.overlayRenderer;


@Mixin(value = WorldRenderer.class, remap = false)
public class WorldRendererMixin {

	@Shadow
	private Minecraft mc;


	@Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/debug/Debug;change(Ljava/lang/String;)V", ordinal = 9, shift = At.Shift.BEFORE))
	private void BTALightOverlayRender(float partialTick, long updateRenderersUntil, CallbackInfo ci) {
		overlayRenderer.draw(mc, partialTick);
	}

}
