package net.swing.src.obj;

import net.LunarEngine2DMainClass;

public class CollAreaSoundStart extends CollAreaSoundActions
{
	private final int[]	soundID;
	
	public CollAreaSoundStart(String instruction)
	{
		super(instruction);
		String[] s = instruction.split(",");
		soundID = new int[s.length];
		for (int j = 0; j < s.length; j++)
		{
			soundID[j] = Integer.parseInt(s[j]);
		}
	}
	
	@Override
	public void run()
	{
		LunarEngine2DMainClass.getAudioManager().getSoundStore().playSound(soundID);
	}
	
}
