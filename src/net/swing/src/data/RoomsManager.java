package net.swing.src.data;

/** Specific implementation of SaveManagerAbstract that contains methods working only for origin saves */
public final class RoomsManager extends SaveManagerAbstract
{
	
	private final SaveOrigin	saveOrigin;
	
	public RoomsManager(SaveOrigin saveOrigin)
	{
		super(saveOrigin);
		this.saveOrigin = saveOrigin;
	}
	
	public SaveOrigin getSave()
	{
		return saveOrigin;
	}
	
}
