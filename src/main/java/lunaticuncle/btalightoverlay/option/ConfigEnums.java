package lunaticuncle.btalightoverlay.option;

import turniplabs.halplibe.util.TomlConfigHandler;

public class ConfigEnums extends ConfigOption<Enum>{
	protected ConfigEnums(String modID, String name, String comment, Enum defaultValue) {
		super(modID, name, comment, defaultValue);
	}

	@Override
	public void read(TomlConfigHandler tomlConfigHandler) {

	}

	@Override
	public void setValue(Enum value) {

	}
}
