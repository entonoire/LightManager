package fr.vallfeur.lm.map;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.bergerkiller.bukkit.common.events.map.MapClickEvent;
import com.bergerkiller.bukkit.common.events.map.MapKeyEvent;
import com.bergerkiller.bukkit.common.map.MapBlendMode;
import com.bergerkiller.bukkit.common.map.MapColorPalette;
import com.bergerkiller.bukkit.common.map.MapDisplay;
import com.bergerkiller.bukkit.common.map.MapFont;
import com.bergerkiller.bukkit.common.map.MapSessionMode;
import com.bergerkiller.bukkit.common.map.MapTexture;
import com.bergerkiller.bukkit.common.map.widgets.MapWidgetButton;
import com.bergerkiller.bukkit.common.map.widgets.MapWidgetSubmitText;
import com.bergerkiller.bukkit.common.resources.SoundEffect;

import fr.vallfeur.lm.Light;
import fr.vallfeur.lm.ServerLight;

public class MapManager extends MapDisplay{

	private Player player;
	public static String actualMenu;; //create ; position ; delete
	private int[] position = {39,38};
	private boolean posmode = false;
	private MapWidgetButton button = new MapWidgetButton();
	public static String lightName = "light"+ServerLight.getLights().size();
	
	//--- set widget ---
	
	private MapWidgetSubmitText st = new MapWidgetSubmitText(){
		@Override
		public void onAttached() {
			setDescription("Set new light name");
			super.onAttached();
		}
		public void onAccept(String text) {
			lightName = text.equalsIgnoreCase(" ") ? "light"+ServerLight.getLights().size() : text;
			updateMenu("create", true);
		};
	};
	
	MapWidgetButton btn = new MapWidgetButton(){
		@Override
		public void onActivate() {
			st.activate();
		}
		@Override
		public void onAttached() {
			setText("Edit Name");
			setBounds(39, 104, 60, 17);
			super.onAttached();
		}
	};
	
	MapWidgetButton name = new MapWidgetButton(){
		@Override
		public void onAttached() {
			setText("Current Name:");
			setFocusable(false);
			setBounds(10, 38, 75, 15);
			setShowBorder(false);
			super.onAttached();
		}
	};
	
	MapWidgetButton CurrentName = new MapWidgetButton(){
		@Override
		public void onAttached() {
			setText("\""+lightName+"\"");
			setFocusable(false);
			setBounds(10, 53, 75, 15);
			setShowBorder(false);
			super.onAttached();
		}
	};
	
	
	@Override
	public void onAttached() {
		setGlobal(false);
        setSessionMode(MapSessionMode.VIEWING);
        setReceiveInputWhenHolding(true);
		player = getOwners().get(0);
		
		MapTexture background = loadTexture("fr/vallfeur/lm/res/background.png");
        getLayer(0).setBlendMode(MapBlendMode.NONE);
        getLayer(0).draw(background, 0, 0);
		player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.1f, 1.0f);
		
		//menu
		button.setText(actualMenu);
		button.setBounds(31, 2, 66, 13);
		
		addWidget(button);
		addWidget(st);
		addWidget(btn);
		addWidget(name);
		addWidget(CurrentName);
		
		updateMenu("create", true);
	}
	
	@Override
	public void onTick() {
		
		super.onTick();
	}
	
	@Override
	public void onLeftClick(MapClickEvent event) {
		System.out.println("left");
		super.onLeftClick(event);
	}
	
	@Override
	public void onRightClick(MapClickEvent event) {
		System.out.println("a");
		if(actualMenu == "create"){
			Location loc = new Location(player.getWorld(), event.getX(), event.getDoubleY(), player.getLocation().getZ());
			loc.getBlock().setType(Material.STONE);
		}
		
		super.onRightClick(event);
	}
	
	private void drawBack(int x, int y0, String str, byte... color){
		Set<Point> points = new HashSet<>();
		int y = y0+8;
		for(int i = 10 ; i>=0 ; i--){
			if(i == 10 || i == 0){
				points.add(new Point(x-2, y-i));
				points.add(new Point(x+(str.length()*5)-5, y-i));
			}else{
				points.add(new Point(x-3, y-i));
				points.add(new Point(x+(str.length()*5)-4, y-i));
			}
			
			getLayer(1).drawContour(points, color.length > 0 ? color[0] : MapColorPalette.getColor(0, 0, 0));
			points.clear();
		}
	}
	
	private void updateMenu(String menu, boolean... init){
		
		if(button.isFocused() || (init.length > 0 ? init[0] : false)){
			actualMenu = menu;
			button.setText(actualMenu);
			
			btn.setVisible(false);
			btn.invalidate();
			
			name.setVisible(false);
			name.invalidate();
			
			CurrentName.setVisible(false);
			CurrentName.setText("\""+lightName+"\"");
			CurrentName.invalidate();
			
			getLayer(2).clear();
			getLayer(1).clear();
			
			
			switch (actualMenu) {
			case "create":
				btn.setVisible(true);
				btn.invalidate();
				
				name.setVisible(true);
				name.invalidate();
				
				CurrentName.setVisible(true);
				CurrentName.invalidate();
				break;
			case "position":
				if(ServerLight.getLights().isEmpty()){
					getLayer(2).draw(MapFont.MINECRAFT, 44, 38, MapColorPalette.getColor(255, 0, 0), "No light!");
					drawBack(44, 38, "No light!");
				}else{
					if(ServerLight.getLights().stream().anyMatch(li -> li.isEditing(player))){
						Light light = ServerLight.getEdited(player);
						
						getLayer(2).draw(MapFont.MINECRAFT, 5, 10, MapColorPalette.getColor(153, 255, 55), "editing light "+light.getName());
						drawBack(5, 10, "editing light "+light.getName());
					}else{
						getLayer(2).draw(MapFont.MINECRAFT, 21, 25, MapColorPalette.getColor(255, 0, 0), "No light selected!");
						drawBack(21, 25, "No light selected!");
					}
				}
				break;
			}
			
			
			playSound(SoundEffect.CLICK, 0.1f, 1.0f);
		}
	}
	
	@Override
	public void onKeyPressed(MapKeyEvent event) {

		
		if(posmode){
			switch (event.getKey()) {
			case RIGHT:
				position[0]+=1;
				break;
			case LEFT:
				position[0]-=1;
				break;
			case UP:
				position[1]-=1;
				break;
			case DOWN:
				position[1]+=1;
				break;
			case ENTER:
				event.getPlayer().sendMessage(position[0]+" "+position[1]);
				break;
			case BACK:
				posmode = !posmode;
				player.sendMessage("posemode "+posmode);
				break;
			}
		}else{
			switch (event.getKey()) {
			case RIGHT:
				if(actualMenu == "create"){
					updateMenu("position");
				}else if(actualMenu == "position"){
					updateMenu("delete");
				}
				
				break;
			case LEFT:
				if(actualMenu == "position"){
					updateMenu("create");
				}else if(actualMenu == "delete"){
					updateMenu("position");
				}
				break;
			case UP:
				break;
			case BACK:
				posmode = !posmode;
				player.sendMessage("posemode "+posmode);
				break;
			case DOWN:
				break;
			case ENTER:
				break;
			}
		}
		
		super.onKeyPressed(event);
	}
}
