package net.swing.src.ent;


import net.swing.engine.WPS;
import net.swing.src.ent.Entity;
import net.swing.src.ent.EntityModelTextured;
import net.swing.src.ent.Mind;
import net.swing.src.ent.mind.AIPlayer;
import net.swing.src.env.Room;

public class EntityPlayer extends EntityModelTextured {
	
	private AIPlayer ai = new AIPlayer();
	
	public EntityPlayer(){
		super("PNG", "res/objects/entities/player/player.png");
		getGraphics().setHeight(50);
		
	}
	
	@Override
	public float getMaxHealth(){
		return 100;
	}
	
	@Override
	public String getName(){
		return "player";
	}
	
	@Override
	public int getSize(){
		return 2;
	}
	
	@Override
	public boolean isTeleportationAllowed(){
		return true;
	}
	
	@Override
	public WPS think(Entity ent, Mind program, Room r) {
		return ai.think(ent, program, r);
	}
	
	@Override
	public boolean isAffectedAsPoint()
	{
		return true;
	}
}






//////////////////////OLD PLAYER CODE
//package net.swing.src.ent;
//
//import net.swing.engine.WPS;
//import net.swing.engine.graph.Animation;
//import net.swing.engine.graph.AnimationsBase;
//import net.swing.engine.graph.DisplayAnimation;
//import net.swing.ground.Graphics;
//import net.swing.src.ent.mind.AI;
//import net.swing.src.ent.mind.AIPlayer;
//import net.swing.src.env.WorldSettings;
//
//public final class EntityPlayer extends Entity
//{
//	
//	private DisplayAnimation	r		= new DisplayAnimation(new Animation(AnimationsBase.TEST));
//	private AIPlayer			inputs	= new AIPlayer();
//	
//	public EntityPlayer()
//	{
//		super(2, "player");
//		health = 1;
//		r.getRenderer().setSize(WorldSettings.collWidth() * 2, WorldSettings.collHeight() * pointsQuantity);
//		setAffectedAsPoint(true);
//	}
//	
//	@Override
//	public void render(WPS movingDirection, WPS AIdesiredMovVector)
//	{
//		r.renderLeft(WorldSettings.WPS.getLocation(this));
//	}
//	
//	@Override
//	public AI getAI()
//	{
//		return inputs;
//	}
//	
//	@Override
//	public float getMaxHealth()
//	{
//		return 100;
//	}
//	
//	@Override
//	public Graphics getGraphics()
//	{
//		return r.getRenderer();
//	}
//	
//	@Override
//	public boolean isTeleportationAllowed()
//	{
//		return true;
//	}
//	
//	@Override
//	public Entity createNewInstance()
//	{
//		return null;
//	}
//	
//}
