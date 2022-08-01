package user.uziza.MMCraft;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;

public class StatBlock implements Listener {
	
	private MMCraft plugin;
	
	public StatBlock(MMCraft p) {
        plugin = p;
    }
	
	private Map<UUID, Entity> checking = new HashMap<>();
	private Map<UUID, JsonObject> checking2 = new HashMap<>();
	@EventHandler
	public void onInventoryCloseEvent(InventoryCloseEvent e) {
		Player player = (Player) e.getPlayer();
		if (e.getView().getTitle().contains("(SB)")) {
			checking.remove(player.getUniqueId());
			checking2.remove(player.getUniqueId());
		}
	}
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		
		if (e.getView().getTitle().contains("(SB)")) {
			
			String roll_val = "/roll 1d20";
			//ah
			if (player.getItemInHand().getItemMeta().getLore().get(0).contains("true")) {
				roll_val = "/rollmax 2d20";
			}
			
			JsonObject f_MOB = checking2.get(player.getUniqueId());
			
			String str = f_MOB.get("str").toString();
			String dex = f_MOB.get("dex").toString();
			String con = f_MOB.get("con").toString();
			String intell = f_MOB.get("int").toString();
			String wis = f_MOB.get("wis").toString();
			String cha = f_MOB.get("cha").toString();
			
			int str_modi = 0;
			if (Integer.valueOf(str) < 10) {
				str_modi = (int) (Integer.valueOf(str)-10);
			}
			else {
				str_modi = (int) (Integer.valueOf(str)-10)/2;
			}
			
			String str_mod = "";
			if (str_modi >= 0) {
				str_mod = "+"+str_modi;
			}
			else {
				str_mod = String.valueOf(str_modi);
			}
			
			int dex_modi = 0;
			if (Integer.valueOf(dex) < 10) {
				dex_modi = (int) (Integer.valueOf(dex)-10);
			}
			else {
				dex_modi = (int) (Integer.valueOf(dex)-10)/2;
			}
			
			String dex_mod = "";
			if (dex_modi >= 0) {
				dex_mod = "+"+dex_modi;
			}
			else {
				dex_mod = String.valueOf(dex_modi);
			}
			
			
			
            int con_modi = 0;
			if (Integer.valueOf(con) < 10) {
				con_modi = (int) (Integer.valueOf(con)-10);
			}
			else {
				con_modi = (int) (Integer.valueOf(con)-10)/2;
			}
			
			String con_mod = "";
			if (con_modi >= 0) {
				con_mod = "+"+con_modi;
			}
			else {
				con_mod = String.valueOf(con_modi);
			}
			
			
			
			int intell_modi = 0;
			if (Integer.valueOf(intell) < 10) {
				intell_modi = (int) (Integer.valueOf(intell)-10);
			}
			else {
				intell_modi = (int) (Integer.valueOf(intell)-10)/2;
			}
			
			String intell_mod = "";
			if (intell_modi >= 0) {
				intell_mod = "+"+intell_modi;
			}
			else {
				intell_mod = String.valueOf(intell_modi);
			}
			
			
			//FIX1
            int wis_modi = 0;
			if (Integer.valueOf(wis) < 10) {
				wis_modi = (int) (Integer.valueOf(wis)-10+2);
			}
			else {
				wis_modi = (int) (Integer.valueOf(wis)-10+2)/2;
			}
			
			String wis_mod = "";
			if (wis_modi >= 0) {
				wis_mod = "+"+wis_modi;
			}
			else {
				wis_mod = String.valueOf(wis_modi);
			}
			
			int cha_modi = 0;
			if (Integer.valueOf(cha) < 10) {
				cha_modi = (int) (Integer.valueOf(cha)-10);
			}
			else {
				cha_modi = (int) (Integer.valueOf(cha)-10)/2;
			}
			
			String cha_mod = "";
			if (cha_modi >= 0) {
				cha_mod = "+"+cha_modi;
			}
			else {
				cha_mod = String.valueOf(cha_modi);
			}
			
			if (e.getCurrentItem() != null) {
			switch(e.getCurrentItem().getType()) {
				case DIAMOND_SWORD:
					player.closeInventory();
					JsonArray action = (JsonArray) f_MOB.get("action");
		            Iterator action_I = action.iterator();
		            
					List<Object> action_List = new ArrayList<Object>();
					
					while (action_I.hasNext()) {
						JsonObject a = (JsonObject) action_I.next();
						String action_name = a.get("name").toString();
						
						JsonArray action_entries = (JsonArray) a.get("entries");
						
						action_List.add(Arrays.asList(action_name, action_entries.get(0).getAsString()));
					}
					
					int a_count = 0;
					Iterator a = action_List.iterator();
					
					player.sendMessage("===Action===");
					while (a.hasNext()) {
						List<String> action_next = (List<String>) a.next();
							String word = a_count+": "+ action_next.get(0).replace("\"", "");
							
							if (action_next.get(0).replace("\"", "").toLowerCase().equals("multiattack")) {
								TextComponent multiattack = new TextComponent(word);
								multiattack.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(action_next.get(1)).create()));
								player.spigot().sendMessage(multiattack);
							}
							else if (action_next.get(1).contains("@hit")) {
								
								String[] damages = action_next.get(1).split("@damage ");
								damages[0] = "";
								
								String atkr = "";
								for (String d: damages) {
									String damage = d.split("}")[0].replace(" ", "");
									atkr += damage+" ";
								}
								atkr = atkr.trim().replace(" ", "+");
								
								TextComponent roll_dmg = new TextComponent("- damage");
								roll_dmg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/roll "+atkr));
								//roll_dmg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(action_next.get(1).split("@damage ")[1].split("}")[0].split("d")[1].split(" ")[0]).create()));
								
								String atk = action_next.get(1).replace("@hit", "@GO").split("@GO")[1].split("}")[0].replace(" ", "");
								if (!atk.contains("-")) {
									atk = "+"+atk;
								}
								TextComponent roll_atk = new TextComponent("- attack");
								roll_atk.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+atk));
								
								String disclaimer = "";
								
								if (action_next.get(1).contains("@dc")) {
									disclaimer = "*";
								}
								
								player.sendMessage(word + disclaimer);
								player.spigot().sendMessage(roll_dmg);
								player.spigot().sendMessage(roll_atk);
							}
							else if (action_next.get(1).contains("@dc")){
								
								TextComponent roll_dmg = new TextComponent("- damage");
								roll_dmg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/roll "+action_next.get(1).split("@damage ")[1].split("}")[0].replace(" ", "")));
								//roll_dmg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(action_next.get(1).split("@damage ")[1].split("}")[0].split("d")[1].split(" ")[0]).create()));
								
