package user.uziza.MMCraft;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class MMCraftCommands implements CommandExecutor {
	public String statblock = "statblock";
	public String toggleadv = "toggleadv";
	
	private MMCraft plugin;
	
	public MMCraftCommands(MMCraft p) {
        plugin = p;
    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		if (command.getName().equals("statblock")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.getGameMode().equals(GameMode.CREATIVE)) {
					ItemStack item = new ItemStack(Material.STICK, 1);
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(ChatColor.YELLOW + "StatBlock");
					ArrayList<String> lore = new ArrayList<String>();
					lore.add("toggleadv: false");
					meta.setLore(lore);
					item.setItemMeta(meta);
					player.getInventory().addItem(item);
				}
			}
			return true;
		}
		else if (command.getName().equals("toggleadv")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.getGameMode().equals(GameMode.CREATIVE)) {
					ItemStack item = player.getInventory().getItemInHand();
					ItemMeta meta = item.getItemMeta();
					if (args.length == 0) {
						if (meta.getDisplayName().equals(ChatColor.YELLOW + "StatBlock")) {
							if (meta.getLore().get(0).contains("false")) {
								ArrayList<String> lore = new ArrayList<String>();
								lore.add("toggleadv: true");
								meta.setLore(lore);
								item.setItemMeta(meta);
							}
							else {
								ArrayList<String> lore = new ArrayList<String>();
								lore.add("toggleadv: false");
								meta.setLore(lore);
								item.setItemMeta(meta);
							}
						}
					}
					else if (args.length == 1) {
						if (args[0].equals("true")) {
							ArrayList<String> lore = new ArrayList<String>();
							lore.add("toggleadv: true");
							meta.setLore(lore);
							item.setItemMeta(meta);
						}
						else if (args[0].equals("false")) {
							ArrayList<String> lore = new ArrayList<String>();
							lore.add("toggleadv: false");
							meta.setLore(lore);
							item.setItemMeta(meta);
						}
					}
					return true;
				}
			}
		}
		return false;
	}
	
}
