package lunaticuncle.btalightoverlay;

import lunaticuncle.btalightoverlay.mixin.interfaces.IOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;

public class BTALightOverlayInit {
	public static IOptions modSettings;
	public static OptionsPage BTALightOverlayOptions;
	public static OverlayRenderer overlayRenderer;

	public static void initMod() {
		modSettings = (IOptions) Minecraft.getMinecraft(Minecraft.class).gameSettings;

		BTALightOverlayOptions = new OptionsPage("btalightoverlay.options.title")
			.withComponent(new OptionsCategory("btalightoverlay.options.category.keybinds")
				.withComponent(new KeyBindingComponent(modSettings.bTALightOverlay$getKeyToggleLightOverlay())));
		OptionsPages.register(BTALightOverlayOptions);
		OptionsPages.CONTROLS.withComponent(
			new OptionsCategory("btalightoverlay.options.category.keybinds.explicit")
				.withComponent(new KeyBindingComponent(modSettings.bTALightOverlay$getKeyToggleLightOverlay()))
		);

		overlayRenderer = new OverlayRenderer();
	}
}