								String sav = action_next.get(1).replace("@dc", "@GO").split("@GO")[1].split("}")[0].replace(" ", "");
								String sav_type = "";
								
								if (action_next.get(1).toLowerCase().contains("strength")) {
									sav_type = "STR";
								}
								else if (action_next.get(1).toLowerCase().contains("dexterity")) {
									sav_type = "DEX";
								}
								else if (action_next.get(1).toLowerCase().contains("constitution")) {
									sav_type = "CON";
								}
								else if (action_next.get(1).toLowerCase().contains("intellegence")) {
									sav_type = "INT";
								}
								else if (action_next.get(1).toLowerCase().contains("wisdom")) {
									sav_type = "WIS";
								}
								else if (action_next.get(1).toLowerCase().contains("charisma")) {
									sav_type = "CHA";
								}
								
								player.sendMessage(word + " " + "(" +"DC " + sav + " " + sav_type + ")");
								player.spigot().sendMessage(roll_dmg);
							}
							else if (!action_next.get(1).contains("@hit") & !action_next.get(1).contains("@dc")) {
								player.sendMessage(word);
							}
							
							a_count++;
					}
					break;
				case OAK_SIGN:
					player.closeInventory();
					
					player.sendMessage("===Ability-Check===");
					TextComponent STR = new TextComponent("STR"+"("+str_mod+")");
					STR.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+str_mod));
					STR.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(str).create()));
					
					BaseComponent DEX = new TextComponent("DEX"+"("+dex_mod+")");
					DEX.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+dex_mod));
					DEX.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(dex).create()));
					
					BaseComponent CON = new TextComponent("CON"+"("+con_mod+")");
					CON.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+con_mod));
					CON.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(con).create()));
					
					BaseComponent INT = new TextComponent("INT"+"("+intell_mod+")");
					INT.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+intell_mod));
					INT.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(intell).create()));
					
					BaseComponent WIS = new TextComponent("WIS"+"("+wis_mod+")");
					WIS.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+wis_mod));
					WIS.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(wis).create()));
					
					BaseComponent CHA = new TextComponent("CHA"+"("+cha_mod+")");
					CHA.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+cha_mod));
					CHA.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(cha).create()));
					
					player.spigot().sendMessage(STR);
					player.spigot().sendMessage(DEX);
					player.spigot().sendMessage(CON);
					player.spigot().sendMessage(INT);
					player.spigot().sendMessage(WIS);
					player.spigot().sendMessage(CHA);
					
					break;
				case HONEY_BOTTLE:
					player.closeInventory();
					List<String> vulnerable_List = new ArrayList<String>();
					if (f_MOB.get("vulnerable") != null) {
						JsonArray vulnerable = (JsonArray) f_MOB.get("vulnerable");
						Iterator vulnerable_i = vulnerable.iterator();
						
						while (vulnerable_i.hasNext()) {
							String v_i = vulnerable_i.next().toString();
							vulnerable_List.add(v_i);
						}
					}
					
					
					List<String> resist_List = new ArrayList<String>();
					if (f_MOB.get("resist") != null) {
						JsonArray resist = (JsonArray) f_MOB.get("resist");
						Iterator resist_i = resist.iterator();
						
						
						while (resist_i.hasNext()) {
							Object r_i = (Object) resist_i.next();
							try {
								JsonArray r_i_i = (JsonArray) ((JsonObject) r_i).get("resist");
								
								for (int i = 0; i < r_i_i.size(); i++) {
									resist_List.add(r_i_i.get(i).getAsString());
								}
								
							}
							catch (Exception er) {
								JsonElement r_i_i = (JsonElement) r_i;
								resist_List.add(r_i_i.getAsString());
							}
						}
					}
					
					
					List<String> immune_List = new ArrayList<String>();
					if (f_MOB.get("immune") != null) {
						JsonArray immune = (JsonArray) f_MOB.get("immune");
						Iterator immune_i = immune.iterator();
						
						while (immune_i.hasNext()) {
							String i_i = immune_i.next().toString();
							immune_List.add(i_i);
						}
					}
					
					List<String> conditionImmune_List = new ArrayList<String>();
					if (f_MOB.get("conditionImmune") != null) {
						JsonArray conditionImmune = (JsonArray) f_MOB.get("conditionImmune");
						Iterator conditionImmune_i = conditionImmune.iterator();
						
						while (conditionImmune_i.hasNext()) {
							String c_i = conditionImmune_i.next().toString();
							conditionImmune_List.add(c_i);
						}
					}
					
					player.sendMessage("===DR/Immunities===");
					if (resist_List != null) {
						player.sendMessage("Damage Resistances: "+ resist_List.toString().replace("\"", "").replace("[", "").replace("]", "").replace("\"", ""));
					}
					else {
						player.sendMessage("Damage Resistances: None");
					}
					if (immune_List != null) {
						player.sendMessage("Damage Immunities: "+ immune_List.toString().replace("[", "").replace("]", "").replace("\"", ""));
					}
					else {
						player.sendMessage("Damage Immunities: None");
					}
					if (vulnerable_List != null) {
						player.sendMessage("Damage Vulnerabilities: "+ vulnerable_List.toString().replace("[", "").replace("]", "").replace("\"", ""));
					}
					else {
						player.sendMessage("Damage Vulnerabilities: None");
					}
					if (conditionImmune_List != null) {
						player.sendMessage("Condition Immunities: "+ conditionImmune_List.toString().replace("[", "").replace("]", "").replace("\"", ""));
					}
					else {
						player.sendMessage("Condition Immunities: None");
					}
					break;
				case ENDER_EYE:
					player.closeInventory();
					TextComponent INIT_TXT = new TextComponent("Initiative");
					
					int init_hover = 10+Integer.valueOf(dex_mod);
					
					INIT_TXT.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+dex_mod));
					INIT_TXT.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(init_hover)).create()));
					
					String PER_VAL = wis_mod;
					
					TextComponent PERC_TXT = new TextComponent("Perception");
					if (f_MOB.get("skill") != null) {
						JsonObject skill = (JsonObject) f_MOB.get("skill");
						if (skill.get("perception") != null) {
							PER_VAL = skill.get("perception").getAsString();
						}
					}
					
					int per_hover = 10+Integer.valueOf(PER_VAL);
					
					PERC_TXT.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+PER_VAL));
					PERC_TXT.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(per_hover)).create()));
					
					player.spigot().sendMessage(INIT_TXT);
					player.spigot().sendMessage(PERC_TXT);
					break;
				case ARROW:
					player.closeInventory();
					player.sendMessage("===Reactions===");
					
					List<Object> reaction_List = new ArrayList<Object>();
					if (f_MOB.get("reaction") != null) {
                        JsonArray reaction = (JsonArray) f_MOB.get("reaction");
                        Iterator reaction_I = reaction.iterator();
                        
						
						
						while (reaction_I.hasNext()) {
							JsonObject r = (JsonObject) reaction_I.next();
							String reaction_name = r.get("name").toString();
							JsonArray reaction_entries = (JsonArray) r.get("entries");
							
							List<String> reactionEntries_List = new ArrayList<String>();
							Iterator reaction_entries_i = reaction_entries.iterator();
							while (reaction_entries_i.hasNext()) {
								String ent = reaction_entries_i.next().toString();
								reactionEntries_List.add(ent);
							}
							reaction_List.add(Arrays.asList(reaction_name, reactionEntries_List));
						}
					}
					Iterator r = reaction_List.iterator();
					while (r.hasNext()) {
						List<String> reaction_next = (List<String>) r.next();
						player.sendMessage("Reaction: "+ reaction_next.get(0));
					}
					break;
				case CHEST:
					player.closeInventory();
					player.sendMessage("===Saves===");
					
					int save_str = 0;
					int save_dex = 0;
					int save_con = 0;
					int save_int = 0;
					int save_wis = 0;
					int save_cha = 0;
					
					int save_str2 = Integer.parseInt(str);
					int save_dex2 = Integer.parseInt(dex);
					int save_con2 = Integer.parseInt(con);
					int save_int2 = Integer.parseInt(intell);
					int save_wis2 = Integer.parseInt(wis);
					int save_cha2 = Integer.parseInt(cha);
					
					String save_strs = "";
					String save_dexs = "";
					String save_cons = "";
					String save_ints = "";
					String save_wiss = "";
					String save_chas = "";
					
					if (f_MOB.get("save") != null) {
						JsonObject save = (JsonObject) f_MOB.get("save");
							
							if (save.get("str") != null) {
								save_str = save.get("str").getAsInt();
								save_str2 = 10+save_str;
								if (save_str >= 0) {
									save_strs = "+"+save_str;
								}
								else {
									save_strs = String.valueOf(save_str);
								}
							}
							else {
								
								save_strs = str_mod;
							}
					}
					else {
						save_strs = str_mod;
					}
					
					if (f_MOB.get("save") != null) {
						JsonObject save = (JsonObject) f_MOB.get("save");
						if (save.get("dex") != null) {
							save_dex = save.get("dex").getAsInt();
							save_dex2 = 10+save_dex;
							if (save_dex >= 0) {
								save_dexs = "+"+save_dex;
							}
							else {
								save_dexs = String.valueOf(save_dex);
							}
						}
						else {
							
							save_dexs = dex_mod;
						}
					}
					else {
						save_dexs = dex_mod;
					}
					
					if (f_MOB.get("save") != null) {	
						JsonObject save = (JsonObject) f_MOB.get("save");
						if (save.get("con") != null) {
							save_con = save.get("con").getAsInt();
							save_con2 = 10+save_con;
							if (save_con >= 0) {
								save_cons = "+"+save_con;
							}
							else {
								save_cons = String.valueOf(save_con);
							}
						}
						else {
							
							save_cons = con_mod;
						}
					}
					else {
						save_cons = con_mod;
					}

					if (f_MOB.get("save") != null) {
					JsonObject save = (JsonObject) f_MOB.get("save");
						if (save.get("int") != null) {
							save_int = save.get("int").getAsInt();
							save_int2 = 10+save_int;
							if (save_int >= 0) {
								save_ints = "+"+save_int;
							}
							else {
								
								save_ints = String.valueOf(save_int);
							}
						}
						else {
							save_ints = intell_mod;
						}
					}
					else {
						save_ints = intell_mod;
					}
						
						if (f_MOB.get("save") != null) {
						JsonObject save = (JsonObject) f_MOB.get("save");
						if (save.get("wis") != null) {
							save_wis = save.get("wis").getAsInt();
							save_wis2 = 10+save_wis;
							if (save_wis >= 0) {
								save_wiss = "+"+save_wis;
							}
							else {
								
								save_wiss = String.valueOf(save_wis);
							}
						}
						else {
							save_wiss = wis_mod;
						}
						}
						else {
							save_wiss = wis_mod;
						}
						
					if (f_MOB.get("save") != null) {
						JsonObject save = (JsonObject) f_MOB.get("save");
						if (save.get("cha") != null) {
							save_cha = save.get("cha").getAsInt();
							save_cha2 = 10+save_cha;
							if (save_cha >= 0) {
								save_chas = "+"+save_cha;
							}
							else {
								
								save_chas = String.valueOf(save_cha);
							}
						}
						else {
							save_chas = cha_mod;
						}
					}
					else {
						save_chas = cha_mod;
					}

					TextComponent STR_TXT = new TextComponent("STR"+"("+save_strs+")");
					STR_TXT.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+save_strs));
					STR_TXT.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(save_str2+"").create()));
					
					TextComponent DEX_TXT = new TextComponent("DEX"+"("+save_dexs+")");
					DEX_TXT.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+save_dexs));
					DEX_TXT.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(save_dex2+"").create()));
					
					TextComponent CON_TXT = new TextComponent("CON"+"("+save_cons+")");
					CON_TXT.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+save_cons));
					CON_TXT.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(save_con2+"").create()));
					
					TextComponent INT_TXT = new TextComponent("INT"+"("+save_ints+")");
					INT_TXT.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+save_ints));
					INT_TXT.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(save_int2+"").create()));
					
					TextComponent WIS_TXT = new TextComponent("WIS"+"("+save_wiss+")");
					WIS_TXT.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+save_wiss));
					WIS_TXT.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(save_wis2+"").create()));
					
					TextComponent CHA_TXT = new TextComponent("CHA"+"("+save_chas+")");
					CHA_TXT.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+save_chas));
					CHA_TXT.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(save_cha2+"").create()));
					
					player.spigot().sendMessage(STR_TXT);
					player.spigot().sendMessage(DEX_TXT);
					player.spigot().sendMessage(CON_TXT);
					player.spigot().sendMessage(INT_TXT);
					player.spigot().sendMessage(WIS_TXT);
					player.spigot().sendMessage(CHA_TXT);
					break;
				case WRITABLE_BOOK:
					player.closeInventory();
					player.sendMessage("===Skill-Check===");
					String ACRO = dex_mod;
					String ANI = wis_mod;
					String ARC = intell_mod;
					String ATH = str_mod;
					String DEC = cha_mod;
					String HIS = intell_mod;
					String INS = wis_mod;
					String INTI = cha_mod;
					String INV = intell_mod;
					String MED = wis_mod;
					String NAT = intell_mod;
					String PER = wis_mod;
					String PERF = cha_mod;
					String PERS = cha_mod;
					String REL = intell_mod;
					String SOH = dex_mod;
					String STH = dex_mod;
					String SVL = wis_mod;
					
					if (f_MOB.get("skill") != null) {
						JsonObject skill = (JsonObject) f_MOB.get("skill");
						

						if (skill.get("acrobatics") != null) {
							ACRO = skill.get("acrobatics").getAsString();
						}
						if (skill.get("animalhandling") != null) {
							ANI = skill.get("animalhandling").getAsString();
						}
						if (skill.get("arcana") != null) {
							ARC = skill.get("arcana").getAsString();
						}
						if (skill.get("athletics") != null) {
							ATH = skill.get("athletics").getAsString();
						}
						if (skill.get("deception") != null) {
							DEC = skill.get("deception").getAsString();
						}
						if (skill.get("history") != null) {
							HIS = skill.get("history").getAsString();
						}
						if (skill.get("insight") != null) {
							INS = skill.get("insight").getAsString();
						}
						if (skill.get("intimidation") != null) {
							INTI = skill.get("intimidation").getAsString();
						}
						if (skill.get("investigation") != null) {
							INV = skill.get("investigation").getAsString();
						}
						if (skill.get("medicine") != null) {
							MED = skill.get("medicine").getAsString();
						}
						if (skill.get("nature") != null) {
							NAT = skill.get("nature").getAsString();
						}
						if (skill.get("perception") != null) {
							PER = skill.get("perception").getAsString();
						}
						if (skill.get("performance") != null) {
							PERF = skill.get("performance").getAsString();
						}
						if (skill.get("persuasion") != null) {
							PERS = skill.get("persuasion").getAsString();
						}
						if (skill.get("religion") != null) {
							REL = skill.get("religion").getAsString();
						}
						if (skill.get("slightofhand") != null) {
							SOH = skill.get("slightofhand").getAsString();
						}
						if (skill.get("stealth") != null) {
							STH = skill.get("stealth").getAsString();
						}
						if (skill.get("survival") != null) {
							SVL = skill.get("survival").getAsString();
						}
					}
					
					int str_val01 = 10+Integer.valueOf(ATH);
					
					int dex_val01 = 10+Integer.valueOf(ACRO);
					int dex_val02 = 10+Integer.valueOf(SOH);
					int dex_val03 = 10+Integer.valueOf(STH);
					
					int int_val01 = 10+Integer.valueOf(ARC);
					int int_val02 = 10+Integer.valueOf(HIS);
					int int_val03 = 10+Integer.valueOf(INV);
					int int_val04 = 10+Integer.valueOf(NAT);
					int int_val05 = 10+Integer.valueOf(REL);

					int wis_val01 = 10+Integer.valueOf(ANI);
					int wis_val02 = 10+Integer.valueOf(INS);
					int wis_val03 = 10+Integer.valueOf(MED);
					int wis_val04 = 10+Integer.valueOf(PER);
					int wis_val05 = 10+Integer.valueOf(SVL);
					
					int cha_val01 = 10+Integer.valueOf(DEC);
					int cha_val02 = 10+Integer.valueOf(INTI);
					int cha_val03 = 10+Integer.valueOf(PERF);
					int cha_val04 = 10+Integer.valueOf(PERS);
					
					player.sendMessage("Strength");
					TextComponent ath_history = new TextComponent("Athletics ("+ATH+")");
					ath_history.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+ATH));
					ath_history.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(str_val01)).create()));
					player.spigot().sendMessage(ath_history);
					
					player.sendMessage("");
					
					player.sendMessage("Dexterity");
					TextComponent txt_acro = new TextComponent("Acrobatics "+"("+ACRO+")");
					txt_acro.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+ACRO));
					txt_acro.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(dex_val01)).create()));
					player.spigot().sendMessage(txt_acro);
					
					TextComponent txt_soh = new TextComponent("Slight of Hand ("+SOH+")");
					txt_soh.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+SOH));
					txt_soh.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(dex_val02)).create()));
					player.spigot().sendMessage(txt_soh);
					
					TextComponent txt_stealth = new TextComponent("Stealth ("+STH+")");
					txt_stealth.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+STH));
					txt_stealth.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(dex_val03)).create()));
					player.spigot().sendMessage(txt_stealth);
					
					player.sendMessage("");
					
					player.sendMessage("Intelligence");
					TextComponent txt_arcana = new TextComponent("Arcana ("+ARC+")");
					txt_arcana.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+ARC));
					txt_arcana.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(int_val01)).create()));
					player.spigot().sendMessage(txt_arcana);
					
					TextComponent txt_history = new TextComponent("History ("+HIS+")");
					txt_history.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+HIS));
					txt_history.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(int_val02)).create()));
					player.spigot().sendMessage(txt_history);
					
					TextComponent inv_history = new TextComponent("Investigation ("+INV+")");
					inv_history.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+INV));
					inv_history.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(int_val03)).create()));
					player.spigot().sendMessage(inv_history);
					
					TextComponent nat_history = new TextComponent("Nature ("+NAT+")");
					nat_history.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+NAT));
					nat_history.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(int_val04)).create()));
					player.spigot().sendMessage(nat_history);
					
					
					TextComponent rel_history = new TextComponent("Religion ("+REL+")");
					rel_history.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+REL));
					rel_history.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(int_val05)).create()));
					player.spigot().sendMessage(rel_history);
					
					player.sendMessage("");
					
					player.sendMessage("Wisdom");
					TextComponent ani_history = new TextComponent("Animal Handling ("+ANI+")");
					ani_history.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+ANI));
					ani_history.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(wis_val01)).create()));
					player.spigot().sendMessage(ani_history);
					
					TextComponent ins_history = new TextComponent("Insight ("+INS+")");
					ins_history.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+INS));
					ins_history.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(wis_val02)).create()));
					player.spigot().sendMessage(ins_history);
					
					TextComponent med_history = new TextComponent("Medicine ("+MED+")");
					med_history.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+MED));
					med_history.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(wis_val03)).create()));
					player.spigot().sendMessage(med_history);
					
					TextComponent per_history = new TextComponent("Perception ("+PER+")");
					per_history.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+PER));
					per_history.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(wis_val04)).create()));
					player.spigot().sendMessage(per_history);
					
					TextComponent svl_history = new TextComponent("Survival ("+SVL+")");
					svl_history.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+SVL));
					svl_history.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(wis_val05)).create()));
					player.spigot().sendMessage(svl_history);
					
					player.sendMessage("");
					
					player.sendMessage("Charisma");
					TextComponent dec_history = new TextComponent("Deception ("+DEC+")");
					dec_history.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+DEC));
					dec_history.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(cha_val01)).create()));
					player.spigot().sendMessage(dec_history);
					
					TextComponent inti_history = new TextComponent("Intimidation ("+INTI+")");
					inti_history.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+INTI));
					inti_history.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(cha_val02)).create()));
					player.spigot().sendMessage(inti_history);
					
					TextComponent perf_history = new TextComponent("Performance ("+PERF+")");
					perf_history.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+PERF));
					perf_history.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(cha_val03)).create()));
					player.spigot().sendMessage(perf_history);
					
					TextComponent pers_history = new TextComponent("Persuasion ("+PERS+")");
					pers_history.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, roll_val+PERS));
					pers_history.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(cha_val04)).create()));
					player.spigot().sendMessage(pers_history);
					
					break;
				case REDSTONE_TORCH:
					player.closeInventory();
					int ac_str = 0;
					List<String> ac_List = new ArrayList<String>();
					if (f_MOB.get("ac") != null) {
						JsonArray ac = (JsonArray) f_MOB.get("ac");
						if (ac.get(0) != null) {
							try {
								JsonObject ac1 = (JsonObject) ac.get(0);
								
								ac_str = ac1.get("ac").getAsInt();
								Iterator ac_from = ((JsonArray) ac1.get("from")).iterator();
								
								
								while (ac_from.hasNext()) {
									String ac_f = ac_from.next().toString();
									ac_List.add(ac_f);
								}
							}
							catch (Exception exception) {
								ac_str = ac.get(0).getAsInt();
								
							}
						}
					}
					String hp_formula = "None";
					int speed_walk = 0;
					
					if (f_MOB.get("hp") != null) {
						JsonObject hp = (JsonObject) f_MOB.get("hp");
						int hp_average = Integer.valueOf(hp.get("average").toString());
						
						hp_formula = hp.get("formula").toString();
					}
					
					if (f_MOB.get("speed") != null) {
					JsonObject speed = (JsonObject) f_MOB.get("speed");
					speed_walk = Integer.valueOf(speed.get("walk").toString());
					}
					
					
					
					List<String> senses_List = new ArrayList<String>();
					if (f_MOB.get("senses") != null) {
						JsonArray senses = (JsonArray) f_MOB.get("senses");
						Iterator senses_i = senses.iterator();
						
						
						
						while (senses_i.hasNext()) {
							String s_i = senses_i.next().toString();
							senses_List.add(s_i);
						}
					}
					else {
						senses_List.add("None");
					}
					
					int passive = 0;
					if (f_MOB.get("passive") != null) {
						passive = f_MOB.get("passive").getAsInt();
					}
					
					List<String> languages_List = new ArrayList<String>();
					if (f_MOB.get("languages") != null) {
						JsonArray languages = (JsonArray) f_MOB.get("languages");
						Iterator languages_i = languages.iterator();
					
						while (languages_i.hasNext()) {
							String l_i = languages_i.next().toString();
							languages_List.add(l_i);
						}
					}
					
					String cr = "0";
					if (f_MOB.get("cr") != null) {
						cr = f_MOB.get("cr").getAsString();
					}
					
					
					player.sendMessage("===Stats===");
					player.sendMessage("Armor Class: "+ac_str);
					player.sendMessage("Hit Dice: "+hp_formula.replace("\"", ""));
					player.sendMessage("Speed: "+speed_walk+"ft");
					if (senses_List != null) {
						player.sendMessage("Senses: "+senses_List.toString().toString().replace("[", "").replace("]", "").replace("\"", "")+", "+"Passive Perception: "+passive);
					}
					else {
						player.sendMessage("Senses: None");
					}
					if (languages_List != null) {
						player.sendMessage("Languages: "+languages_List.toString().replace("[", "").replace("]", "").replace("\"", ""));
					}
					else {
						player.sendMessage("Languages: None");
					}
					player.sendMessage("Challenge: "+cr);
					break;
				case PLAYER_HEAD:
					player.closeInventory();
					List<Object> trait_List = new ArrayList<Object>();
					
					if (f_MOB.get("trait") != null) {
					JsonArray trait = (JsonArray) f_MOB.get("trait");

					Iterator trait_I = trait.iterator();
					
					while (trait_I.hasNext()) {
						JsonObject t = (JsonObject) trait_I.next();
						String trait_name = t.get("name").toString();
						JsonArray trait_entries = (JsonArray) t.get("entries");
						
						List<String> traitEntries_List = new ArrayList<String>();
						Iterator trait_entries_i = trait_entries.iterator();
						while (trait_entries_i.hasNext()) {
							String ent = trait_entries_i.next().toString();
							traitEntries_List.add(ent);
						}
						trait_List.add(Arrays.asList(trait_name, traitEntries_List));
					}
					
					JsonArray traitTags = (JsonArray) f_MOB.get("traitTags");
                    Iterator trait_i = trait.iterator();
					
					List<String> traitTags_List = new ArrayList<String>();
					
					while (trait_i.hasNext()) {
						String t_i = trait_i.next().toString();
						traitTags_List.add(t_i);
					}
					}
					player.sendMessage("===Traits===");
					
					if (trait_List.size() > 0) {
					int trait_count = 0;
					Iterator t = trait_List.iterator();
					
					while (t.hasNext()) {
						List<String> trait_next = (List<String>) t.next();
						TextComponent trait_msg = new TextComponent("Trait"+trait_count+": "+ trait_next.get(0).replace("\"", ""));
						trait_msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(trait_next.toString().split("\"")[3].replace("]", "").replace("\"", "").replace("({@skill ", "(").replace("})", ")")).create()));

						player.spigot().sendMessage(trait_msg);
						
						trait_count++;
					}
					}
					else {
						player.sendMessage("None");
					}
					break;
				case ENDER_PEARL:
					player.closeInventory();
					player.sendMessage("===Spellcasting===");
					if (f_MOB.get("spellcasting") != null) {
						JsonArray a_spellcasting = (JsonArray) f_MOB.get("spellcasting");
						JsonObject spellcasting = (JsonObject) a_spellcasting.get(0);
						
						
						
						List<Object> will_List = new ArrayList<Object>();
						if (spellcasting.get("will") != null) {
							JsonArray will = (JsonArray) spellcasting.get("will");
							Iterator will_I = will.iterator();
							while (will_I.hasNext()) {
								String w = will_I.next().toString();
								will_List.add(w.replace("{@spell ", "").replace("}", ""));
							}
							player.sendMessage("At Will: "+will_List.toString().toString().replace("[", "").replace("]", "").replace("\"", ""));

						}
						
						if (a_spellcasting.get(1)!=null) {
							JsonObject spellscasting2 = (JsonObject) a_spellcasting.get(1);
							JsonObject spells = (JsonObject) spellscasting2.get("spells");
							Iterator spells_I = spells.entrySet().iterator();
							while (spells_I.hasNext()) {
								Object s = spells_I.next();
								String s_lvl = s.toString().split("=")[0];
								String s_spells = s.toString().split("=")[1];
								
								if (s_lvl.equals("0")) {
								player.sendMessage("Cantrips "+"(at will):"+" "+s_spells.toString().split("\"spells\":")[1].replace(",", ", ").replace("{@spell ", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", ""));
								}
								else {
									String s_slots = s_spells.toString().split("\"slots\":")[1].split(",")[0];
									player.sendMessage("("+s_lvl+") "+"Spells"+" ("+s_slots+" Slots): "+s_spells.toString().split("\"spells\":")[1].replace(",", ", ").replace("{@spell ", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", ""));
								}
							}
						}
						
						if (spellcasting.get("daily") != null) {
							JsonObject daily = (JsonObject) spellcasting.get("daily");
							Iterator daily_I = daily.entrySet().iterator();
							while (daily_I.hasNext()) {
								Object d = daily_I.next();
								String d_key = d.toString().split("=")[0].replace("e", "");
								String d_string = d.toString().split("=")[1].replace(",", ", ").replace("{@spell ", "").replace("}", "").replace("\"", "").replace("[", "").replace("]", "");
								player.sendMessage(d_key+"/Day each: "+d_string);
							}
						}
					}
					break;
					
				}
			}
			
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
		Entity i = e.getDamager();
		
		if (i instanceof Player) {
			Player player = (Player) i;
			ItemStack item = player.getInventory().getItemInMainHand();
			ItemMeta meta = item.getItemMeta();
			if (meta != null) {
				if (meta.getLore() != null) {
					if (item.getType().equals(Material.STICK)) {
						if (meta.getLore().get(0).contains("toggleadv") && meta.getDisplayName().equals(ChatColor.YELLOW + "StatBlock")) {
							if (player.getGameMode().equals(GameMode.CREATIVE)) {
								Entity mob = e.getEntity();
								//fix this
								if (MythicBukkit.inst().getAPIHelper().getMythicMobInstance(mob) != null) {
									ActiveMob MMCraftMob = MythicBukkit.inst().getAPIHelper().getMythicMobInstance(mob);
									String MMCraftMob_name = MMCraftMob.getName().replaceAll("&[a-zA-Z]{1}", "").substring(2).trim();
									int MMCraftMob_hp = (int) MMCraftMob.getEntity().getHealth();
									
									File resources = new File(plugin.getDataFolder() + "/resources/");
									File[] listOfJsons = resources.listFiles();
									if (listOfJsons != null) {
										boolean done = false;
									    for (File child : listOfJsons) {
									    	if (done == true) {
									    		break;
									    	}
									    	try {
									    		JsonParser parser = new JsonParser();
												Object f = parser.parse(new FileReader(child));
												JsonArray f_Box = (JsonArray) f;
												Iterator<JsonElement> f_Iterator = f_Box.iterator();
												while (f_Iterator.hasNext()) {
													JsonObject f_MOB = (JsonObject) f_Iterator.next();
													String mobname = f_MOB.get("name").getAsString();
													
													if (MMCraftMob_name.equals(mobname)) {
														int mobmaxhp = -1;
														
														if (f_MOB.get("hp") != null) {
															JsonObject hp = (JsonObject) f_MOB.get("hp");
															mobmaxhp = Integer.valueOf(hp.get("average").toString());
														}
														
														ItemStack gui_action = new ItemStack(Material.DIAMOND_SWORD);
														ItemMeta action_meta = gui_action.getItemMeta();
														action_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
														action_meta.setDisplayName(ChatColor.WHITE + "Action");
														action_meta.setLore(null);
														gui_action.setItemMeta(action_meta);
														
														ItemStack gui_abilitycheck = new ItemStack(Material.OAK_SIGN);
														ItemMeta abilitycheck_meta = gui_abilitycheck.getItemMeta();
														abilitycheck_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
														abilitycheck_meta.setDisplayName(ChatColor.WHITE + "Ability Check");
														abilitycheck_meta.setLore(null);
														gui_abilitycheck.setItemMeta(abilitycheck_meta);
														
														ItemStack gui_damage = new ItemStack(Material.HONEY_BOTTLE);
														ItemMeta damage_meta = gui_damage.getItemMeta();
														damage_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
														damage_meta.setDisplayName(ChatColor.WHITE + "DR/Immunities");
														damage_meta.setLore(null);
														gui_damage.setItemMeta(damage_meta);
														
														ItemStack gui_init = new ItemStack(Material.ENDER_EYE);
														ItemMeta init_meta = gui_init.getItemMeta();
														init_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
														init_meta.setDisplayName(ChatColor.WHITE + "Initiative/Perception");
														init_meta.setLore(null);
														gui_init.setItemMeta(init_meta);
														
														ItemStack gui_reaction = new ItemStack(Material.ARROW);
														ItemMeta reaction_meta = gui_reaction.getItemMeta();
														reaction_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
														reaction_meta.setDisplayName(ChatColor.WHITE + "Reactions");
														reaction_meta.setLore(null);
														gui_reaction.setItemMeta(reaction_meta);
														
														ItemStack gui_saves = new ItemStack(Material.CHEST);
														ItemMeta saves_meta = gui_saves.getItemMeta();
														saves_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
														saves_meta.setDisplayName(ChatColor.WHITE + "Saves");
														saves_meta.setLore(null);
														gui_saves.setItemMeta(saves_meta);
														
														ItemStack gui_skillcheck = new ItemStack(Material.WRITABLE_BOOK);
														ItemMeta skill_meta = gui_skillcheck.getItemMeta();
														skill_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
														skill_meta.setDisplayName(ChatColor.WHITE + "Skill Check");
														skill_meta.setLore(null);
														gui_skillcheck.setItemMeta(skill_meta);
														
														ItemStack gui_stats = new ItemStack(Material.REDSTONE_TORCH);
														ItemMeta stats_meta = gui_stats.getItemMeta();
														stats_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
														stats_meta.setDisplayName(ChatColor.WHITE + "Stats");
														stats_meta.setLore(null);
														gui_stats.setItemMeta(stats_meta);
														
														ItemStack gui_traits = new ItemStack(Material.PLAYER_HEAD);
														ItemMeta trait_meta = gui_traits.getItemMeta();
														trait_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
														trait_meta.setDisplayName(ChatColor.WHITE + "Traits");
														trait_meta.setLore(null);
														gui_traits.setItemMeta(trait_meta);
														
														ItemStack gui_spellcasting = new ItemStack(Material.ENDER_PEARL);
														ItemMeta spellcasting_meta = gui_spellcasting.getItemMeta();
														spellcasting_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
														spellcasting_meta.setDisplayName(ChatColor.WHITE + "Spellcasting");
														spellcasting_meta.setLore(null);
														gui_spellcasting.setItemMeta(spellcasting_meta);
														
														ItemStack[] gui_items_default = {gui_action, gui_abilitycheck, gui_damage, gui_init, gui_reaction, gui_saves, gui_skillcheck, gui_stats, gui_traits};
														ItemStack[] gui_items_spells = {gui_action, gui_abilitycheck, gui_damage, gui_init, gui_reaction, gui_saves, gui_skillcheck, gui_stats, gui_traits, gui_spellcasting};
														
														int slots = 9;
														if (f_MOB.get("spellcasting") != null) {
															slots=18;
														}
														
														Inventory gui = Bukkit.createInventory(player, slots, ChatColor.BLACK + "(SB)"+" "+mobname+" "+MMCraftMob_hp+"/"+mobmaxhp);
														
														checking.put(player.getUniqueId(), mob);
														checking2.put(player.getUniqueId(), f_MOB);
														
														if (slots == 9) {
															gui.setContents(gui_items_default);
														}
														else if (slots == 18) {
															gui.setContents(gui_items_spells);
														}
														player.openInventory(gui);
														
														done = true;
														break;
													}
												}
											} catch (JsonIOException | JsonSyntaxException | FileNotFoundException exception) {
												// TODO Auto-generated catch block
												player.sendMessage("Cannot read file");
											}
									    }
									}
								}
								e.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}
}
