package net.swing.src.sound;

import java.io.File;

public class MusicSource extends SoundSource
{
	
	public final FadingMusicEffect	fadingEffect	= new FadingMusicEffect(0, 0, 0);
	
	public MusicSource(File f)
	{
		super(f);
	}
	
	public MusicSource(Sound sound)
	{
		super(sound);
	}
	
	public MusicSource(Sound sound, float volume)
	{
		super(sound, volume);
	}
	
	/**
	 * This constructor does absolutely nothing so the first thing that you have
	 * to to is to call loadNewSource() method
	 */
	@Deprecated
	public MusicSource()
	{
		super();
	}
	
	private float	fakeVolume		= 1;
	private int		soundtrackIndex	= -1;
	
	public float getFakeVolume()
	{
		return fakeVolume;
	}
	
	public void setFakeVolume(float fakeVolume)
	{
		this.fakeVolume = fakeVolume;
	}
	
	public int getSoundtrackIndex()
	{
		return soundtrackIndex;
	}
	
	public void setSoundtrackIndex(int soundtrackIndex)
	{
		this.soundtrackIndex = soundtrackIndex;
	}
	
	public void changeFadingVelocity(float delay, float toVolume)
	{
		fadingEffect.changeVelocity(delay, toVolume, fakeVolume);
	}
}
