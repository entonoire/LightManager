package fr.vallfeur.lme;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import ru.beykerykt.minecraft.lightapi.common.api.engine.LightFlag;

public class CommandHandler implements CommandExecutor, TabCompleter{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		Player player = (Player) sender;
		player.sendMessage("a");
		switch(args[0]){
			case "create":
				Light create_light = new Light(player.getLocation(), 15, LightFlag.BLOCK_LIGHTING);
				Light.create(create_light, player);
				break;
			case "delete":
				Light delete_light = Light.lights.get(1);
				Light.delete(delete_light);
				break;
		}
		
		return false;
	}
	
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String str, String[] args) {
		
		return Arrays.asList("create", "remove");
	}
	
}
