package lunaticuncle.btalightoverlay.mixin.mixins;

import lunaticuncle.btalightoverlay.BTALightOverlayInit;
import lunaticuncle.btalightoverlay.gui.TextBoxComponent;
import net.minecraft.client.gui.options.GuiOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiOptions.class, remap = false)
public class GuiOptionsMixin {

	@Inject(method = "mouseClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;mouseClicked(III)V", shift = At.Shift.AFTER))
	private void forceLoseFocus(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
		if(BTALightOverlayInit.textBoxComponents != null) {
			for (TextBoxComponent textBoxComponent : BTALightOverlayInit.textBoxComponents.getComponents()) {
				textBoxComponent.onMouseClick(mouseButton, -1, -1, -1, -1, -1);
			}
		}
	}

	

}
