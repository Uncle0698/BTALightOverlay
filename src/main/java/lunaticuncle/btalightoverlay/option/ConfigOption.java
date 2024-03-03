package lunaticuncle.btalightoverlay.option;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

public abstract class ConfigOption<T> {
	private final String name;
	private final String translatedName;
	private final String comment;
	protected final T defaultValue;
	protected T value;

	protected ConfigOption(String name, String translatedName, String comment, T defaultValue) {
		this.name = name;
		this.translatedName = translatedName;
		this.comment = comment;
		this.defaultValue = defaultValue;
		this.value = defaultValue;
	}

	// Read in values from config
	public abstract void read(TomlConfigHandler tomlConfigHandler);

	// Write values into config
	public Toml write(Toml toml) {
		return toml.addEntry(this.getName(), this.getComment(), this.value);
	}

	public String getName() {
		return this.name;
	}

	public String getTranslatedName() {
		return this.translatedName;
	}

	public String getComment() {
		return this.comment;
	}

	public T getDefaultValue() {
		return this.defaultValue;
	}
	public void setValueDefault() {
		this.value = this.defaultValue;
	}

	public T getValue() {
		return this.value;
	}

	public abstract void setValue(T value);
}
