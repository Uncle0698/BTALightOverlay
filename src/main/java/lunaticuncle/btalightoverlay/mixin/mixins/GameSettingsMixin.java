package lunaticuncle.btalightoverlay.mixin.mixins;

import lunaticuncle.btalightoverlay.mixin.interfaces.IOptions;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import static org.lwjgl.input.Keyboard.KEY_F9;

@Mixin(value = GameSettings.class, remap = false)
public class GameSettingsMixin implements IOptions {

	@Unique
	public final KeyBinding keyToggleLightOverlay = new KeyBinding("btalightoverlay.key.toggleoverlay").bindKeyboard(KEY_F9);


	@Override
	public KeyBinding bTALightOverlay$getKeyToggleLightOverlay() {
		return keyToggleLightOverlay;
	}
}
