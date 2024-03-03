package lunaticuncle.btalightoverlay.option;

import net.minecraft.core.lang.I18n;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

public abstract class ConfigOption<T> {
	private final String modID;
	private final String name;
	private final String comment;
	protected final T defaultValue;
	protected T value;

	protected ConfigOption(String modID, String name, String comment, T defaultValue) {
		this.modID = modID;
		this.name = name;
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
		return I18n.getInstance().translateKey(getTranslateKey());
	}

	public String getTranslateKey() {
		return this.modID + "." + this.name;
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
