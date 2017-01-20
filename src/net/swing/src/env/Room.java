package net.swing.src.env;

import org.lwjgl.util.vector.Vector2f;

import net.swing.engine.WPS;
import net.swing.engine.graph.DisplayBlocksMatrix;
import net.swing.engine.graph.DisplayMatrix;
import net.swing.ground.Pointf;
import net.swing.src.data.DataCoder;
import net.swing.src.data.RoomProperties;
import net.swing.src.ent.BindingList;
import net.swing.src.obj.CollAreas;
import net.swing.src.obj.StackOfCollAreas;

public abstract class Room
{
	
	/** Renders blocks grid */
	public void render(DisplayBlocksMatrix roomMatrix)
	{
		roomMatrix.renderDisplay(getFloor().getBlocksArray());
	}
	
	@Deprecated
	public void renderCollAreas(DisplayMatrix roomMatrix)
	{
		// roomMatrix.render(getCollArea().getBlocksArray());
	}
	
	/** This method updates (AI of) all entities but does not render anything */
	public void updateEntities()
	{
		getLivings().updateOrganized(this);
	}
	
	/** This method renders entities but does not update their AI */
	public void renderEntities()
	{
		getLivings().render();
	}
	
	// ========DATA GETTERS=====
	
	/** Returns coded floor data */
	public String getFloorData()
	{
		return DataCoder.codeData(getFloor().getBlocksArray(), 'R');
	}
	
	public String getCollData()
	{
		return DataCoder.codeData(getCollArea().getBlocksArray(), 'C');
	}
	
	// ========abstracts========
	/** Floor of room - all blocks attached to matrix */
	public abstract RoomFloor getFloor();
	
	/**
	 * Collision areas of room keeps information where player can walk and what
	 * will happen if something stands somewhere
	 */
	public abstract RoomCollision getCollArea();
	
	/** Name of room (it is used to load and save data to files) */
	public abstract String getName();
	
	/**
	 * Properties of room - information of what happens when player stands
	 * somewhere
	 */
	public abstract RoomProperties getProps();
	
	public abstract BindingList getLivings();
	
	// =====validations, getters etc
	
	public boolean validateWPSPoint(WPS w)
	{
		return validateWPSPoint(w.b, w.a);
	}
	
	/** Checks if point is in bounds (those bounds are mostly for entities) */
	public boolean validateWPSPoint(double b, double a)
	{
		// checking bounds
		if (1 <= a)
		{ // max x
			return false;
		}
		else if (1 <= b)
		{ // max y
			return false;
		}
		else if (0.004 > a)
		{ // min x
			return false;
		}
		else if (0.004 > b)
		{ // min y
			return false;
		}
		return true;
	}
	
	// =====non-validated======
	
	public Cell getCellAtCollision(WPS w)
	{
		return getCellAtCollision(w.a, w.b);
	}
	
	public Cell getCellAtCollision(double a, double b)
	{
		return getCellAtCollision(WorldSettings.WPS.getLocation(a, b));
	}
	
	public Cell getCellAtCollision(Pointf p)
	{
		return getCellAtCollision(p.getX(), p.getY());
	}
	
	public Cell getCellAtCollision(float x, float y)
	{
		return WorldSettings.cSelector.getCell(x, y);
	}
	
	public int getAreaIDAt(Cell c) throws IndexOutOfBoundsException
	{
		return getCollArea().getIDorError(c, CollAreas.solidBlock);
	}
	
	public int getAreaIDAt(float x, float y) throws IndexOutOfBoundsException
	{
		return getCollArea().getIDorError(WorldSettings.cSelector.getCell(x, y), CollAreas.solidBlock);
	}
	
	public StackOfCollAreas getAreaAt(Cell c) throws IndexOutOfBoundsException
	{
		return CollAreas.getByID(getAreaIDAt(c));
	}
	
	public StackOfCollAreas getAreaAt(float x, float y)
	{
		return CollAreas.getByID(getAreaIDAt(x, y));
	}
	
	public boolean isAreaSolid(float x, float y)
	{
		return getAreaIDAt(x, y) == CollAreas.solidBlock;
	}
	
	public int getAreaIDAt(Vector2f v)
	{
		return getAreaIDAt(v.x, v.y);
	}
	
	public StackOfCollAreas getAreaAt(Vector2f v)
	{
		return getAreaAt(v.x, v.y);
	}
	
	public boolean isAreaSolid(Vector2f v)
	{
		return isAreaSolid(v.x, v.y);
	}
	
	public int getAreaIDAt(Pointf p)
	{
		return getAreaIDAt(p.getX(), p.getY());
	}
	
	public StackOfCollAreas getAreaAt(Pointf p)
	{
		return getAreaAt(p.getX(), p.getY());
	}
	
	public boolean isAreaSolid(Pointf p)
	{
		return isAreaSolid(p.getX(), p.getY());
	}
	
	public int getAreaIDAt(WPS w)
	{
		return getAreaIDAt(WorldSettings.WPS.getLocation(w));
	}
	
	public StackOfCollAreas getAreaAt(WPS w)
	{
		return getAreaAt(WorldSettings.WPS.getLocation(w));
	}
	
	public boolean isAreaSolid(WPS w)
	{
		return isAreaSolid(WorldSettings.WPS.getLocation(w));
	}
	
	// ======validated=====
	public int getValidatedAreaIDAt(float x, float y)
	{
		return getCollArea().getIDorError(WorldSettings.cSelector.getCell(x, y), CollAreas.solidBlock);
	}
	
	public StackOfCollAreas getValidatedAreaAt(float x, float y)
	{
		return CollAreas.getByID(getValidatedAreaIDAt(x, y));
	}
	
	public boolean isValidatedAreaSolid(float x, float y)
	{
		return getValidatedAreaIDAt(x, y) == CollAreas.solidBlock;
	}
	
	public int getValidatedAreaIDAt(Vector2f v)
	{
		return getValidatedAreaIDAt(v.x, v.y);
	}
	
	public StackOfCollAreas getValidatedAreaAt(Vector2f v)
	{
		return getValidatedAreaAt(v.x, v.y);
	}
	
	public boolean isValidatedAreaSolid(Vector2f v)
	{
		return isValidatedAreaSolid(v.x, v.y);
	}
	
	public int getValidatedAreaIDAt(Pointf p)
	{
		return getValidatedAreaIDAt(p.getX(), p.getY());
	}
	
	public StackOfCollAreas getValidatedAreaAt(Pointf p)
	{
		return getValidatedAreaAt(p.getX(), p.getY());
	}
	
	public boolean isValidatedAreaSolid(Pointf p)
	{
		return isValidatedAreaSolid(p.getX(), p.getY());
	}
	
	public int getValidatedAreaIDAt(WPS w)
	{
		return getValidatedAreaIDAt(WorldSettings.WPS.getLocation(w));
	}
	
	public StackOfCollAreas getValidatedAreaAt(WPS w)
	{
		return getValidatedAreaAt(WorldSettings.WPS.getLocation(w));
	}
	
	public boolean isValidatedAreaSolid(WPS w)
	{
		return isValidatedAreaSolid(WorldSettings.WPS.getLocation(w));
	}
	
}
