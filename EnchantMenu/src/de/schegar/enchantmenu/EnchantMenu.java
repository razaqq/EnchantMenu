package de.schegar.enchantmenu;

import de.schegar.enchantmenu.listener.MenuListener;
import de.schegar.enchantmenu.util.MessageManager;
import de.schegar.enchantmenu.util.SettingsManager;
import java.io.IOException;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class EnchantMenu extends JavaPlugin {
	public SettingsManager sm;
	public MessageManager mm;
	public Economy econ;

	public void onDisable() {
		System.out.println("[EnchantMenu] Plugin stopped");
	}

	public void onEnable() {
		this.sm = new SettingsManager(this);
		this.sm.init();
		this.mm = new MessageManager(this);
		this.mm.init();
		this.sm = new SettingsManager(this);

		if (this.sm.getCfg().getBoolean("Payment.Use Vault")) {
			this.setupEconomy();
		}

		this.getCommand("em").setExecutor(new MenuCommand(this));
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new MenuListener(this), this);
		System.out.println("[EnchantMenu] Plugin started");
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = this.getServer().getServicesManager()
				.getRegistration(Economy.class);
		if (economyProvider != null) {
			this.econ = (Economy) economyProvider.getProvider();
		}

		return this.econ != null;
	}
}