package lunaticuncle.btalightoverlay;

import lunaticuncle.btalightoverlay.gui.TextBoxComponents;
import lunaticuncle.btalightoverlay.mixin.interfaces.IOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import static lunaticuncle.btalightoverlay.BTALightOverlay.MOD_ID;

public class BTALightOverlayInit {
	public static TomlConfigHandler tomlConfigHandler;

	public static void initTOMLConfig() {
		Toml toml = new Toml("BTA Light Overlay configurations.");

		toml.addCategory("General settings","general");
			toml.addEntry("general.rangeVertical","The vertical range of the light overlay, range: 1-32", 6);
			toml.addEntry("general.rangeHorizontal","The horizontal range of the light overlay, range: 1-32", 24);
			toml.addEntry("general.markersCondition", "When should markers be shown, values: never, spawnable, always", "spawnable");
			toml.addEntry("general.numbers", "Which light value should be shown, values: none, block, sky, both", "none");
			toml.addEntry("general.updateInterval", "How often should the light overlay update in ticks, range: 1-20", 20);

		toml.addCategory("Color values settings","colors");
			toml.addEntry("colors.markerColorDark","The hostile spawnable spot color for marker", "#ff0000");
			toml.addEntry("colors.markerColorBlockLit", "The safe spot color for marker", "#007f00");
			toml.addEntry("colors.markerColorSkyLit", "The safe spot (during day and clear weather) color for marker", "#ffff00");
			toml.addEntry("colors.numberColorBlockDark","The hostile spawnable spot color for block light value", "#ff0000");
			toml.addEntry("colors.numberColorBlockLit", "The safe spot color for block light value", "#00ff00");
			toml.addEntry("colors.numberColorSkyDark","The hostile spawnable spot color for sky light value", "#ffff00");
			toml.addEntry("colors.numberColorSkyLit", "The safe spot color for sky light value", "#00ffff");

		tomlConfigHandler = new TomlConfigHandler(MOD_ID, toml);
	}

	public static IOptions modSettings;
	public static OptionsPage BTALightOverlayOptions;
	public static TextBoxComponents textBoxComponents;
	public static OverlayRenderer overlayRenderer;

	public static void initMod() {
		modSettings = (IOptions) Minecraft.getMinecraft().gameSettings;

		BTALightOverlayOptions = new OptionsPage("btalightoverlay.options.title", new ItemStack(Blocks.GLOWSTONE))
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
