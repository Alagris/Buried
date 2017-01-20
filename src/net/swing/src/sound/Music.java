package net.swing.src.sound;

import static org.lwjgl.openal.AL10.*;

import java.io.File;
import java.io.FilenameFilter;

import net.swing.src.ent.mind.AImath;

public final class Music
{
	
	/**
	 * Set of files with music sound tracks. Those tracks need to be buffered
	 * into Sound at first. It takes some time so don't do that during game. Do
	 * it when new room is being loaded.
	 */
	private File[]			musicStore;
	/**
	 * This is the volume that is selected by user is audio settings. Primary
	 * and secondary music sources use this value to calculate specific volume
	 * for each of them. The formula looks like this: real volume = fake volume
	 * * global volume.
	 */
	private float			globalVolume	= 1;
	/**
	 * volumes of sources (notice that the real values volume are shifted by
	 * globalVolume)
	 */
	private MusicSource[]	musicSources	= new MusicSource[3];
	
	public Music(File musicFolder)
	{
		musicStore = musicFolder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File arg0, String arg1)
			{
				return arg1.endsWith(".wav") || arg1.endsWith(".WAV");
			}
		});
		for (int i = 0; i < musicStore.length; i++)
		{
			System.out.println("[" + i + "] music " + musicStore[i].getName() + " loaded");
		}
		
		for (int i = 0; i < musicSources.length; i++)
		{
			musicSources[i] = new MusicSource(null,0);
			alSourcei(musicSources[i].getSource(), AL_LOOPING, AL_TRUE);
			System.out.println("Music source [" + i + "] creation finished successfully");
		}
	}
	
	/**
	 * for better synchronization at first all soundtracks are loaded and then
	 * played, instead of loading and playing first and then loading and playing
	 * second soundtrack and so on.
	 */
	public void playMusic(int[] musicIndexes)
	{
		if (getGlobalVolume() > 0)
		{// no point in playing with volume = 0
			for (int i = 0; i < musicSources.length && i < musicIndexes.length; i++)
			{
				if (musicSources[i].isPlaying()) continue;
				loadSoundtrack(i, musicIndexes[i]);
			}
			for (int i = 0; i < musicSources.length && i < musicIndexes.length; i++)
			{
				if (musicSources[i].isPlaying()) continue;
				startMusic(i);
			}
		}
	}
	
	/**
	 * plays music but if it is already playing it will start from the beginning
	 */
	public void startMusic(int[] sources)
	{
		if (getGlobalVolume() > 0)
		{// no point in playing with volume = 0
			for (int i : sources)
			{
				startMusicUnvalidetedVolume(i);
			}
		}
	}
	
	/**
	 * plays music but if it is already playing it will start from the beginning
	 */
	public void startMusic(int source)
	{
		if (getGlobalVolume() > 0)
		{// no point in playing with volume = 0
			startMusicUnvalidetedVolume(source);
		}
	}
	
	private void startMusicUnvalidetedVolume(int source)
	{
		if (AImath.validateIndex(source, musicSources.length))
		{
			alSourceRewind(musicSources[source].getSource());
			alSourcePlay(musicSources[source].getSource());
		}
	}
	
	/**
	 * plays music but if it is already playing it will start from the beginning
	 */
	public void startMusic(int source, int musicIndex)
	{
		if (getGlobalVolume() > 0)
		{// no point in playing with volume = 0
			loadSoundtrack(source, musicIndex);
			alSourcePlay(musicSources[source].getSource());
		}
	}
	
	/** plays music but if it is already playing nothing will happen */
	public void playMusic(int sourceIndex, int musicIndex)
	{
		if (getGlobalVolume() > 0)
		{// no point in playing with volume = 0
			if (musicSources[sourceIndex].isPlaying()) return;
			loadSoundtrack(sourceIndex, musicIndex);
			alSourcePlay(musicSources[sourceIndex].getSource());
		}
	}
	
	/** plays music from the moment when it last stopped */
	public void resumeMusic(int source)
	{
		if (getGlobalVolume() <= 0) return;// no point in playing with volume =
											// 0
		resumeOrStart(source);
	}
	
	/** plays music from the moment when it last stopped */
	public void resumeMusic(int[] sources)
	{
		if (getGlobalVolume() <= 0) return;// no point in playing with volume =
											// 0
		for (int i : sources)
		{
			if (AImath.validateIndex(i, musicSources.length))
			{
				resumeOrStart(i);
			}
		}
	}
	
	/** i - source index */
	private void resumeOrStart(int i)
	{
		if (AImath.validateIndex(i, musicSources.length))
		{
			if (musicSources[i].getState() != AL_PLAYING)
			{
				alSourcePlay(musicSources[i].getSource());
			}
		}
	}
	
	public void resumeAll()
	{
		for (int i = 0; i < musicSources.length; i++)
		{
			resumeMusic(i);
		}
	}
	
	public void pauseSource(int source)
	{
		musicSources[source].pauseSound();
	}
	
	public void pauseSource(int[] sources)
	{
		for (int i : sources)
		{
			if (AImath.validateIndex(i, musicSources.length))
			{
				musicSources[i].pauseSound();
			}
		}
	}
	
	public boolean isSourcePlaying(int source)
	{
		return musicSources[source].isPlaying();
	}
	
	/**
	 * when you load new sound track the last one is removed automatically so
	 * using this method is not really necessary
	 */
	public void removeSoundtrack(int sourceindex)
	{
		musicSources[sourceindex].cleanBuffer();
		musicSources[sourceindex].setSoundtrackIndex(-1);
	}
	
	/**
	 * returns source index if such exists. Otherwise it returns -1. If source
	 * exists but this soundtrack is already loaded it will also return -1
	 */
	public int loadSoundtrack(int source, int musicIndex)
	{
		if (musicIndex == musicSources[source].getSoundtrackIndex())
		{
			return -1;// stops because this soundtrack is already loaded here
		}
		if (AImath.validateIndex(source, musicSources.length))
		{
			musicSources[source].loadNewBuffer(musicStore[musicIndex]);
			musicSources[source].setSoundtrackIndex(musicIndex);
		}
		else
		{
			return -1;
		}
		return source;
	}
	
	public void loadSoundtracks(int[] soundtrackindexes)
	{
		for (int i = 0; i < soundtrackindexes.length; i++)
		{
			if (AImath.validateIndex(i, soundtrackindexes.length)) loadSoundtrack(i, soundtrackindexes[i]);
		}
	}
	
	public MusicSource getMusicSource(int source)
	{
		return musicSources[source];
	}
	
	public MusicSource[] getMusicSources()
	{
		return musicSources;
	}
	
	/** stops all sources */
	public void stopMusic()
	{
		for (int i = 0; i < musicSources.length; i++)
		{
			stop(i);
		}
	}
	
	public void stop(int source)
	{
		musicSources[source].stop();
	}
	
	public void stop(int[] sources)
	{
		for (int i : sources)
		{
			try
			{
				musicSources[i].stop();
			}
			catch (IndexOutOfBoundsException e)
			{
			}
		}
	}
	
	public float getGlobalVolume()
	{
		return globalVolume;
	}
	
	public void setGlobalVolume(float globalVolume)
	{
		this.globalVolume = globalVolume;
		resetVolumes();
	}
	
	private void resetVolumes()
	{
		for (int i = 0; i < musicSources.length; i++)
		{
			setVolume(i, getVolume(i));
		}
	}
	
	/** Sets both primary and secondary volume */
	public void setVolumeForAll(float v)
	{
		for (int i = 0; i < musicSources.length; i++)
		{
			setVolume(i, v);
		}
	}
	
	public void setVolume(int source, float v)
	{
		if (AImath.validateIndex(source, musicSources.length))
		{
			musicSources[source].setFakeVolume(v);
			musicSources[source].setVolume(v * globalVolume);
		}
	}
	
	public void addVolume(int source, float v)
	{
		setVolume(source, getVolume(source) + v);
	}
	
	public float getRealVolume(int source)
	{
		return musicSources[source].getVolume();
	}
	
	public float getVolume(int source)
	{
		if (AImath.validateIndex(source, musicSources.length))
		{
			return musicSources[source].getFakeVolume();
		}
		else
		{
			return 0;
		}
	}
	
	public void cleanUp()
	{
		for (SoundSource s : musicSources)
		{
			s.cleanUp();
		}
	}
	
	public File getMusic(int index)
	{
		if (AImath.validateIndex(index, musicStore.length))
		{
			return musicStore[index];
		}
		return null;
	}
	
	/** Quantity of music sources */
	public int size()
	{
		return musicSources.length;
	}
	
}
