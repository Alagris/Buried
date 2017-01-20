package net.swing.src.ent;

// import net.BuriedMain;
// import net.swing.engine.WPS;
// import net.swing.engine.graph.Animation;
// import net.swing.engine.graph.RenderAnimation;
// import net.swing.src.ent.mind.AI;
// import net.swing.src.ent.mind.AISimple;
// import net.swing.src.env.Room;
// import net.swing.src.env.WorldSettings;

public class TempEntity// extends EntityStatic
{
	
	// private RenderAnimation r;
	// private Mind program = null;
	// private int bindingIndex;
	//
	// /**
	// * It is possible to change animation size, but collision detection still
	// * works for only one point
	// */
	// public TempEntity(Animation anim, float width, float height, int
	// sizeInAreas, int index, String name)
	// {
	// super(EntState.NORMAL, sizeInAreas, name);
	// bindingIndex = index;
	// r = new RenderAnimation(anim);
	// r.setSize(width, height);
	// }
	//
	// /** Default size is the same as of single collision area */
	// public TempEntity(Animation anim, int sizeInAreas, int index, String
	// name)
	// {
	// super(EntState.NORMAL, sizeInAreas, name);
	// bindingIndex = index;
	// r = new RenderAnimation(anim);
	// r.setSize(WorldSettings.collWidth(), WorldSettings.collHeight());
	// }
	//
	// /**
	// * It is possible to change animation size, but collision detection still
	// * works for only one point
	// */
	// public TempEntity(Animation anim, float width, float height, int
	// sizeInAreas, String name)
	// {
	// super(EntState.NORMAL, sizeInAreas, name);
	// r = new RenderAnimation(anim);
	// r.setSize(width, height);
	// }
	//
	// /** Default size is the same as of single collision area */
	// public TempEntity(Animation anim, int sizeInAreas, String name)
	// {
	// super(EntState.NORMAL, sizeInAreas, name);
	// r = new RenderAnimation(anim);
	// r.setSize(WorldSettings.collWidth(), WorldSettings.collHeight());
	// }
	//
	// /** Default size is the same as of single collision area */
	// public TempEntity(TempEntityLoaded loaded)
	// {
	// super(EntState.NORMAL, 1, loaded.name);
	// r = new RenderAnimation(loaded.anim);
	// r.setSize(WorldSettings.collWidth(), WorldSettings.collHeight());
	// }
	//
	// @Override
	// public void update(Mind program, Room r)
	// {
	//
	// if (state.moving)
	// {
	// movementVector.set(getAI().think(this, this.program, r));
	// vector.addVector((acc.toWPS().a * BuriedMain.getGamma() +
	// movementVector.a), (acc.toWPS().b * BuriedMain.getGamma() +
	// movementVector.b));
	// vector.apply(this, BuriedMain.getGamma());
	// vector.subtractVector(movementVector.a, movementVector.b);
	// }
	// render();
	// movementVector.set(0, 0);
	//
	// }
	//
	// @Override
	// public AI getAI()
	// {
	// return new AISimple();
	// }
	//
	// @Override
	// protected void render(WPS movingDirection)
	// {
	// r.renderLeft(WorldSettings.WPS.getLocation(this));
	// }
	//
	// @Override
	// public float getMaxHealth()
	// {
	// return -1;
	// }
	//
	// @Override
	// protected void addHealth(float hp)
	// {
	// if (getMaxHealth() == -1) return;
	// super.addHealth(hp);
	// }
	//
	// @Override
	// public double getWidth()
	// {
	// return 0;
	// }
	//
	// @Override
	// public double getHeight()
	// {
	// return 0;
	// }
	//
	// public int getBindingIndex()
	// {
	// return bindingIndex;
	// }
	//
	// public Mind getProgram()
	// {
	// return program;
	// }
	//
	// public void setProgram(Mind program)
	// {
	// this.program = program;
	// }
	//
	// /** Deletes all instructions and entity is stupid again **/
	// public void resetProgram()
	// {
	// this.program = null;
	// }
}
