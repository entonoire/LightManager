package fr.vallfeur.lme;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	private static Logger logger;
	
	@Override
	public void onEnable() {
		
		logger = getLogger();
		
		getCommand("light").setExecutor(new CommandHandler());
		getCommand("light").setTabCompleter(new CommandHandler());
		
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		
		
		
		super.onDisable();
	}
	
	
	public static Logger getLog() {
		return logger;
	}
	
}
