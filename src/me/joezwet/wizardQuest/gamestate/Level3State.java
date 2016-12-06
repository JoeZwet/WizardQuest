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
import me.joezwet.wizardQuest.entity.enemies.L3Slugger;
import me.joezwet.wizardQuest.entity.enemies.Squid;
import me.joezwet.wizardQuest.main.GamePanel;
import me.joezwet.wizardQuest.tilemap.TileMap;

public class Level3State extends GameState {

	private TileMap tileMap;
	private Player player;
	private HUD hud;
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	public long waitTimer = System.nanoTime();

	public Level3State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	public void init() {

		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/bonuslevel.gif");
		tileMap.loadMap("/Maps/level3.map");
		tileMap.setPosition(0, 0);
		
		player = new Player(tileMap);
		player.setPosition(72, 1420);
		//player.setPosition(190, 140);

		populateEnemies();
		explosions = new ArrayList<Explosion>();

		hud = new HUD(player);
	}
	public void populateEnemies() {
		enemies = new ArrayList<Enemy>();
		
		//Boss @ 2700, 1430
		//Boss @ 5000, 890
		
		L3Slugger s;
		Point[] points = new Point[] {
			new Point(508, 1460),
			new Point(2310, 1430),
			new Point(786, 170),
			new Point(3097, 380),
			new Point(4979, 260),
			
		};
		for(int i = 0; i < points.length; i++) {
			s = new L3Slugger(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		Squid f;
		Point[] p = new Point[] {
			new Point(2726, 1420),
			new Point(4777, 880),
			new Point(3110, 910),
			new Point(2094, 790),
			new Point(1701, 820),
			new Point(1069, 790),
			new Point(420, 700),
			
		};
		for(int i = 0; i < p.length; i++) {
			f = new Squid(tileMap);
			f.setPosition(p[i].x, p[i].y);
			enemies.add(f);
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
	 
		if(player.isDead()) {
			gsm.setState(GameStateManager.DEATH3STATE);
		}
		
		
	}

	public void draw(Graphics2D g) {

		// clear screen
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		// draw tilemap
		tileMap.draw(g);
		player.draw(g);
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