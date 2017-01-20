package net.swing.src.ent;

import net.LunarEngine2DMainClass;
import net.swing.src.ent.Mind.DefaultMind;
import net.swing.src.env.Room;

public class Living implements Comparable<Living>
{
	
	private Entity	ent;
	private Mind	mind;
	
	/**
	 * Adds new instance of entity.
	 * 
	 * @param entMarker
	 *            - it's just a name of entity
	 */
	public Living(String entMarker, Mind mind) throws NullPointerException
	{
		this(LunarEngine2DMainClass.getWorld().getEntities().parseEntity(entMarker), mind);
	}
	
	/** Sets mind as MINDLESS and adds this instance of entity */
	public Living(Entity entity)
	{
		this.setEnt(entity);
		this.setMind(new Mind(DefaultMind.MINDLESS));
	}
	
	/** Adds new instance of entity */
	public Living(Entity entity, Mind mind) throws NullPointerException
	{
		if (entity == null) throw new NullPointerException("Entity is null!");
		set(entity.createNewInstance(), mind);
	}
	
	public Mind getMind()
	{
		return mind;
	}
	
	public void setMind(Mind mind)
	{
		this.mind = mind;
	}
	
	public Entity getEnt()
	{
		return ent;
	}
	
	public void setEnt(Entity ent)
	{
		this.ent = ent;
	}
	
	public void set(Entity ent, Mind mind)
	{
		if (ent == null)
		{
			System.err.println("Living: Entity is null!");
			System.exit(10);
		}
		else
		{
			this.setEnt(ent);
		}
		if (mind == null)
		{
			System.err.println("Living: Mind is null!");
			this.setMind(new Mind(DefaultMind.MINDLESS));
		}
		else
		{
			this.setMind(mind);
		}
	}
	
	public void update(Room r)
	{
		ent.update(mind, r);
	}
	
	@Override
	public int compareTo(Living o)
	{
		return this.ent.b > o.ent.b ? 1 : -1;
	}
}
