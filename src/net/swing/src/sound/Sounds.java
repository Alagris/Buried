package net.swing.src.sound;

import static org.lwjgl.openal.AL10.alSourcePlay;

import java.io.File;
import java.io.FilenameFilter;

import net.swing.src.ent.mind.AImath;

public final class Sounds
{
	
	private Sound[]			sounds;
	/**
	 * volume level is also used to tell methods that everything was loaded
	 * correctly
	 */
	private float			volume	= -1;
	private SoundSource[]	sources;
	
	public Sounds(File folder, int sourcesToCreate)
	{
		File[] files = folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File arg0, String arg1)
			{
				return arg1.endsWith(".wav") || arg1.endsWith(".WAV");
			}
		});
		if (files.length > 0)
		{
			volume = 1; // volume level is used to tell methods that everything
						// was loaded correctly
			
			sounds = new Sound[files.length];
			
			for (int i = 0; i < sounds.length; i++)
			{
				sounds[i] = new Sound(files[i]);
				System.out.println("[" + i + "] sound " + files[i] + " loaded");
			}
			
			sources = new SoundSource[sourcesToCreate];
			System.out.println("base created with " + sourcesToCreate + " sources");
			
			for (int i = 0; i < sources.length; i++)
			{
				sources[i] = new SoundSource(null,0);
			}
		}
	}
	
	/** Plays new sound */
	public void playSound(int... index)
	{
		for (int i : index)
		{
			playSound(getSoundSource(i));
		}
	}
	
	public void playSound(SoundSource src)
	{
		if (src == null) return;
		alSourcePlay(src.getSource());
	}
	
	/**
	 * Returns source that is unused but also loads a sound from sounds base
	 * into this source
	 * 
	 * @param index
	 *            - index of sound in the base
	 */
	public SoundSource getSoundSource(int index)
	{
		if (volume <= 0) return null;
		if (AImath.validateIndex(index, sounds.length))
		{
			return getFreeSource(sounds[index]);
		}
		else
		{
			return null;
		}
	}
	
	/** Returns source that is unused */
	public SoundSource getFreeSource()
	{
		if (volume <= 0) return null;
		for (SoundSource src : sources)
		{
			if (!src.isPlaying())
			{
				src.setVolume(volume);
				return src;
			}
		}
		return null;
	}
	
	/** Finds source that is unused and loads sound into it */
	public SoundSource getFreeSource(Sound sound)
	{
		if (volume <= 0) return null;
		for (SoundSource src : sources)
		{
			if (!src.isPlaying())
			{
				src.bindSound(sound);
				src.setVolume(volume);
				return src;
			}
		}
		return null;
	}
	
	public void stopSounds()
	{
		if (volume == -1) return;
		for (SoundSource src : sources)
		{
			src.stop();
		}
	}
	
	public void cleanUp()
	{
		if (volume == -1) return;
		for (Sound ss : sounds)
		{
			ss.cleanUp();
		}
		for (SoundSource src : sources)
		{
			src.cleanUp();
		}
	}
	
	public void setVolume(float volume)
	{
		if (volume == -1) return;
		this.volume = volume;
	}
	
	public float getVolume()
	{
		return volume;
	}
}
