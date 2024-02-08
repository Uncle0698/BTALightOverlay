package lunaticuncle.btalightoverlay;

import lunaticuncle.btalightoverlay.gui.TextBoxComponent;
import lunaticuncle.btalightoverlay.gui.TextBoxComponents;
import lunaticuncle.btalightoverlay.mixin.interfaces.IOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import org.spongepowered.asm.mixin.Unique;

public class BTALightOverlayInit {
	public static IOptions modSettings;
	public static OptionsPage BTALightOverlayOptions;
	public static TextBoxComponents textBoxComponents;
	public static OverlayRenderer overlayRenderer;
	public static TextBoxComponent textBoxComponent1;
	public static TextBoxComponent textBoxComponent2;


	public static void initMod() {
		modSettings = (IOptions) Minecraft.getMinecraft(Minecraft.class).gameSettings;

		textBoxComponent1 = new TextBoxComponent("options.colorLit", "0~15");
		textBoxComponent2 = new TextBoxComponent("options.test", "Text...");

		BTALightOverlayOptions = new OptionsPage("btalightoverlay.options.title")
			.withComponent(new OptionsCategory("btalightoverlay.options.category.general")
				.withComponent(textBoxComponent1))
			.withComponent(new OptionsCategory("btalightoverlay.options.category.others")
				.withComponent(textBoxComponent2))
			.withComponent(new OptionsCategory("btalightoverlay.options.category.keybinds")
				.withComponent(new KeyBindingComponent(modSettings.bTALightOverlay$getKeyToggleLightOverlay())));


		OptionsPages.register(BTALightOverlayOptions);
		OptionsPages.CONTROLS.withComponent(
			new OptionsCategory("btalightoverlay.options.category.keybinds.explicit")
				.withComponent(new KeyBindingComponent(modSettings.bTALightOverlay$getKeyToggleLightOverlay()))
		);

		textBoxComponents = new TextBoxComponents();
		textBoxComponents.addTextBoxComponent(textBoxComponent1);
		textBoxComponents.addTextBoxComponent(textBoxComponent2);

		overlayRenderer = new OverlayRenderer();
	}
}
