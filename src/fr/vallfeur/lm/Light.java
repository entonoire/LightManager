package fr.vallfeur.lm;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import ru.beykerykt.lightapi.LightType;

public class Light {

	private Location loc;
	private LightType type;
	private int level;
	private String name;
	private List<Player> editors = new ArrayList<>();
	
	
	public Light(Location loc, LightType type, int level, String name) {
		this.loc = loc;
		this.type = type;
		this.level = level;
		this.name = name;
	}
	
	public int getLevel() {
		return level;
	}
	
	public Location getLoc() {
		return loc;
	}
	
	public LightType getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void setLoc(Location loc) {
		this.loc = loc;
	}
	
	public void setType(LightType type) {
		this.type = type;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean edit(Player player){
		if(editors.contains(player)){
			return false;
		}else{
			editors.add(player);
			return true;
		}
	}
	
	public boolean unEdit(Player player){
		if(editors.contains(player)){
			editors.remove(player);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isEditing(Player player){
		return getEditors().contains(player);
	}
	
	public List<Player> getEditors(){
		return editors;
	}
}
