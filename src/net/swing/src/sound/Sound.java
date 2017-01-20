package net.swing.src.sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.annotation.PreDestroy;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class Sound
{
	
	/** Buffers hold sound data. */
	private int	index;
	
	public Sound(File f)
	{
		WaveData waveFile;
		try
		{
			waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(f)));
			index = AL10.alGenBuffers();
			AL10.alBufferData(index, waveFile.format, waveFile.data, waveFile.samplerate);
			waveFile.dispose();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	@PreDestroy
	public void cleanUp()
	{
		AL10.alDeleteBuffers(index);
	}
	
	public int getBuffer()
	{
		return index;
	}
	
}
