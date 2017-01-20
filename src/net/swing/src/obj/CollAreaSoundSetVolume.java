package net.swing.src.obj;

import net.LunarEngine2DMainClass;

public class CollAreaSoundSetVolume extends CollAreaSoundActions
{
	private final float	v;
	
	public CollAreaSoundSetVolume(String instruction)
	{
		super(instruction);
		v = (Integer.parseInt(instruction) / 1000f);
		
	}
	
	@Override
	public void run()
	{
		LunarEngine2DMainClass.getAudioManager().getSoundStore().setVolume(v);
	}
	
}
