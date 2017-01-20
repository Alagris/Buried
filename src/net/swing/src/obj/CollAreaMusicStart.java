package net.swing.src.obj;

import net.LunarEngine2DMainClass;
import net.swing.src.ent.mind.AImath;

public class CollAreaMusicStart extends CollAreaMusicActions
{
	public CollAreaMusicStart(String instruction)
	{
		super(instruction);
		indexes = AImath.partseInts(instruction);
	}
	
	private final int[]	indexes;
	
	@Override
	public void run()
	{
		LunarEngine2DMainClass.getAudioManager().getMusicStore().startMusic(indexes);
	}
	
}