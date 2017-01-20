package net.swing.src.obj;

import net.LunarEngine2DMainClass;
import net.swing.src.ent.mind.AImath;

public class CollAreaMusicLoad extends CollAreaMusicActions
{
	public CollAreaMusicLoad(String instruction)
	{
		super(instruction);
		indexes = AImath.partseInts(instruction);
	}
	
	private final int[]	indexes;
	
	@Override
	public void run()
	{
		LunarEngine2DMainClass.getAudioManager().getMusicStore().loadSoundtracks(indexes);
	}
	
}