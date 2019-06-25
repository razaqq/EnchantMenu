package de.schegar.enchantmenu;

import de.schegar.enchantmenu.util.MessageManager;
import de.schegar.enchantmenu.util.SettingsManager;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuCommand implements CommandExecutor {
	private EnchantMenu plugin;
	private SettingsManager sm;
	private MessageManager mm;
	private HashMap<Player, Long> cooldowns = new HashMap();
	private Inventory menu = null;

	public MenuCommand(EnchantMenu plugin) {
		this.plugin = plugin;
		this.sm = plugin.sm;
		this.mm = plugin.mm;
	}

	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (!label.equalsIgnoreCase("em")) {
			return true;
		} else if (!(cs instanceof Player)) {
			cs.sendMessage("§cThis command can only be run by a player");
			return true;
		} else {
			Player p = (Player) cs;
			Long time = System.currentTimeMillis();
			if (args.length == 0) {
				cs.sendMessage(this.mm.getMessage("categories"));
				cs.sendMessage("- " + convert_upper(this.mm.getMessageWP("weapons")));
				cs.sendMessage("- " + convert_upper(this.mm.getMessageWP("tools")));
				cs.sendMessage("- " + convert_upper(this.mm.getMessageWP("armor")));
				cs.sendMessage("- " + convert_upper(this.mm.getMessageWP("bow")));
				return true;
			} else {
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
						if (p.hasPermission("em.reload")) {
							Bukkit.getServer().getPluginManager().disablePlugin(this.plugin);
							Bukkit.getServer().getPluginManager().enablePlugin(this.plugin);
							p.sendMessage(this.mm.prefix + "Plugin reloaded");
							return true;
						} else {
							p.sendMessage("§c" + this.mm.getMessage("Permission"));
							return true;
						}
					}

					if (cs.hasPermission("em.open")) {
						if (this.sm.getPCfg().getBoolean("Payment.Cooldowns.Use Cooldowns")
								&& this.cooldowns.containsKey(p)) {
							Long timeToWait = this.sm.getCfg().getLong("Payment.Cooldowns.Time to wait in seconds");
							Long lastUsage = (Long) this.cooldowns.get(p.getName());
							if (lastUsage + timeToWait * 1000L > time) {
								Long remainingTime = lastUsage + (timeToWait * 1000L - time);
								p.sendMessage(this.mm.getMessage("timeToWait").replace("%time%",
										String.valueOf(remainingTime / 1000L)));
								return true;
							}
						}

						if (args[0].equalsIgnoreCase(this.mm.getMessageWP("weapons"))) {
							this.weaponMenu(p);
							this.initCooldown(p, time);
							p.getPlayer().openInventory(this.menu);
						} else if (args[0].equalsIgnoreCase(this.mm.getMessageWP("tools"))) {
							this.toolMenu(p);
							this.initCooldown(p, time);
							p.getPlayer().openInventory(this.menu);
						} else if (args[0].equalsIgnoreCase(this.mm.getMessageWP("armor"))) {
							this.armorMenu(p);
							this.initCooldown(p, time);
							p.getPlayer().openInventory(this.menu);
						} else if (args[0].equalsIgnoreCase(this.mm.getMessageWP("bow"))) {
							this.bowMenu(p);
							this.initCooldown(p, time);
							p.getPlayer().openInventory(this.menu);
						} else {
							cs.sendMessage(this.mm.getMessage("categories"));
							cs.sendMessage("- " + convert_upper(this.mm.getMessageWP("weapons")));
							cs.sendMessage("- " + convert_upper(this.mm.getMessageWP("tools")));
							cs.sendMessage("- " + convert_upper(this.mm.getMessageWP("armor")));
							cs.sendMessage("- " + convert_upper(this.mm.getMessageWP("bow")));
						}

						return true;
					}

					p.sendMessage("§c" + this.mm.getMessage("Permission"));
				} else if (args.length > 1) {
					return false;
				}

				return true;
			}
		}
	}

	public String description(String menuName, Integer number, Player p) {
		String description = null;
		Double price = 0.0D;
		double level = 0.0D;
		Enchantment enchant = null;
		switch (menuName.hashCode()) {
			case -791821796 :
				if (menuName.equals("weapon")) {
					switch (number) {
						case 1 :
							price = this.sm.getPCfg().getDouble("Weapons.Sharpness.Price");
							level = (double) this.sm.getPCfg().getInt("Weapons.Sharpness.Level");
							enchant = Enchantment.DAMAGE_ALL;
							break;
						case 2 :
							price = this.sm.getPCfg().getDouble("Weapons.Smite.Price");
							level = (double) this.sm.getPCfg().getInt("Weapons.Smite.Level");
							enchant = Enchantment.DAMAGE_UNDEAD;
							break;
						case 3 :
							price = this.sm.getPCfg().getDouble("Weapons.Bane of Arthropods.Price");
							level = (double) this.sm.getPCfg().getInt("Weapons.Bane of Arthropods.Level");
							enchant = Enchantment.DAMAGE_ARTHROPODS;
							break;
						case 4 :
							price = this.sm.getPCfg().getDouble("Weapons.Knockback.Price");
							level = (double) this.sm.getPCfg().getInt("Weapons.Knockback.Level");
							enchant = Enchantment.KNOCKBACK;
							break;
						case 5 :
							price = this.sm.getPCfg().getDouble("Weapons.Fire Aspect.Price");
							level = (double) this.sm.getPCfg().getInt("Weapons.Fire Aspect.Level");
							enchant = Enchantment.FIRE_ASPECT;
							break;
						case 6 :
							price = this.sm.getPCfg().getDouble("Weapons.Looting.Price");
							level = (double) this.sm.getPCfg().getInt("Weapons.Looting.Level");
							enchant = Enchantment.LOOT_BONUS_MOBS;
						case 7 :
							price = this.sm.getPCfg().getDouble("Weapons.Sweeping Edge.Price");
							level = (double) this.sm.getPCfg().getInt("Weapons.Sweeping Edge.Level");
							enchant = Enchantment.SWEEPING_EDGE;
					}
				}
				break;
			case 97738 :
				if (menuName.equals("bow")) {
					switch (number) {
						case 1 :
							price = this.sm.getPCfg().getDouble("Bow.Power.Price");
							level = (double) this.sm.getPCfg().getInt("Bow.Power.Level");
							enchant = Enchantment.ARROW_DAMAGE;
							break;
						case 2 :
							price = this.sm.getPCfg().getDouble("Bow.Punch.Price");
							level = (double) this.sm.getPCfg().getInt("Bow.Punch.Level");
							enchant = Enchantment.ARROW_KNOCKBACK;
							break;
						case 3 :
							price = this.sm.getPCfg().getDouble("Bow.Flame.Price");
							level = (double) this.sm.getPCfg().getInt("Bow.Flame.Level");
							enchant = Enchantment.ARROW_FIRE;
							break;
						case 4 :
							price = this.sm.getPCfg().getDouble("Bow.Infinity.Price");
							level = (double) this.sm.getPCfg().getInt("Bow.Infinity.Level");
							enchant = Enchantment.ARROW_INFINITE;
					}
				}
				break;
			case 93086015 :
				if (menuName.equals("armor")) {
					switch (number) {
						case 1 :
							price = this.sm.getPCfg().getDouble("Armor.Protection.Price");
							level = (double) this.sm.getPCfg().getInt("Armor.Protection.Level");
							enchant = Enchantment.PROTECTION_ENVIRONMENTAL;
							break;
						case 2 :
							price = this.sm.getPCfg().getDouble("Armor.Fire Protection.Price");
							level = (double) this.sm.getPCfg().getInt("Armor.Fire Protection.Level");
							enchant = Enchantment.PROTECTION_FIRE;
							break;
						case 3 :
							price = this.sm.getPCfg().getDouble("Armor.Feather Falling.Price");
							level = (double) this.sm.getPCfg().getInt("Armor.Feather Falling.Level");
							enchant = Enchantment.PROTECTION_FALL;
							break;
						case 4 :
							price = this.sm.getPCfg().getDouble("Armor.Blast Protection.Price");
							level = (double) this.sm.getPCfg().getInt("Armor.Blast Protection.Level");
							enchant = Enchantment.PROTECTION_EXPLOSIONS;
							break;
						case 5 :
							price = this.sm.getPCfg().getDouble("Armor.Projectile Protection.Price");
							level = (double) this.sm.getPCfg().getInt("Armor.Projectile Protection.Level");
							enchant = Enchantment.PROTECTION_PROJECTILE;
							break;
						case 6 :
							price = this.sm.getPCfg().getDouble("Armor.Respiration.Price");
							level = (double) this.sm.getPCfg().getInt("Armor.Respiration.Level");
							enchant = Enchantment.OXYGEN;
							break;
						case 7 :
							price = this.sm.getPCfg().getDouble("Armor.Aqua Affinity.Price");
							level = (double) this.sm.getPCfg().getInt("Armor.Aqua Affinity.Level");
							enchant = Enchantment.WATER_WORKER;
							break;
						case 8 :
							price = this.sm.getPCfg().getDouble("Armor.Thorns.Price");
							level = (double) this.sm.getPCfg().getInt("Armor.Thorns.Level");
							enchant = Enchantment.THORNS;
						case 9 :
							price = this.sm.getPCfg().getDouble("Armor.Depth Strider.Price");
							level = (double) this.sm.getPCfg().getInt("Armor.Depth Strider.Level");
							enchant = Enchantment.DEPTH_STRIDER;
					}
				}
				break;
			case 110545371 :
				if (menuName.equals("tools")) {
					switch (number) {
						case 1 :
							price = this.sm.getPCfg().getDouble("Tools.Efficiency.Price");
							level = (double) this.sm.getPCfg().getInt("Tools.Efficiency.Level");
							enchant = Enchantment.DIG_SPEED;
							break;
						case 2 :
							price = this.sm.getPCfg().getDouble("Tools.Silk Touch.Price");
							level = (double) this.sm.getPCfg().getInt("Tools.Silk Touch.Level");
							enchant = Enchantment.SILK_TOUCH;
							break;
						case 3 :
							price = this.sm.getPCfg().getDouble("Tools.Unbreaking.Price");
							level = (double) this.sm.getPCfg().getInt("Tools.Unbreaking.Level");
							enchant = Enchantment.DURABILITY;
							break;
						case 4 :
							price = this.sm.getPCfg().getDouble("Tools.Fortune.Price");
							level = (double) this.sm.getPCfg().getInt("Tools.Fortune.Level");
							enchant = Enchantment.LOOT_BONUS_BLOCKS;
					}
				}
		}

		if (p.getInventory().getItemInMainHand().containsEnchantment(enchant) && p.getInventory().getItemInMainHand().getEnchantmentLevel(enchant) != 0) {
			int currentLevel = p.getInventory().getItemInMainHand().getEnchantmentLevel(enchant);
			double multi = this.sm.getPCfg().getDouble("Multiplicator");
			price = price * (double) currentLevel * multi;
			if (currentLevel == 0) {
				level = (double) Math.round(level);
			} else {
				level = Math.ceil(level * (double) currentLevel * multi);
			}
		}

		if (this.sm.getCfg().getBoolean("Payment.Use Vault") && this.sm.getCfg().getBoolean("Payment.Use Levels")) {
			description = ", " + this.mm.getMessageWP("price") + ": " + price + ", " + this.mm.getMessageWP("level")
					+ ": " + level;
		} else if (this.sm.getCfg().getBoolean("Payment.Use Vault")) {
			description = ", " + this.mm.getMessageWP("price") + ": " + price;
		} else if (this.sm.getCfg().getBoolean("Payment.Use Levels")) {
			description = ", " + this.mm.getMessageWP("level") + ": " + level;
		}

		return description;
	}

	public void weaponMenu(Player p) {
		this.menu = p.getPlayer().getServer().createInventory((InventoryHolder) null, 9, "Enchant Menu - Weapons");
		
		ItemStack sharp = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta sharpMeta = sharp.getItemMeta();
		sharpMeta.setDisplayName("Sharpness" + this.description("weapon", 1, p));
		sharp.setItemMeta(sharpMeta);
		
		ItemStack smite = new ItemStack(Material.SKELETON_SKULL);
		ItemMeta smiteMeta = smite.getItemMeta();
		smiteMeta.setDisplayName("Smite" + this.description("weapon", 2, p));
		smite.setItemMeta(smiteMeta);
		
		ItemStack bane = new ItemStack(Material.SPIDER_EYE);
		ItemMeta baneMeta = bane.getItemMeta();
		baneMeta.setDisplayName("Bane of Arthropods" + this.description("weapon", 3, p));
		bane.setItemMeta(baneMeta);
		
		ItemStack knockback = new ItemStack(Material.STICK);
		ItemMeta knockbackMeta = knockback.getItemMeta();
		knockbackMeta.setDisplayName("Knockback" + this.description("weapon", 4, p));
		knockback.setItemMeta(knockbackMeta);
		
		ItemStack fire = new ItemStack(Material.FIRE_CHARGE);
		ItemMeta fireMeta = fire.getItemMeta();
		fireMeta.setDisplayName("Fire Aspect" + this.description("weapon", 5, p));
		fire.setItemMeta(fireMeta);
		
		ItemStack loot = new ItemStack(Material.GOLD_INGOT);
		ItemMeta lootMeta = loot.getItemMeta();
		lootMeta.setDisplayName("Looting" + this.description("weapon", 6, p));
		loot.setItemMeta(lootMeta);
		
		this.menu.setItem(0, sharp);
		this.menu.setItem(1, smite);
		this.menu.setItem(2, bane);
		this.menu.setItem(3, knockback);
		this.menu.setItem(4, fire);
		this.menu.setItem(5, loot);
	}

	public void toolMenu(Player p) {
		this.menu = p.getPlayer().getServer().createInventory((InventoryHolder) null, 9, "Enchant Menu - Tools");
		
		ItemStack istack1 = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta istackMeta1 = istack1.getItemMeta();
		istackMeta1.setDisplayName("Efficiency" + this.description("tools", 1, p));
		istack1.setItemMeta(istackMeta1);
		
		ItemStack istack2 = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta istackMeta2 = istack2.getItemMeta();
		istackMeta2.setDisplayName("Silk Touch" + this.description("tools", 2, p));
		istack2.setItemMeta(istackMeta2);
		
		ItemStack istack3 = new ItemStack(Material.OBSIDIAN);
		ItemMeta istackMeta3 = istack3.getItemMeta();
		istackMeta3.setDisplayName("Unbreaking" + this.description("tools", 3, p));
		istack3.setItemMeta(istackMeta3);
		
		ItemStack istack4 = new ItemStack(Material.GOLD_INGOT);
		ItemMeta istackMeta4 = istack4.getItemMeta();
		istackMeta4.setDisplayName("Fortune" + this.description("tools", 4, p));
		istack4.setItemMeta(istackMeta4);
		
		this.menu.setItem(0, istack1);
		this.menu.setItem(1, istack2);
		this.menu.setItem(2, istack3);
		this.menu.setItem(3, istack4);
	}

	public void armorMenu(Player p) {
		this.menu = p.getPlayer().getServer().createInventory((InventoryHolder) null, 9, "Enchant Menu - Armor");
		
		ItemStack istack = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta istackMeta = istack.getItemMeta();
		istackMeta.setDisplayName("Protection" + this.description("armor", 1, p));
		istack.setItemMeta(istackMeta);
		
		ItemStack istack2 = new ItemStack(Material.FIRE_CHARGE);
		ItemMeta istackMeta2 = istack.getItemMeta();
		istackMeta2.setDisplayName("Fire Protection" + this.description("armor", 2, p));
		istack2.setItemMeta(istackMeta2);
	
		ItemStack istack3 = new ItemStack(Material.FEATHER);
		ItemMeta istackMeta3 = istack.getItemMeta();
		istackMeta3.setDisplayName("Feather Falling" + this.description("armor", 3, p));
		istack3.setItemMeta(istackMeta3);
		
		ItemStack istack4 = new ItemStack(Material.TNT);
		ItemMeta istackMeta4 = istack.getItemMeta();
		istackMeta4.setDisplayName("Blast Protection" + this.description("armor", 4, p));
		istack4.setItemMeta(istackMeta4);
		
		ItemStack istack5 = new ItemStack(Material.ARROW);
		ItemMeta istackMeta5 = istack.getItemMeta();
		istackMeta5.setDisplayName("Projectile Protection" + this.description("armor", 5, p));
		istack5.setItemMeta(istackMeta5);
		
		ItemStack istack6 = new ItemStack(Material.LILY_PAD);
		ItemMeta istackMeta6 = istack.getItemMeta();
		istackMeta6.setDisplayName("Respiration" + this.description("armor", 6, p));
		istack6.setItemMeta(istackMeta6);
		
		ItemStack istack7 = new ItemStack(Material.WATER_BUCKET);
		ItemMeta istackMeta7 = istack.getItemMeta();
		istackMeta7.setDisplayName("Aqua Affinity" + this.description("armor", 7, p));
		istack7.setItemMeta(istackMeta7);
		
		ItemStack istack8 = new ItemStack(Material.STONE_SWORD);
		ItemMeta istackMeta8 = istack.getItemMeta();
		istackMeta8.setDisplayName("Thorns" + this.description("armor", 8, p));
		istack8.setItemMeta(istackMeta8);
		
		ItemStack istack9 = new ItemStack(Material.DIAMOND_BOOTS);
		ItemMeta istackMeta9 = istack.getItemMeta();
		istackMeta9.setDisplayName("Depth Strider" + this.description("armor", 9, p));
		istack9.setItemMeta(istackMeta9);

		this.menu.setItem(0, istack);
		this.menu.setItem(1, istack2);
		this.menu.setItem(2, istack3);
		this.menu.setItem(3, istack4);
		this.menu.setItem(4, istack5);
		this.menu.setItem(5, istack6);
		this.menu.setItem(6, istack7);
		this.menu.setItem(7, istack8);
		this.menu.setItem(8, istack9);
	}

	public void bowMenu(Player p) {
		this.menu = p.getPlayer().getServer().createInventory((InventoryHolder) null, 9, "Enchant Menu - Bow");
		
		ItemStack istack = new ItemStack(Material.BOW);
		ItemMeta istackMeta = istack.getItemMeta();
		istackMeta.setDisplayName("Power" + this.description("bow", 1, p));
		istack.setItemMeta(istackMeta);
		
		ItemStack istack2 = new ItemStack(Material.STICK);
		ItemMeta istackMeta2 = istack.getItemMeta();
		istackMeta2.setDisplayName("Punch" + this.description("bow", 2, p));
		istack2.setItemMeta(istackMeta2);
		
		ItemStack istack3 = new ItemStack(Material.FIRE);
		ItemMeta istackMeta3 = istack.getItemMeta();
		istackMeta3.setDisplayName("Flame" + this.description("bow", 3, p));
		istack3.setItemMeta(istackMeta3);
		
		ItemStack istack4 = new ItemStack(Material.ARROW);
		ItemMeta istackMeta4 = istack.getItemMeta();
		istackMeta4.setDisplayName("Infinity" + this.description("bow", 4, p));
		istack4.setItemMeta(istackMeta4);
		
		this.menu.setItem(0, istack);
		this.menu.setItem(1, istack2);
		this.menu.setItem(2, istack3);
		this.menu.setItem(3, istack4);
	}

	public void initCooldown(Player p, Long time) {
		if (this.sm.getCfg().getBoolean("Payment.Cooldowns.Use Cooldowns")) {
			this.cooldowns.put(p, time);
		}

	}

	static String convert_upper(String payment) {
		String firstLetter = payment.substring(0, 1).toUpperCase();
		String rest = payment.substring(1);
		return firstLetter + rest;
	}
}