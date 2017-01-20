package net.editor.ents;

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
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import net.swing.src.data.Files;

public final class EntsPane extends JDialog
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	private String				result				= null;
	/** If true then room will be added, otherwise room will be deleted */
	private boolean				addEnt;
	
	public EntsPane(Frame owner, String selectionText)
	{
		this(owner, 250, 300, selectionText);
	}
	
	public EntsPane(Frame owner, int width, int height, final String selectionText)
	{
		super(owner, "Entities chooser", true);
		// components in panel
		JPanel panel = new JPanel(); // panel
		final JTextField setField = new JTextField();// text field
		final JButton button = new JButton("Error");// buttons
		button.setEnabled(false);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				result = setField.getText();
				addEnt = button.getText().equals("Create");
				dispose();
			}
		});
		String[] columnName = { "Existing ents:" };
		final ArrayList<String> scannedSets = scanForEnts_ArrayList();
		TableModel model = new DefaultTableModel(scanForEnts_ArraysArray(), columnName) {
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
				/* column is only 1 , index = 0 */
				if (setsTable.getSelectedRow() > -1)
				{
					setField.setText(setsTable.getValueAt(setsTable.getSelectedRow(), 0).toString());
				}
				button.setText(selectionText);
				button.setEnabled(true);
			}
		});
		
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
				
				if (Files.validateName(setField.getText()))
				{
					button.setEnabled(true);
					if (scannedSets.contains(setField.getText()))
					{
						button.setText(selectionText);
					}
					else
					{
						button.setText("Create");
					}
				}
				else
				{
					button.setText("Error");
					button.setEnabled(false);
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e)
			{
			}
		});
		
		addStuffRest(panel, button, setField, setsTable, owner, width, height, scannedSets, sorter);
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
		
		addStuffRest(panel, button, setField, setsTable, owner, width, height, scannedSets, sorter);
	}
	
	private void addStuffRest(JPanel panel, final JButton button, final JTextField setField, final JTable setsTable, Frame owner, int width, int height, final ArrayList<String> scannedSets, final DefaultRowSorter<TableModel, Integer> sorter)
	{
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
	 *            set from this list is selected then addRoom will be set to
	 *            false
	 */
	public EntsPane(Frame owner, int width, int height, String[] blacklist)
	{
		this(owner, width, height, Arrays.asList(blacklist));
	}
	
	/**
	 * @param setBlacklist
	 *            - sets that cannot be added. Only removing is possible. If any
	 *            set from this list is selected then addRoom will be set to
	 *            false
	 */
	public EntsPane(Frame owner, String[] blacklist)
	{
		this(owner, 250, 300, Arrays.asList(blacklist));
	}
	
	/**
	 * @param setBlacklist
	 *            - sets that cannot be added. Only removing is possible. If any
	 *            set from this list is selected then addRoom will be set to
	 *            false
	 */
	public EntsPane(Frame owner, DefaultListModel<String> blacklist)
	{
		this(owner, 250, 300, blacklist);
	}
	
	/**
	 * @param setBlacklist
	 *            - sets that cannot be added. Only removing is possible. If any
	 *            set from this list is selected then addRoom will be set to
	 *            false
	 */
	public EntsPane(Frame owner, ArrayList<String> blacklist)
	{
		this(owner, 250, 300, blacklist);
	}
	
	/**
	 * @param setBlacklist
	 *            - sets that cannot be added. Only removing is possible. If any
	 *            set from this list is selected then addRoom will be set to
	 *            false
	 */
	public EntsPane(Frame owner, int width, int height, final DefaultListModel<String> blacklist)
	{
		super(owner, "Entities chooser", true);
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
				addEnt = button.getText().equals("Add");
				dispose();
			}
		});
		String[] columnName = { "Existing ents:" };
		final ArrayList<String> scannedSets = scanForEnts_ArrayList();
		TableModel model = new DefaultTableModel(scanForEnts_ArraysArray(), columnName) {
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
	public EntsPane(Frame owner, int width, int height, final List<String> blacklist)
	{
		super(owner, "Entities chooser", true);
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
				addEnt = button.getText().equals("Add");
				dispose();
			}
		});
		String[] columnName = { "Existing ents:" };
		final ArrayList<String> scannedSets = scanForEnts_ArrayList();
		TableModel model = new DefaultTableModel(scanForEnts_ArraysArray(), columnName) {
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
	
	public static String[][] scanForEnts_ArraysArray()
	{
		String[] s = scanForEnts();
		
		String[][] arrays = new String[s.length][];
		for (int i = 0; i < arrays.length; i++)
		{
			String[] array = { s[i] };
			arrays[i] = array;
		}
		return arrays;
	}
	
	public static ArrayList<String> scanForEnts_ArrayList()
	{
		return new ArrayList<String>(Arrays.asList(scanForEnts()));
	}
	
	public static String[] scanForEnts()
	{
		return Files.ENTITIES_FOLDER.f.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name)
			{
				return new File(dir.getPath() + "/" + name + "/Main.groovy").exists();
			}
		});
	}
	
	/** returns set selected by player */
	public String getResult()
	{
		return result;
	}
	
	public boolean isAddEnt()
	{
		return addEnt;
	}
	
}
