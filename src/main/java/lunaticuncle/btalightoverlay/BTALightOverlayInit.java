package lunaticuncle.btalightoverlay;

import lunaticuncle.btalightoverlay.gui.IntegerOptionComponent;
import lunaticuncle.btalightoverlay.gui.TextBoxComponents;
import lunaticuncle.btalightoverlay.mixin.interfaces.IOptions;
import lunaticuncle.btalightoverlay.option.ConfigOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import java.util.ArrayList;

import static lunaticuncle.btalightoverlay.BTALightOverlay.MOD_ID;
public class BTALightOverlayInit {
	public static ModConfigHandler modConfigHandler;

	public static void initTOMLConfig() {
		ArrayList<ConfigOption<?>> options = new ArrayList<>();
		Toml toml = new Toml("BTA Light Overlay configurations.");

		toml.addCategory("General settings","general");
		addTOMLConfig(toml, Configs.General.VERTICAL_RANGE, options);
		addTOMLConfig(toml, Configs.General.HORIZONTAL_RANGE, options);
		addTOMLConfig(toml, Configs.General.MARKERS_CONDITION, options);
		addTOMLConfig(toml, Configs.General.NUMBERS_MODE, options);
		addTOMLConfig(toml, Configs.General.UPDATE_INTERVAL, options);

		toml.addCategory("Color values settings","colors");
		addTOMLConfig(toml, Configs.Colors.MARKER_DARK, options);
		addTOMLConfig(toml, Configs.Colors.MARKER_BLOCK_LIT, options);
		addTOMLConfig(toml, Configs.Colors.MARKER_SKY_LIT, options);
		addTOMLConfig(toml, Configs.Colors.NUMBER_BLOCK_DARK, options);
		addTOMLConfig(toml, Configs.Colors.NUMBER_BLOCK_LIT, options);
		addTOMLConfig(toml, Configs.Colors.NUMBER_SKY_DARK, options);
		addTOMLConfig(toml, Configs.Colors.NUMBER_SKY_LIT, options);

		modConfigHandler = new ModConfigHandler(MOD_ID, new TomlConfigHandler(MOD_ID, toml));
		modConfigHandler.addOptions(options);
		modConfigHandler.init();
	}

	private static void addTOMLConfig(Toml toml, ConfigOption<?> option, ArrayList<ConfigOption<?>> options) {
		toml.addEntry(option.getName(), option.getComment(), option.getValue());
		options.add(option);
	}

	public static IOptions modSettings;
	public static OptionsPage BTALightOverlayOptions;
	public static TextBoxComponents textBoxComponents;
	public static OverlayRenderer overlayRenderer;

	public static void initMod() {
		modSettings = (IOptions) Minecraft.getMinecraft(Minecraft.class).gameSettings;

		BTALightOverlayOptions = new OptionsPage("btalightoverlay.options.title")
			.withComponent(new OptionsCategory("btalightoverlay.options.category.general")
				.withComponent(new IntegerOptionComponent(Configs.General.VERTICAL_RANGE))
				.withComponent(new IntegerOptionComponent(Configs.General.HORIZONTAL_RANGE))
				.withComponent(new IntegerOptionComponent(Configs.General.UPDATE_INTERVAL)))
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
