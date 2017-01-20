package net.swing.src.obj;

import net.swing.src.ent.Action;
import net.swing.src.ent.Entity;

public final class CollAreaPlaySound extends CollArea
{
	private final CollAreaSoundActions	action;
	
	public CollAreaPlaySound(String instruction)
	{
		super(instruction);
		
		switch (instruction.charAt(0))
		{
			case 'S':
				action = new CollAreaSoundStop(instruction.replaceFirst("S", ""));
				break;
			case 'P':
				action = new CollAreaSoundStart(instruction.replaceFirst("P", ""));
				break;
			case 'V':
				action = new CollAreaSoundSetVolume(instruction.replaceFirst("V", ""));
				break;
			default:
				action = new CollAreaSoundActions("") {
					@Override
					public void run()
					{
					}
				};
				break;
		}
	}
	
	@Override
	public Action getAction()
	{
		return Action.PLAY_SOUND;
	}
	
	@Override
	public void run(Entity source, boolean wasAreaChanged)
	{
		if (wasAreaChanged)
		{
			action.run();
		}
	}
	
}
