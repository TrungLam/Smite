package com.github.TrungLam;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Smite extends JavaPlugin implements Listener{
	public static Smite plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	public final PluginDescriptionFile pdf = this.getDescription();
	public static boolean smite = false;
	
	public void onDisable(){
		logger.info(pdf + " is disabled");
	}
	
	public void onEnable(){
		logger.info(pdf + " is enabled");
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args){
		Player player = (Player) sender;
		
		if (Label.equalsIgnoreCase("smite") && player.isOp()){
			World world = player.getWorld();
			Server server = player.getServer();
			if (args.length == 0){
				smite = (smite) ? false:true;
				player.sendMessage("smite: " + smite);
			}
			else {
				Player targetPlayer;
				for (int index = 0; index < args.length; index++){
					try {
						targetPlayer = server.getPlayer(args[index]);
						world.strikeLightning(targetPlayer.getLocation());
						server.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] " + player.getDisplayName() + " has struck " + (targetPlayer.equals(player) ? "himself/herself" : targetPlayer.getDisplayName()));						
					}
					catch (NullPointerException e){
						player.sendMessage("No such player");
					}
				}
			}
		}
		return false;
	}
	
	@EventHandler
	public void playerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if (player.isOp() && smite){
			if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && player.getItemInHand().getType().equals(Material.DIAMOND_SWORD)){
				player.getWorld().strikeLightning(player.getTargetBlock(null, 500).getLocation());
			}
		}
	}
}
