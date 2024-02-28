package lunaticuncle.btalightoverlay.option;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

public class ConfigInteger extends ConfigOption<Integer>{
	public ConfigInteger(String name, String translatedName, String comment, int defaultValue) {
		super(name, translatedName, comment, defaultValue);
	}

	@Override
	public void read(TomlConfigHandler tomlConfigHandler) {
		this.value = tomlConfigHandler.getInt(this.getName());
	}
}
