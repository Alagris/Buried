package net.swing.src.data;

/** Specific implementation of SaveManagerAbstract that contains methods working only for playable saves */
public final class SavePlayabeManager extends SaveManagerAbstract
{
	
	private final SavePlayable	savePlayable;
	
	public SavePlayabeManager(SavePlayable save)
	{
		super(save);
		this.savePlayable = save;
	}
	
	public SavePlayable getSavePlayable()
	{
		return savePlayable;
	}
	
}
