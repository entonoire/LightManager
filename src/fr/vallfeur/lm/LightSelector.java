package fr.vallfeur.lm;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.md_5.bungee.api.ChatColor;

public class LightSelector implements Listener{

	@EventHandler
	public void selectLight(EntityDamageByEntityEvent event){
		if(event.getEntityType().equals(EntityType.ARMOR_STAND)){
			ArmorStand stand_selector = (ArmorStand) event.getEntity();
			Player player = (Player) event.getDamager();
			String name = stand_selector.getCustomName();
			
			if(ServerLight.exist(name)){
				ArmorStand stand_item = ServerLight.getStand(name);
				
				if(stand_item.isGlowing()){
					//deselect light
					stand_item.setGlowing(false);
					ServerLight.get(name).unEdit(player);
				}else{
					//select light
					ServerLight.getLights().forEach(light ->{
						//if he is editing an other light unedit the other the edit the new
						if(light.isEditing(player)){
							light.unEdit(player);
							ServerLight.getStand(light.getName()).setGlowing(false);
						}
					});
					stand_item.setGlowing(true);
					ServerLight.get(name).edit(player);
				}
			}
		}
	}
	
	@EventHandler()
	public void block(BlockBreakEvent event){
		
		if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.YELLOW+"light")){
			for(Light light : ServerLight.getLights()){
				event.setCancelled(event.getBlock().getLocation().distance(light.getLoc()) <= 2);
			}
		}
	}
	
}
