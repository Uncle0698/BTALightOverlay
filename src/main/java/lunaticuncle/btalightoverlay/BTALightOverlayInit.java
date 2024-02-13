package lunaticuncle.btalightoverlay;

import lunaticuncle.btalightoverlay.gui.TextBoxComponents;
import lunaticuncle.btalightoverlay.mixin.interfaces.IOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import static lunaticuncle.btalightoverlay.BTALightOverlay.MOD_ID;

public class BTALightOverlayInit {
	public static TomlConfigHandler tomlConfigHandler;

	public static void initTOMLConfig() {
		Toml toml = new Toml("BTA Light Overlay configurations.");

		toml.addCategory("General settings","general");
			toml.addEntry("general.rangeVertical","The vertical range of the light overlay", 16);
			toml.addEntry("general.rangeHorizontal","The horizontal range of the light overlay", 16);
			toml.addEntry("general.markersCondition", "When should markers be shown, values: never, spawnable, always", "spawnable");
			toml.addEntry("general.numbersCondition", "When should numbers be shown, values: never, spawnable, always", "never");
			toml.addEntry("general.numbers", "Which light value should be shown, values: none, block, sky, both", "block");

		toml.addCategory("Color values settings","colors");
			toml.addEntry("colors.markerColorDark","The hostile spawnable spot color for marker", "#FFFFFF");
			toml.addEntry("colors.markerColorBlockLit", "The safe spot color for mamrker", "#FFFFFF");
			toml.addEntry("colors.markerColorSkyLit", "The safe spot (during day) color for marker", "#FFFFFF");
			toml.addEntry("colors.numberColorBlockDark","The hostile spawnable spot color for block light value", "#FFFFFF");
			toml.addEntry("colors.numberColorBlockLit", "The safe spot color for block light value", "#FFFFFF");
			toml.addEntry("colors.numberColorSkyDark","The hostile spawnable spot color for sky light value", "#FFFFFF");
			toml.addEntry("colors.numberColorSkyLit", "The safe spot color for sky light value", "#FFFFFF");

		tomlConfigHandler = new TomlConfigHandler(MOD_ID, toml);
	}

	public static IOptions modSettings;
	public static OptionsPage BTALightOverlayOptions;
	public static TextBoxComponents textBoxComponents;
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
