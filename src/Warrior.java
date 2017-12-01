import java.awt.Color;
import java.awt.Graphics;
/******************************************************************************
 *Warrior Class
 * 	A landed defender. Stores data for all stats. Has a constructor that takes 
 * a row and column and calls super. Has a draw method.
 *****************************************************************************/
public class Warrior extends Defender 
{
	//stats used in damage calculation
	private static final int WARRIOR_HEALTH = 1000;
	private final static int WARRIOR_ATTACK = 75;
	private final static int WARRIOR_DEFENSE = 55;
	
	//stats used when determining when to move and fire
	private final static int WARRIOR_RANGE = 5;
	private final static int WARRIOR_TPF = 5;
	
	//data related to building
	public final static int COST = 10;
	private final static char TYPE = World.GROUND;

	/**
	 * Take a row and column. Call super with all of the stats shared by
	 * all Warrior and the row and column.
	 * 
	 * @param row		the initial row
	 * @param col		the initial column
	 */
	public Warrior(int x, int y) 
	{
		super(WARRIOR_HEALTH, WARRIOR_ATTACK, WARRIOR_DEFENSE, WARRIOR_TPF,
				WARRIOR_RANGE, COST, TYPE, x, y);
	}

	/**
	 * Draw method. Draws a square for the warrior, and a red health bar
	 * 
	 * @param g			graphics
	 * @param world		the world
	 */
	public void draw(Graphics g, World world)
	{

		final int SIZE = 15; 	//the size of the square
		
		//get the coordinates
		int row = super.getRow();
		int col = super.getCol();
		
		//determine the exact location based on coordinates and dimensions of
		//the world
		int xCoord = col*world.WIDTH + world.WIDTH/2 - SIZE/2;
		int yCoord = row*world.HEIGHT + world.HEIGHT/2 - SIZE/2;
		
		//set the color
		final Color WARRIOR = new Color(135, 0, 183);
				
		//draw the warrior
		g.setColor(WARRIOR);
		g.fillRect(xCoord, yCoord, SIZE, SIZE);
				
		//draw the health bar
		super.draw(g, world);
	}
}
