package fr.vallfeur.lm.map;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.bergerkiller.bukkit.common.map.MapDisplay;

import fr.vallfeur.lm.Light;
import fr.vallfeur.lm.ServerLight;
import ru.beykerykt.lightapi.LightType;

public class ClickHandler implements Listener{
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent event){
		if(MapDisplay.getHeldDisplay(event.getPlayer()) != null && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(MapManager.actualMenu == "create"){
				Location loc = event.getClickedBlock().getLocation();
				loc.setY(loc.getY()+1);
				
				ServerLight.create(new Light(loc, LightType.BLOCK, 15, MapManager.lightName));
			}
		}
	}
	
}
