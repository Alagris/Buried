package net.swing.src.obj;

import net.LunarEngine2DMainClass;

public class CollAreaSoundStop extends CollAreaSoundActions
{
	
	public CollAreaSoundStop(String instruction)
	{
		super(instruction);
	}
	
	@Override
	public void run()
	{
		LunarEngine2DMainClass.getAudioManager().getSoundStore().stopSounds();
	}
	
}
