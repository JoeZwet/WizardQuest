package me.joezwet.wizardQuest.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.json.JSONObject;

public class HUD {
	
	private Player player;
	private BufferedImage image;
	private Font font;
	private JSONObject json;
	
	public HUD(Player p){
		player = p;
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud.gif"));
			font = new Font("Arial", Font.PLAIN, 14);
			
			json = new JSONObject();
			json.put("debug", false);
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	public void draw(Graphics2D g){
		g.drawImage(image, 0, 10, null);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 30, 25);
		g.drawString(player.getFire()/100 + "/" + player.getMaxFire()/100, 30, 45);
		if(json.getBoolean("debug")) {
			g.drawString("X: " + player.getx(), 2, 65);
			g.drawString("Y: " + player.gety(), 2, 80);
		}
	}

}