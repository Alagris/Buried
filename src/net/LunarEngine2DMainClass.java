package net;

import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glGetString;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.Scanner;

import javax.imageio.ImageIO;

import net.editor.main.DerpyIsBestPony;
import net.swing.ground.Delta;
import net.swing.ground.GameInterface;
import net.swing.ground.GameViewInterface;
import net.swing.ground.ScreenInterface;
import net.swing.ground.Window;
import net.swing.src.data.Files;
import net.swing.src.data.SaveOrigin;
import net.swing.src.data.SavePlayable;
import net.swing.src.env.Room;
import net.swing.src.env.World;
import net.swing.src.env.WorldSettings;
import net.swing.src.main.Launcher;
import net.swing.src.main.MainClassInterface;
import net.swing.src.obj.Things;
import net.swing.src.sound.AudioManager;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.opengl.ImageIOImageData;

/** This is the class with main method. Completely static. */
public final class LunarEngine2DMainClass
{
	// ////////////////////////////////////////////
	// / list of noticed bugs
	// ////////////////////////////////////////////
	
	// FIXME: when full-screen mode is on setting display up takes much longer and engine logo displays for shorter time (in the intro state)
	
	// ////////////////////////////////////////////
	// / list of TO DO things
	// ////////////////////////////////////////////
	
	// TODO: add customizable size of rooms
	// TODO: add text displaying action
	// TODO: add customizable font for worlds
	// TODO: add servers
	// TODO: entities editor
	// TODO: items
	// TODO: room background image
	// TODO: gravity for rooms
	// TODO: RGBA for blocks
	// TODO: stop using BitmapFont and use UnicodeFont (from slick2D )
	// TODO: possibly make a native launcher and pass selected options as JVM arguments
	
	// ////////////////////////////////////////////
	// /Variables
	// ////////////////////////////////////////////
	
	/** It decided whether game should be running or shut down */
	private static boolean				isRunning		= true;
	/** This is not really used anywhere except for saving last selected model */
	private static int					displaymodel	= 0;
	/** Main class interface **/
	private static MainClassInterface	engineInterface;
	/**
	 * REMEMBER TO UPDATE VERSION EVERYTIME A NEW BURIED IS RELEASED
	 * */
	public static final String			version			= "0.2.6";
	
	/**
	 * Those values are not used directly. Changing them wont cause anything
	 * until those changes are applied to game (AduioManager). For example when
	 * you go to audio settings and move music bar the main theme wont change
	 * its volume until you click "save"
	 **/
	protected static float				musicVol, soundVol, voiceVol;
	
	// ////////////////////////////////////////////
	// /main method
	// ////////////////////////////////////////////
	
	/**
	 * Here everything starts
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		/**
		 * HOW GAME WORKS (BASICS)
		 * -read properties and run launcher
		 * -decide which mode to run
		 * -set up LWJGL if game mode selected (run editor otherwise)
		 * -call setup() to initialize everything
		 * -call loop() every frame
		 * -call whenStops() to do the clean up
		 * -System.exit()
		 */
		// checking if files exist
		Files.checkFiles();
		// reading from those files
		readProperties();
		
