package net.swing.src.sound;

import net.swing.ground.Delta;

public class FadingMusicEffect
{
	
	private float	fadingVelocity	= 0, aimedVolume = 1;
	private float	volume;
	
	public FadingMusicEffect(float delay, float toVolume, float currentVolume)
	{
		changeVelocity(delay, toVolume, currentVolume);
	}
	
	public float update()
	{
		if (aimedVolume * fadingVelocity < volume * fadingVelocity)
		{
			disableFading();
		}
		else
		{
			volume += fadingVelocity * Delta.get();
		}
		return volume;
	}
	
	/** delay in milliseconds */
	public void changeVelocity(float delay, float toVolume, float currentVolume)
	{
		if (toVolume > 1)
		{
			toVolume = 1;
		}
		else if (toVolume < 0)
		{
			toVolume = 0;
		}
		if (toVolume == currentVolume)
		{
			disableFading();
		}
		else
		{
			volume = currentVolume;
			aimedVolume = toVolume;
			fadingVelocity = (toVolume - currentVolume) / delay;
		}
	}
	
	/** to enable it just call changeVelocity() method */
	public void disableFading()
	{
		fadingVelocity = 0;
		volume = aimedVolume;
	}
	
	public float getVelocity()
	{
		return fadingVelocity;
	}
	
	public boolean isEnabled()
	{
		return getVelocity() != 0;
	}
}
