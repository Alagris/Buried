package net.swing.src.ent;

import net.swing.engine.graph.AnimationSet;
import net.swing.ground.Graphics;

public abstract class EntityModelRendered implements EntityModel
{
	AnimationSet	set;
	
	public EntityModelRendered(AnimationSet set)
	{
		this.set = set;
	}
	
	@Override
	public Graphics getGraphics()
	{
		return set.getGraphics();
	}
	
	// protected void renderDependingOnAI(WPS AIdesiredMovVector, int[]
	// tripleSet)
	// {
	// switchTheAnimation(AIdesiredMovVector,tripleSet);
	// }
	// /**@param tripleSet - array with 3 indexes.
	// * <pre>0- index of animation of entity moving left</pre>
	// * <pre>1- index of animation of entity standing</pre>
	// * <pre>2- index of animation of entity moving right</pre>*/
	// protected void renderDependingOnMovement(WPS movingDirection, int[]
	// tripleSet)
	// {
	// switchTheAnimation(movingDirection,tripleSet);
	// }
	//
	// private void switchTheAnimation(WPS direction, int[] tripleSet)
	// {
	// switchTheAnimation(direction.getA(),tripleSet);
	// }
	//
	// private void switchTheAnimation(double a, int[] tripleSet)
	// {
	// switchTheAnimation((int) Math.signum(a),tripleSet);
	// }
	//
	// private void switchTheAnimation(int direction, int[] tripleSet)
	// {
	// switch (direction)
	// {
	// case 1:
	//
	// break;
	// case 0:
	//
	// break;
	// case -1:
	//
	// break;
	// }
	// }
	
}
