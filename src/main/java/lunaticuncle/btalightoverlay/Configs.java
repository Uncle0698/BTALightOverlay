package lunaticuncle.btalightoverlay;


import lunaticuncle.btalightoverlay.option.ConfigInteger;
import lunaticuncle.btalightoverlay.option.ConfigString;

public class Configs {
	public static class General {
		//TODO: Make comment, prefix and postfix also translatable
		public static final ConfigInteger VERTICAL_RANGE = new ConfigInteger(BTALightOverlay.MOD_ID, "general.rangeVertical","The vertical range of the light overlay, range: 1-32", 16, 1, 32, "", " blocks");
		public static final ConfigInteger HORIZONTAL_RANGE = new ConfigInteger(BTALightOverlay.MOD_ID,"general.rangeHorizontal", "The horizontal range of the light overlay, range: 1-32", 16, 1, 32, "", " blocks");
		public static final ConfigString MARKERS_CONDITION = new ConfigString(BTALightOverlay.MOD_ID,"general.markersCondition", "When should markers be shown, values: never, spawnable, always", "spawnable");
		public static final ConfigString NUMBERS_MODE = new ConfigString(BTALightOverlay.MOD_ID,"general.numbers", "Which light value should be shown, values: none, block, sky, both", "none");
		public static final ConfigInteger UPDATE_INTERVAL = new ConfigInteger(BTALightOverlay.MOD_ID,"general.updateInterval", "How often should the light overlay update in ticks, range: 1-20", 20, 1, 20, "", " ticks");
	}

	public static class Colors {
		public static final ConfigString MARKER_DARK = new ConfigString(BTALightOverlay.MOD_ID,"colors.markerColorDark", "The hostile spawnable spot color for marker", "#ff0000");
		public static final ConfigString MARKER_BLOCK_LIT = new ConfigString(BTALightOverlay.MOD_ID,"colors.markerColorBlockLit", "The safe spot color for marker", "#007f00");
		public static final ConfigString MARKER_SKY_LIT = new ConfigString(BTALightOverlay.MOD_ID,"colors.markerColorSkyLit", "The safe spot (during day and clear weather) color for marker", "#ffff00");
		public static final ConfigString NUMBER_BLOCK_DARK = new ConfigString(BTALightOverlay.MOD_ID,"colors.numberColorBlockDark", "The hostile spawnable spot color for block light value", "#ff0000");
		public static final ConfigString NUMBER_BLOCK_LIT = new ConfigString(BTALightOverlay.MOD_ID,"colors.numberColorBlockLit", "The safe spot color for block light value", "#00ff00");
		public static final ConfigString NUMBER_SKY_DARK = new ConfigString(BTALightOverlay.MOD_ID,"colors.numberColorSkyDark", "The hostile spawnable spot color for sky light value", "#ffff00");
		public static final ConfigString NUMBER_SKY_LIT = new ConfigString(BTALightOverlay.MOD_ID,"colors.numberColorSkyLit", "The safe spot color for sky light value", "#00ffff");
	}
}
