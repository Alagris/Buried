package net.swing.src.main;

import net.swing.ground.ScreenInterface;
import net.swing.src.sound.AudioManager;

public interface MainClassInterface
{
	
	/** Saving all data and destroying all objects */
	public void whenStops();
	
	public void loop();
	
	/** LWJGL sets up automatically! */
	public void setup();
	
	/**
	 * parses declaration (from properties of loaded room).
	 * It is designed to trigger some actions when player enters a room
	 * but not only capable of doing that one particular thing. In fact
	 * you can use this method every time you need to trigger
	 * any random action only once. The only problem is that you must write
	 * instruction for that and it's going to be parsed every time you declare it.
	 * In many cases it would be easier if you just create a new instance of action
	 * class (that can be found in net.swing.src.obj). All those classes start with
	 * phrase "CollArea". (only exception: CollAreaSolid is not really an action)
	 * 
	 */
	public void declare(int id, String instructions);
	
	/** This method should always return CREATED AduioManager. */
	public AudioManager getAudioManager();
	
	public ScreenInterface getScreen();
}
