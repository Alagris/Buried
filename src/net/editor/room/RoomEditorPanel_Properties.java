package net.editor.room;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import net.editor.main.DerpyIsBestPony;
import net.swing.src.data.PrimitiveRoom;
import net.swing.src.data.QuickRoomsManager;

public class RoomEditorPanel_Properties extends JPanel implements ActionListener
{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	private JButton				loadAnotherRoom;
	private JTextArea			saveNameArea;
	
	public RoomEditorPanel_Properties()
	{
		GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		saveNameArea = new JTextArea();
		saveNameArea.setEditable(false);
		saveNameArea.setBackground(null);
		saveNameArea.setFont(new Font("", 0, 20));
		saveNameArea.setWrapStyleWord(true);
		saveNameArea.setLineWrap(true);
		saveNameArea.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		loadAnotherRoom = new JButton("change room");
		loadAnotherRoom.setToolTipText("Loads another room from this save");
		loadAnotherRoom.addActionListener(this);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 5;
		c.ipady = 5;
		c.insets = new Insets(3, 3, 3, 3);
		
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 0;
		add(saveNameArea, c);
		
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 1;
		add(loadAnotherRoom, c);
		
	}
	
	public void setProperties(String room)
	{
		saveNameArea.setText("Save: " + PrimitiveRoom.currentSave.getName() + "\nRoom: " + PrimitiveRoom.currentRoom);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == loadAnotherRoom)
		{
			RoomsPane pane = new RoomsPane(DerpyIsBestPony.general, PrimitiveRoom.currentSave, "Load");
			if (pane.getResult() == null) return;
			if (pane.isAddRoom())
			{
				QuickRoomsManager.createDefaultRoom(pane.getResult(),PrimitiveRoom.currentSave);
			}
			else
			{
				DerpyIsBestPony.general.roomEditor.loadRoom(PrimitiveRoom.currentSave, pane.getResult());
			}
		}
	}
	
	public void save()
	{
		
	}
	
}
