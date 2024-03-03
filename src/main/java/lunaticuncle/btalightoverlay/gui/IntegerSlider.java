package lunaticuncle.btalightoverlay.gui;

import lunaticuncle.btalightoverlay.option.ConfigInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.render.FontRenderer;
import org.lwjgl.opengl.GL11;

public class IntegerSlider extends GuiButton {
	private ConfigInteger option;
	private int min;
	private int max;
	private int range;
	private int interval;
	private int thumbWidth;
	private double thumbInterval;
	private double thumbPosition;
	private boolean isDragging;

	public IntegerSlider(int id, int xPosition, int yPosition, String text, ConfigInteger option) {
		this(id, xPosition, yPosition, 150, 20, text, 8, option, 1);
	}

	public IntegerSlider(int id, int xPosition, int yPosition, int width, int height, String text, int thumbWidth, ConfigInteger option, int interval) {
		super(id, xPosition, yPosition, width, height, text);
		this.thumbWidth = thumbWidth;
		this.option = option;
		this.interval = interval;
		this.min = option.getMinValue();
		this.max = option.getMaxValue();
		this.range = this.max - this.min;
		this.thumbInterval = this.getThumbIntervalValue();
		this.thumbPosition = this.intToPosition();
		this.isDragging = false;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if(!this.visible) {
			return;
		}

		FontRenderer fontRenderer = mc.fontRenderer;

		GL11.glBindTexture(3553, mc.renderEngine.getTexture("/gui/gui.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.drawSliderTrack();
		this.drawSliderThumb();
		this.drawValueString(fontRenderer, mouseX, mouseY);
		this.mouseDragged(mc, mouseX, mouseY);
	}

	private void drawSliderTrack() {
		this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46, this.width / 2, this.height);
		this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46, this.width / 2, this.height);
	}

	private void drawSliderThumb() {
		int thumbHalf = this.thumbWidth / 2;
		this.thumbInterval = this.getThumbIntervalValue();
		this.thumbPosition = this.intToPosition();

		this.drawTexturedModalRect(this.xPosition + (int) this.thumbPosition, this.yPosition, 0, 66, thumbHalf, 20);
		this.drawTexturedModalRect(this.xPosition + (int) this.thumbPosition + thumbHalf, this.yPosition, 200 - thumbHalf, 66, thumbHalf, 20);
	}

	private void drawValueString(FontRenderer fontRenderer, int mouseX, int mouseY) {
		int hoverState = this.getButtonState(this.isHovered(mouseX, mouseY));
		int fontColor;
		switch(hoverState) {
			case 0:
				fontColor = 10526880;
				break;
			case 1:
				fontColor = 14737632;
				break;
			default:
				fontColor = 16777120;
		}
		String displayString = this.option.prefix + this.option.getValue().toString() + this.option.postfix;
		this.drawStringCentered(fontRenderer, displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, fontColor);
	}

	@Override
	public boolean mouseClicked(Minecraft mc, int mouseX, int mouseY) {
		if(!super.mouseClicked(mc, mouseX, mouseY)) {
			return false;
		}

		this.thumbPosition = mouseX - this.xPosition;
		this.option.setValue(positionToInt());
		this.isDragging = true;

		return true;
	}

	@Override
	public void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
		if(!this.visible) {
			return;
		}

		if(!this.isDragging) {
			return;
		}

		this.thumbPosition = mouseX - this.xPosition;
		this.option.setValue(positionToInt());
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY) {
		this.isDragging = false;
	}

	private double intToPosition() {
		return Math.round((double)(this.option.getValue() - this.min) * this.thumbInterval);
	}

	private int positionToInt() {
		return this.min + (int)(this.thumbPosition / this.thumbInterval);
	}

	private double getThumbIntervalValue() {
		return ((double)this.interval * (double)(this.width - this.thumbWidth)) / (double)this.range;
	}


}
