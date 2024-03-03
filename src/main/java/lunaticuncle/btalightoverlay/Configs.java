package lunaticuncle.btalightoverlay;


import lunaticuncle.btalightoverlay.option.ConfigInteger;
import lunaticuncle.btalightoverlay.option.ConfigString;

public class Configs {
	public static class General {
		public static final ConfigInteger VERTICAL_RANGE = new ConfigInteger("general.rangeVertical", "Vertical Range", "The vertical range of the light overlay, range: 1-32", 16, 1, 32);
		public static final ConfigInteger HORIZONTAL_RANGE = new ConfigInteger("general.rangeHorizontal", "Horizontal Range", "The horizontal range of the light overlay, range: 1-32", 16, 1, 32);
		public static final ConfigString MARKERS_CONDITION = new ConfigString("general.markersCondition", "Markers Condition", "When should markers be shown, values: never, spawnable, always", "spawnable");
		public static final ConfigString NUMBERS_MODE = new ConfigString("general.numbers", "Numbers Mode", "Which light value should be shown, values: none, block, sky, both", "none");
		public static final ConfigInteger UPDATE_INTERVAL = new ConfigInteger("general.updateInterval", "Update Interval", "How often should the light overlay update in ticks, range: 1-20", 20, 1, 20);
	}

	public static class Colors {
		public static final ConfigString MARKER_DARK = new ConfigString("colors.markerColorDark", "Marker Dark", "The hostile spawnable spot color for marker", "#ff0000");
		public static final ConfigString MARKER_BLOCK_LIT = new ConfigString("colors.markerColorBlockLit", "Marker Block Lit", "The safe spot color for marker", "#007f00");
		public static final ConfigString MARKER_SKY_LIT = new ConfigString("colors.markerColorSkyLit", "Marker Sky Lit", "The safe spot (during day and clear weather) color for marker", "#ffff00");
		public static final ConfigString NUMBER_BLOCK_DARK = new ConfigString("colors.numberColorBlockDark", "Number Block Dark", "The hostile spawnable spot color for block light value", "#ff0000");
		public static final ConfigString NUMBER_BLOCK_LIT = new ConfigString("colors.numberColorBlockLit", "Number Block Lit", "The safe spot color for block light value", "#00ff00");
		public static final ConfigString NUMBER_SKY_DARK = new ConfigString("colors.numberColorSkyDark", "Number Sky Dark", "The hostile spawnable spot color for sky light value", "#ffff00");
		public static final ConfigString NUMBER_SKY_LIT = new ConfigString("colors.numberColorSkyLit", "Number Sky Lit", "The safe spot color for sky light value", "#00ffff");
	}
}