		if (runLauncher())// checking what mode to run
		{
			// initializing LWJGL in this thread
			setUpDisplay();
			
			// checking if user has compatible OpenGL version
			// this must be done after LWJGL initialization
			checkOpenGL();
			
			// Some time ago editor was also made in LWJGL and this
			// line has mostly historical meaning (but still it gives us
			// some extra flexibility in case we wanted to add more LWJGL-based
			// modes of engine)
			engineInterface = new net.game.src.main.LunarEngine2D();
			
			engineInterface.setup();
			
			while (isRunning)
			{
				engineInterface.loop();
			}
			
			engineInterface.whenStops();
			
			System.out.println("===System will exit with status 0===");
			System.exit(0);
		}
		else
		{
			// Notice it is possible to run editor directly from its own
			// main method without running launcher (but it gives user less flexibility)
			DerpyIsBestPony.main(null);
		}
		
	}
	
	// ////////////////////////////////////////////
	// /Constructor - private,not used
	// ////////////////////////////////////////////
	
	private LunarEngine2DMainClass()
	{
	}
	
	// ////////////////////////////////////////////
	// /Properties read/save
	// ////////////////////////////////////////////
	
	/** Saves current properties of game */
	public static void saveProperties()
	{
		saveProperties(Window.isFullscreen(), displaymodel, musicVol, soundVol, voiceVol);
	}
	
	/** Saves customized properties of game */
	public static void saveProperties(boolean fullscreen, int dispModel, float musicV, float soundV, float voiceV)
	{
		try
		{
			PrintWriter saving = new PrintWriter(Files.PROPERTIES_FILE.f);
			if (fullscreen)
			{
				saving.println("fullscreen: enabled");
			}
			else
			{
				saving.println("fullscreen: disabled");
			}
			saving.println(dispModel);
			saving.println(musicV);
			saving.println(soundV);
			saving.println(voiceV);
			saving.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void readProperties()
	{
		try
		{
			Scanner sc = new Scanner(Files.PROPERTIES_FILE.f);
			// skipping to lines
			sc.nextLine();// this is read only by launcher
			sc.nextLine();// this is read only by launcher
			musicVol = Float.parseFloat(sc.nextLine());
			soundVol = Float.parseFloat(sc.nextLine());
			if (soundVol < 0)
			{
				soundVol = 0.3f;
			}
			voiceVol = Float.parseFloat(sc.nextLine());
			sc.close();
		}
		catch (Exception e)
		{
			System.err.println("Game properties are invalid! \n Setting to default!");
			saveProperties(false, 0, 1, 1, 1);
		}
	}
	
	// ////////////////////////////////////////////
	// /launcher
	// ////////////////////////////////////////////
	
	/**
	 * Returns true if game mode was selected. If user decided to open editor
	 * this game will close and files net.editor will be run
	 */
	private static boolean runLauncher()
	{
		
		Launcher launcher = new Launcher();
		
		try
		{
			launcher.setSizes(Display.getAvailableDisplayModes());
		}
		catch (LWJGLException e1)
		{
			e1.printStackTrace();
		}
		
		//Attention!
		//A busy loop goes here!
		//TODO: replace busy loop with Thread.join() or something like this
		while (!launcher.isReady)
		{
			launcher.validate();
		}
		
		launcher.shutdown();
		
		/* Saving user's start preferences set in launcher */
		displaymodel = launcher.getSelectedDisplay();
		Window.initialize(launcher.getSelectedWidth(), launcher.getSelectedHeight(), "LunarEngine2D alpha v" + version);
		Window.setFullscreen(launcher.isFullscreenSelected());// this works even though Display has not been initialized yet
		
		// returning chosen mode
		return launcher.isGame;
	}
	
	private static void setUpDisplay()
	{
		// Setting up display
		try
		{
			/*
			 * Notice that there is not need to set display mode (including full-screen)
			 * I has already been initialized when launcher was closed and it doesn't prevent user from
			 * selecting editor mode (with Swing thread).
			 */
			Display.setTitle(Window.getGameName());
			Display.setResizable(false);
			Display.setVSyncEnabled(true);
			Display.setIcon(bufferIcons());
			Display.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
			forceStop(1);
		}
		catch (IOException e)
		{
			System.err.println("Failed to set up icons!");
			e.printStackTrace();
			forceStop(2);
		}
	}
	
	private static void checkOpenGL()
	{
		System.out.println("Your OpenGL version is:" + glGetString(GL_VERSION));
		if (!GLContext.getCapabilities().OpenGL11)
		{
			System.err.println("Your OpenGL version doesn't support the required functionality.");
			forceStop(3);
		}
	}
	
	private static ByteBuffer[] bufferIcons() throws IOException
	{
		ByteBuffer[] buffers = null;
		String OS = System.getProperty("os.name").toUpperCase();
		if (OS.contains("WIN"))
		{
			buffers = new ByteBuffer[2];
			buffers[0] = new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File("res/gui/logo16.png")), false, false, null);
			buffers[1] = new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File("res/gui/logo32.png")), false, false, null);
		}
		else if (OS.contains("MAC"))
		{
			buffers = new ByteBuffer[1];
			buffers[0] = new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File("res/gui/logo128.png")), false, false, null);
		}
		else
		{
			buffers = new ByteBuffer[1];
			buffers[0] = new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File("res/gui/logo32.png")), false, false, null);
		}
		
		return buffers;
	}
	
	// ////////////////////////////////////////////
	// /Game closing
	// ////////////////////////////////////////////
	
	/**
	 * Stops the game but not abruptly.
	 * It finished the currently running logic
	 * and paints a frame for the last time.
	 * Then saves all the data and destroys OpenGL.
	 */
	public static void shutdown()
	{
		isRunning = false;
	}
	
	/**
	 * Stops the game no matter what. Everything after calling this method won't
	 * work.
	 * 
	 * @param status
	 *            - higher than 0. Specifies what kind of error occurred.
	 */
	public static void forceStop(int status)
	{
		System.out.println("===Force Stop: status " + status + "===");
		System.exit(status);
	}
	
	// ////////////////////////////////////////////
	// /Other methods (including getters/setters)
	// ////////////////////////////////////////////
	
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
	public static void declare(int id, String instructions)
	{
		engineInterface.declare(id, instructions);
	}
	
	public static AudioManager getAudioManager()
	{
		return engineInterface.getAudioManager();
	}
	
	public static ScreenInterface getScreen()
	{
		return engineInterface.getScreen();
	}
	
	public static GameInterface getGameState()
	{
		return getScreen().getGameState();
	}
	
	public static GameViewInterface getGameView()
	{
		return getGameState().getGameView();
	}
	
	/** Returns currently loaded playable save */
	public static SavePlayable getSavePlayable()
	{
		return getGameView().getSavePlayable();
	}
	
	/** Returns  origin of currently loaded playable save */
	public static SaveOrigin getOriginSave()
	{
		return getSavePlayable().getOriginSave();
	}
	
	/** Returns currently loaded world */
	public static World getWorld()
	{
		return getGameView().getWorld();
	}
	
	/** Returns currently loaded set of things */
	public static Things getThings()
	{
		return getWorld().getThings();
	}
	
	/** Returns currently loaded room */
	public static Room getRoom()
	{
		return getWorld().getRoom();
	}
	
	/**
	 * returns delta multiplied by time lapse. If delta equals about 14.285 then
	 * gamma = 1 (when time lapse = 0.07)
	 */
	public static float getGamma()
	{
		return Delta.get() * WorldSettings.timelapse;
	}
	
	public static float getMusicVol()
	{
		return musicVol;
	}
	
	public static void setMusicVol(float musicVol)
	{
		LunarEngine2DMainClass.musicVol = musicVol;
	}
	
	public static float getSoundVol()
	{
		return soundVol;
	}
	
	public static void setSoundVol(float soundVol)
	{
		LunarEngine2DMainClass.soundVol = soundVol;
		
	}
	
	public static float getVoiceVol()
	{
		return voiceVol;
	}
	
	public static void setVoiceVol(float voiceVol)
	{
		LunarEngine2DMainClass.voiceVol = voiceVol;
		
	}
}
