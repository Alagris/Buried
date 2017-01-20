package net.twi.AI.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

import net.math.src.neurons.InputNeuron;
import net.math.src.neurons.NeurogliaRecursive;
import net.math.src.neurons.Neuron;
import net.math.src.neurons.NeuronInterface;

public class NeuronsCirclePanel extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener, KeyListener
{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID		= 1L;
	
	private NeurogliaRecursive	testedBrain				= null;
	private int					lastMouseLocationX, lastMouseLocationY;
	
	private Point				viewLocation			= new Point();
	private int					circleRadiusSize		= 70;
	private int					selectedNeuronIndex		= 0;
	double						angleBetweenNeurons;
	/** extra comments */
	String						s						= "";
	private boolean				isFocusedOnOneNeuron	= false;
	private boolean				isInputShown			= false;
	private boolean				isOutputShown			= false;
	private boolean				isBiasShown				= false;
	
	public NeuronsCirclePanel()
	{
		
		addMouseWheelListener(this);
		
		addMouseListener(this);
		
		addMouseMotionListener(this);
		
		addKeyListener(this);
		
		setFocusable(true);
		
	}
	
	public void setBrain(NeurogliaRecursive brain)
	{
		testedBrain = brain;
		angleBetweenNeurons = 2 * Math.PI / testedBrain.getSize();
		selectedNeuronIndex = 0;
	}
	
	public void setText(String extraComment)
	{
		s = extraComment;
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyChar())
		{
			case 'b':
				isBiasShown = !isBiasShown;
				repaint();
				break;
			case 'o':
				isOutputShown = !isOutputShown;
				repaint();
				break;
			case 'i':
				isInputShown = !isInputShown;
				repaint();
				break;
			case 'p':
				R.isSimulationPaused = !R.isSimulationPaused;
				repaint();
				break;
			case 'n':
				if (R.isSimulationPaused) R.doJustOneNextFrame = true;
				break;
			case 'a':
				moveViewByVector(-1, 0);
				break;
			case 'd':
				moveViewByVector(1, 0);
				break;
			case 's':
				moveViewByVector(0, 1);
				break;
			case 'w':
				moveViewByVector(0, -1);
				break;
			case ' ':
				System.out.println("0");
				if (selectedNeuronIndex < testedBrain.getSize())
				{
					System.out.println("1");
					NeuronInterface selectedNeuron = testedBrain.getNeuron(selectedNeuronIndex);
					if (selectedNeuron instanceof Neuron)
					{
						System.out.println("2");
						R.frameFuncDisplayer.setVisible(true);
						R.frameFuncDisplayer.setDefaultBounds();
						R.frameFuncDisplayer.requestCustomFunction(testedBrain.getNeuron(selectedNeuronIndex).getFunction(), testedBrain.getNeuron(selectedNeuronIndex).getBias());
					}
				}
				break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		circleRadiusSize -= e.getWheelRotation() * 10;
		repaint();
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		int x = viewLocation.x - e.getX();
		int y = viewLocation.y - e.getY();
		double angle = Math.atan2(y, x) + Math.PI;
		selectedNeuronIndex = (int) (angle / angleBetweenNeurons + 0.5);
		
		lastMouseLocationX = e.getX();
		lastMouseLocationY = e.getY();
		repaint();
		
		if (e.getClickCount() != 2 || (testedBrain.getNeuron(selectedNeuronIndex) instanceof InputNeuron))
		{
			isFocusedOnOneNeuron = false;
		}
		else
		{
			isFocusedOnOneNeuron = true;
		}
	}
	
	@Override
	public void mouseExited(MouseEvent e)
	{
	}
	
	@Override
	public void mouseEntered(MouseEvent e)
	{
		requestFocus();
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		moveViewByVector(e.getX() - lastMouseLocationX, e.getY() - lastMouseLocationY);
		lastMouseLocationX = e.getX();
		lastMouseLocationY = e.getY();
	}
	
	private void moveViewByVector(int x, int y)
	{
		viewLocation.setLocation(viewLocation.x + x, viewLocation.y + y);
		repaint();
	}
	
