package net.editor.blocksets;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.DefaultRowSorter;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public final class BlockSetsPane extends JDialog
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public static final String	path				= "res/objects/blocks/";
	
	private String				result				= null;
	/** If true then room will be added, otherwise room will be deleted */
	private boolean				addSet;
	
	public BlockSetsPane(Frame owner)
	{
		this(owner, 250, 300);
	}
	
	public BlockSetsPane(Frame owner, int width, int height)
	{
		super(owner, "Block set chooser", true);
		// components in panel
		JPanel panel = new JPanel(); // panel
		final JTextField setField = new JTextField();// text field
		final JButton button = new JButton("Select");// buttons
		button.setEnabled(false);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				result = setField.getText();
				dispose();
			}
		});
		String[] columnName = { "Detected sets:" };
		final ArrayList<String> scannedSets = scanForSets_ArrayList();
		TableModel model = new DefaultTableModel(scanForSets_ArraysArray(), columnName) {
			/**
			 * 
			 */
			private static final long	serialVersionUID	= 1L;
			
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		final JTable setsTable = new JTable(model);// table
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		setsTable.setRowSorter(sorter);
		setsTable.setCellSelectionEnabled(false);
		setsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				if (0 > setsTable.getSelectedRow())
				{
					button.setEnabled(scannedSets.contains(setField.getText()));
					return;
				}
				button.setEnabled(true);
				/* column is only 1 , index = 0 */
				setField.setText(setsTable.getValueAt(setsTable.getSelectedRow(), 0).toString());
			}
		});
		
		addStuff(panel, button, setField, setsTable, owner, width, height, scannedSets, sorter);
	}
	
	private void addStuff(JPanel panel, final JButton button, final JTextField setField, final JTable setsTable, Frame owner, int width, int height, final ArrayList<String> scannedSets, final DefaultRowSorter<TableModel, Integer> sorter)
	{
		
		setsTable.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e)
			{
				setsTable.clearSelection();
			}
			
			@Override
			public void focusGained(FocusEvent e)
			{
			}
		});
		setField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e)
			{
			}
			
			@Override
			public void keyReleased(KeyEvent e)
			{
				String text = setField.getText();
				RowFilter<TableModel, Integer> filter = RowFilter.regexFilter(text, 0);
				sorter.setRowFilter(filter);
				setsTable.clearSelection();
				button.setEnabled(scannedSets.contains(text));
			}
			
			@Override
			public void keyPressed(KeyEvent e)
			{
			}
		});
		
		JScrollPane scrollForList = new JScrollPane(setsTable);// scroll
		// layout for panel
		GridBagConstraints c = new GridBagConstraints();
		GridBagLayout l = new GridBagLayout();
		panel.setLayout(l);
		// adding components to panel
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 5;
		c.ipady = 5;
		c.insets = new Insets(3, 3, 3, 3);
		
		c.weightx = 0.1;
		c.gridx = 1;
		c.gridy = 0;
		panel.add(button, c);
		c.weightx = 10;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(setField, c);
		
		c.weighty = 1;
		c.weightx = 0.1;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		panel.add(scrollForList, c);
		// adding panel
		add(panel, BorderLayout.CENTER);
		
		// Don't touch anything below
		// order of those methods must stay like this
		setLocation(owner.getX() + owner.getWidth() / 2 - width / 2, owner.getY() + owner.getHeight() / 2 - height / 2);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(width, height);// otherwise strange things happen
		setVisible(true);
	}
	
	/**
	 * @param setBlacklist
	 *            - sets that cannot be added. Only removing is possible. If any
	 *            set from this list is selected then addSet will be set to
	 *            false
	 */
	public BlockSetsPane(Frame owner, int width, int height, String[] blacklist)
	{
		this(owner, width, height, Arrays.asList(blacklist));
	}
	
	/**
	 * @param setBlacklist
	 *            - sets that cannot be added. Only removing is possible. If any
	 *            set from this list is selected then addSet will be set to
	 *            false
	 */
	public BlockSetsPane(Frame owner, String[] blacklist)
	{
		this(owner, 250, 300, Arrays.asList(blacklist));
	}
	
	/**
	 * @param setBlacklist
	 *            - sets that cannot be added. Only removing is possible. If any
	 *            set from this list is selected then addSet will be set to
	 *            false
	 */
	public BlockSetsPane(Frame owner, DefaultListModel<String> blacklist)
	{
		this(owner, 250, 300, blacklist);
	}
	
	/**
	 * @param setBlacklist
	 *            - sets that cannot be added. Only removing is possible. If any
	 *            set from this list is selected then addSet will be set to
	 *            false
	 */
	public BlockSetsPane(Frame owner, int width, int height, final DefaultListModel<String> blacklist)
	{
		super(owner, "Block set chooser", true);
		// components in panel
		JPanel panel = new JPanel(); // panel
		final JTextField setField = new JTextField();// text field
		final JButton button = new JButton("Add");// buttons
		button.setEnabled(false);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				result = setField.getText();
				addSet = button.getText().equals("Add");
				dispose();
			}
		});
		String[] columnName = { "Detected sets:" };
		final ArrayList<String> scannedSets = scanForSets_ArrayList();
		TableModel model = new DefaultTableModel(scanForSets_ArraysArray(), columnName) {
			/**
			 * 
			 */
			private static final long	serialVersionUID	= 1L;
			
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		final JTable setsTable = new JTable(model);// table
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		setsTable.setRowSorter(sorter);
		setsTable.setCellSelectionEnabled(false);
		setsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				if (0 > setsTable.getSelectedRow())
				{
					if (scannedSets.contains(setField.getText()))
					{
						if (blacklist.contains(setField.getText()))
						{
							button.setText("Remove");
						}
						else
						{
							button.setText("Add");
						}
						button.setEnabled(true);
					}
					else
					{
						button.setEnabled(false);
					}
					return;
				}
				button.setEnabled(true);
				/* column is only 1 , index = 0 */
				setField.setText(setsTable.getValueAt(setsTable.getSelectedRow(), 0).toString());
				if (blacklist.contains(setField.getText()))
				{
					button.setText("Remove");
				}
				else
				{
					button.setText("Add");
				}
			}
		});
		
		addStuff(panel, button, setField, setsTable, owner, width, height, scannedSets, sorter);
		
	}
	
	/**
	 * @param setBlacklist
	 *            - sets that cannot be added. Only removing is possible. If any
	 *            set from this list is selected then addRoom will be set to
	 *            false
	 */
	public BlockSetsPane(Frame owner, int width, int height, final List<String> blacklist)
	{
		super(owner, "Block set chooser", true);
		// components in panel
		JPanel panel = new JPanel(); // panel
		final JTextField setField = new JTextField();// text field
		final JButton button = new JButton("Add");// buttons
		button.setEnabled(false);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				result = setField.getText();
				addSet = button.getText().equals("Add");
				dispose();
			}
		});
		String[] columnName = { "Detected sets:" };
		final ArrayList<String> scannedSets = scanForSets_ArrayList();
		TableModel model = new DefaultTableModel(scanForSets_ArraysArray(), columnName) {
			/**
			 * 
			 */
			private static final long	serialVersionUID	= 1L;
			
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		final JTable setsTable = new JTable(model);// table
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		setsTable.setRowSorter(sorter);
		setsTable.setCellSelectionEnabled(false);
		setsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				if (0 > setsTable.getSelectedRow())
				{
					if (scannedSets.contains(setField.getText()))
					{
						if (blacklist.contains(setField.getText()))
						{
							button.setText("Remove");
						}
						else
						{
							button.setText("Add");
						}
						button.setEnabled(true);
					}
					else
					{
						button.setEnabled(false);
					}
					return;
				}
				button.setEnabled(true);
				/* column is only 1 , index = 0 */
				setField.setText(setsTable.getValueAt(setsTable.getSelectedRow(), 0).toString());
				if (blacklist.contains(setField.getText()))
				{
					button.setText("Remove");
				}
				else
				{
					button.setText("Add");
				}
			}
		});
		
		addStuff(panel, button, setField, setsTable, owner, width, height, scannedSets, sorter);
		
	}
	
	public static String[][] scanForSets_ArraysArray()
	{
		ArrayList<String> sets = new ArrayList<String>();
		for (File f : new File(path).listFiles())
		{
			if (f.getName().endsWith(".png"))
			{
				try
				{
					ImageIO.read(f);
				}
				catch (IOException e)
				{
					continue;
				}
				String name = f.getName().split("\\.")[0];
				if (new File(path + name + ".txt").exists())
				{
					sets.add(name);
				}
			}
		}
		String[][] arrays = new String[sets.size()][];
		for (int i = 0; i < arrays.length; i++)
		{
			String[] array = { sets.get(i) };
			arrays[i] = array;
		}
		return arrays;
	}
	
	public static ArrayList<String> scanForSets_ArrayList()
	{
		ArrayList<String> sets = new ArrayList<String>();
		for (File f : new File(path).listFiles())
		{
			if (f.getName().endsWith(".png"))
			{
				try
				{
					ImageIO.read(f);
				}
				catch (IOException e)
				{
					continue;
				}
				String name = f.getName().split("\\.")[0];
				if (new File(path + name + ".txt").exists())
				{
					sets.add(name);
				}
			}
		}
		return sets;
	}
	
	public static String[] scanForSets()
	{
		return scanForSets_ArrayList().toArray(new String[0]);
	}
	
	/** returns set selected by player */
	public String getResult()
	{
		return result;
	}
	
	public boolean isAddSet()
	{
		return addSet;
	}
	
}
