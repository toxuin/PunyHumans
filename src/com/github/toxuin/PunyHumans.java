package com.github.toxuin;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


public class PunyHumans extends JavaPlugin implements Listener{

  @Override
  public void onEnable() {
    this.getServer().getPluginManager().registerEvents(this, this);
  }
  @Override
  public void onDisable() {
    // ОЛОЛО!
  }
  
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onEntityDeathEvent(EntityDeathEvent event){
    if (!(event instanceof PlayerDeathEvent)) return; 
        PlayerDeathEvent death = (PlayerDeathEvent) event;
        Player player = death.getEntity();
        EntityDamageEvent damageEvent = player.getLastDamageCause();
		DeathCause deathCause = DeathCause.getDeathCause(damageEvent);
		Messenger mess = new Messenger();
		
		String message;
		message = ChatColor.YELLOW+"Игрок "+ChatColor.WHITE+player.getDisplayName() +ChatColor.YELLOW+ " умер от "+deathCause.toName()+" ";
		if (player.getWorld().getEnvironment() == Environment.NETHER) message += "в аду ";
		if (player.getWorld().getEnvironment() == Environment.THE_END) message += "в краю ";
		
		
		List<ItemStack> dropped = death.getDrops();
		
		int diamondCount = 0;
		int goldCount = 0;
		int ironCount = 0;

			for (ItemStack item : dropped) {
				if (item.getType().equals(Material.DIAMOND)) {
					diamondCount += item.getAmount();
				} else if (item.getType().equals(Material.GOLD_ORE) || item.getType().equals(Material.GOLD_INGOT)) {
					goldCount += item.getAmount();
				} else if (item.getType().equals(Material.IRON_ORE) || item.getType().equals(Material.IRON_INGOT)) {
					ironCount += item.getAmount();
				}
			}

		if (diamondCount >= 5 || goldCount >= 5 || ironCount >= 5) {
			if (diamondCount >= 5) mess.losses.add(ChatColor.WHITE+Integer.toString(diamondCount)+ChatColor.YELLOW+" алмазов");
			if (goldCount >= 5) mess.losses.add(ChatColor.WHITE+Integer.toString(goldCount)+ChatColor.YELLOW+" золота"); 
			if (ironCount >= 5) mess.losses.add(ChatColor.WHITE+Integer.toString(ironCount)+ChatColor.YELLOW+" железа");
		}
		
		
		if (dropped.contains(new ItemStack(Material.DIAMOND_AXE)) ||
				dropped.contains(new ItemStack(Material.DIAMOND_HOE)) ||
				dropped.contains(new ItemStack(Material.DIAMOND_PICKAXE)) ||
				dropped.contains(new ItemStack(Material.DIAMOND_SPADE)) ||
				dropped.contains(new ItemStack(Material.DIAMOND_SWORD))) {
			mess.losses.add("алмазные инструменты");
		}
		
		if (dropped.contains(new ItemStack(Material.DIAMOND_HELMET)) ||
				dropped.contains(new ItemStack(Material.DIAMOND_CHESTPLATE)) ||
				dropped.contains(new ItemStack(Material.DIAMOND_LEGGINGS)) ||
				dropped.contains(new ItemStack(Material.DIAMOND_BOOTS))) {
			mess.losses.add("алмазную броню");
		}
		
		if (!mess.losses.isEmpty()) {
			message += "и потерял "+mess.getPlayerLosses()+".";
			mess.losses.clear();
		}
		
        death.setDeathMessage(message);
        this.getServer().getScheduler().scheduleSyncDelayedTask(this, new sendTrollMessage(), 5);
  }
  
  private class sendTrollMessage implements Runnable {
	  String message = Messenger.getTrollPhrase();
	  @Override
		public void run() {
		  for (World w : getServer().getWorlds()){
		  	for (Player p : w.getPlayers()){
		          p.sendMessage(ChatColor.GOLD+message);
		      }
		  }
		}
		
	}
}