	private void paintText(String text, int x, int y, Graphics g)
	{
		g.setColor(Color.BLACK);
		g.drawString(text, x, y);
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		if (testedBrain == null) return;
		paintText(s + "|paused: " + R.isSimulationPaused + "|show input: " + isInputShown + "|show output: " + isOutputShown + "|show bias: " + isBiasShown, 3, 20, g);
		NeuronInterface selectedNeuron = null;
		if (selectedNeuronIndex < testedBrain.getSize())
		{
			selectedNeuron = testedBrain.getNeuron(selectedNeuronIndex);
			paintText("Selected neuron (" + selectedNeuronIndex + ") output: " + selectedNeuron.getOutput(), 3, 50, g);
			if (!(selectedNeuron instanceof InputNeuron))
			{
				Neuron n = ((Neuron) selectedNeuron);
				paintText("summa:" + n.getS(), 3, 70, g);
			}
		}
		g.setColor(Color.RED);
		if (isFocusedOnOneNeuron && selectedNeuron != null)
		{
			// painting neurons dendrites
			drawDendritesForNeuron(selectedNeuronIndex, g);
			// painting neurons circle
			g.setColor(Color.GREEN);
			paintNeuronAt(g, selectedNeuronIndex, "");
			
			int weightIndex = 0;
			for (int i : selectedNeuron.getInputs())
			{
				g.setColor(testedBrain.getNeuron(i) instanceof InputNeuron ? Color.CYAN : Color.LIGHT_GRAY);
				paintNeuronAt(g, i, "o: " + testedBrain.getNeuron(i).getOutput() + "|w:" + selectedNeuron.getW()[weightIndex]);
				weightIndex++;
			}
		}
		else
		{
			// painting neurons dendrites
			for (int i = 0; i < testedBrain.getSize(); i++)
			{
				if (!(testedBrain.getNeuron(i) instanceof InputNeuron))
				{
					if (i == selectedNeuronIndex)
					{
						g.setColor(Color.GREEN);
						drawDendritesForNeuron(i, g);
						g.setColor(Color.RED);
					}
					else
					{
						drawDendritesForNeuron(i, g);
					}
				}
			}
			// painting neurons circle
			for (int i = 0; i < testedBrain.getSize(); i++)
			{
				String textOnNeuron = " " + i;
				NeuronInterface neuronInterface = testedBrain.getNeuron(i);
				if (neuronInterface instanceof InputNeuron)
				{
					if (isInputShown)
					{
						textOnNeuron += "|i:" + neuronInterface.getOutput();
					}
					g.setColor(i == selectedNeuronIndex ? Color.GREEN : Color.CYAN);
				}
				else
				{
					if (isOutputShown)
					{
						textOnNeuron += "|o:" + neuronInterface.getOutput();
					}
					if (isBiasShown)
					{
						textOnNeuron += "|b:" + neuronInterface.getBias();
					}
					if (i == selectedNeuronIndex)
					{
						g.setColor(Color.GREEN);
					}
					else
					{
						g.setColor(Color.LIGHT_GRAY);
					}
					
				}
				paintNeuronAt(g, i, textOnNeuron);
			}
		}
		
	}
	
	private void drawDendritesForNeuron(int index, Graphics g)
	{
		for (int ii : testedBrain.getNeuron(index).getInputs())
		{
			drawDendrite(index, ii, g);
		}
	}
	
	private int getY(int index)
	{
		return (int) (circleRadiusSize * Math.sin(index * angleBetweenNeurons));
	}
	
	private int getX(int index)
	{
		return (int) (circleRadiusSize * Math.cos(index * angleBetweenNeurons));
	}
	
	private void paintNeuronAt(Graphics g, int index, String textOnNeuron)
	{
		paintNeuronAt(viewLocation.x + getX(index), viewLocation.y + getY(index), g, index, textOnNeuron);
	}
	
	private void paintNeuronAt(int x, int y, Graphics g, int index, String textOnNeuron)
	{
		g.fillRect(x - 7, y - 7, 14, 14);
		paintText(textOnNeuron, x - 7, y + 7, g);
	}
	
	private void drawDendrite(int startNeuronIndex, int aimedNeuronIndex, Graphics g)
	{
		drawDendrite(viewLocation.x + getX(startNeuronIndex), viewLocation.y + getY(startNeuronIndex), aimedNeuronIndex, g);
	}
	
	private void drawDendrite(int xFrom, int yFrom, int aimedNeuronIndex, Graphics g)
	{
		drawDendrite(xFrom, yFrom, viewLocation.x + getX(aimedNeuronIndex), viewLocation.y + getY(aimedNeuronIndex), g);
	}
	
	private void drawDendrite(int xFrom, int yFrom, int xTo, int yTo, Graphics g)
	{
		g.drawLine(xFrom, yFrom, xTo, yTo);
	}
	
}

