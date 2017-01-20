package net.swing.src.obj;

import net.LunarEngine2DMainClass;
import net.swing.src.ent.mind.AImath;

public class CollAreaMusicEnableFading extends CollAreaMusicActions
{
	public CollAreaMusicEnableFading(String instruction)
	{
		super(instruction);
		String[] parts = instruction.split("_");
		indexes = new int[parts.length][];
		for (int j = 0; j < parts.length; j++)
		{
			indexes[j] = AImath.partseInts(parts[j]);
		}
	}
	
	private final int[][]	indexes;
	
	@Override
	public void run()
	{
		for (int j = 0; j < indexes.length; j++)
		{
			LunarEngine2DMainClass.getAudioManager().getMusicStore().getMusicSource(indexes[j][0]).changeFadingVelocity(indexes[j][2], indexes[j][1] / 1000f);
		}
	}
	
}