import java.awt.*;		 				  
import javax.swing.*;
/******************************************************************************
 * Warship Class
 * 	A defender that is built on water rather than land. All warships have the
 * same stats, and these are stored as static finals. Warship has a constructor,
 * which takes only a row and column, and calls super with the row and column
 * and the appropriate finals. Has a draw method that draws a square of a
 * distinct color, and calls the superclass' draw method to draw the health
 * bar.
 *****************************************************************************/
public class Warship extends Defender
{
	//stats used in damage calculation. All warships have the same stats
	private final static int HEALTH = 1000;
	private final static int ATTACK = 75;
	private final static int DEFENSE = 55;
	
	//stats used in firing calculation. All warships have the same stats
	private final static int RANGE = 3;
	private final static int ROF = 7;
	
	//information used in building
	public final static int COST = 10;
	private final static char TYPE = World.WATER;
	
	/**
	 * Take the initial coordinates. Call super using these values and all the
	 * stats that are common to all instances of this class.
	 * 	
	 * @param row			the row
	 * @param col			the column
	 */
	public Warship(int row, int col) 
	{
		//call super
		super(HEALTH, ATTACK, DEFENSE, ROF, RANGE,
				COST, TYPE, row, col);
	}
	
	/**
	 * Draw a warship. Draw a blue-ish square, location to be determined by
	 * the row and column and dimensions of the world, and call super.draw 
	 * to draw a red health bar.
	 * 
	 * @param g				graphics
	 * @param world			the world
	 */
	public void draw(Graphics g, World world)
	{
		final int SIZE = 15;	//size of the square
		
		//determine the position of the warships
		int row = super.getRow();
		int col = super.getCol();
		
		//center the warship in its grid square
		int xCoord = col*world.WIDTH + world.WIDTH/2 - SIZE/2;
		int yCoord = row*world.HEIGHT + world.HEIGHT/2 - SIZE/2;
		
		final Color WARSHIP = new Color(70,81,159);	//a blue-ish color
		
		//draw the square
		g.setColor(WARSHIP);
		g.fillRect(xCoord, yCoord, SIZE, SIZE);
		
		//draw the health bar.
		super.draw(g, world);
	}

}
