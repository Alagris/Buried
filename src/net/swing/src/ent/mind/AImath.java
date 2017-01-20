package net.swing.src.ent.mind;

import java.util.ArrayList;

import net.swing.engine.WPS;
import net.swing.src.ent.Entity;
import net.swing.src.env.Cell;
import net.swing.src.env.Matrix;
import net.swing.src.env.Room;
import net.swing.src.env.WorldSettings;
import net.swing.src.obj.CollAreas;

public final class AImath
{
	/**
	 * Visited cell is used as marker of potentially useful cells in path
	 * finding
	 */
	public static final int		visitedCellID			= 70;
	
	private static Cell			tempCell				= new Cell();
	private static Cell			result					= new Cell();
	private static double		shortest;
	private static double		tempdistance;
	/** uses Pythagorean theorem and calculates the distance in straight line */
	public static final boolean	AI_MEASURING_STRAIGHT	= true;
	/** measures X and Y distance separately and then adds those two values */
	public static final boolean	AI_MEASURING_XYSUM		= false;
	
	/**
	 * There are two modes of measuring distance between cells. True - uses
	 * Pythagorean theorem and calculates the distance in straight line. False -
	 * measures X and Y distance separately and then adds those two values. By
	 * default it's set to false
	 */
	private static boolean		distanceMeasuringMode	= false;
	
	public static void enableMeasuringMode(boolean ai_measuring_mode)
	{
		distanceMeasuringMode = ai_measuring_mode;
	}
	
	private static void resetStaticvariables()
	{
		result.set(0, 0);
		shortest = 10000;
		tempdistance = 0;
	}
	
