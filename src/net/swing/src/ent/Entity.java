package net.swing.src.ent;

import net.LunarEngine2DMainClass;
import net.swing.engine.PAcceleration;
import net.swing.engine.PVector;
import net.swing.engine.WPS;
import net.swing.ground.Graphics;
import net.swing.src.ent.mind.AI;
import net.swing.src.env.Room;
import net.swing.src.env.WorldSettings;
import net.swing.src.obj.CollAreaTeleported;

public abstract class Entity extends WPS
{
	
	public enum EntState
	{
		FROZEN(false, true), // not moving
		VANISHED(false, false), // not moving/rendered
		INVISIBLE(true, false), // not rendered
		NORMAL(true, true);
		public boolean	moving, rendering;
		
		private EntState(boolean m, boolean r)
		{
			moving = m;
			rendering = r;
		}
	}
	
	private boolean			affectedAsPoint		= false;
	public final String		name;
	protected ActionManager	action				= new ActionManager();
	/**
	 * It is not used by default. This value changes only when other entities or
	 * any physical stuff (wind,explosions,etc.) touch/push/pull this entity
	 */
	public PAcceleration	acc					= new PAcceleration();
	/**
	 * Used to calculate final vector of movement of entity. This value at first
	 * is the same as entity AI's movement vector returned by method think().
	 * Then acceleration vector is applied to it . Then game checks if entity
	 * cannot move because of any obstacles (if so then this vector is reseted
	 * to 0). Then finally entity is moved and render. Everything starts once
	 * again when method update() is called. Value of this vector is NOT reseted
	 * to 0 so movement can be accelerated by environment.
	 * */
	protected PVector		vector				= new PVector();
	protected WPS			movementVector		= new WPS();
	/**
	 * Difference between AIdesiredMovVector and movementVector is that:
	 * <b>AIdesiredMovVector</b> - stays the same once AI decided to move entity
	 * somewhere. <b>movementVector</b> - at first is the same as
	 * AIdesiredMovVector but then it is changed by environment
	 */
	protected WPS			AIdesiredMovVector	= new WPS();
	protected float			health				= 1;
	/**
	 * number of extra points (default is one) that will be checked for
	 * collision. If its value is 2 then 3 points will be checked
	 */
	protected int			pointsQuantity;
	protected EntState		state				= EntState.NORMAL;
	/** index where this entity is on the bindingList */
	public int				index				= -1;
	
	public Entity(int sizeInAreas, String name)
	{
		pointsQuantity = sizeInAreas;
		this.name = name;
	}
	
	public void render()
	{
		if (state.rendering) render(movementVector, AIdesiredMovVector);
	}
	
	public int getAreaSize()
	{
		return pointsQuantity;
	}
	
	/**
	 * Short mark that represents reference to specific Entity in file (used to
	 * save data).Use 1 or 2 letters
	 */
	protected abstract void render(WPS movingDirection, WPS AIdesiredMovVector);
	
	public abstract AI getAI();
	
	/** Width measured with WPS vector */
	public abstract Graphics getGraphics();
	
	/** Returns new instance of this entity */
	public abstract Entity createNewInstance();
	
	/**
	 * if true this entity will be moved to various rooms when necessary (Look
	 * at {@link CollAreaTeleported})
	 */
	public abstract boolean isTeleportationAllowed();
	
	public abstract float getMaxHealth();
	
	public float getHealth()
	{
		return health;
	}
	
	protected void addHealth(float hp)
	{
		health += hp;
		if (health > getMaxHealth())
		{
			health = getMaxHealth();
		}
		else
		{
			health = 0;
			setState(EntState.VANISHED);
		}
	}
	
	public void update(Mind program, Room r)
	{
		if (state.moving)
		{
			if (getAI() != null)
			{
				/* This vector stays not changed */
				AIdesiredMovVector = getAI().think(this, program, r);
				/* This vector is going to be changed by environment */
				movementVector.set(AIdesiredMovVector);
			}
			if (affectedAsPoint)
			{
				affectByPhysicsForPoint(r);
			}
			else
			{
				affectByPhysics(r);
			}
			action.correctEntity(this);
		}
		render();
		movementVector.set(0, 0);
	}
	
	/**
	 * Collision detection is not 100% precise (on purpose). For example
	 * collision detection for player, which is as wide as 2 areas, works as if
	 * player was only one and a half area wide.
	 * 
	 * @param room
	 */
	protected void affectByPhysics(Room room)
	{
		affectByPhysics(pointsQuantity, room);
	}
	
