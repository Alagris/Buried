package net.swing.src.obj;

import net.LunarEngine2DMainClass;
import net.swing.src.ent.Action;
import net.swing.src.ent.Entity;

public final class CollAreaPlayVoice extends CollArea
{
	private final String	voiceName;
	
	public CollAreaPlayVoice(String instruction)
	{
		super(instruction);
		voiceName = instruction;
	}
	
	@Override
	public Action getAction()
	{
		return Action.PLAY_VOICE;
	}
	
	@Override
	public void run(Entity source, boolean wasAreaChanged)
	{
		if (wasAreaChanged)
		{
			LunarEngine2DMainClass.getGameView().loadVocieDialog(voiceName);
		}
	}
	
}