	/**
	 * @param pointA
	 *            - indicated start of ray
	 * @param pointB
	 *            - indicates beginning of ray
	 * @return true if any obstacles are found
	 */
	public static boolean findObstacles(Cell pointA, Cell pointB, Matrix matrix, int indexOfObstacle)
	{
		if (matrix.validateCell(pointB))
		{
			if (matrix.validateCell(pointA))
			{
				Matrix m = matrix.collectNewMatrix(indexOfObstacle);
				while (!pointA.equalsCell(pointB))
				{
					if (m.getID(pointA) == indexOfObstacle) return true;
					pointA = getNearestAdjacentCellIncludingCorners(m, pointA, pointB);
				}
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * tracks the shortest path from start to target and returns this path as
	 * matrix with nodes
	 */
	public static Cell[] findPath(Matrix areas, Cell start, Cell target)
	{
		if (areas.getID(target) == CollAreas.solidBlock)
		{
			return new Cell[0];// target is unreachable
		}
		else if (start.equalsCell(target))
		{
			return new Cell[0];
		}
		
		Matrix matrix = areas.collectNewMatrix(CollAreas.solidBlock);
		/*
		 * reached (but not exactly visited) cells. When tracking finishes
		 * openList will be cleared and filled with set of cells (from target to
		 * start) and then converted into array (Cell[]) - final version of path
		 */
		ArrayList<Cell> openList = new ArrayList<Cell>(WorldSettings.collAreaSize() / 3);
		
		openList.add(start);
		Cell nearestCell;
		int nearestCellIndex;
		// DisplayCollisionMatrix displayMat = new
		// DisplayCollisionMatrix(areas.getRows(), areas.getColumns());
		while (true)
		{
			// glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			//
			//
			// try {
			// Thread.sleep(10);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			// find the nearest (to target) reached cell{
			nearestCellIndex = getNearsetCell(openList, target);
			nearestCell = openList.get(nearestCellIndex);
			// }
			
			// removes cell from openList, sets it as solid in matrix and
			// creates a node{
			matrix.putBlock(visitedCellID, nearestCell);
			openList.remove(nearestCellIndex);
			// }
			
			// check if algorithm should end
			if (nearestCell.equalsCell(target))
			{// Notice that target cell wont
				// be added to matrix as
				// visited cell. Only as
				// solid (reached)
				matrix.putBlock(CollAreas.solidBlock, nearestCell);
				break;
			}
			else
			{// collect cells adjacent to nearest (just visited) cell
				addAdjacentCells(matrix, nearestCell, openList);
				if (openList.isEmpty())
				{
					// displayMat.removeMatrix();
					return new Cell[0];// target is unreachable
				}
			}
			// displayMat.renderDisplay(matrix.getBlocksArray());
			// Screen.update();
			// Display.update();
			// Display.sync(60);
		}
		
		// tracking finishes
		// openList will be cleared and filled with set of cells (from target to
		// start)
		openList.clear();
		openList.add(target);
		
		while (true)
		{
			// glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			// try {
			// Thread.sleep(10);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			nearestCell = getNearestVisitedCellExcludingCorners(matrix, target, start);
			matrix.putBlock(CollAreas.solidBlock, nearestCell);
			openList.add(nearestCell);
			target = nearestCell;
			
			// displayMat.renderDisplay(matrix.getBlocksArray());
			// Screen.update();
			// Display.update();
			// Display.sync(60);
			if (nearestCell.equalsCell(start))
			{
				break;
			}
		}
		// displayMat.removeMatrix();
		// converting into array (Cell[]) - final version of path
		return openList.toArray(new Cell[0]);
	}
	
	/**
	 * tracks the shortest path from start to target and returns this path as
	 * array of cells
	 */
	public static Cell[] trackPath(Matrix areas, Cell start, Cell target)
	{
		return findPath(areas, start, target);
	}
	
	/**
	 * tracks the shortest smooth path from start to target and returns this
	 * path as array of cells. Smooth path is the one that can be reached by
	 * entity of a particular size. Too tiny paths are skipped.
	 * 
	 * @param entitySize
	 *            - width of entity described by areas quantity
	 */
	public static Cell[] smoothPath(Matrix areas, Cell start, Cell target, int entitySize)
	{
		return findPath(areas.extendPathRows(entitySize - 1, CollAreas.solidBlock), start, target);
	}
	
	/**
	 * tracks the shortest smooth path from start to target and returns this
	 * path as array of cells
	 */
	public static Cell[] trackPathForEntity(Entity ent, Room r, Cell target)
	{
		return AImath.smoothPath(r.getCollArea(), WorldSettings.cSelector.getCell(WorldSettings.WPS.getX(ent.a), WorldSettings.WPS.getY(ent.b)), target, ent.getAreaSize());
	}
	
	/**
	 * tracks the shortest smooth path from start to target and returns this
	 * path as array of cells
	 */
	public static Cell[] trackPathForEntity(Entity ent, Room r, float targetX, float targetY)
	{
		return AImath.smoothPath(r.getCollArea(), WorldSettings.cSelector.getCell(WorldSettings.WPS.getX(ent.a), WorldSettings.WPS.getY(ent.b)), WorldSettings.cSelector.getCell(targetX, targetY), ent.getAreaSize());
	}
	
	private static int getNearsetCell(ArrayList<Cell> array, Cell target)
	{
		int nearestIndex = 0;
		double newDistance;
		double distance = 100000;// this is surely enough
		
		for (int i = 0; i < array.size(); i++)
		{
			newDistance = doPythagoreanTheorem(target.x - array.get(i).x, target.y - array.get(i).y);
			if (newDistance < distance)
			{
				distance = newDistance;
				nearestIndex = i;
			}
		}
		return nearestIndex;
		
	}
	
	/**
	 * Returns cell adjacent to start that is the nearest to target. Checks 4
	 * cells surrounding start.
	 */
	public static Cell getNearestAdjacentCellExcludingCorners(Matrix areas, Cell start, Cell target)
	{
		resetStaticvariables();
		
		checkAdjacentSides_1(areas, target, start);
		
		return new Cell(result);
	}
	
	/**
	 * Returns cell adjacent to start that is the nearest to target. It doesn't
	 * check what number in that cell is. Checks 8 cells surrounding start.
	 */
	public static Cell getNearestAdjacentCellIncludingCorners(Matrix areas, Cell start, Cell target)
	{
		resetStaticvariables();
		
		checkAdjacentSides_1(areas, target, start);
		
		checkAdjacentCorners_1(areas, target, start);
		
		return new Cell(result);
	}
	
	private static void checkAdjacentCorners_1(Matrix areas, Cell target, Cell start)
	{
		checkAdjacentCell_1(areas, target, start.x + 1, start.y + 1);
		
		checkAdjacentCell_1(areas, target, start.x - 1, start.y + 1);
		
		checkAdjacentCell_1(areas, target, start.x + 1, start.y - 1);
		
		checkAdjacentCell_1(areas, target, start.x - 1, start.y - 1);
	}
	
	private static void checkAdjacentSides_1(Matrix areas, Cell target, Cell start)
	{
		checkAdjacentCell_1(areas, target, start.x - 1, start.y);
		
		checkAdjacentCell_1(areas, target, start.x, start.y - 1);
		
		checkAdjacentCell_1(areas, target, start.x + 1, start.y);
		
		checkAdjacentCell_1(areas, target, start.x, start.y + 1);
	}
	
	private static void checkAdjacentCell_1(Matrix areas, Cell target, int x, int y)
	{
		tempCell.set(x, y);
		checkAdjacentCell(areas, target);
	}
	
	/**
	 * Returns cell adjacent to start cell that is the nearest to target. Only
	 * cell containing ID of visited area are checked (checks maximally 8 cells)
	 */
	public static Cell getNearestVisitedCellIncludingCorners(Matrix areas, Cell start, Cell target)
	{
		resetStaticvariables();
		checkAdjacentSides_2(areas, start, target);
		checkAdjacentCorners_2(areas, start, target);
		
		return new Cell(result);
	}
	
	/**
	 * Returns cell adjacent to start cell that is the nearest to target. Only
	 * cell containing ID of visited area are checked. Cells adjacent to corners
	 * of start are skipped. (checks maximally 4 cells)
	 */
	public static Cell getNearestVisitedCellExcludingCorners(Matrix areas, Cell start, Cell target)
	{
		resetStaticvariables();
		checkAdjacentSides_2(areas, start, target);
		
		return new Cell(result);
	}
	
	private static void checkAdjacentCorners_2(Matrix areas, Cell start, Cell target)
	{
		checkVisitedCell_2(areas, target, start.x - 1, start.y - 1);
		
		checkVisitedCell_2(areas, target, start.x + 1, start.y - 1);
		
		checkVisitedCell_2(areas, target, start.x + 1, start.y + 1);
		
		checkVisitedCell_2(areas, target, start.x - 1, start.y + 1);
	}
	
	private static void checkAdjacentSides_2(Matrix areas, Cell start, Cell target)
	{
		checkVisitedCell_2(areas, target, start.x - 1, start.y);
		
		checkVisitedCell_2(areas, target, start.x, start.y - 1);
		
		checkVisitedCell_2(areas, target, start.x + 1, start.y);
		
		checkVisitedCell_2(areas, target, start.x, start.y + 1);
	}
	
	private static void checkVisitedCell_2(Matrix areas, Cell target, int x, int y)
	{
		tempCell.set(x, y);
		checkVisitedCell_2(areas, target);
	}
	
	private static void checkVisitedCell_2(Matrix areas, Cell target)
	{
		if (areas.getIDorError(tempCell, 0) == visitedCellID)
		{
			checkAdjacentCell(areas, target);
		}
	}
	
	private static void checkAdjacentCell(Matrix areas, Cell target)
	{
		tempdistance = distanceMeasuringMode ? tempCell.getStraightDistanceToCell(target) : tempCell.getDistanceToCell(target);
		if (tempdistance < shortest)
		{
			result.set(tempCell);
			shortest = tempdistance;
		}
	}
	
	/**
	 * finds all adjacent cells around aim cell and adds them to openList. Solid
	 * or visited areas to skipped
	 */
	private static void addAdjacentCells(Matrix areas, Cell aim, ArrayList<Cell> openList)
	{
		addCell(areas, aim.x - 1, aim.y, openList);
		addCell(areas, aim.x, aim.y - 1, openList);
		addCell(areas, aim.x + 1, aim.y, openList);
		addCell(areas, aim.x, aim.y + 1, openList);
	}
	
	private static void addCell(Matrix areas, int x, int y, ArrayList<Cell> openList)
	{
		if (areas.validateCell(x, y))
		{
			if (areas.getID(x, y) < CollAreas.solidBlock)
			{
				tempCell.set(x, y);
				areas.putBlock(CollAreas.solidBlock, tempCell);
				openList.add(new Node(tempCell, x, y));
			}
		}
	}
	
	/**
	 * returns the length of c. It doesn't matter if a or b is negative or
	 * positive
	 * 
	 * @param a
	 *            - side a (or b - it doesn't matter)
	 * @param b
	 *            - side b (or a - it doesn't matter)
	 */
	public static double doPythagoreanTheorem(double a, double b)
	{
		return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
	}
	
	/**
	 * returns WPS vector (that starts in the <b>WPS point</b> of entity) aimed
	 * toward WPS location of center point of a cell. Length of this vector
	 * depends on speed e.g. speed = 0.01 makes vector length 0.01 (in WPS of
	 * course)
	 */
	public static WPS aimCell(WPS entityLocation, Cell aim, double speed)
	{
		WPS vector = aim.getCellCenter(WorldSettings.collWidth(), WorldSettings.collHeight());
		vector.subtracWPS(entityLocation);
		double vLenght = vector.doPythagoreanTheorem();
		vector.setA(vector.a * speed / vLenght);
		vector.setB(vector.b * speed / vLenght);
		return vector;
	}
	
	/**
	 * returns WPS vector (that starts in the <b>half</b> of entity's width)
	 * aimed toward WPS location of center point of a cell. Length of this
	 * vector depends on speed e.g. speed = 0.01 makes vector length 0.01 (in
	 * WPS of course)
	 * */
	public static WPS aimCellAndShiftEntity(Entity ent, Cell aim, double speed)
	{
		WPS vector = aim.getCellCenter(WorldSettings.collWidth(), WorldSettings.collHeight());
		vector.subtracWPS(ent.getCenter());
		double vLenght = vector.doPythagoreanTheorem();
		vector.setA(vector.a * speed / vLenght);
		vector.setB(vector.b * speed / vLenght);
		return vector;
	}
	
	/**
	 * returns WPS vector aimed toward WPS location of center point of a cell.
	 * Length of this vector depends on speed e.g. speed = 0.01 makes vector
	 * length 0.01 (in WPS of course) <b>WARNING: aim variable will be changed
	 * after calling this method!</b>
	 */
	public static WPS aimWPS(WPS sourcePoint, WPS aim, double speed)
	{
		aim.subtracWPS(sourcePoint);
		double vLenght = aim.doPythagoreanTheorem();
		aim.setA(aim.a * speed / vLenght);
		aim.setB(aim.b * speed / vLenght);
		return aim;
	}
	
	/** returns true if index is valid */
	public static boolean validateIndex(int index, int arrayLenght)
	{
		if (index < 0)
		{
			return false;
		}
		if (index >= arrayLenght)
		{
			return false;
		}
		return true;
	}
	
	/** Parses array of integers (e.g. 1,43,214,0,-1,23 ) */
	public static int[] partseInts(String ints)
	{
		int[] indexes = new int[ints.split(",").length];
		int i = 0;
		for (String s : ints.split(","))
		{
			indexes[i] = Integer.parseInt(s);
			i++;
		}
		return indexes;
	}
	
	/** Parses array of integers (e.g. 1,43,214,0,-1,23 ) */
	public static Integer[] partseIntegers(String ints)
	{
		Integer[] indexes = new Integer[ints.split(",").length];
		int i = 0;
		for (String s : ints.split(","))
		{
			indexes[i] = Integer.parseInt(s);
			i++;
		}
		return indexes;
	}
	
}
