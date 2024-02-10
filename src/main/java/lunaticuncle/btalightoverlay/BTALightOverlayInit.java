package lunaticuncle.btalightoverlay;

import lunaticuncle.btalightoverlay.gui.TextBoxComponent;
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
	public static TomlConfigHandler config;

	public static void initTOMLConfig() {
		Toml toml = new Toml("BTA Light Overlay configurations. NOT USED FOR NOW");

		toml.addCategory("Color values settings","colors");
			toml.addCategory("Light level markers", "colors.markers");
				toml.addCategory("Block light level","colors.markers.block");
					toml.addEntry("colors.markers.block.colorDark","Color for light level below threshold", "#FFFFFF");
					toml.addEntry("colors.markers.block.colorLit", "Color for light level above threshold", "#FFFFFF");
				toml.addCategory("Sky light level", "colors.markers.sky");
					toml.addEntry("colors.markers.sky.colorDark","Color for light level below threshold", "#FFFFFF");
					toml.addEntry("colors.markers.sky.colorLit", "Color for light level above threshold", "#FFFFFF");
			toml.addCategory("Light level numbers", "colors.numbers");
				toml.addCategory("Block light level","colors.numbers.block");
					toml.addEntry("colors.numbers.block.colorDark","Color for light level below threshold", "#FFFFFF");
					toml.addEntry("colors.numbers.block.colorLit", "Color for light level above threshold", "#FFFFFF");
				toml.addCategory("Sky light level", "colors.numbers.sky");
					toml.addEntry("colors.numbers.sky.colorDark","Color for light level below threshold", "#FFFFFF");
					toml.addEntry("colors.numbers.sky.colorLit", "Color for light level above threshold", "#FFFFFF");
		toml.addCategory("General settings","general");
			toml.addCategory("Overlay effective ranges","general.radius");
				toml.addEntry("general.radius.horizontal","Horizontal", 24);
				toml.addEntry("general.radius.vertical","Vertical", 8);
			toml.addEntry("general.condition.number","Displays number under certain conditions","always");
			toml.addEntry("general.condition.markers","Displays markers under certain conditions","always");

		config = new TomlConfigHandler(MOD_ID, toml);
	}

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