	/**
	 * Collision detection is not 100% precise (on purpose). For example
	 * collision detection for player, which is as wide as 2 areas, works as if
	 * player was only one and a half area wide.
	 * 
	 * @param argPointsQuantity
	 * @param room
	 */
	protected void affectByPhysics(int argPointsQuantity, Room room)
	{
		
		vector.addVector((acc.toWPS().a * LunarEngine2DMainClass.getGamma() + movementVector.a), (acc.toWPS().b * LunarEngine2DMainClass.getGamma() + movementVector.b));
		
		if (canMoveAForAll(this, argPointsQuantity, WorldSettings.WPScollWidth, room))
		{
			
			if (canMoveBForAll(this, argPointsQuantity, WorldSettings.WPScollWidth, room))
			{
				vector.apply(this, LunarEngine2DMainClass.getGamma());
				vector.subtractVector(movementVector.a, movementVector.b);
				// didEntityHitSolidArea = false;
			}
			else
			{
				vector.applyA(this, LunarEngine2DMainClass.getGamma());
				vector.subtractVectorAxisA(movementVector.a);
				vector.resetB();
				// didEntityHitSolidArea = true;
			}
			
		}
		else
		{
			
			if (canMoveBForAll(this, argPointsQuantity, WorldSettings.WPScollWidth, room))
			{
				vector.applyB(this, LunarEngine2DMainClass.getGamma());
				vector.subtractVectorAxisB(movementVector.b);
				vector.resetA();
				// didEntityHitSolidArea = true;
			}
			else
			{
				vector.reset();
				// didEntityHitSolidArea = true;
			}
		}
	}
	
	/**
	 * Collision detection is not 100% precise (on purpose). For example
	 * collision detection for player, which is as wide as 2 areas, works as if
	 * player was only one and a half area wide.
	 * 
	 * @param room
	 */
	protected void affectByPhysicsForPoint(Room room)
	{
		
		vector.addVector((acc.toWPS().a * LunarEngine2DMainClass.getGamma() + movementVector.a), (acc.toWPS().b * LunarEngine2DMainClass.getGamma() + movementVector.b));
		if (cantMoveA(this, room))
		{
			
			if (cantMoveB(this, room))
			{
				vector.reset();
				// didEntityHitSolidArea = true;
			}
			else
			{
				vector.applyB(this, LunarEngine2DMainClass.getGamma());
				vector.subtractVectorAxisB(movementVector.b);
				vector.resetA();
				// didEntityHitSolidArea = true;
				
			}
			
		}
		else
		{
			if (cantMoveB(this, room))
			{
				vector.applyA(this, LunarEngine2DMainClass.getGamma());
				vector.subtractVectorAxisA(movementVector.a);
				vector.resetB();
				// didEntityHitSolidArea = true;
			}
			else
			{
				vector.apply(this, LunarEngine2DMainClass.getGamma());
				vector.subtractVector(movementVector.a, movementVector.b);
				// didEntityHitSolidArea = false;
			}
			
		}
	}
	
	/**
	 * Set to true so collision detection will work only for one point (in the
	 * middle of entity's width). It is false by default.
	 */
	public void setAffectedAsPoint(boolean affectedAsPoint)
	{
		this.affectedAsPoint = affectedAsPoint;
	}
	
	/**
	 * checks if all points can be moved by vector in axis A. Those points are
	 * calculated like this: point.A = start.A+index*width and indexes are
	 * between 0 (start point) to quantity (last point) and n < quantity (not
	 * <=)
	 * 
	 * @param width
	 *            - usually it is area width in WPS
	 */
	private boolean canMoveAForAll(WPS start, int quantity, double width, Room room)
	{
		for (int i = 0; i < quantity; i++)
		{
			if (cantMoveA(start.shiftA(width / 2 + i * width), room))
			{
				return false;
			}
		}
		return true;
	}
	
	/** @return width converted into WPS A value */
	public double getWidthWPS()
	{
		return WorldSettings.WPS.getWorldPositionSystem().getA(getGraphics().width);
	}
	
	/** @return height converted into WPS B value */
	public double getHeightWPS()
	{
		return WorldSettings.WPS.getWorldPositionSystem().getB(getGraphics().height);
	}
	
	/** Returns WPS point in the center of bottom of entity */
	public WPS getCenter()
	{
		return shiftA(getWidthWPS() / 2);
	}
	
	/**
	 * checks if all points can be moved by vector in axis B. Those points are
	 * calculated like this: point.A = start.A+index*width and indexes are
	 * between 0 (start point) to quantity (last point) and n < quantity (not
	 * <=)
	 * 
	 * @param width
	 *            - usually it is area width
	 * @param room
	 */
	private boolean canMoveBForAll(WPS start, int quantity, double width, Room room)
	{
		for (int i = 0; i < quantity; i++)
		{
			if (cantMoveB(start.shiftA(width / 2 + i * width), room))
			{
				return false;
			}
		}
		return true;
	}
	
	private boolean cantMoveA(WPS w, Room room)
	{
		return room.isValidatedAreaSolid(vector.abstractApplyAxisA(w, LunarEngine2DMainClass.getGamma()));
	}
	
	private boolean cantMoveB(WPS w, Room room)
	{
		return room.isValidatedAreaSolid(vector.abstractApplyAxisB(w, LunarEngine2DMainClass.getGamma()));
	}
	
	/** Takes all HP */
	public void kill()
	{
		addHealth(-getHealth());
	}
	
	public void hurt(float hp)
	{
		addHealth(-hp);
	}
	
	public void heal(float hp)
	{
		addHealth(hp);
	}
	
	public void setState(EntState s)
	{
		state = s;
	}
	
	public EntState getState()
	{
		return state;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
	
}
