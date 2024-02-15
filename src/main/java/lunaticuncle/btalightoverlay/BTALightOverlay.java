package lunaticuncle.btalightoverlay;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import turniplabs.halplibe.util.GameStartEntrypoint;


public class BTALightOverlay implements ModInitializer, GameStartEntrypoint {
    public static final String MOD_ID = "btalightoverlay";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
		BTALightOverlayInit.initTOMLConfig();
		LOGGER.info("BTALightOverlay config initialized.");
    }

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {
		BTALightOverlayInit.initMod();
		LOGGER.info("BTALightOverlay initialized.");
	}
}
