package user.uziza.MMCraft;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MMCraft extends JavaPlugin implements Listener {
	private MMCraftCommands commands = new MMCraftCommands(this); 
	public void onEnable() {
		Boolean resources = new File(this.getDataFolder() + "/resources/").mkdirs();
		this.getCommand(commands.statblock).setExecutor(commands);
		this.getCommand(commands.toggleadv).setExecutor(commands);
		Bukkit.getPluginManager().registerEvents(new StatBlock(this), this);
	}
	
	public void onDisable() {
		
	}

}