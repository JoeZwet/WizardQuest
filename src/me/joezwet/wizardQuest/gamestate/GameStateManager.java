package me.joezwet.wizardQuest.gamestate;

public class GameStateManager {
	
	private GameState[] gameStates;
	public int currentState;
	
	public static final int NUMGAMESTATES = 9;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	public static final int LEVEL2STATE = 2;
	public static final int LEVEL3STATE = 3;
	public static final int HELPSTATE = 4;
	public static final int DEATH1STATE = 5;
	public static final int DEATH2STATE = 6;
	public static final int DEATH3STATE = 7;
	public static final int ENDGAMESTATE = 8;
	

	
	public GameStateManager() {
		
		gameStates = new GameState[NUMGAMESTATES];
		
		currentState = MENUSTATE;
		
        loadState(currentState);
		
	}
	private void loadState(int state) {
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if(state == HELPSTATE)
			gameStates[state] = new HelpState(this);
		if(state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
		if(state == LEVEL2STATE)
			gameStates[state] = new Level2State(this);
		if(state == LEVEL3STATE)
			gameStates[state] = new Level3State(this);
		if(state == DEATH1STATE)
			gameStates[state] = new DeathState(this);
		if(state == DEATH2STATE)
			gameStates[state] = new Death2State(this);
		if(state == DEATH3STATE)
			gameStates[state] = new Death3State(this);
		if(state == ENDGAMESTATE)
			gameStates[state] = new EndGameState(this);
			
	}
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		//gameStates[currentState].init();
	}
	
	public void newLevel() {
		if(currentState == LEVEL1STATE) {
			setState(LEVEL2STATE);
		}
		if(currentState == LEVEL2STATE) {
			setState(LEVEL3STATE);
		}
		if(currentState == LEVEL3STATE) {
			setState(MENUSTATE);
		}
	}
	
	public void update() {
		try{
		gameStates[currentState].update();
		} catch(Exception e) {}
	}
	
	public void draw(java.awt.Graphics2D g) {
		try{
		gameStates[currentState].draw(g);
		} catch(Exception e) {}
	}
	
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);
	}
	
}