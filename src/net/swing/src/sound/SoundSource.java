package net.swing.src.sound;

import static org.lwjgl.openal.AL10.alSourcePause;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.lwjgl.openal.AL10;

public class SoundSource
{
	
	/** Sources are points emitting sound. */
	private int		source;
	
	private Sound	sound;
	
	public SoundSource(File f)
	{
		loadNewSource(1f, new Sound(f));
	}
	
	public SoundSource(Sound sound)
	{
		loadNewSource(1f, sound);
	}
	
	public SoundSource(Sound sound, float volume)
	{
		loadNewSource(volume, sound);
	}
	
	/**
	 * This constructor does absolutely nothing so the first thing that you have
	 * to to is to call loadNewSource() method. If you don't know what sound to
	 * put in source yet, it's better to use constructor SoundSource(Sound sound,float volume)
	 * and set sound to null than use this one.
	 */
	@Deprecated
	public SoundSource()
	{
	}
	
	/**
	 * Creates new source without removing the last one (It's deprecated because
	 * this method should be used only in constructor of this class but it is
	 * public to use it just in case of emergency ! )
	 */
	@Deprecated
	@PostConstruct
	public void loadNewSource(float volume, Sound sound)
	{
		this.sound = sound;
		source = AL10.alGenSources();
		if (sound != null) AL10.alSourcei(source, AL10.AL_BUFFER, sound.getBuffer());
		setVolume(volume);
		setDefaultParameters();
	}
	
	private void setDefaultParameters()
	{
		AL10.alSourcef(source, AL10.AL_PITCH, 1.0f);
		AL10.alSource3f(source, AL10.AL_POSITION, 0.0f, 0.0f, 0.0f);
		AL10.alSource3f(source, AL10.AL_VELOCITY, 0.0f, 0.0f, 0.0f);
	}
	
	public void bindSound(Sound sound)
	{
		if (isPlaying())
		{
			stop();
		}
		this.sound = sound;
		AL10.alSourcei(source, AL10.AL_BUFFER, sound.getBuffer());
	}
	
	/**
	 * loads audio from file without changing parameters (volume etc). Last
	 * buffer will be removed !
	 */
	public int loadNewBuffer(File f)
	{
		if (sound != null) cleanBuffer();
		bindSound(new Sound(f));
		if (AL10.alGetError() == AL10.AL_NO_ERROR) return AL10.AL_TRUE;
		
		return AL10.AL_FALSE;
	}
	
	/**
	 * loads audio from file without changing parameters (volume etc). Last
	 * buffer will NOT be removed !
	 */
	public int bindNewBuffer(File f)
	{
		bindSound(new Sound(f));
		if (AL10.alGetError() == AL10.AL_NO_ERROR) return AL10.AL_TRUE;
		return AL10.AL_FALSE;
	}
	
	public void setVolume(float volume)
	{
		AL10.alSourcef(source, AL10.AL_GAIN, volume);
	}
	
	/** If no sound is binded this method will do nothing */
	public void cleanBuffer()
	{
		if (sound == null) return;
		sound.cleanUp();
	}
	
	@PreDestroy
	public void cleanUp()
	{
		if (sound != null) // this should stay just for safety
			if (AL10.alIsBuffer(sound.getBuffer()))
			{
				sound.cleanUp();
			}
		// if(AL10.alIsSource(source)){ this is not needed as long as source is
		// created in every constructor
		AL10.alDeleteSources(source);
		// }
		
	}
	
	public int getSource()
	{
		return source;
	}
	
	public Sound getSound()
	{
		return sound;
	}
	
	public boolean isPlaying()
	{
		return AL10.alGetSourcei(source, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}
	
	public int getState()
	{
		return AL10.alGetSourcei(source, AL10.AL_SOURCE_STATE);
	}
	
	public void stop()
	{
		AL10.alSourceStop(source);
	}
	
	public float getVolume()
	{
		return AL10.alGetSourcef(source, AL10.AL_GAIN);
	}
	
	/** pauses sound played at this source */
	public void pauseSound()
	{
		alSourcePause(source);
	}
	
}