package net.swing.src.ent;

import java.lang.reflect.InvocationTargetException;

import net.swing.engine.WPS;
import net.swing.ground.Graphics;
import net.swing.src.ent.mind.AI;
import net.swing.src.env.WorldSettings;

public final class EntityLoaded extends Entity
{
	private final EntityModel	model;
	
	public EntityLoaded(EntityModel model)
	{
		super(model.getSize(), model.getName());
		this.model = model;
		getGraphics().setWidth(WorldSettings.collWidth() * getAreaSize());
		setAffectedAsPoint(model.isAffectedAsPoint());
	}
	
	public EntityLoaded(EntityLoaded anotherInstance)
	{
		super(anotherInstance.model.getSize(), anotherInstance.model.getName());
		this.model = anotherInstance.model;
		getGraphics().setWidth(WorldSettings.collWidth() * getAreaSize());
	}
	
	@Override
	protected void render(WPS movingDirection, WPS AIdesiredMovVector)
	{
		model.render(movingDirection, AIdesiredMovVector, this);
	}
	
	@Override
	public AI getAI()
	{
		return model;
	}
	
	@Override
	public float getMaxHealth()
	{
		return model.getMaxHealth();
	}
	
	public EntityModel getModel()
	{
		return model;
	}
	
	@Override
	public Graphics getGraphics()
	{
		return model.getGraphics();
	}
	
	@Override
	public boolean isTeleportationAllowed()
	{
		return model.isTeleportationAllowed();
	}
	
	@Override
	public Entity createNewInstance()
	{
		try
		{
			EntityModel m;
			m = model.getClass().getConstructor().newInstance();
			System.out.println("lol " + m);
			System.out.println(m instanceof EntityModelTextured);
			return new EntityLoaded(model.getClass().getConstructor().newInstance());
		}
		catch (InstantiationException e)
		{
			
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			
			e.printStackTrace();
		}
		return null;
	}
	
}
