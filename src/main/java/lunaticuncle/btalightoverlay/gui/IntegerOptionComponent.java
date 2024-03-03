package lunaticuncle.btalightoverlay.gui;

import net.minecraft.client.gui.options.components.ButtonComponent;

import lunaticuncle.btalightoverlay.option.ConfigInteger;

public class IntegerOptionComponent extends ButtonComponent {

	private final ConfigInteger option;
	private final IntegerSlider slider;

	public IntegerOptionComponent(ConfigInteger option) {
		super(option.getTranslateKey());
        this.option = option;
		this.slider = new IntegerSlider(0, 0, 0, 150, 20, option.getValue().toString(), 8, option, 1);
    }

	@Override
	public void resetValue() {
		this.option.setValueDefault();
	}

	@Override
	public boolean isDefault() {
		return (this.option.getValue()).equals(this.option.getDefaultValue());
	}

	@Override
	protected void buttonClicked(int mouseButton, int x, int y, int width, int height, int relativeMouseX, int relativeMouseY) {
		this.slider.mouseClicked(mc, this.slider.xPosition + relativeMouseX, this.slider.yPosition + relativeMouseY);
	}

	@Override
	protected void buttonDragged(int x, int y, int width, int height, int relativeMouseX, int relativeMouseY) {

	}

	@Override
	protected void buttonReleased(int mouseButton, int x, int y, int width, int height, int relativeMouseX, int relativeMouseY) {
		this.slider.mouseReleased(this.slider.xPosition + relativeMouseX, this.slider.yPosition + relativeMouseY);
	}


	@Override
	protected void renderButton(int x, int y, int relativeButtonX, int relativeButtonY, int buttonWidth, int buttonHeight, int relativeMouseX, int relativeMouseY) {
		super.renderButton(x, y, relativeButtonX, relativeButtonY, buttonWidth, buttonHeight, relativeMouseX, relativeMouseY);
		this.slider.xPosition = x + relativeButtonX;
		this.slider.yPosition = y + relativeButtonY;
		this.slider.width = buttonWidth;
		this.slider.height = buttonHeight;
		this.slider.drawButton(mc, x + relativeMouseX, y + relativeMouseY);
	}
}
