package net.editor.room;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.editor.main.DerpyIsBestPony;
import net.editor.room.Map.MapLayer;
import net.swing.src.data.DataCoder;
import net.swing.src.data.SaveOrigin;

public class RoomEditorPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long		serialVersionUID	= 1L;
	
	RoomEditorPanel_Areas			areas;
	RoomEditorPanel_Blocks			blocks;
	RoomEditorPanel_BackgroundImage	backgroundImage;
	RoomEditorPanel_Entities		entities;
	RoomEditorPanel_Properties		properties;
	
	JButton							saveButton;
	
	JTabbedPane						tabbedPane			= new JTabbedPane();
	
	public RoomEditorPanel()
	{
		properties = new RoomEditorPanel_Properties();
		entities = new RoomEditorPanel_Entities();
		areas = new RoomEditorPanel_Areas();
		blocks = new RoomEditorPanel_Blocks();
		backgroundImage = new RoomEditorPanel_BackgroundImage();
		
		tabbedPane.addTab("Blocks", blocks);
		tabbedPane.addTab("Areas", areas);
		tabbedPane.addTab("Entities", entities);
		tabbedPane.addTab("Background", backgroundImage);
		tabbedPane.addTab("Properties", properties);
		
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e)
			{
				Object o = tabbedPane.getSelectedComponent();
				if (o == blocks)
				{
					DerpyIsBestPony.general.roomEditor.map.setLayer(MapLayer.BLOCKS);
				}
				else if (o == areas)
				{
					DerpyIsBestPony.general.roomEditor.map.setLayer(MapLayer.AREAS);
				}
				else if (o == entities)
				{
					DerpyIsBestPony.general.roomEditor.map.setLayer(MapLayer.ENTITIES);
				}
			}
		});
		
		GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.1;
		c.gridwidth = 1;
		c.gridheight = 1;
		
		c.weighty = 1.5;
		c.gridx = 0;
		c.gridy = 0;
		add(tabbedPane, c);
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				DerpyIsBestPony.general.roomEditor.map.saveAll();
			}
		});
		
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 1;
		add(saveButton, c);
		
	}
	
	public void load(SaveOrigin saveOrigin, String room)
	{
		properties.setProperties(room);
		entities.resetList(DataCoder.readLinesArray(saveOrigin.getInfoFile(), ";Ent;"));
	}
	
}
