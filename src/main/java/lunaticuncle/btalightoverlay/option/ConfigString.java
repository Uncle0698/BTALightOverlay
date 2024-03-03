package lunaticuncle.btalightoverlay.option;

import turniplabs.halplibe.util.TomlConfigHandler;

public class ConfigString extends ConfigOption<String>{
	public ConfigString(String modID, String name, String comment, String defaultValue) {
		super(modID, name, comment, defaultValue);
	}

	@Override
	public void read(TomlConfigHandler tomlConfigHandler) {
		this.value = tomlConfigHandler.getString(this.getName());
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}
}
