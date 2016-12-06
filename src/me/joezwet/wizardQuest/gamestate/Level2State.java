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
import me.joezwet.wizardQuest.entity.enemies.Squid;
import me.joezwet.wizardQuest.main.GamePanel;
import me.joezwet.wizardQuest.tilemap.TileMap;

public class Level2State extends GameState {

	private TileMap tileMap;
	private Player player;
	private HUD hud;
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	private Teleporter t;
	public long waitTimer = System.nanoTime();

	public Level2State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	public void init() {

		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/tileset.gif");
		tileMap.loadMap("/Maps/level2.map");
		tileMap.setPosition(0, 0);

		player = new Player(tileMap);
		//player.setPosition(5077, 110);
		player.setPosition(100, 100);
		populateEnemies();
		explosions = new ArrayList<Explosion>();

		hud = new HUD(player);
	}
	public void populateEnemies() {
		enemies = new ArrayList<Enemy>();
		
		Slugger s;
		Point[] points = new Point[] {
			new Point(2234, 100),
            new Point(4981, 140),
            new Point(3524, 350)
			
		};
		for(int i = 0; i < points.length; i++) {
			s = new Slugger(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		Point[] pointsT = new Point[] {
				new Point(5174, 140)	
			};
			for(int i = 0; i < pointsT.length; i++) {
				t = new Teleporter(tileMap);
				t.setPosition(pointsT[i].x, pointsT[i].y);
				enemies.add(t);
			}
	  Squid sq;
	  Point[] sqP = new Point[] {
		new Point(1040, 335)	  
	  };
	  for(int i = 0; i < sqP.length; i++) {
		  sq = new Squid(tileMap);
		  sq.setPosition(sqP[i].x, sqP[i].y);
		  enemies.add(sq);
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
	
	 	if(player.gety() > 411) {
	 //      player.hit(player.getHealth());
		}
	 
		if(player.isDead()) {
			gsm.setState(GameStateManager.DEATH2STATE);
		}

		if(player.intersects(t)) {
			gsm.setState(GameStateManager.LEVEL3STATE);
		}
		
	}

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
