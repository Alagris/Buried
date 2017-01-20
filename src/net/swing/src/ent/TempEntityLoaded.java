package net.swing.src.ent;

import java.io.File;

import net.swing.engine.graph.Animation;
import net.swing.src.data.Files;

public class TempEntityLoaded
{
	
	public final Animation	anim;
	public final String		name;
	
	public TempEntityLoaded(File file)
	{
		anim = new Animation(file, 32, 100);
		name = file.getName().split("\\.")[0];
	}
	
	public TempEntityLoaded(String name)
	{
		anim = new Animation(new File(Files.TEMP_ENTITIES_FOLDER.getPath() + "/" + name + ".png"), 32, 100);
		this.name = name;
	}
}
