package net.swing.src.ent;

import net.LunarEngine2DMainClass;
import net.swing.src.obj.CollAreas;
import net.swing.src.obj.StackOfCollAreas;

public final class ActionManager
{
	
	public ActionManager()
	{
	}
	
	private int		lastArea	= 0, newArea = 0;
	private boolean	wasAreaChanged;
	
	/**
	 * WARNING: this method was designed to be used by only one and the same
	 * entity for all the time!
	 */
	public void correctEntity(Entity ent)
	{
		/*
		 * Finds area that contains point which is in the center of entity's
		 * width and in the bottom of its height
		 */
		newArea = LunarEngine2DMainClass.getRoom().getAreaIDAt(ent.shiftA(ent.getWidthWPS() / 2));
		wasAreaChanged = newArea != lastArea;
		correctEntity(ent, CollAreas.getByID(newArea));
		lastArea = newArea;
	}
	
	// F - plays sound.
	// F:[index] -plays sound loaded at index
	//
	// V - displays text and plays voice auio file.
	// V:[name]
	// name - name of folder with voice.WAV and text.TXT (e.g.
	// res/texts/sampleVoiceActingFolder)
	//
	// U - plays backgound music.
	// U:[index] -plays music loaded at index
	// If there is another music already being played it will stop
	// automatically. If index equals -1 (or is just lower than 0) the music
	// will be paused.
	private void correctEntity(Entity ent, StackOfCollAreas a)
	{
		if (a == null) return;
		a.run(ent, wasAreaChanged);
		
		// if (a.getAction() == Action.NULL) return;
		// switch (a.getAction())
		// {
		// case DAMAGE:
		// damageAction(ent, a);
		// break;
		// case SPAWN:
		// spawnLiving(a);
		// break;
		// case TELEPORT:
		// teleportAction(ent, a);
		// break;
		// case PUT_AREA:
		// putArea(a);
		// break;
		// case PUT_BlOCK:
		// putBlock(a);
		// break;
		// case PUT:
		// put(a);
		// break;
		// case MIXED:
		// correctEntity(ent, a.getInstructions());
		// break;
		// case PLAY_MUSIC:
		// playMusic(a);
		// break;
		// case PLAY_VOICE:
		// doText(a);
		// break;
		// case PLAY_SOUND:
		// playSound(a);
		// break;
		// }
	}
	
	// private void correctEntity(Entity ent, String a)
	// {
	// String[] actions = a.split("#");
	// for (String act : actions)
	// {
	// switch (act.charAt(0))
	// {
	// case 'D':
	// damageAction(ent, act.replaceFirst("D=", ""));
	// break;
	// case 'S':
	// spawnLiving(act.replaceFirst("S=", ""));
	// break;
	// case 'T':
	// teleportAction(ent, act.replaceFirst("T=", ""));
	// break;
	// case 'A':
	// putArea(act.replaceFirst("A=", ""));
	// break;
	// case 'B':
	// putBlock(act.replaceFirst("B=", ""));
	// break;
	// case 'P':
	// put(act.replaceFirst("P=", ""));
	// break;
	// case 'U':
	// playMusic(act.replaceFirst("U=", ""));
	// break;
	// case 'V':
	// doText(act.replaceFirst("V=", ""));
	// break;
	// case 'F':
	// playSound(act.replaceFirst("F=", ""));
	// break;
	// }
	// }
	// }
}
