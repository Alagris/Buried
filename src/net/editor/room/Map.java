package net.editor.room;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JRootPane;

import net.swing.src.data.SaveOrigin;

public class Map extends JRootPane
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public enum MapLayer
	{
		BLOCKS, AREAS, ENTITIES;
	}
	
	public final RoomEditorMapComponent			mapComp			= new RoomEditorMapComponent();
	public final RoomEditorAreasComponent		areasComp		= new RoomEditorAreasComponent();
	public final RoomEditorEntitiesComponent	entitiesComp	= new RoomEditorEntitiesComponent();
	
	private final JPanel						glassPane		= new GlassPanePanel();
	
	public Map()
	{
		
		setGlassPane(glassPane);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(mapComp, BorderLayout.CENTER);
		getContentPane().setVisible(true);
		
		setLayer(MapLayer.BLOCKS);
	}
	
	public void saveAll()
	{
		mapComp.saveAll();
	}
	
	public void refresh()
	{
		mapComp.refresh();
		areasComp.fillCellsWithData(mapComp.primitive.areaMatrix);
	}
	
	public void loadRoom(SaveOrigin saveOrigin, String room)
	{
		mapComp.setRoomBuilder(saveOrigin, room);
		areasComp.fillCellsWithData(mapComp.primitive.areaMatrix);
		entitiesComp.setEntities(mapComp.primitive.entiesList);
	}
	
	public void setLayer(MapLayer l)
	{
		switch (l)
		{
			case AREAS:
				getGlassPane().setVisible(true);
				add(areasComp, BorderLayout.CENTER);
				break;
			case ENTITIES:
				getGlassPane().setVisible(true);
				add(entitiesComp, BorderLayout.CENTER);
				break;
			case BLOCKS:
				getGlassPane().setVisible(false);
				break;
		}
		revalidate();
	}
	
	protected class GlassPanePanel extends JPanel
	{
		
		/**
		 * 
		 */
		private static final long	serialVersionUID	= 1L;
		
		public GlassPanePanel()
		{
			setLayout(new BorderLayout());
			areasComp.setOpaque(false);
			entitiesComp.setOpaque(false);
			setOpaque(false);
			setVisible(true);
		}
		
	}
	
}
