package lunaticuncle.btalightoverlay.gui;

import java.util.ArrayList;
import java.util.List;

public class TextBoxComponents {
	private final List<TextBoxComponent> components = new ArrayList<>();

	public TextBoxComponents(){}

	public void addTextBoxComponent(TextBoxComponent textBoxComponent) {
		this.components.add(textBoxComponent);
	}

	public List<TextBoxComponent> getComponents() {
		return components;
	}
}
