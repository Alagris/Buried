package net.swing.ground;

public interface ScreenInterface
{
	
	public GameInterface getGameState();
	
	public void setErrorState(String errorMessage);
	
	public void setErrorStateAndBlock(String errorMessage);
	
	/** State should IDs be in Screen class that implements this interface */
	public void setState(byte stateID);
	
	/**
	 * If something blocked it there should be a reason for that, which means
	 * that unblocking could be dangerous in some cases
	 */
	@Deprecated
	public void unblock();
	
	public void update();
	
}
