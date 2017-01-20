package net.twi.AI.main;

import javax.swing.SpinnerNumberModel;

import net.swing.src.env.CellSelector;
import net.swing.src.env.Matrix;

public class R
{
	// /
	// variables:
	// /
	public static Matrix					MATRIX;
	public static final int					CELL_WIDTH				= 12;
	public static final int					CELL_HEIGHT				= 12;
	public static int						WIDTH;
	public static int						HEIGHT;
	public static final byte				foodCellSign			= 1;
	public static final byte				emptyCellSign			= -1;
	public static int						hungerTime				= 3000;
	public static int						replicationTime;
	public static long						sleepTime				= 0;
	public static int						foodRestorationSpeed	= 0;
	/** how many points breeding a new child costs */
	public static int						childCost				= 10;
	public static final int[]				outputNeurons			= { 21, 22, 23, 24 };
	public static final CellSelector		BLOCK_SELECTOR			= new CellSelector(CELL_WIDTH, CELL_HEIGHT, 0, 0);
	public static boolean					isSimulationPaused		= true;
	public static int						dataPushFrequency;
	public static int						foodAdding;
	public static FrameNeuralPixelTest		FRAME_NEURAL_PIXEL_TEST;
	public static boolean					doJustOneNextFrame;
	// /
	// Components:
	// /
	public static final SpinnerNumberModel	foodModel				= new SpinnerNumberModel(foodRestorationSpeed, 0, 1000, 1);
	public static final SpinnerNumberModel	hungerModel				= new SpinnerNumberModel(hungerTime, 100, 100000, 1);
	public static final SpinnerNumberModel	sleepTimeModel			= new SpinnerNumberModel(sleepTime, 0, 100000, 1);
	public static final SpinnerNumberModel	childCostModel			= new SpinnerNumberModel(childCost, 1, 100000, 1);
	public static final FrameFuncDisplayer	frameFuncDisplayer		= new FrameFuncDisplayer();
	
	static
	{
		frameFuncDisplayer.dispose();
	}
}
