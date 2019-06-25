package de.schegar.enchantmenu.util;

import de.schegar.enchantmenu.EnchantMenu;
import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class MessageManager {
	private FileConfiguration langCfg;
	private EnchantMenu plugin;
	private SettingsManager sm;
	private String lang;
	public String prefix = "§7[EnchantMenu§7] §3";

	public MessageManager(EnchantMenu plugin) {
		this.plugin = plugin;
		this.sm = plugin.sm;
	}

	public void init() {
		this.lang = this.sm.getLanguage();
		File langFile = new File("plugins/EnchantMenu/LanguageData", this.lang + ".yml");
		this.langCfg = YamlConfiguration.loadConfiguration(langFile);
		this.langCfg.addDefault("missingLevel", "You need at least level %level%");
		this.langCfg.addDefault("missingMoney", "You need at least %price%");
		this.langCfg.addDefault("selectEnchant", "Please select what you want to enchant");
		this.langCfg.addDefault("gotEnchanted", "Your item is now enchanted with %enchantment% at level %levels%");
		this.langCfg.addDefault("validPayment", "Please select a valid payment-method in the config-file");
		this.langCfg.addDefault("categories", "Please choose one of the categories");
		this.langCfg.addDefault("weapons", "weapons");
		this.langCfg.addDefault("armor", "armor");
		this.langCfg.addDefault("tools", "tools");
		this.langCfg.addDefault("bow", "bow");
		this.langCfg.addDefault("timeToWait", "You have to wait %time% seconds");
		this.langCfg.addDefault("price", "Price");
		this.langCfg.addDefault("level", "Level");
		this.langCfg.addDefault("No Enchant", "You can't enchant %item% with %enchantment%");
		this.langCfg.addDefault("Max Enchant", "You already enchanted your item to the maxlevel");
		this.langCfg.addDefault("Permission", "You don't have the right permission to do that");
		this.langCfg.options().copyDefaults(true);

		try {
			this.langCfg.save(langFile);
		} catch (IOException error) {
			error.printStackTrace();
		}

	}

	public FileConfiguration getMsgCfg() {
		return this.langCfg;
	}

	public String getMessage(String name) {
		this.lang = this.sm.getLanguage();
		File langFile = new File("plugins/EnchantMenu/LanguageData", this.lang + ".yml");
		this.langCfg = YamlConfiguration.loadConfiguration(langFile);
		return this.prefix + this.langCfg.getString(name);
	}

	public String getMessageWP(String name) {
		this.lang = this.sm.getLanguage();
		File langFile = new File("plugins/EnchantMenu/LanguageData", this.lang + ".yml");
		this.langCfg = YamlConfiguration.loadConfiguration(langFile);
		return this.langCfg.getString(name);
	}
}