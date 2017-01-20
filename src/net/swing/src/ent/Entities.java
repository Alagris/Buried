package net.swing.src.ent;



public final class Entities
{
	
	private Entity[]	entities;
	
	/**
	 * @param names
	 *            - set of names of <b><i>folders</i></b> with entity files.
	 *            Those names<b> HAVE to be THE SAME</b> as actual names of
	 *            entities.
	 * @exception IllegalArgumentException
	 * */
	public Entities(String... names) throws IllegalArgumentException
	{
		if (names == null || names.length == 0)
		{
			throw new IllegalArgumentException("There cannot exist any world without at least one entity");
		}
		System.out.println("Loading enitites started!");
		entities = new Entity[names.length];
		
		for (int i = 0; i < names.length; i++)
		{
			//TODO: create a system for entities loading
			//for now every loaded entity will be just an instance of player
			entities[i] = new EntityLoaded(new EntityPlayer());
			System.out.println("[" + i + "]" + entities[i].name + " loaded successfuly!");
		}
		System.out.println("Loading enitites finished!");
	}
	
	/** Finds entities by name */
	public Entity parseEntity(String name)
	{
		for (Entity entity : entities)
		{
			if (entity.name.equals(name))
			{
				return entity;
			}
		}
		return null;
	}
	
}
