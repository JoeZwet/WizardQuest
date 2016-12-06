package me.joezwet.wizardQuest.entity.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import me.joezwet.wizardQuest.entity.Animation;
import me.joezwet.wizardQuest.entity.Enemy;
import me.joezwet.wizardQuest.tilemap.TileMap;

public class Squid extends Enemy {
	
	private BufferedImage[] sprites;

	public Squid(TileMap tm) {
		super(tm);
		moveSpeed = 0.3;
		maxSpeed = 0.3;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 60;
		height = 60;
		cwidth = 30;
		cheight = 30;
		
		health = maxHealth = 30;
		damage = 10;
		
		//spirte
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/squid.gif"));
			
			sprites = new BufferedImage[5];
			for(int i = 0; i < sprites.length; i++){
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
		
		} catch(Exception e){
			e.printStackTrace();
		}
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(200);
		
		right = true;
		facingRight = true;
	}
	
	private void getNextPosition() {
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		if(falling) {
			dy += fallSpeed;
		}

	}
	public void update() {
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 400) {
				flinching = false;
			}
		}
		
		if(right && dx == 0) {
			right = false;
			left = true;
			facingRight = false;
		}
		else if(left && dx == 0) {
			left = false;
			right = true;
			facingRight = true;
		}
		
		animation.update();
	}
	
	public void draw(Graphics2D g) {
		
		//if(notOnScreen()) return;
		setMapPosition();
		
		super.draw(g);
	}


}
