package lunaticuncle.btalightoverlay.option;

import net.minecraft.core.util.helper.MathHelper;
import turniplabs.halplibe.util.TomlConfigHandler;

public class ConfigInteger extends ConfigOption<Integer>{
	private int minValue;
	private int maxValue;

	public ConfigInteger(String name, String translatedName, String comment, int defaultValue, int minValue, int maxValue) {
		super(name, translatedName, comment, defaultValue);
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	@Override
	public void read(TomlConfigHandler tomlConfigHandler) {
		this.value = tomlConfigHandler.getInt(this.getName());
	}

	@Override
	public void setValue(Integer value) {
		this.value = MathHelper.clamp(value, this.minValue, this.maxValue);
	}

	public int getMinValue() {
		return this.minValue;
	}

	public int getMaxValue() {
		return  this.maxValue;
	}
}
