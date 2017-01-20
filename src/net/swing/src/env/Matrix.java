package net.swing.src.env;

import java.util.Arrays;

import net.swing.src.ent.mind.AImath;

public class Matrix
{
	
	/**
	 * Array of IDs of blocks (saving only numbers wastes much less memory than
	 * saving whole {@link CollArea} instance)
	 */
	protected int[]	blocks;
	/** rows and columns start with index 1 (first row/column is 1 NOT 0) */
	protected int	r, c;
	
	/**
	 * Creation of empty room (everything is air)
	 * 
	 * @param columns
	 * @param rows
	 */
	public Matrix(int rows, int columns)
	{
		this.r = rows;
		this.c = columns;
		blocks = new int[r * c];
	}
	
	/**
	 * @param parsedData
	 *            - this must have the same length as it was set in the
	 *            constructor
	 */
	public void attachDataToMatrix(int[] parsedData)
	{
		if (parsedData.length < blocks.length)
		{
			parsedData = Arrays.copyOf(parsedData, blocks.length);
		}
		else if (parsedData.length > blocks.length)
		{
			parsedData = Arrays.copyOf(parsedData, blocks.length);
		}
		blocks = parsedData;
	}
	
	/**
	 * Creation of room filled with blocks from an array
	 * 
	 * @param maxValidID
	 *            - 0 will be put if ID in IDsToFillMatrix is HIGHER or EQUAL to
	 *            maxValidID
	 */
	public Matrix(int rows, int columns, int[] IDsToFillMatrix, int maxValidID)
	{
		blocks = new int[IDsToFillMatrix.length];
		this.r = rows;
		this.c = columns;
		for (int i = 0; i < blocks.length; i++)
		{
			if (IDsToFillMatrix[i] > maxValidID)
			{
				if (IDsToFillMatrix[i] < 0)
				{
					blocks[i] = 0;
					continue;
				}
			}
			blocks[i] = IDsToFillMatrix[i];
		}
	}
	
	/** Creation of room filled with blocks from an array */
	public Matrix(int rows, int columns, int[] IDsToFillMatrix)
	{
		blocks = new int[IDsToFillMatrix.length];
		this.r = rows;
		this.c = columns;
		for (int i = 0; i < blocks.length; i++)
		{
			if (IDsToFillMatrix[i] < 0)
			{
				blocks[i] = 0;
				continue;
			}
			blocks[i] = IDsToFillMatrix[i];
		}
	}
	
	/** Returns ID of block which is pointed by cell */
	public int getID(Cell location) throws IndexOutOfBoundsException
	{
		return blocks[getIndex(location)];
	}
	
	/** Returns ID of block which is pointed by cell */
	public int getID(int x, int y) throws IndexOutOfBoundsException
	{
		return blocks[getIndex(x, y)];
	}
	
	/** Returns ID of block which is pointed by cell */
	public int getID(int index) throws IndexOutOfBoundsException
	{
		return blocks[index];
	}
	
	/** Returns ID of block which is pointed by cell */
	public int getIDorError(Cell location, int error)
	{
		return getIDorError(location.getX(), location.getY(), error);
	}
	
	/** Returns ID of block which is pointed by cell */
	public int getIDorError(int x, int y, int error)
	{
		return validateCell(x, y) ? blocks[getIndex(x, y)] : error;
	}
	
	/** Returns ID of block which is pointed by cell */
	public int getIDorError(int index, int error)
	{
		return AImath.validateIndex(index, blocks.length) ? blocks[index] : error;
	}
	
	/** Returns index which is pointed by cell */
	public int getIndex(int x, int y)
	{// room (floor and collision areas) start coordinates are 1,1
		// don't change start coordinates to 0,0
		return (y - 1) * c + x - 1; // This line calculates index
	}
	
	/** Returns index which is pointed by cell */
	public int getIndex(Cell location)
	{
		return getIndex(location.x, location.y);
	}
	
	public Cell getCell(int index)
	{
		Cell c = new Cell();
		c.setY(getCellRow(index));
		c.setX(getCellColumn(index));
		return c;
	}
	
	public int getCellColumn(int index)
	{
		return index % c + 1;
	}
	
	public int getCellRow(int index)
	{
		return index / c + 1;
	}
	
	public int[] getBlocksArray()
	{
		return blocks;
	}
	
	public void putBlock(int ID, Cell... cell)
	{
		for (Cell cell2 : cell)
		{
			putBlockHere(cell2.x, cell2.y, ID);
		}
	}
	
	public void putBlockHere(int x, int y, int ID)
	{
		if (validateCell(x, y)) blocks[getIndex(x, y)] = ID;
	}
	
	/**
	 * @param maxValid
	 *            - method will stop if ID is HIGHER or EQUAL to maxValid
	 */
	public void putBlock(int ID, int maxValid, Cell... cell)
	{
		if (ID < maxValid)
		{
			for (Cell cell2 : cell)
			{
				putBlockHere(cell2.x, cell2.y, ID);
			}
		}
	}
	
	/**
	 * @param maxValid
	 *            - method will stop if ID is HIGHER or EQUAL to maxValid
	 */
	public void putBlockHere(int x, int y, int ID, int maxValid)
	{
		if (ID < maxValid)
		{
			if (validateCell(x, y))
			{
				blocks[getIndex(x, y)] = ID;
			}
		}
	}
	
