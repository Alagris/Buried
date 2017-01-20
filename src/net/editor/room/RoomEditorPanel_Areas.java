package net.editor.room;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.editor.main.DerpyIsBestPony;
import net.swing.src.env.WorldSettings;

public class RoomEditorPanel_Areas extends JPanel
{
	
	/**
	 * 
	 */
	private static final long					serialVersionUID		= 1L;
	
	private final JCheckBox						enableAreaMarkCheckBox	= new JCheckBox("Mark areas");
	
	private final DefaultComboBoxModel<Integer>	comboBoxModel			= new DefaultComboBoxModel<Integer>();
	private final JComboBox<Integer>			areaComboBox			= new JComboBox<Integer>(comboBoxModel);
	private final JLabel						areaComboBoxLabel		= new JLabel("Selected area:");
	
	public RoomEditorPanel_Areas()
	{
		
		// layout for panel
		
		for (int i = 0; i < WorldSettings.collAreasQuantity; i++)
		{
			comboBoxModel.addElement(new Integer(i));
		}
		
		areaComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				DerpyIsBestPony.general.roomEditor.map.areasComp.setUsedArea(areaComboBox.getSelectedIndex());
			}
		});
		add(areaComboBoxLabel);
		add(areaComboBox);
		
		enableAreaMarkCheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				DerpyIsBestPony.general.roomEditor.map.areasComp.enableMarker(enableAreaMarkCheckBox.isSelected());
			}
		});
		add(enableAreaMarkCheckBox);
	}
	
}
