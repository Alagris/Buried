package net.swing.src.obj;

import java.util.ArrayList;

import net.LunarEngine2DMainClass;
import net.swing.src.ent.mind.AImath;

public class CollAreaMusicSetVolume extends CollAreaMusicActions
{
	public CollAreaMusicSetVolume(String instruction)
	{
		super(instruction);
		/* Here will be stored only valid volumes */
		ArrayList<int[]> tempArrayList = new ArrayList<>(3);
		/* Here are all volumes */
		int[] tempArray = AImath.partseInts(instruction);
		for (int sourceIndex = 0; sourceIndex < tempArray.length; sourceIndex++)
		{
			/* If volume is equal to -1 then source will be skipped */
			if (tempArray[sourceIndex] > -1)
			{
				int[] validVlume = { sourceIndex, tempArray[sourceIndex] };
				tempArrayList.add(validVlume);
			}
			
		}
		indexes = tempArrayList.toArray(new int[0][]);
	}
	
	private final int[][]	indexes;
	
	@Override
	public void run()
	{
		for (int i = 0; i < indexes.length; i++)
		{
			LunarEngine2DMainClass.getAudioManager().getMusicStore().setVolume(indexes[i][0], indexes[i][1] / 1000f);
		}
	}
	
}