package net.editor.room;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class RoomEditorPanel_Entities extends JPanel
{
	
	/**
	 * 
	 */
	private static final long			serialVersionUID	= 1L;
	
	private JScrollPane					scroll;
	private DefaultListModel<String>	listModel;
	private JList<String>				list;
	
	public RoomEditorPanel_Entities()
	{
		listModel = new DefaultListModel<>();
		list = new JList<String>(listModel);
		scroll = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
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
		
		JTextArea helpJTextArea = new JTextArea("Select entity from list to put it on map");
		helpJTextArea.setEditable(false);
		helpJTextArea.setFont(new Font("", 0, 20));
		helpJTextArea.setLineWrap(true);
		helpJTextArea.setWrapStyleWord(true);
		helpJTextArea.setBackground(null);
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 1;
		add(helpJTextArea, c);
		
	}
	
	public void resetList(String[] entsNames)
	{
		if (entsNames.length == entsNames.length)
		{
			if (entsNames.length > 0)
			{
				listModel.clear();
				for (int i = 0; i < entsNames.length; i++)
				{
					listModel.addElement(entsNames[i]);
				}
			}
		}
	}
	
	public String getSelectedEntity()
	{
		return list.getSelectedValue();
	}
}
