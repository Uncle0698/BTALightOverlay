package lunaticuncle.btalightoverlay.option;

import net.minecraft.core.util.helper.MathHelper;
import turniplabs.halplibe.util.TomlConfigHandler;

public class ConfigInteger extends ConfigOption<Integer>{
	private int minValue;
	private int maxValue;
	public String prefix;
	public String postfix;

	public ConfigInteger(String modID, String name, String comment, int defaultValue, int minValue, int maxValue) {
		this(modID, name, comment, defaultValue, minValue, maxValue, "","");
	}

	public ConfigInteger(String modID, String name, String comment, int defaultValue, int minValue, int maxValue, String prefix, String postfix) {
		super(modID, name, comment, defaultValue);
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.setValue(this.value);
		this.prefix = prefix;
		this.postfix = postfix;
	}

	@Override
	public void read(TomlConfigHandler tomlConfigHandler) {
		this.setValue(tomlConfigHandler.getInt(this.getName()));
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
