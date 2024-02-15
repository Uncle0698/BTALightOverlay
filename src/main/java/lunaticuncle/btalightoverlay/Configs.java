package lunaticuncle.btalightoverlay;

import static lunaticuncle.btalightoverlay.BTALightOverlayInit.tomlConfigHandler;

public class Configs {
	public static class General {
		public static final int VERTICAL_RANGE = tomlConfigHandler.getInt("general.rangeVertical");
		public static final int HORIZONTAL_RANGE = tomlConfigHandler.getInt("general.rangeHorizontal");
		public static final String MARKERS_CONDITION = tomlConfigHandler.getString("general.markersCondition");
		public static final String NUMBERS_MODE = tomlConfigHandler.getString("general.numbers");
		public static final int UPDATE_INTERVAL = tomlConfigHandler.getInt("general.updateInterval");
	}

	public static class Colors {
		public static final String MARKER_DARK = tomlConfigHandler.getString("colors.markerColorDark");
		public static final String MARKER_BLOCK_LIT = tomlConfigHandler.getString("colors.markerColorBlockLit");
		public static final String MARKER_SKY_LIT = tomlConfigHandler.getString("colors.markerColorSkyLit");
		public static final String NUMBER_BLOCK_DARK = tomlConfigHandler.getString("colors.numberColorBlockDark");
		public static final String NUMBER_BLOCK_LIT = tomlConfigHandler.getString("colors.numberColorBlockLit");
		public static final String NUMBER_SKY_DARK = tomlConfigHandler.getString("colors.numberColorSkyDark");
		public static final String NUMBER_SKY_LIT = tomlConfigHandler.getString("colors.numberColorSkyLit");
	}
}
