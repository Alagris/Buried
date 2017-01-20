package net.swing.src.sound;

import java.io.File;

import net.swing.src.data.Files;

public class VoiceManager
{
	private SoundSource	voiceSource = new SoundSource(null,0);
	
	public SoundSource getVoiceSource()
	{
		return voiceSource;
	}
	
	public void stopVoice()
	{
		voiceSource.stop();
		voiceSource.cleanBuffer();
	}
	
	public void setVolume(float voiceVol)
	{
		voiceSource.setVolume(voiceVol);
	}
	
	/** Returns true if recording was
	 *  found and loading it succeeded. To play
	 *  loaded recording you must use playing methods 
	 *  from {@link AudioManager} */
	public boolean load(String recordingName)
	{
		File f = getVoiceFile(recordingName);
		if (f.exists())
		{
			voiceSource.loadNewBuffer(f);
			return true;
		}
		return false;
	}
	
	public File getVoiceFile(String recordingName)
	{
		return new File(Files.VOICE_FILE.f + "/" + recordingName + "/voice.wav");
	}
	
	public float getVolume()
	{
		return voiceSource.getVolume();
	}

	
}
