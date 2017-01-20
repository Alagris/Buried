package net.swing.ground;

public interface GameInterface
{
	
	public GameViewInterface getGameView();
	
	/** If false then game is paused and logic of game freezes. Only rendering is done. */
	public boolean isAction();
	
	/** If true switches game mode to active */
	void switchGame(boolean arg0);
}