	/** Index is NOT validated */
	public void putBlock(int index, int ID)
	{
		blocks[index] = ID;
	}
	
	/**
	 * Index is NOT validated
	 * 
	 * @param maxValid
	 *            - method will stop if ID is HIGHER or EQUAL to maxValid
	 */
	public void putBlock(int index, int ID, int maxValid)
	{
		if (ID < maxValid) blocks[index] = ID;
	}
	
	public void fillWithNumber(int n)
	{
		for (int i = 0; i < blocks.length; i++)
		{
			blocks[i] = n;
		}
	}
	
	/** true if cell is valid */
	public boolean validateCell(Cell arg0)
	{
		return validateCell(arg0.x, arg0.y);
	}
	
	/** true if cell is valid */
	public boolean validateCell(int x, int y)
	{
		if (x > 0)
		{
			if (x <= c)
			{
				if (y > 0)
				{
					if (y <= r)
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public int size()
	{
		return blocks.length;
	}
	
	/** returns new matrix that includes only ID from array */
	public Matrix collectNewMatrix(int... acceptedNumbers)
	{
		Matrix m = new Matrix(r, c);
		for (int i = 0; i < m.size(); i++)
		{
			// checking if ID is in array (accpetedNumbers)
			for (int j = 0; j < acceptedNumbers.length; j++)
			{
				if (getID(i) == acceptedNumbers[j])
				{
					m.putBlock(i, getID(i));
				}
			}
		}
		return m;
	}
	
	public int getRows()
	{
		return r;
	}
	
	public int getColumns()
	{
		return c;
	}
	
	/**
	 * Returns new matrix which has shifted rows/columns.
	 * 
	 * @param rowsOrcolumns
	 *            - if true matrix will have shifted rows
	 * @param distance
	 *            - how far each area will be moved e.g. when distance = 1 an
	 *            area will move from x:0 y:4 to x:0 y:5 (while rowsOrcolumns is
	 *            false)
	 */
	public Matrix collectShifted(boolean rowsOrcolumns, int distance)
	{
		if (rowsOrcolumns)
		{
			return collectShiftedRows(distance);
		}
		else
		{
			return collectShiftedColumns(distance);
		}
	}
	
	/**
	 * Returns new matrix which has shifted rows/columns.
	 * 
	 * @param distance
	 *            - how far each area will be moved e.g. when distance = 1 an
	 *            area will move from x:0 y:4 to x:0 y:5
	 */
	public Matrix collectShiftedRows(int distance)
	{
		Matrix m = new Matrix(r, c);
		for (int x = 1; x <= c; x++)
		{
			try
			{
				for (int y = 1; y <= r; y++)
				{
					m.putBlockHere(x, y, getID(x + distance, y));
				}
			}
			catch (IndexOutOfBoundsException e)
			{
				break;
			}
		}
		return m;
	}
	
	/**
	 * Returns new matrix which has shifted rows/columns.
	 * 
	 * @param distance
	 *            - how far each area will be moved e.g. when distance = 1 an
	 *            area will move from x:0 y:4 to x:0 y:5
	 */
	public Matrix collectShiftedColumns(int distance)
	{
		Matrix m = new Matrix(r, c);
		for (int x = 1; x <= c; x++)
		{
			try
			{
				for (int y = 1; y <= r; y++)
				{
					m.putBlockHere(x, y, getID(x, y + distance));
				}
			}
			catch (IndexOutOfBoundsException e)
			{
				break;
			}
		}
		return m;
	}
	
	/**
	 * In each cell sets the highest value found in any of matrices. This
	 * operation is applied to new empty matrix.
	 */
	public Matrix matricesFusion(Matrix... matrices)
	{
		Matrix m = new Matrix(r, c);
		for (Matrix mx : matrices)
		{
			m.matricesFusionApplied(mx);
		}
		return m;
	}
	
	/**
	 * In each cell sets the highest value found in exactly the same cell in
	 * matrix m or this matrix. This operation is applied to this matrix. Notice
	 * that fs in every cell in matrix m value is lower than value in the same
	 * cell in this matrix then this matrix will not change at all. Matrix m is
	 * not edited in any case.
	 */
	public void matricesFusionApplied(Matrix m)
	{
		for (int i = 0; i < m.size(); i++)
		{
			putBlock(i, getID(i) > m.getID(i) ? getID(i) : m.getID(i));
		}
		
	}
	
	/** This method is designed to be used by AI. */
	public Matrix extendPathRows(int shift, int solidBlockID)
	{
		if (shift < 1)
		{
			return collectNewMatrix(solidBlockID);
		}
		Matrix[] mxs = new Matrix[shift + 1];
		mxs[0] = collectNewMatrix(solidBlockID);// matrix at index 0 is
												// original one
		for (int i = 1; i < mxs.length; i++)
		{
			mxs[i] = mxs[0].collectShiftedRows(i);
		}
		for (int i = 1; i < mxs.length; i++)
		{
			mxs[0].matricesFusionApplied(mxs[i]);
		}
		return mxs[0];
	}
	
	@Override
	public String toString()
	{
		return "Matrix[columns=" + c + " ,rows=" + r + "]";
	}
	
	public int getRandomColumn()
	{
		return (int) (c * Math.random());
	}
	
	public int getRandomRow()
	{
		return (int) (r * Math.random());
	}
	
}