// class NeuronsPanel extends JPanel
// {
// /**
// *
// */
// private static final long serialVersionUID = 1L;
//
// private NeurogliaLayered testedBrain = null;
// private int lastMouseLocationX, lastMouseLocationY;
//
// private Point viewLocation = new Point();
//
// private static final int halfOfNeuronSize = 6;
// /** Empty space between neurons */
// private int padding = 70;
// private int selectedLayer;
// private int selectedNeuron;
//
// public NeuronsPanel()
// {
// addMouseWheelListener(new MouseWheelListener() {
//
// @Override
// public void mouseWheelMoved(MouseWheelEvent e)
// {
// padding -= e.getWheelRotation() * 10;
// repaint();
// }
// });
//
// addMouseListener(new MouseListener() {
//
// @Override
// public void mouseReleased(MouseEvent e)
// {
//
// }
//
// @Override
// public void mousePressed(MouseEvent e)
// {
// lastMouseLocationX = e.getX();
// lastMouseLocationY = e.getY();
//
// selectedLayer = (int) ((lastMouseLocationX - viewLocation.x + padding/2) / padding);
// selectedNeuron = (int) ((lastMouseLocationY - viewLocation.y + padding/2) / padding);
// repaint();
// }
//
// @Override
// public void mouseExited(MouseEvent e)
// {
// }
//
// @Override
// public void mouseEntered(MouseEvent e)
// {
// }
//
// @Override
// public void mouseClicked(MouseEvent e)
// {
// }
// });
//
// addMouseMotionListener(new MouseMotionListener() {
//
// @Override
// public void mouseMoved(MouseEvent e)
// {
// }
//
// @Override
// public void mouseDragged(MouseEvent e)
// {
//
// viewLocation.setLocation(viewLocation.x + e.getX() - lastMouseLocationX, viewLocation.y + e.getY() - lastMouseLocationY);
// lastMouseLocationX = e.getX();
// lastMouseLocationY = e.getY();
// repaint();
// }
// });
// }
//
//
//
// @Override
// public void paint(Graphics g)
// {
// super.paint(g);
// if (testedBrain == null) return;
// // rendering input layer
// for (int i = 0; i < testedBrain.getLayer(0).getSize(); i++)
// {
// paintNeuron(g, 0, i);
// }
// // rendering hidden layers and output layer
// for (int i = 1; i < testedBrain.size(); i++)
// {
// paintLayer(g, i);
// }
// paintSelectedNeuron(g);
// }
//
// private void paintSelectedNeuron(Graphics g)
// {
// g.setColor(Color.green);
// g.fillOval(viewLocation.x + selectedLayer * padding - halfOfNeuronSize, viewLocation.y + selectedNeuron * padding - halfOfNeuronSize, 2 * halfOfNeuronSize, 2 * halfOfNeuronSize);
// }
//
// private void paintLayer(Graphics g, int index)
// {
// for (int i = 0; i < testedBrain.getLayer(index).getSize(); i++)
// {
// paintDendrites(g, index, i, testedBrain.getLayer(index).getCell(i).getInputs());
// paintNeuron(g, index, i);
// }
// }
//
// private void paintDendritesNoPadding(Graphics g, int neuronX, int neuronY, int[] inputs)
// {
// for (Integer dendrite : inputs)
// {
// paintDendrite(g, neuronX, neuronY, dendrite);
// }
// }
//
// private void paintDendrite(Graphics g, int neuronX, int neuronY, Integer dendrite)
// {
// paintDendriteToPreviousNeuron(g, neuronX, neuronY, neuronX - padding, dendrite * padding);
// }
//
// private void paintDendriteToPreviousNeuron(Graphics g, int neuronX, int neuronY, int previousX, int previousY)
// {
// g.drawLine(neuronX + viewLocation.x, neuronY + viewLocation.y, previousX + viewLocation.x, previousY + viewLocation.y);
// }
//
// private void paintDendrites(Graphics g, int neuronX, int neuronY, int[] inputs)
// {
// paintDendritesNoPadding(g, neuronX * padding, neuronY * padding, inputs);
// }
//
// private void paintNeuron(Graphics g, int x, int y)
// {
// paintNeuronNoPadding(g, x * padding, y * padding);
// }
//
// private void paintNeuronNoPadding(Graphics g, int x, int y)
// {
// g.fillRect(x - halfOfNeuronSize + viewLocation.x, y - halfOfNeuronSize + viewLocation.y, halfOfNeuronSize * 2, halfOfNeuronSize * 2);
// }
// }
