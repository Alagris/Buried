package net.twi.AI.main;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glGetString;
import static net.twi.AI.main.R.*;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.math.src.genetics.BinaryDNA;
import net.math.src.genetics.GeneticCode;
import net.math.src.genetics.LethalMutationException;
import net.swing.engine.graph.DisplayMatrix;
import net.swing.ground.Camera;
import net.swing.ground.Controls;
import net.swing.ground.Pointf;
import net.swing.src.env.Matrix;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.Color;

public class FrameNeuralPixelTest
{

	private static boolean				isRunning				= true;

	private ArrayList<Agent>			agents;

	public final DisplayMatrix			BOOLEAN_MATRIX;

	private NeuronsAndStatisticsViewer	viewer					= new NeuronsAndStatisticsViewer(2);
	private Pointf						viewLocation			= new Pointf(0, 0);

	private static boolean				visualizationEnabled	= true;
	private Agent						trackedAgent;

	@SuppressWarnings("deprecation")
	public FrameNeuralPixelTest()
	{

		// NeuronsViewer neuronsViewer = new NeuronsViewer();
		LauncherAI launcher = new LauncherAI(Thread.currentThread());
		try
		{
			synchronized (Thread.currentThread())
			{
				Thread.currentThread().wait();
			}
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}

		Thread viewThread = new Thread(viewer);
		viewThread.start();

		MATRIX = new Matrix((int) launcher.rowsModel.getValue(), (int) launcher.columnsModel.getValue());
		WIDTH = CELL_WIDTH * MATRIX.getColumns();
		HEIGHT = CELL_HEIGHT * MATRIX.getRows();
		agents = new ArrayList<Agent>((int) launcher.agentsModel.getValue());
		replicationTime = (int) (hungerTime * 1.5f);
		double beta = (double) launcher.betaModel.getValue();

		foodRestorationSpeed = (int) launcher.foodModel.getValue();
		hungerTime = (int) launcher.hungerModel.getValue();

		hungerModel.setValue(launcher.hungerModel.getValue());
		foodModel.setValue(launcher.foodModel.getValue());

		try
		{
			Display.setDisplayMode(new DisplayMode((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2)));
			Display.setTitle("Evolution simulation window");
			Display.setResizable(true);
			Display.setVSyncEnabled(true);
			Display.setFullscreen(false);
			Display.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}

		System.out.println("Your OpenGL version is:" + glGetString(GL_VERSION));
		if (!GLContext.getCapabilities().OpenGL11)
		{
			System.err.println("Your OpenGL version doesn't support the required functionality.");
			shutdown();
		}
		// View initialization
		Camera.initializeView(Display.getWidth(), Display.getHeight());
		// Preparing to build agents
		File file = new File("AgentsData");
		file.mkdir();
		File[] f = file.listFiles();
		if (f.length > 0)
		{
			for (int i = 0; i < f.length; i++)
			{
				agents.add(new Agent(f[i], outputNeurons));
			}
		}
		else
		{
			// Building agents with random DNA
			for (int i = 0; i < (int) launcher.agentsModel.getValue(); i++)
			{
				agents.add(new Agent(GeneticCode.generateRandomCode(GeneticCode.calculateLengthOfRecursiveNetworkDNA(10, 21)), 0, beta, outputNeurons));
			}
		}
		boolean I=true;
		boolean O=false;
				
//		O,c;
//		  // first layer
//		O,(c.x - 1, c.y);
//		O,(c.x - 2, c.y);
//		O,(c.x + 1, c.y);
//		O,(c.x + 2, c.y);
//		  //
//		O,(c.x - 1, c.y - 1);
//		O,(c.x - 2, c.y - 1);
//		O,(c.x + 1, c.y - 1);
//		O,(c.x + 2, c.y - 1);
//		O,(c.x, c.y - 1);
//		  // second top layer
//		O,(c.x - 1, c.y + 1);
//		O,(c.x - 2, c.y + 1);
//		O,(c.x + 1, c.y + 1);
//		O,(c.x + 2, c.y + 1);
//		O,(c.x, c.y + 1);
		// third top layer
//		  
//		O,(c.x - 1, c.y + 2);
//		O,(c.x, c.y + 2);
//		O,(c.x + 1, c.y + 2);
//		  //third bottom
//		O,(c.x - 1, c.y - 2);
//		O,(c.x, c.y - 2);
//		O,(c.x + 1, c.y - 2);
//		
		//UP
//		O,c;                 
//		  // first layer     
//		O,(c.x - 1, c.y);    
//		O,(c.x - 2, c.y);    
//		O,(c.x + 1, c.y);    
//		O,(c.x + 2, c.y);    
//		  //                 
//		O,(c.x - 1, c.y - 1);
//		O,(c.x - 2, c.y - 1);
//		O,(c.x + 1, c.y - 1);
//		O,(c.x + 2, c.y - 1);
//		O,(c.x, c.y - 1);    
//		  // second top layer
//		I,(c.x - 1, c.y + 1);
//		O,(c.x - 2, c.y + 1);
//		I,(c.x + 1, c.y + 1);
//		O,(c.x + 2, c.y + 1);
//		I,(c.x, c.y + 1);    
//		    // third top layer   
//		I,(c.x - 1, c.y + 2);
//		I,(c.x, c.y + 2);    
//		I,(c.x + 1, c.y + 2);
//		  //third bottom 
//		O,(c.x - 1, c.y - 2);
//		O,(c.x, c.y - 2);    
//		O,(c.x + 1, c.y - 2);
//		
//		
		//DOWN
//		O,c;                 
//		  // first layer     
//		O,(c.x - 1, c.y);    
//		O,(c.x - 2, c.y);    
//		O,(c.x + 1, c.y);    
//		O,(c.x + 2, c.y);    
//		  //                 
//		I,(c.x - 1, c.y - 1);
//		O,(c.x - 2, c.y - 1);
//		I,(c.x + 1, c.y - 1);
//		O,(c.x + 2, c.y - 1);
//		I,(c.x, c.y - 1);    
//		  // second top layer
//		O,(c.x - 1, c.y + 1);
//		O,(c.x - 2, c.y + 1);
//		O,(c.x + 1, c.y + 1);
//		O,(c.x + 2, c.y + 1);
//		O,(c.x, c.y + 1);    
//		  //third bottom     
//		O,(c.x - 1, c.y + 2);
//		O,(c.x, c.y + 2);    
//		O,(c.x + 1, c.y + 2);
//		  // third top layer 
//		I,(c.x - 1, c.y - 2);
//		I,(c.x, c.y - 2);    
//		I,(c.x + 1, c.y - 2);
//		
//		
//		
		//LEFT
//		
//		O,c;                 
//		  // first layer     
//		I,(c.x - 1, c.y);    
//		I,(c.x - 2, c.y);    
//		O,(c.x + 1, c.y);    
//		O,(c.x + 2, c.y);    
//		  //                 
//		I,(c.x - 1, c.y - 1);
//		I,(c.x - 2, c.y - 1);
//		O,(c.x + 1, c.y - 1);
//		O,(c.x + 2, c.y - 1);
//		O,(c.x, c.y - 1);    
//		  // second top layer
//		I,(c.x - 1, c.y + 1);
//		I,(c.x - 2, c.y + 1);
//		O,(c.x + 1, c.y + 1);
//		O,(c.x + 2, c.y + 1);
//		O,(c.x, c.y + 1);    
//		  //third bottom     
//		O,(c.x - 1, c.y + 2);
//		O,(c.x, c.y + 2);    
//		O,(c.x + 1, c.y + 2);
//		  // third top layer 
//		O,(c.x - 1, c.y - 2);
//		O,(c.x, c.y - 2);    
//		O,(c.x + 1, c.y - 2);
		
		//RIGHT
//		O,c;
//		  // first layer
//		O,(c.x - 1, c.y);
//		O,(c.x - 2, c.y);
//		I,(c.x + 1, c.y);
//		I,(c.x + 2, c.y);
//		  //
//		O,(c.x - 1, c.y - 1);
//		O,(c.x - 2, c.y - 1);
//		I,(c.x + 1, c.y - 1);
//		I,(c.x + 2, c.y - 1);
//		O,(c.x, c.y - 1);
//		  // second top layer
//		O,(c.x - 1, c.y + 1);
//		O,(c.x - 2, c.y + 1);
//		I,(c.x + 1, c.y + 1);
//		I,(c.x + 2, c.y + 1);
//		O,(c.x, c.y + 1);
//		  //third bottom
//		O,(c.x - 1, c.y + 2);
//		O,(c.x, c.y + 2);
//		O,(c.x + 1, c.y + 2);
//		  // third top layer
//		O,(c.x - 1, c.y - 2);
//		O,(c.x, c.y - 2);
//		O,(c.x + 1, c.y - 2);
		
//		agents.add(new Agent(new BinaryDNA(
//				//
//				O,
//
//				O, O, O, O,
//
//				O, O, O, O, O,
//
//				I, O, I, O, I,
//
//				I, I, I,
//
//				O, O, O,
//				//
//				O, O, O, O,
//				//
//				O,
//
//				O, O, O, O,
//
//				I, O, I, O, I,
//
//				O, O, O, O, O,
//
//				O, O, O,
//
//				I, I, I,
//
//				//
//				O, O, O, O,
//				//
//				//
//
//				O,
//
//				I, I, O, O,
//
//				I, I, O, O, O,
//
//				I, I, O, O, O,
//
//				O, O, O,
//
//				O, O, O,
//				//
//				O, O, O, O,
//				//
//				//
//				O,
//
//				O, O, I, I,
//
//				O, O, I, I, O,
//
//				O, O, I, I, O,
//
//				O, O, O,
//
//				O, O, O,
//
//				//
//				O, O, O, O
//				), 0, beta, outputNeurons));
		System.out.println("Spawned " + agents.size() + "agents. Start generation = 0 ");
		fillMatrixRandomly();

		BOOLEAN_MATRIX = new DisplayMatrix(MATRIX.getRows(), MATRIX.getColumns(), CELL_WIDTH, CELL_HEIGHT, 0, 0);

		while (isRunning && !Display.isCloseRequested())
		{

			if (Display.wasResized())
			{
				Camera.viewResize(Display.getWidth(), Display.getHeight());
			}
			checkKeyboard();
			checkControls();
			// erasing last view
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			GL11.glTranslatef(viewLocation.getX(), viewLocation.getY(), 0);
			// updating game and creating new view
			if (doJustOneNextFrame)
			{
				doJustOneNextFrame = false;
				update();
				if (isSimulationPaused) viewer.neuronsCirclePanel.repaint();
			}
			else if (isSimulationPaused)
			{
				render();
			}
			else
			{
				update();
			}

			GL11.glTranslatef(-viewLocation.getX(), -viewLocation.getY(), 0);
			try
			{
				sleepTime += Mouse.getDWheel();
				if (sleepTime > 0)
				{
					Thread.sleep(sleepTime);
				}
				else
				{
					sleepTime = 0;
				}
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			// repaint
			Display.update();
			Display.sync(60);
		}
		saveAgents();
		Display.destroy();
		System.exit(0);
	}

	private void checkKeyboard()
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_R))
		{
			viewLocation.setLocation(0, 0);
		}
		if (trackedAgent == null)
		{
			float speed = 8;
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			{
				viewLocation.addY(speed);
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			{
				viewLocation.addY(-speed);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			{
				viewLocation.addX(speed);
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			{
				viewLocation.addX(-speed);
			}
		}
		else
		{
			viewLocation.setLocation(-trackedAgent.getX() + Display.getWidth() / 2, -trackedAgent.getY() + Display.getHeight() / 2);
		}
	}

	private void checkControls()
	{
		Controls.updateMouse();
		if (Controls.isMouse1clicked)
		{
			trackedAgent = null;
			for (int j = 0; j < agents.size(); j++)
			{
				if (agents.get(j).isPointInside(Controls.mouseX - viewLocation.getX(), Controls.mouseY - viewLocation.getY()))
				{
					trackedAgent = agents.get(j);
					viewer.viewNeuralNet(agents.get(j).getNeuroglia(), agents.get(j).getGeneration() + " :gnrtn|beta:" + agents.get(j).getBeta());
					break;
				}
			}

		}
		if (Controls.isMouse0clicked)
		{
			MATRIX.putBlock(foodCellSign, BLOCK_SELECTOR.getCell(Controls.mouseX - viewLocation.getX(), Controls.mouseY - viewLocation.getY()));
		}

	}

	private void saveAgents()
	{
		for (int i = 0; i < agents.size(); i++)
		{
			try
			{
				agents.get(i).writeToFile(new File("AgentsData/" + i));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void fillMatrixRandomly()
	{
		for (int i = 0; i < MATRIX.size(); i++)
		{
			MATRIX.putBlock(i, Math.random() < 0.04 ? foodCellSign : emptyCellSign);
		}
	}

	public int	dataPushFrequency, foodAdding;

	private int countFood()
	{
		int r = 0;
		for (int i : MATRIX.getBlocksArray())
		{
			if (i == foodCellSign) r++;
		}
		return r;
	}

	private void render()
	{
		if (visualizationEnabled)
		{
			BOOLEAN_MATRIX.renderDisplay_BooleanColor(MATRIX, Color.cyan);

			for (int i = 0; i < agents.size(); i++)
			{
				agents.get(i).render();
			}
		}
	}

	private void update()
	{
		dataPushFrequency++;
		if (dataPushFrequency > 100)
		{
			dataPushFrequency = 0;
			viewer.pushStatisticsData(agents.size(), countFood());
		}
		foodAdding++;
		if (foodAdding > foodRestorationSpeed)
		{
			MATRIX.putBlockHere(MATRIX.getRandomColumn(), MATRIX.getRandomRow(), foodCellSign);
			foodAdding = 0;
		}
		if (visualizationEnabled)
		{
			BOOLEAN_MATRIX.renderDisplay_BooleanColor(MATRIX, Color.cyan);

			for (int i = 0; i < agents.size(); i++)
			{
				Agent a = agents.get(i);
				a.update(BLOCK_SELECTOR, MATRIX, WIDTH, HEIGHT);
				updateAngent(a, i);
			}
		}
		else
		{
			for (int i = 0; i < agents.size(); i++)
			{
				Agent a = agents.get(i);
				a.updateNoRender(BLOCK_SELECTOR, MATRIX, WIDTH, HEIGHT);
				updateAngent(a, i);
			}

		}
	}

	private void updateAngent(Agent agent, int i)
	{
		if (agent.getHungerTime() > hungerTime)
		{
			agents.remove(i);
		}
		else if (agent.getCollectedFood() >= childCost)
		{
			agent.takeCollectedFood(childCost);
			try
			{
				Agent newAgent = new Agent(GeneticCode.recombinate(agent.getGenetics(), 0.01, 0.04), agents.get(i).getGeneration() + 1, agents.get(i).getBeta() + (Math.random() > 0.98 ? Math.random() * (Math.random() > 5 ? 1 : -1) : 0),
						outputNeurons);
				newAgent.setLocation(agents.get(i));
				agents.add(newAgent);
				agent.addSize(3, 3);
			}
			catch (LethalMutationException e)
			{
				e.printStackTrace();
			}
		}
	}

	/** Stops the game */
	public static void shutdown()
	{
		isRunning = false;
	}

	public static boolean isVisualizationEnabled()
	{
		return visualizationEnabled;
	}

	public static void setVisualizationEnabled(boolean visualizationEnabled)
	{
		FrameNeuralPixelTest.visualizationEnabled = visualizationEnabled;
	}

}
