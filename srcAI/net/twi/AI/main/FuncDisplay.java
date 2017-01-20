package net.twi.AI.main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.math.src.func.EnumFunctions;
import net.math.src.func.Function;

public class FuncDisplay extends JPanel implements ActionListener, MouseListener, MouseWheelListener
{
	
	/**
	 * .
	 */
	private static final long	serialVersionUID	= 1L;
	private JComboBox<String>	funcs				= new JComboBox<String>();
	private JRadioButton		x, y, z, bias;
	private double				a, b, c;
	private Function			customFunction;
	
	private int					yShift				= -100;
	private double				addedToX			= 0;
	
	public FuncDisplay()
	{
		setLayout(new FlowLayout());
		for (String f : EnumFunctions.allnames)
		{
			funcs.addItem(f);
		}
		funcs.addItem("Custom");
		x = new JRadioButton("X");
		y = new JRadioButton("Y");
		z = new JRadioButton("Z");
		bias = new JRadioButton("Bias");
		
		x.addActionListener(this);
		y.addActionListener(this);
		z.addActionListener(this);
		bias.addActionListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
		add(funcs);
		add(x);
		add(y);
		add(z);
		
		repaint();
	}
	
	@Override
	public void paint(Graphics arg0)
	{
		int heightOfOne = 200;
		int yOfBottom = 10;
		int yOf0 = yOfBottom + heightOfOne;
		int xOf0 = 100;
		Graphics2D g2d = (Graphics2D) arg0;
		g2d.clearRect(0, 0, getWidth(), getHeight());
		funcs.repaint();
		x.repaint();
		y.repaint();
		z.repaint();
		g2d.drawString("x=" + a + " |y=" + b + " |z=" + c + "|bias=" + addedToX, 10, 50);
		g2d.setColor(Color.RED);
		
		g2d.drawLine(0, getFlippedY(yOfBottom), getWidth(), getFlippedY(yOfBottom));
		g2d.drawLine(0, getFlippedY(yOf0), getWidth(), getFlippedY(yOf0));
		g2d.drawLine(0, getFlippedY(yOf0 + heightOfOne), getWidth(), getFlippedY(yOf0 + heightOfOne));
		g2d.drawLine(xOf0, 0, xOf0, getHeight());
		g2d.setColor(Color.BLACK);
		if (funcs.getSelectedIndex() < funcs.getItemCount() - 1)
		{
			int lastPixelHeight = doFunction(heightOfOne, 0), thisPixelHeight;
			for (int i = 0; i < getWidth(); i++)
			{
				thisPixelHeight = doFunction(heightOfOne, i - xOf0);
				g2d.drawLine(i, getFlippedY(yOf0 + thisPixelHeight), i, getFlippedY(yOf0 + lastPixelHeight));
				lastPixelHeight = thisPixelHeight;
			}
		}
		else if (customFunction != null)
		{
			int lastPixelHeight = doCustomFunction(heightOfOne, 0), thisPixelHeight;
			for (int i = 0; i < getWidth(); i++)
			{
				thisPixelHeight = doCustomFunction(heightOfOne, i - xOf0);
				g2d.drawLine(i, getFlippedY(yOf0 + thisPixelHeight), i, getFlippedY(yOf0 + lastPixelHeight));
				lastPixelHeight = thisPixelHeight;
			}
		}
	}
	
	private int doCustomFunction(int heightOfOne, double funcArgument)
	{
		return (int) Math.ceil(customFunction.doFunction(funcArgument + addedToX) * heightOfOne);
	}
	
	private int doFunction(int heightOfOne, double funcArgument)
	{
		return (int) Math.ceil(EnumFunctions.all[funcs.getSelectedIndex()].doFunction(funcArgument + addedToX) * heightOfOne);
	}
	
	private int getFlippedY(int y)
	{
		return yShift + getHeight() - y;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		x.setSelected(arg0.getSource() == x);
		y.setSelected(arg0.getSource() == y);
		z.setSelected(arg0.getSource() == z);
		bias.setSelected(arg0.getSource() == bias);
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e)
	{
		
	}
	
	@Override
	public void mouseExited(MouseEvent e)
	{
		
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (funcs.getSelectedIndex() == funcs.getItemCount() - 1) return;
		if (x.isSelected())
		{
			a = e.getX();
		}
		else if (y.isSelected())
		{
			b = e.getX();
		}
		else if (z.isSelected())
		{
			c = e.getX();
		}
		else if (bias.isSelected())
		{
			addedToX = e.getX();
		}
		EnumFunctions.all[funcs.getSelectedIndex()].setParameters(a, b, c);
		repaint();
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		if (funcs.getSelectedIndex() == funcs.getItemCount() - 1) return;
		if (x.isSelected())
		{
			a += e.getPreciseWheelRotation() / (e.isShiftDown() ? 100 : 2);
		}
		else if (y.isSelected())
		{
			b += e.getPreciseWheelRotation() / (e.isShiftDown() ? 100 : 2);
		}
		else if (z.isSelected())
		{
			c += e.getPreciseWheelRotation() / (e.isShiftDown() ? 100 : 2);
		}
		else if (bias.isSelected())
		{
			addedToX += e.getPreciseWheelRotation() / (e.isShiftDown() ? 100 : 2);
		}
		EnumFunctions.all[funcs.getSelectedIndex()].setParameters(a, b, c);
		repaint();
	}
	
	public void requestFunction(int index, double x2, double y2, double z2)
	{
		funcs.setSelectedIndex(index);
		a = x2;
		b = y2;
		c = z2;
	}
	
	public void requestCustomFunction(Function customFunc, double addedToX)
	{
		customFunction = customFunc;
		this.addedToX = addedToX;
	}
}
