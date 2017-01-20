import static net.swing.src.ent.mind.AImath.*;

import java.io.InputStream;

import net.swing.engine.WPS;
import net.swing.src.ent.Entity;
import net.swing.src.ent.EntityModelRendered;
import net.swing.src.ent.EntityModelTextured;
import net.swing.src.ent.Mind;
import net.swing.src.ent.mind.AIPlayer;
import net.swing.src.env.Room;

public class Main extends EntityModelTextured {
	
	private AIPlayer ai = new AIPlayer();
	
	public Main(){
		super("PNG", "res/objects/entities/player/player.png");
		getGraphics().setHeight(50);
		
	}
	
	@Override
	public float getMaxHealth(){
		return 100;
	}
	
	@Override
	public String getName(){
		return "player"
	}
	
	@Override
	public int getSize(){
		return 2
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

