package fr.vallfeur.lm;

import org.bukkit.plugin.java.JavaPlugin;

import com.bergerkiller.bukkit.common.map.MapDisplay;

import fr.vallfeur.lm.map.ClickHandler;
import fr.vallfeur.lm.map.MapManager;

public class Main extends JavaPlugin{
	
	@Override
	public void onEnable() {
		
		getServer().getPluginManager().registerEvents(new LightSelector(), this);
		getServer().getPluginManager().registerEvents(new ClickHandler(), this);
		getCommand("light").setExecutor(new CommandHandler());
		
		super.onEnable();
	}
	
	
	@Override
	public void onDisable() {
		
		//for test only
		MapDisplay.getAllDisplays(MapManager.class).clear();
//		if(!ServerLight.getLights().isEmpty()){
//			ServerLight.getLights().forEach(light ->{
//				ServerLight.delete(light);
//			});
//		}
//		
		super.onDisable();
	}
	
}
