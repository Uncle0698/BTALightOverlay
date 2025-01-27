package lunaticuncle.btalightoverlay.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.TextFieldElement;
import net.minecraft.client.gui.options.components.OptionsComponent;
import net.minecraft.client.render.Font;
import net.minecraft.core.lang.I18n;


public class TextBoxComponent implements OptionsComponent {
	private static final Minecraft mc = Minecraft.getMinecraft();
	private final TextFieldElement textField;
	private final String translationKey;


	public TextBoxComponent(String translationKey, String placeholder) {
		this.textField = new TextFieldElement(null, mc.font, 0, 0, 120, 18, "", placeholder);
		this.textField.setMaxStringLength(20);
		this.translationKey = translationKey;
	}


	@Override
	public int getHeight() {
        return 4 + this.textField.height + 4;
	}

	@Override
	public void render(int x, int y, int width, int relativeMouseX, int relativeMouseY) {
		Font fontRenderer = mc.font;

		String translated = I18n.getInstance().translateKey(this.translationKey);
		int textColor = -1; //Normal white color
		if (relativeMouseX >= 0 && relativeMouseX <= width && relativeMouseY >= 2 && relativeMouseY <= this.getHeight() - 2) {
			textColor = -96; //Hovering yellow color
		}

		fontRenderer.drawStringWithShadow(translated, x, y + this.getHeight() / 2 - 4, textColor);

        this.textField.xPosition = x + width - this.textField.width - 1;
		this.textField.yPosition = y + 4;
		this.textField.drawTextBox();
	}

	@Override
	public void tick() {
		this.textField.updateCursorCounter();
	}

	@Override
	public void onMouseClick(int mouseButton, int x, int y, int width, int relativeMouseX, int relativeMouseY) {
		this.textField.setFocused(relativeMouseX >= width - 120 && relativeMouseX <= width && relativeMouseY >= 2 && relativeMouseY <= 22);
	}

	@Override
	public void onMouseMove(int i, int j, int k, int l, int m) {

	}

	@Override
	public void onMouseRelease(int i, int j, int k, int l, int m, int n) {

	}

	@Override
	public void onKeyPress(int keyCode, char character) {
		if (this.textField.isFocused && keyCode != 1) {
			this.textField.textboxKeyTyped(character, keyCode);
		} else if (keyCode == 1) {
			this.textField.setFocused(false);
		}
	}

	@Override
	public boolean matchesSearchTerm(String term) {
		return I18n.getInstance().translateKey(this.translationKey).toLowerCase().contains(term.toLowerCase());
	}
}
