package net.editor.room;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import net.editor.main.DerpyIsBestPony;

public class RoomEditorPanel_Blocks extends JPanel implements ListSelectionListener
{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	private DefaultTableModel	model;
	private JTable				blocks;
	private JScrollPane			scroll;
	private final String[]		columnIdentifiers	= { "id", "name" };
	
	private final Object[]		airRow				= { 0, "air" };
	
	public RoomEditorPanel_Blocks()
	{
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columnIdentifiers);
		
		blocks = new JTable(model);
		scroll = new JScrollPane(blocks, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
		blocks.setRowSorter(sorter);
		blocks.setColumnSelectionAllowed(false);
		blocks.setRowSelectionAllowed(true);
		blocks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		blocks.getTableHeader().setReorderingAllowed(false);
		
		blocks.getSelectionModel().addListSelectionListener(this);
		
		// layout for panel
		GridBagConstraints c = new GridBagConstraints();
		GridBagLayout l = new GridBagLayout();
		setLayout(l);
		// adding components to panel
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 5;
		c.ipady = 5;
		c.insets = new Insets(3, 3, 3, 3);
		
		c.weighty = 0.9;
		c.gridx = 0;
		c.gridy = 0;
		add(scroll, c);
		
		JTextArea helpJTextArea = new JTextArea("Select block from list to put it on map");
		helpJTextArea.setEditable(false);
		helpJTextArea.setFont(new Font("", 0, 20));
		helpJTextArea.setLineWrap(true);
		helpJTextArea.setWrapStyleWord(true);
		helpJTextArea.setBackground(null);
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 1;
		add(helpJTextArea, c);
		
		adjustColumnPreferredWidths(blocks);
		
	}
	
	public void adjustColumnPreferredWidths(final JTable table)
	{
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run()
			{
				
				// strategy - get max width for cells in column and
				// make that the preferred width
				TableColumnModel columnModel = table.getColumnModel();
				for (int col = 0; col < table.getColumnCount(); col++)
				{
					
					int maxwidth = 0;
					for (int row = 0; row < table.getRowCount(); row++)
					{
						TableCellRenderer rend = table.getCellRenderer(row, col);
						Object value = table.getValueAt(row, col);
						Component comp = rend.getTableCellRendererComponent(table, value, false, false, row, col);
						maxwidth = Math.max(comp.getPreferredSize().width, maxwidth);
					} // for row
					TableColumn column = columnModel.getColumn(col);
					column.setPreferredWidth(maxwidth);
				} // for col
				
				table.revalidate();
			}
		});
		
	}
	
	public void resetTable(String[] blockNames)
	{
		if (blockNames.length == blockNames.length)
		{
			if (blockNames.length > 0)
			{
				clear();
				model.addRow(airRow);
				Object[] data = new Object[3];
				for (int i = 0; i < blockNames.length; i++)
				{
					data[0] = i + 1;// ID 0 is for air
					data[1] = blockNames[i];
					model.addRow(data);
				}
			}
		}
		
		blocks.setRowSelectionInterval(0, 0);
	}
	
	private void clear()
	{
		model.setRowCount(0);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		if (e.getValueIsAdjusting()) return;
		DerpyIsBestPony.general.roomEditor.map.mapComp.setUsedBlock(blocks.getSelectedRow());
		
	}
	
}
