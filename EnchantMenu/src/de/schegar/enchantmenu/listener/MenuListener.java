package de.schegar.enchantmenu.listener;

import de.schegar.enchantmenu.EnchantMenu;
import de.schegar.enchantmenu.util.MessageManager;
import de.schegar.enchantmenu.util.SettingsManager;
import java.util.HashMap;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.ChatColor;

public class MenuListener implements Listener {
	private EnchantMenu plugin;
	private Economy econ;
	private SettingsManager sm;
	private MessageManager mm;
	private Enchantment enchant;
	private Integer maxLevel;
	private String menu = null;
	private String save;
	private double price;
	private double level;
	ItemStack empty;

	public MenuListener(EnchantMenu plugin) {
		this.empty = new ItemStack(Material.AIR);
		this.plugin = plugin;
		this.econ = plugin.econ;
		this.sm = plugin.sm;
		this.mm = plugin.mm;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void Inventory(InventoryClickEvent cevent) {
		Player p = (Player) cevent.getWhoClicked();
		Inventory i = cevent.getClickedInventory();
		String inv_title = cevent.getView().getTitle();
		if (cevent.getCurrentItem() != null && i != null) {
			try_inventories: {
				switch (inv_title) {
				case "Enchant Menu - Bow":
					this.menu = "bow";
					cevent.setCancelled(true);
					p.updateInventory();
					switch (cevent.getCurrentItem().getType()) {
						case BOW:
							this.enchant = Enchantment.ARROW_DAMAGE;
							this.save = "Bow.Power";
							break try_inventories;
						case ARROW:
							this.enchant = Enchantment.ARROW_INFINITE;
							this.save = "Bow.Infinity";
							break try_inventories;
						case STICK:
							this.enchant = Enchantment.ARROW_KNOCKBACK;
							this.save = "Bow.Punch";
							break try_inventories;
						case FIRE:
							this.enchant = Enchantment.ARROW_FIRE;
							this.save = "Bow.Flame";
							break try_inventories;
						default:
							this.enchant = null;
							this.price = 0.0D;
							this.level = 0.0D;
							this.menu = null;
							break try_inventories;
					}
				case "Enchant Menu - Weapons":
					this.menu = "weapons";
					cevent.setCancelled(true);
					p.updateInventory();
					switch (cevent.getCurrentItem().getType()) {
						case GOLD_INGOT:
							this.enchant = Enchantment.LOOT_BONUS_MOBS;
							this.save = "Weapons.Looting";
							break try_inventories;
						case DIAMOND_SWORD:
							this.enchant = Enchantment.DAMAGE_ALL;
							this.save = "Weapons.Sharpness";
							break try_inventories;
						case STICK:
							this.enchant = Enchantment.KNOCKBACK;
							this.save = "Weapons.Knockback";
							break try_inventories;
						case SPIDER_EYE:
							this.enchant = Enchantment.DAMAGE_ARTHROPODS;
							this.save = "Weapons.Bane of Arthropods";
							break try_inventories;
						case FIRE_CHARGE:
							this.enchant = Enchantment.FIRE_ASPECT;
							this.save = "Weapons.Fire Aspect";
							break try_inventories;
						case SKELETON_SKULL:
							this.enchant = Enchantment.DAMAGE_UNDEAD;
							this.save = "Weapons.Smite";
							break try_inventories;
						default:
							this.enchant = null;
							this.price = 0.0D;
							this.level = 0.0D;
							this.menu = null;
							break try_inventories;
					}
				case "Enchant Menu - Armor":
					this.menu = "armor";
					cevent.setCancelled(true);
					p.updateInventory();
					switch (cevent.getCurrentItem().getType()) {
						case TNT:
							this.enchant = Enchantment.PROTECTION_EXPLOSIONS;
							this.save = "Armor.Blast Protection";
							break try_inventories;
						case WATER_BUCKET:
							this.enchant = Enchantment.WATER_WORKER;
							this.save = "Armor.Aqua Affinity";
							break try_inventories;
						case ARROW:
							this.enchant = Enchantment.PROTECTION_PROJECTILE;
							this.save = "Armor.Projectile Protection";
							break try_inventories;
						case STONE_SWORD:
							this.enchant = Enchantment.THORNS;
							this.save = "Armor.Thorns";
							break try_inventories;
						case FEATHER:
							this.enchant = Enchantment.PROTECTION_FALL;
							this.save = "Armor.Feather Falling";
							break try_inventories;
						case DIAMOND_CHESTPLATE:
							this.enchant = Enchantment.PROTECTION_ENVIRONMENTAL;
							this.save = "Armor.Protection";
							break try_inventories;
						case LILY_PAD:
							this.enchant = Enchantment.OXYGEN;
							this.save = "Armor.Respiration";
							break try_inventories;
						case FIRE_CHARGE:
							this.enchant = Enchantment.PROTECTION_FIRE;
							this.save = "Armor.Fire Protection";
							break try_inventories;
						case DIAMOND_BOOTS:
							this.enchant = Enchantment.DEPTH_STRIDER;
							this.save = "Armor.Depth Strider";
							break try_inventories;
						default:
							this.enchant = null;
							this.price = 0.0D;
							this.level = 0.0D;
							this.menu = null;
							break try_inventories;
					}
				case "Enchant Menu - Tools":
					this.menu = "tools";
					cevent.setCancelled(true);
					p.updateInventory();
					switch (cevent.getCurrentItem().getType()) {
						case OBSIDIAN:
							this.enchant = Enchantment.DURABILITY;
							this.save = "Tools.Unbreaking";
							break try_inventories;
						case REDSTONE_BLOCK:
							this.enchant = Enchantment.SILK_TOUCH;
							this.save = "Tools.Silk Touch";
							break try_inventories;
						case GOLD_INGOT:
							this.enchant = Enchantment.LOOT_BONUS_BLOCKS;
							this.save = "Tools.Fortune";
							break try_inventories;
						case DIAMOND_PICKAXE:
							this.enchant = Enchantment.DIG_SPEED;
							this.save = "Tools.Efficiency";
							break try_inventories;
						default:
							this.enchant = null;
							this.price = 0.0D;
							this.level = 0.0D;
							this.menu = null;
							break try_inventories;
					}
				}
				this.menu = null;
			}

			this.price = this.sm.getPCfg().getDouble(this.save + ".Price");
			this.level = this.sm.getPCfg().getDouble(this.save + ".Level");
			this.maxLevel = this.sm.getPCfg().getInt(this.save + ".MaxLevel");
			
			if (this.menu == null) {
				return;
			}

			cevent.getView().close();

			if (!this.checkItem(p.getInventory().getItemInMainHand(), this.menu)) {
				p.sendMessage(this.mm.getMessage("No Enchant")
						.replace("%item%", "§6" + p.getInventory().getItemInMainHand().getType().toString() + "§3")
						.replace("%enchantment%", "§6" + this.enchant.getKey() + "§3"));
				return;
			}

			if (this.enchant != null && this.price != 0.0D && this.level != 0.0D && this.menu != null) {
				cevent.getView().close();
				if (p.getInventory().getItemInMainHand().getType() == Material.AIR || p.getInventory().getItemInMainHand() == null) {
					p.sendMessage(this.mm.getMessage("selectEnchant"));
					return;
				}

				int newEnchantLevel = p.getInventory().getItemInMainHand().getEnchantmentLevel(this.enchant) + 1;
				if (newEnchantLevel > this.maxLevel) {
					p.sendMessage(this.mm.getMessage("Max Enchant"));
					return;
				}

				int currentLevel = p.getInventory().getItemInMainHand().getEnchantmentLevel(this.enchant);
				double multi = this.sm.getPCfg().getDouble("Multiplicator");
				this.price = this.price * (double) currentLevel * multi;
				if (currentLevel == 0) {
					this.level = (double) Math.round(this.level);
				} else {
					this.level = Math.ceil(this.level * (double) currentLevel * multi);
				}

				if (this.sm.getCfg().getBoolean("Payment.Use Vault")
						&& this.sm.getCfg().getBoolean("Payment.Use Levels")) {
					if (this.econ.has(p, this.price) && (double) p.getLevel() >= this.level) {
						this.econ.withdrawPlayer(p, this.price);
						p.setLevel((int) ((double) p.getLevel() - this.level));
						this.addEnchantment(p, this.enchant, 1);
					} else if (!this.econ.has(p, this.price)) {
						p.sendMessage(
								this.mm.getMessage("missingMoney").replace("%price%", String.valueOf(this.price)));
					} else if ((double) p.getLevel() < this.level) {
						p.sendMessage(
								this.mm.getMessage("missingLevel").replace("%level%", String.valueOf(this.level)));
					}
				} else if (this.sm.getCfg().getBoolean("Payment.Use Vault")) {
					if (this.econ.has(p, this.price)) {
						this.econ.withdrawPlayer(p, this.price);
						this.addEnchantment(p, this.enchant, 1);
					} else {
						p.sendMessage(
								this.mm.getMessage("missingMoney").replace("%price%", String.valueOf(this.price)));
					}
				} else if (this.sm.getCfg().getBoolean("Payment.Use Levels")) {
					if ((double) p.getLevel() >= this.level) {
						p.setLevel((int) ((double) p.getLevel() - this.level));
						this.addEnchantment(p, this.enchant, 1);
					} else {
						p.sendMessage(
								this.mm.getMessage("missingLevel").replace("%level%", String.valueOf(this.level)));
					}
				} else {
					p.sendMessage(this.mm.getMessage("validPayment"));
				}

				cevent.getView().close();
			}
		}

	}

	public boolean checkItem(ItemStack toCheck, String menu) {
		HashMap<String, ItemStack[]> items = this.sm.getItems();
		ItemStack[] allowed = (ItemStack[]) items.get(menu);

		for (int i = 0; i < allowed.length; ++i) {
			if (allowed[i].getType().equals(toCheck.getType())) {
				return true;
			}
		}
		return false;
	}

	public void addEnchantment(Player p, Enchantment en, int level) {
		int currentLevel = p.getInventory().getItemInMainHand().getEnchantmentLevel(en);
		if (currentLevel != 0) {
			int newLevel = currentLevel + 1;
			p.getInventory().getItemInMainHand().addUnsafeEnchantment(en, newLevel);
		} else {
			p.getInventory().getItemInMainHand().addUnsafeEnchantment(en, 1);
		}

		p.sendMessage(this.mm.getMessage("gotEnchanted").replace("%enchantment%", "§6" + this.enchant.getKey() + "§3")
				.replace("%levels%", "§6" + p.getInventory().getItemInMainHand().getEnchantmentLevel(this.enchant) + "§3"));
	}
}