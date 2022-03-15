package fr.vallfeur.lm;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.bergerkiller.bukkit.common.map.MapDisplay;

import fr.vallfeur.lm.map.MapManager;
import ru.beykerykt.lightapi.LightType;

public class CommandHandler implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		
		Player player = (Player) sender;
		
		if(args.length < 1){
			ItemStack map =  MapDisplay.createMapItem(MapManager.class);
			ItemMeta meta = map.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW+"light");
			
			map.setItemMeta(meta);
			
			player.getInventory().addItem(map);
			player.sendMessage("light map gived!");
			
		}else{
			switch (args[0]) {
			case "create":
				Light light = new Light(player.getLocation(), LightType.BLOCK, 15, args[1]);
				
				if(ServerLight.create(light)){
					player.sendMessage("Light created!");
				}else{
					player.sendMessage("A light with that name already exist!");
				}
				break;
			case "level":
				try{
					int level = Integer.parseInt(args[2]);
					
					if(level < 15 || level > 1){
						Light light_update = ServerLight.get(args[1]);
						light_update.setLevel(level);
						
						ServerLight.update(light_update.getName(), light_update);
					}else{
						player.sendMessage("between 1 and 15 please ...");
					}
				}catch(NumberFormatException ex){
					player.sendMessage("that's not fucking number idiot!");
				}
				break;
			case "delete":
				if(ServerLight.delete(args[1])){
					player.sendMessage("Light delete!");
				}else{
					player.sendMessage("This light does not exist!");
				}
				break;
			case "list":
				StringBuilder builder = new StringBuilder();
				
				for(Light lit : ServerLight.getLights()){
					builder.append(lit.getName()+" ");
				}
				
				player.sendMessage(builder.toString());
				break;
			}
		}
		
		return false;
	}
	
}
