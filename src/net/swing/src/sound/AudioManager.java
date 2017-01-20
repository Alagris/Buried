package net.swing.src.sound;

import java.io.File;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;

import static org.lwjgl.openal.AL10.*;

public final class AudioManager
{
	// ////////////////////////////////////////////
	// / variables
	// ////////////////////////////////////////////
	
	/** Set of all sources for sounds and all buffered sounds ready to use */
	private Sounds			soundStore;
	/**
	 * The basic difference between music and sounds is that music source is
	 * looping
	 */
	private Music			musicStore;
	/** Voice manager handles all tasks related to playing voice of characters */
	private VoiceManager	voiceManager;
	
	// ////////////////////////////////////////////
	// / getters
	// ////////////////////////////////////////////
	
	public Sounds getSoundStore()
	{
		return soundStore;
	}
	
	public Music getMusicStore()
	{
		return musicStore;
	}
	
	public VoiceManager getVoiceManager()
	{
		return voiceManager;
	}
	
	// ////////////////////////////////////////////
	// / AL initialization
	// ////////////////////////////////////////////
	
	public void create(File soundsFolder, File musicFolder)
	{
		if (AL.isCreated()) return;
		try
		{
			AL.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		setListenerValues();
		
		System.out.println("LOADING SOUNDS...");
		soundStore = new Sounds(soundsFolder, 10);
		
		System.out.println("LOADING MUSIC...");
		musicStore = new Music(musicFolder);
		
		voiceManager = new VoiceManager();
	}
	
	/**
	 * void setListenerValues()
	 * 
	 * We already defined certain values for the Listener, but we need to tell
	 * OpenAL to use that data. This function does just that.
	 */
	private void setListenerValues()
	{
		alListener3f(AL_POSITION, 0.0f, 0.0f, 0.0f);
		alListener3f(AL_VELOCITY, 0.0f, 0.0f, 0.0f);
		alListener3f(AL_ORIENTATION, 0.0f, 0.0f, -1.0f);
	}
	
	// ////////////////////////////////////////////
	// / playing sounds
	// ////////////////////////////////////////////
	
	/**
	 * Plays sound. If this source is already playing a sound it will stop and
	 * play this sound again from the beginning
	 */
	public void playSound(SoundSource source)
	{
		alSourcePlay(source.getSource());
	}
	/**
	 * Plays voice from source in {@link VoiceManager}. If this source is already playing a sound it will stop and
	 * play this sound again from the beginning
	 */
	public void playVoice()
	{
		playSound(getVoiceManager().getVoiceSource());
	}
	
	/** Plays sound loaded from file into the source. */
	public void playSound(SoundSource source, File data, boolean eraseLastBuffer)
	{
		if (eraseLastBuffer)
		{
			source.loadNewBuffer(data);
		}
		else
		{
			source.bindNewBuffer(data);
		}
		alSourcePlay(source.getSource());
	}
	
	// ////////////////////////////////////////////
	// / clean up
	// ////////////////////////////////////////////
	public void destroy()
	{
		if (!AL.isCreated()) return;
		musicStore.cleanUp();
		soundStore.cleanUp();
		AL.destroy();
	}
	
	// ////////////////////////////////////////////
	// / methods above work instantly. Everything
	// / below takes time and needs to be updated
	// ////////////////////////////////////////////
	
	/** update() method works all the time so it is easier to keep this variable */
	private int	i;
	
	/** This method is used only for fading so far */
	public void update()
	{
		for (i = 0; i < musicStore.size(); i++)
		{
			if (musicStore.getMusicSource(i).fadingEffect.isEnabled())
			{
				
				musicStore.setVolume(i, musicStore.getMusicSource(i).fadingEffect.update());
			}
		}
	}

	
}
