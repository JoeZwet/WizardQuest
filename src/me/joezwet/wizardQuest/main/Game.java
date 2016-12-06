package me.joezwet.wizardQuest.main;

import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Game extends JPanel {
	
	
	public static void main(String[] args) {
		JFrame window = new JFrame("Wizard Quest - Version: 0.0.1.PreAlpha_001");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
}