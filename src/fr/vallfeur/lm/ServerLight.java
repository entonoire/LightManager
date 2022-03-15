package fr.vallfeur.lm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.ArmorStand.LockType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightapi.LightAPI;

@SuppressWarnings("deprecation")
public class ServerLight {

	/*
	 * list: 0: light ; 1: stand_item ; 2: stand_selector
	 */
	private static HashMap<String, List<Object>> lights = new HashMap<>();
	
	public static boolean create(Light light){
		LightAPI.createLight(light.getLoc(), light.getType(), light.getLevel(), true);
		if(lights.containsKey(light.getName())){
			return false;
		}else{
			
			Location location = light.getLoc();
			
			ArmorStand stand_item = (ArmorStand) location.getWorld().spawnEntity(new Location(location.getWorld(), location.getBlockX()+0.5, location.getBlockY()-1.7, location.getBlockZ()+0.8), EntityType.ARMOR_STAND);
	        stand_item.setGravity(false);
	        stand_item.getEquipment().setItem(EquipmentSlot.HEAD, new ItemStack(Material.HONEY_BOTTLE));
	        stand_item.setRotation(0, 0);
	        stand_item.setVisible(false);
	        stand_item.addEquipmentLock(EquipmentSlot.HEAD, LockType.REMOVING_OR_CHANGING);
	        stand_item.setMarker(true);
	        stand_item.setGlowing(false);
	        stand_item.setCustomName(light.getName());
	        
	        ArmorStand stand_selector = (ArmorStand) location.getWorld().spawnEntity(new Location(location.getWorld(), location.getBlockX()+0.5, location.getBlockY()-1, location.getBlockZ()+0.8), EntityType.ARMOR_STAND);
	        stand_selector.setGravity(false);
	        stand_selector.setRotation(0, 0);
	        stand_selector.setVisible(false);
	        stand_selector.setCustomName(light.getName());
	        stand_selector.setCustomNameVisible(true);
	        
	        LightAPI.updateChunks(light.getLoc(), Bukkit.getOnlinePlayers());
	        lights.put(light.getName(), Arrays.asList(light, stand_item, stand_selector));
		}
		return true;
	}
	
	public static boolean update(String oldLightName, Light newLight){
		if(lights.containsKey(oldLightName)){
			delete(get(oldLightName));
			create(newLight);
			return true;
		}else{
			return false;
		}
	}
	
	//by light
	public static boolean delete(Light light){
		if(lights.containsKey(light.getName())){
			LightAPI.deleteLight(light.getLoc(), light.getType(), true);
			LightAPI.updateChunks(light.getLoc(), Bukkit.getOnlinePlayers());
			((ArmorStand) lights.get(light.getName()).get(1)).remove();
			((ArmorStand) lights.get(light.getName()).get(2)).remove();
			lights.remove(light.getName());
			return true;
		}else{
			return false;
		}
	}
	
	//by name
	public static boolean delete(String lightName){
		if(lights.containsKey(lightName)){
			Light light = get(lightName);
			delete(light);
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean exist(String name){
		return lights.containsKey(name);
	}
	
	public static ArmorStand getStand(String name){
		return (ArmorStand) lights.get(name).get(1);
	}
	
	public static Light get(String name){
		return ((Light) lights.get(name).get(0));
	}
	
	public static Light getEdited(Player player){
		return ServerLight.getLights().stream().filter(li -> li.isEditing(player)).findFirst().get();
	}
	
	public static List<Light> getLights(){
		List<Light> list = new ArrayList<>();
		
		lights.forEach((str, l) ->{
			list.add((Light) l.get(0));
		});
		
		return list;
	}
}
