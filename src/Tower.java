import java.awt.*;						 
import javax.swing.*;
/******************************************************************************
 *Tower Class
 * 	The objective of the game is for the player to defend this. It has high
 * health and relatively high attack and defense. Contains a constructor, which
 * calls super with all of the tower's stats, a target priority method, which
 * always returns -1, and a draw method.
 *****************************************************************************/
public class Tower extends Shooter
{
	//stats used in damage calculation. The tower's stats are set
	private static final int TOWER_HEALTH = 1500;
	private final static int TOWER_ATTACK = 90;
	private final static int TOWER_DEFENSE = 50;
	
	//stats used when determining when to fire. The tower's stats are set
	private final static int TOWER_RANGE = 4;
	private final static int TPF = 5;	//time per fire
	
	/**
	 * Call super with all the statistics specific to towers, as well as the row
	 * and column.
	 * 	
	 * @param row			the tower's row
	 * @param col			the tower's column
	 */
	public Tower(int row, int col)
	{
		super(TOWER_HEALTH, TOWER_ATTACK, TOWER_DEFENSE, TPF, TOWER_RANGE, row, col);
	}
	
	/**
	 * Tower always is always the top priority target. Since higher numbers
	 * correspond to lower priority, Tower's target priority is always -1 so
	 * it is always top priority.
	 */
	public int targetPriority()
	{
		return -1;
	}
	
	/**
	 * Draw a square, location to be determined by the row and column as
	 * well as the dimensions of the world.
	 */
	public void draw(Graphics g, World world)
	{
		final int SIZE = 30;	//size of the square
		
		//get the coordinates
		int row = super.getRow();
		int col = super.getCol();
		
		//determine the exact location of the Tower
		int xCoord = col*world.WIDTH + world.WIDTH/2 - SIZE/2;
		int yCoord = row*world.HEIGHT + world.HEIGHT/2 - SIZE/2;
		
		final Color TOWER = new Color(40,70,100);	//the color for the tower
		
		//draw the tower
		g.setColor(TOWER);
		g.fillRect(xCoord, yCoord, SIZE, SIZE);
		
		//draw the health bar
		super.draw(g,world);
	}
}
