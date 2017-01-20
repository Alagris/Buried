package net.editor.room;

import java.io.File;

import net.swing.engine.WPS;

public class PrimitiveEntity
{
	private String	name;
	private WPS		location;
	private File	groovyClass;
	
	public File getGroovyClass()
	{
		return groovyClass;
	}
	
	public void setGroovyClass(File groovyClass)
	{
		this.groovyClass = groovyClass;
	}
	
	public WPS getLocation()
	{
		return location;
	}
	
	public void setLocation(WPS location)
	{
		this.location = location;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
}
