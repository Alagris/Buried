package net.game.src.sta;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW_MATRIX;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION_MATRIX;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.swing.ground.GameInterface;
import net.swing.ground.ScreenInterface;
import net.swing.ground.Window;
import net.swing.ground.GUI.State;
import net.swing.src.sta.StateIntro;

import org.lwjgl.BufferUtils;

public final class Screen implements ScreenInterface
{
	
	/** variable that indicates current state */
	private byte						state					= STATE_INTRO;
	
	/*
	 * ALL STATE IDs ARE REPRESENTED BY VARIABLES OF TYPE byte !
	 * byte takes obviously only 1 bytes while int takes 4 bytes .
	 * byte is capable of encoding 265 numbers and it's definitely enough
	 */
	/** State intro */
	public StateIntro					intro;
	public static final byte			STATE_INTRO				= 0;
	/** State lobby */
	public StateLobby					lobby;
	public static final byte			STATE_LOBBY				= 1;
	/** State game */
	public StateGame					game;
	public static final byte			STATE_GAME				= 2;
	/** Errors displaying */
	private StateError					error;
	public static final byte			STATE_ERROR				= 3;
	/** State single player */
	public StateSingleplayer			singleplayer;
	public static final byte			STATE_SINGLEPLAYER		= 4;
	/** State options */
	public StateOptions					options;
	public static final byte			STATE_OPTIONS			= 5;
	/** State video settings */
	public StateVideoSettings			graphics;
	public static final byte			STATE_GRAPHICS			= 6;
	/** State audio settings */
	public StateAudioSettings			audio;
	public static final byte			STATE_AUDIO				= 7;
	// States to do:
	// public static final byte LANGUAGES = 9;
	// public static final byte FRIENDS = 10;
	// public static final byte MULTIPLAYER = 11;
	
	private boolean						isBlockedByError		= false;
	
	/**
	 * List of all game states this array is necessary if we want to
	 * access states by their indexes. Pay attention to the order they are
	 * placed in because indexes of states must overlap with their respective IDs.
	 * However, notice that this array only holds pointers to actual objects
	 * and there is no difference between object.x = y and array[objectIndex].x = y .
	 */
	private State[]						statesList;
	
	// public StateAddMap addMap;
	
	/** Nothing is needed to initialize this */
	@PostConstruct
	public void initializeGraph()
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Window.getWidth(), 0, Window.getHeight(), 1, -1);
		glGetFloat(GL_PROJECTION_MATRIX, /* Orthographic matrix */BufferUtils.createFloatBuffer(16));
		glMatrixMode(GL_MODELVIEW_MATRIX);
	}
	
	/**
	 * Initialization of everything everywhere. Almost. !Screen graphic and
	 * textures of blocks initializations are needed!
	 */
	@PostConstruct
	public final void initializeStates()
	{
		error = new StateError();
		lobby = new StateLobby();
		game = new StateGame();
		intro = new StateIntro(STATE_LOBBY);
		singleplayer = new StateSingleplayer();
		options = new StateOptions();
		graphics = new StateVideoSettings();
		audio = new StateAudioSettings();
		// addMap = new StateAddMap();
		State[] statesList = { intro, lobby, game, error, singleplayer, options, graphics, audio };
		this.statesList = statesList;
	}
	
	/** If you want to set ERROR state use setErrorState() */
	public void setState(byte stateID)
	{
		if (isBlockedByError)
		{
			System.out.println("State blocked! ");
			return;
		}
		System.out.println("State changed to state nr: " + stateID);
		state = stateID;
	}
	
	@Override
	public void setErrorState(String errorMessage)
	{
		error.setError(errorMessage);
		state = STATE_ERROR;
	}
	
	@Override
	public void setErrorStateAndBlock(String errorMessage)
	{
		error.setError(errorMessage);
		state = STATE_ERROR;
		isBlockedByError = true;
	}
	
	@Override
	@Deprecated
	public void unblock()
	{
		isBlockedByError = false;
	}
	
	@Override
	public void update()
	{
		statesList[state].show();
	}
	
	@PreDestroy
	public void cleanUp()
	{
		error.cleanUp();
		audio.cleanUp();
		game.cleanUp();
		graphics.cleanUp();
		intro.cleanUp();
		lobby.cleanUp();
		options.cleanUp();
		singleplayer.cleanUp();
	}
	
	@Override
	public GameInterface getGameState()
	{
		return game;
	}
	
}
