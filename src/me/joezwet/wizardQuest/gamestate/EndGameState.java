package me.joezwet.wizardQuest.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import me.joezwet.wizardQuest.main.GamePanel;

public class EndGameState extends GameState {
	
	private int currentChoice = 0;
	private String[] options = {
		"Main Menu",
		"Quit to Desktop"
	};
	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	
	
	public EndGameState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		
		try {
			
			titleColor = new Color(128, 0, 0);
			titleFont = new Font(
					"Century Gothic",
					Font.PLAIN,
					28);
			
			font = new Font("Arial", Font.PLAIN, 12);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public void init() {}
	
	public void update() {
		
	}

	public void draw(Graphics2D g) {
		
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		// draw title
				g.setColor(titleColor);
				g.setFont(titleFont);
				g.drawString("Congratulations!", 100, 70);
				g.drawString("You Won!", 150, 97);
				
				// draw menu options
				g.setFont(font);
				for(int i = 0; i < options.length; i++) {
					if(i == currentChoice) {
						g.setColor(Color.RED);
					}
					else {
						g.setColor(Color.BLACK);
					}
					g.drawString(options[i], 167, 140 + i * 15);
				}
	}

	private void select() {
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.MENUSTATE);
		}
		if(currentChoice == 1) {
			System.exit(0);
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
		
	}

	public void keyReleased(int k) {}

}
