package net.swing.src.ent;

import net.swing.src.obj.CollArea;
import net.swing.src.obj.CollAreaAir;
import net.swing.src.obj.CollAreaDamage;
import net.swing.src.obj.CollAreaPlayMusic;
import net.swing.src.obj.CollAreaPlaySound;
import net.swing.src.obj.CollAreaPlayVoice;
import net.swing.src.obj.CollAreaPutArea;
import net.swing.src.obj.CollAreaPutBlock;
import net.swing.src.obj.CollAreaSolid;
import net.swing.src.obj.CollAreaSpawn;
import net.swing.src.obj.CollAreaTeleported;

public enum Action
{
	
	SOLID("", CollAreaSolid.class, true),
	DAMAGE("D", CollAreaDamage.class, true),
	TELEPORT("T", CollAreaTeleported.class, true),
	SPAWN("S", CollAreaSpawn.class, true),
	PUT_BlOCK("B", CollAreaPutBlock.class, false),
	PUT_AREA("A", CollAreaPutArea.class, false),
	PLAY_SOUND("F", CollAreaPlaySound.class, true),
	PLAY_MUSIC("U", CollAreaPlayMusic.class, true),
	PLAY_VOICE("V", CollAreaPlayVoice.class, true),
	NULL("", CollAreaAir.class, true);
	
	private String						savingShort;
	private Class<? extends CollArea>	type;
	private boolean						allowedAlways;
	
	private Action(String savingShort, Class<? extends CollArea> c, boolean limit)
	{
		this.savingShort = savingShort;
		this.type = c;
		this.allowedAlways = limit;
	}
	
	public String getShort()
	{
		return ":" + savingShort + ":";
	}
	
	public static Action getActionByShort(String shortS)
	{
		for (Action a : values())
		{
			if (a.savingShort.endsWith(shortS))
			{
				return a;
			}
		}
		return null;
	}
	
	public Class<? extends CollArea> getType()
	{
		return type;
	}
	
	public boolean isAllowedAlways()
	{
		return allowedAlways;
	}
	
}
