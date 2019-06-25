package de.schegar.enchantmenu.util;

import de.schegar.enchantmenu.EnchantMenu;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class SettingsManager {
	private File configFile = new File("plugins/EnchantMenu", "config.yml");
	private FileConfiguration cfg;
	private File pFile;
	private FileConfiguration pCfg;
	private File iFile;
	private FileConfiguration iCfg;
	private EnchantMenu plugin;

	public SettingsManager(EnchantMenu plugin) {
		this.cfg = YamlConfiguration.loadConfiguration(this.configFile);
		this.pFile = new File("plugins/EnchantMenu", "prices.yml");
		this.pCfg = YamlConfiguration.loadConfiguration(this.pFile);
		this.iFile = new File("plugins/EnchantMenu", "items.yml");
		this.iCfg = YamlConfiguration.loadConfiguration(this.iFile);
		this.plugin = plugin;
	}

	public void init() {
		this.cfg.addDefault("Payment", new String[]{"Use Levels:", "Use Cooldowns:", "Use Vault:"});
		this.cfg.addDefault("Payment.Use Levels", true);
		this.cfg.addDefault("Payment.Cooldowns.Use Cooldowns", false);
		this.cfg.addDefault("Payment.Cooldowns.Time to wait in seconds", 60L);
		this.cfg.addDefault("Payment.Use Vault", false);
		this.cfg.addDefault("Language", "en");
		this.cfg.options().copyDefaults(true);

		try {
			this.cfg.save(this.configFile);
		} catch (IOException var2) {
			var2.printStackTrace();
		}

		this.initPaL();
		this.initItemList();
	}

	public void initPaL() {
		this.pCfg.addDefault("Multiplicator", 2.0D);
		this.pCfg.addDefault("Weapons.Sharpness.Price", 100);
		this.pCfg.addDefault("Weapons.Smite.Price", 100);
		this.pCfg.addDefault("Weapons.Bane of Arthropods.Price", 100);
		this.pCfg.addDefault("Weapons.Knockback.Price", 100);
		this.pCfg.addDefault("Weapons.Fire Aspect.Price", 100);
		this.pCfg.addDefault("Weapons.Looting.Price", 100);
		this.pCfg.addDefault("Tools.Efficiency.Price", 100);
		this.pCfg.addDefault("Tools.Silk Touch.Price", 100);
		this.pCfg.addDefault("Tools.Unbreaking.Price", 100);
		this.pCfg.addDefault("Tools.Fortune.Price", 100);
		this.pCfg.addDefault("Armor.Protection.Price", 100);
		this.pCfg.addDefault("Armor.Fire Protection.Price", 100);
		this.pCfg.addDefault("Armor.Feather Falling.Price", 100);
		this.pCfg.addDefault("Armor.Blast Protection.Price", 100);
		this.pCfg.addDefault("Armor.Projectile Protection.Price", 100);
		this.pCfg.addDefault("Armor.Respiration.Price", 100);
		this.pCfg.addDefault("Armor.Aqua Affinity.Price", 100);
		this.pCfg.addDefault("Armor.Depth Strider.Price", 100);
		this.pCfg.addDefault("Armor.Thorns.Price", 100);
		this.pCfg.addDefault("Bow.Power.Price", 100);
		this.pCfg.addDefault("Bow.Punch.Price", 100);
		this.pCfg.addDefault("Bow.Flame.Price", 100);
		this.pCfg.addDefault("Bow.Infinity.Price", 100);
		this.pCfg.addDefault("Weapons.Sharpness.Level", 1);
		this.pCfg.addDefault("Weapons.Smite.Level", 1);
		this.pCfg.addDefault("Weapons.Bane of Arthropods.Level", 1);
		this.pCfg.addDefault("Weapons.Knockback.Level", 1);
		this.pCfg.addDefault("Weapons.Fire Aspect.Level", 1);
		this.pCfg.addDefault("Weapons.Looting.Level", 1);
		this.pCfg.addDefault("Tools.Efficiency.Level", 1);
		this.pCfg.addDefault("Tools.Silk Touch.Level", 1);
		this.pCfg.addDefault("Tools.Unbreaking.Level", 1);
		this.pCfg.addDefault("Tools.Fortune.Level", 1);
		this.pCfg.addDefault("Armor.Protection.Level", 1);
		this.pCfg.addDefault("Armor.Fire Protection.Level", 1);
		this.pCfg.addDefault("Armor.Feather Falling.Level", 1);
		this.pCfg.addDefault("Armor.Blast Protection.Level", 1);
		this.pCfg.addDefault("Armor.Projectile Protection.Level", 1);
		this.pCfg.addDefault("Armor.Respiration.Level", 1);
		this.pCfg.addDefault("Armor.Aqua Affinity.Level", 1);
		this.pCfg.addDefault("Armor.Depth Strider.Level", 1);
		this.pCfg.addDefault("Armor.Thorns.Level", 1);
		this.pCfg.addDefault("Bow.Power.Level", 1);
		this.pCfg.addDefault("Bow.Punch.Level", 1);
		this.pCfg.addDefault("Bow.Flame.Level", 1);
		this.pCfg.addDefault("Bow.Infinity.Level", 1);
		this.pCfg.addDefault("Weapons.Sharpness.MaxLevel", 3);
		this.pCfg.addDefault("Weapons.Smite.MaxLevel", 3);
		this.pCfg.addDefault("Weapons.Bane of Arthropods.MaxLevel", 3);
		this.pCfg.addDefault("Weapons.Knockback.MaxLevel", 3);
		this.pCfg.addDefault("Weapons.Fire Aspect.MaxLevel", 3);
		this.pCfg.addDefault("Weapons.Looting.MaxLevel", 3);
		this.pCfg.addDefault("Tools.Efficiency.MaxLevel", 3);
		this.pCfg.addDefault("Tools.Silk Touch.MaxLevel", 3);
		this.pCfg.addDefault("Tools.Unbreaking.MaxLevel", 3);
		this.pCfg.addDefault("Tools.Fortune.MaxLevel", 3);
		this.pCfg.addDefault("Armor.Protection.MaxLevel", 3);
		this.pCfg.addDefault("Armor.Fire Protection.MaxLevel", 3);
		this.pCfg.addDefault("Armor.Feather Falling.MaxLevel", 3);
		this.pCfg.addDefault("Armor.Blast Protection.MaxLevel", 3);
		this.pCfg.addDefault("Armor.Projectile Protection.MaxLevel", 3);
		this.pCfg.addDefault("Armor.Respiration.MaxLevel", 3);
		this.pCfg.addDefault("Armor.Aqua Affinity.MaxLevel", 3);
		this.pCfg.addDefault("Armor.Depth Strider.MaxLevel", 3);
		this.pCfg.addDefault("Armor.Thorns.MaxLevel", 3);
		this.pCfg.addDefault("Bow.Power.MaxLevel", 3);
		this.pCfg.addDefault("Bow.Punch.MaxLevel", 3);
		this.pCfg.addDefault("Bow.Flame.MaxLevel", 3);
		this.pCfg.addDefault("Bow.Infinity.MaxLevel", 1);
		this.pCfg.options().copyDefaults(true);

		try {
			this.pCfg.save(this.pFile);
		} catch (IOException var2) {
			var2.printStackTrace();
		}

	}

	public void initItemList() {
		String[] weapons = new String[]{"DIAMOND_SWORD", "DIAMOND_AXE", "GOLDEN_SWORD", "GOLDEN_AXE", "IRON_SWORD",
				"IRON_AXE", "STONE_SWORD", "STONE_AXE", "WOODEN_SWORD", "WOODEN_AXE"};
		String[] armor = new String[]{"DIAMOND_HELMET", "DIAMOND_CHESTPLATE", "DIAMOND_LEGGINGS", "DIAMOND_BOOTS",
				"IRON_HELMET", "IRON_CHESTPLATE", "IRON_LEGGINGS", "IRON_BOOTS", "CHAINMAIL_HELMET",
				"CHAINMAIL_CHESTPLATE", "CHAINMAIL_LEGGINGS", "CHAINMAIL_BOOTS", "GOLDEN_HELMET", "GOLDEN_CHESTPLATE",
				"GOLDEN_LEGGINGS", "GOLDEN_BOOTS", "LEATHER_HELMET", "LEATHER_CHESTPLATE", "LEATHER_LEGGINGS",
				"LEATHER_BOOTS"};
		String[] tools = new String[]{"DIAMOND_PICKAXE", "DIAMOND_AXE", "DIAMOND_SHOVEL", "DIAMOND_HOE", "GOLDEN_PICKAXE",
				"GOLDEN_AXE", "GOLDEN_SHOVEL", "GOLDEN_HOE", "IRON_PICKAXE", "IRON_AXE", "IRON_SHOVEL", "IRON_HOE",
				"STONE_PICKAXE", "STONE_AXE", "STONE_SHOVEL", "STONE_HOE", "WOODEN_PICKAXE", "WOODEN_AXE", "WOODEN_SHOVEL",
				"WOODEN_HOE"};
		String[] bow = new String[]{"BOW"};
		this.iCfg.addDefault("Weapons", weapons);
		this.iCfg.addDefault("Tools", tools);
		this.iCfg.addDefault("Armor", armor);
		this.iCfg.addDefault("Bow", bow);
		this.iCfg.options().copyDefaults(true);

		try {
			this.iCfg.save(this.iFile);
		} catch (IOException error) {
			error.printStackTrace();
		}

	}

	public HashMap<String, ItemStack[]> getItems() {
		HashMap<String, ItemStack[]> items = new HashMap();
		ArrayList<String> weapons = (ArrayList) this.iCfg.getList("Weapons");
		ArrayList<String> tools = (ArrayList) this.iCfg.getList("Tools");
		ArrayList<String> armor = (ArrayList) this.iCfg.getList("Armor");
		ArrayList<String> bow = (ArrayList) this.iCfg.getList("Bow");
		ItemStack[] weaponsS = new ItemStack[weapons.size()];
		ItemStack[] toolsS = new ItemStack[tools.size()];
		ItemStack[] armorS = new ItemStack[armor.size()];
		ItemStack[] bowS = new ItemStack[bow.size()];		
		
		int i;
		for (i = 0; i < weapons.size(); ++i) {
			weaponsS[i] = new ItemStack(Material.getMaterial(((String) weapons.get(i)).replace("Material.", "")));
		}

		for (i = 0; i < tools.size(); ++i) {
			toolsS[i] = new ItemStack(Material.getMaterial(((String) tools.get(i)).replace("Material.", "")));
		}

		for (i = 0; i < armor.size(); ++i) {
			armorS[i] = new ItemStack(Material.getMaterial(((String) armor.get(i)).replace("Material.", "")));
		}

		for (i = 0; i < bow.size(); ++i) {
			bowS[i] = new ItemStack(Material.getMaterial(((String) bow.get(i)).replace("Material.", "")));
		}

		items.put("weapons", weaponsS);
		items.put("tools", toolsS);
		items.put("armor", armorS);
		items.put("bow", bowS);
		return items;
	}

	public FileConfiguration getCfg() {
		return this.cfg;
	}

	public String getLanguage() {
		return this.cfg.getString("Language");
	}

	public FileConfiguration getPCfg() {
		return this.pCfg;
	}
}