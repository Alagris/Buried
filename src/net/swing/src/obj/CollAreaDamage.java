package net.swing.src.obj;

import net.LunarEngine2DMainClass;
import net.swing.src.ent.Action;
import net.swing.src.ent.Entity;

public class CollAreaDamage extends CollArea
{
	
	private final float		damage;
	private final Entity	target;
	
	public CollAreaDamage(String instruction)
	{
		super(instruction);
		
		if (instruction.contains(" "))
		{
			String[] s = instruction.split(" ");
			damage = Float.parseFloat(s[0]);
			target = LunarEngine2DMainClass.getRoom().getLivings().getEntity(Integer.parseInt(s[2])).getEnt();
		}
		else
		{
			damage = Float.parseFloat(instruction);
			target = null;
		}
	}
	
	@Override
	public Action getAction()
	{
		return Action.DAMAGE;
	}
	
	/** notice that this method works no matter if area was changed or not */
	@Override
	public void run(Entity source, boolean wasAreaChanged)
	{
		if (target == null)
		{
			if (source != null)
			{
				source.hurt(damage * LunarEngine2DMainClass.getGamma());
			}
		}
		else
		{
			target.hurt(damage * LunarEngine2DMainClass.getGamma());
		}
		
	}
	
}
