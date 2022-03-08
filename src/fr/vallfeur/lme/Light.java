package fr.vallfeur.lme;

import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import ru.beykerykt.minecraft.lightapi.common.LightAPI;
import ru.beykerykt.minecraft.lightapi.common.api.ResultCode;

public class Light {

	private Location location;
	private int level;
	private int type;
	private int id;
	public static HashMap<Integer, Light> lights = new HashMap<>();
	
	public Light(Location location, int level, int type) {
		this.location = location;
		this.level = level;
		this.type = type;
		this.id = lights.size()+1;
	}

	public static void delete(Light light){
		LightAPI.get().setLightLevel(light.getLocation().getWorld().getName(), light.getLocation().getBlockX(), light.getLocation().getBlockY(), light.getLocation().getBlockZ(), light.getType(), 0);
		lights.remove(light.id);
	}
	
	public static Light get(int id) {
		return lights.get(id);
	}
	
	public int getLevel() {
		return level;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public int getType() {
		return type;
	}
	
	public static void create(Light light, Player player){
		int result = LightAPI.get().setLightLevel(light.location.getWorld().getName(), light.type, light.location.getBlockX(), light.location.getBlockY(), light.location.getBlockZ(), light.level);
		
		switch (result) {
		  case ResultCode.SUCCESS:
			  Main.getLog().log(Level.INFO, "Success" + result);
		    break;
		  case ResultCode.MOVED_TO_DEFERRED:
			  Main.getLog().log(Level.INFO, "Light moved" + result);
		    break;
		  default:
		    Main.getLog().log(Level.WARNING, "Something is wrong. Result code:" + result);
		    break;
		}
		lights.put(light.id, light);
	}
	
}
