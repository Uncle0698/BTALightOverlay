package lunaticuncle.btalightoverlay;

import lunaticuncle.btalightoverlay.option.ConfigOption;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;

public class ModConfigHandler {
	private ArrayList<ConfigOption<?>> CONFIG_OPTIONS = new ArrayList<>();
	private TomlConfigHandler tomlConfigHandler;
	private final String modID;

	public ModConfigHandler(String modID, TomlConfigHandler tomlConfigHandler) {
		this.modID = modID;
		this.tomlConfigHandler = tomlConfigHandler;
	}

	public void addOption(ConfigOption<?> option) {
		this.CONFIG_OPTIONS.add(option);
	}

	public void addOptions(ArrayList<ConfigOption<?>> options) {
		this.CONFIG_OPTIONS = options;
	}

	public void saveConfig() {
		File configFile = new File(this.tomlConfigHandler.getFilePath());

		Toml newToml = this.tomlConfigHandler.getRawParsed();
		for(ConfigOption<?> option : this.CONFIG_OPTIONS) {
			newToml = option.write(newToml);
		}

		try (OutputStream output = Files.newOutputStream(configFile.toPath())) {
			output.write(newToml.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.tomlConfigHandler = new TomlConfigHandler(this.modID, newToml);
	}

	public void loadConfig() {
		for(ConfigOption<?> option : this.CONFIG_OPTIONS) {
			option.read(this.tomlConfigHandler);
		}
	}

	public void init() {
		loadConfig();
		saveConfig();
	}
}
