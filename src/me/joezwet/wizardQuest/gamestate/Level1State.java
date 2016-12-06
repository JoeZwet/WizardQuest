package me.joezwet.wizardQuest.gamestate;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import me.joezwet.wizardQuest.entity.Enemy;
import me.joezwet.wizardQuest.entity.Explosion;
import me.joezwet.wizardQuest.entity.HUD;
import me.joezwet.wizardQuest.entity.Player;
import me.joezwet.wizardQuest.entity.Teleporter;
import me.joezwet.wizardQuest.entity.enemies.Slugger;
import me.joezwet.wizardQuest.main.GamePanel;
import me.joezwet.wizardQuest.tilemap.TileMap;

/**
 * @author JoeZwet
 * 
 */
public class Level1State extends GameState {

	private TileMap tileMap;
	private Player player;
	private HUD hud;
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	private Teleporter t;
	public long waitTimer = System.nanoTime();

	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	public void init() {

		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/tileset.gif");
		tileMap.loadMap("/Maps/level1.map");
		tileMap.setPosition(0, 0);

		player = new Player(tileMap);
		player.setPosition(190, 100);
		//player.setPosition(2900, 190);

		populateEnemies();
		explosions = new ArrayList<Explosion>();

		hud = new HUD(player);
		
	}
	public void populateEnemies() {
		enemies = new ArrayList<Enemy>();
		
		Slugger s;
		Point[] points = new Point[] {
			new Point(2234, 340),
            new Point(2357, 340)
			
		};
		for(int i = 0; i < points.length; i++) {
			s = new Slugger(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		Point[] pointsT = new Point[] {
			new Point(2958, 500)	
		};
		for(int i = 0; i < pointsT.length; i++) {
			t = new Teleporter(tileMap);
			t.setPosition(pointsT[i].x, pointsT[i].y);
			enemies.add(t);
		}

	}

	public void update() {
		player.update();
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety());
		player.checkAttack(enemies);

		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if (e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety()));
			}
		}
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
	
	 	if(player.gety() > 540) {
	 	player.hit(player.getHealth() + 1);
		}
	 
		if(player.isDead()) {
			gsm.setState(GameStateManager.DEATH1STATE);
		}
		if(player.intersects(t)) {
			gsm.setState(GameStateManager.LEVEL2STATE);
		}
		
	}

	/**
	 * @param Graphics2D Graphics Engine
	 */
	public void draw(Graphics2D g) {

		// clear screen
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		// draw tilemap
		tileMap.draw(g);
		player.draw(g);
		//bg.draw(g);
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition((int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}

		hud.draw(g);
		

	}

	public void keyPressed(int k) {
		if (k == KeyEvent.VK_LEFT)
			player.setLeft(true);
		if (k == KeyEvent.VK_RIGHT)
			player.setRight(true);
		if (k == KeyEvent.VK_UP)
			player.setUp(true);
		if (k == KeyEvent.VK_DOWN)
			player.setDown(true);
		if (k == KeyEvent.VK_SPACE)
			player.setJumping(true);
		if (k == KeyEvent.VK_SHIFT)
			player.setGliding(true);
		if (k == KeyEvent.VK_F)
			player.setFiring();
		if (k == KeyEvent.VK_PAGE_UP)
			gsm.setState(GameStateManager.DEATH2STATE);
	}

	public void keyReleased(int k) {
		if (k == KeyEvent.VK_LEFT)
			player.setLeft(false);
		if (k == KeyEvent.VK_RIGHT)
			player.setRight(false);
		if (k == KeyEvent.VK_UP)
			player.setUp(false);
		if (k == KeyEvent.VK_DOWN)
			player.setDown(false);
		if (k == KeyEvent.VK_SPACE)
			player.setJumping(false);
		if (k == KeyEvent.VK_SHIFT)
			player.setGliding(false);
	}

}