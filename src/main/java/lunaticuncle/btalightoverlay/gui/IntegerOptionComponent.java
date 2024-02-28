package lunaticuncle.btalightoverlay.gui;

import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.gui.options.components.ButtonComponent;

import lunaticuncle.btalightoverlay.option.ConfigInteger;

public class IntegerOptionComponent extends ButtonComponent {

	private final ConfigInteger option;
	private final GuiSlider slider;

	public IntegerOptionComponent(ConfigInteger option) {
		super(option.getName());
        this.option = option;
		this.slider = new GuiSlider(0, 0, 0, 150, 20, (option.value).toString(), option.value);
    }

	@Override
	public void resetValue() {
		this.option.value = this.option.getDefaultValue();
	}

	@Override
	public boolean isDefault() {
		return (this.option.value).equals(this.option.getDefaultValue());
	}

	@Override
	protected void buttonClicked(int i, int j, int k, int l, int m, int n, int o) {

	}
}
