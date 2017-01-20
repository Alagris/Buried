package net.swing.src.obj;

import net.swing.src.ent.Action;
import net.swing.src.ent.Entity;

public final class CollAreaPlayMusic extends CollArea
{
	private final CollAreaMusicActions	action;
	
	public CollAreaPlayMusic(String instruction)
	{
		super(instruction);
		switch (instruction.charAt(0))
		{
			case 'F':// enabling fading effect
				action = new CollAreaMusicEnableFading(instruction.replaceFirst("F", ""));
				break;
			case 'P':// playing
				action = new CollAreaMusicStart(instruction.replaceFirst("P", ""));
				break;
			case 'S':// stopping
				action = new CollAreaMusicStop(instruction.replaceFirst("S", ""));
				break;
			case 'A':// pausing
				action = new CollAreaMusicPause(instruction.replaceFirst("A", ""));
				break;
			case 'R':// resuming
				action = new CollAreaMusicResume(instruction.replaceFirst("R", ""));
				break;
			case 'L':// loading soundtracks
				action = new CollAreaMusicLoad(instruction.replaceFirst("L", ""));
				break;
			case 'V':// setting volume
				action = new CollAreaMusicSetVolume(instruction.replaceFirst("V", ""));
				break;
			default:
				/*
				 * If instruction deosn't fit any pattern action will be set to
				 * this useless instance
				 */
				action = new CollAreaMusicActions("") {
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
		return Action.PLAY_MUSIC;
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
