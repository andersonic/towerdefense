import java.awt.Color;
import java.awt.Graphics;
/******************************************************************************
 * Destroyer Class
 * 	An attacker. It has high combat stats, but is slower and has lower range
 * and rate of fire. Stores data for all stats. Has a constructor that takes
 * a row and column and calls super. Has a draw method.
 *****************************************************************************/
public class Destroyer extends Attacker
{
	//stats used in damage calculation
	private final static int HEALTH = 1000;
	private final static int ATTACK = 85;
	private final static int DEFENSE = 50;
	
	//stats used when determining when to move and fire
	private final static int RANGE = 2;
	private final static int TPF = 7;			//time per fire
	private final static int SPEED = 13;
	
	/**
	 * Take a row and column. Call super with all of the stats shared by
	 * all Destroyers and the row and column.
	 * 
	 * @param row		the initial row
	 * @param col		the initial column
	 */
	public Destroyer(int row, int col) 
	{
		super(HEALTH , ATTACK, DEFENSE, TPF, RANGE, SPEED, row, col);
	}

	/**
	 * Draw method. Draws a square for the destroyer, and a red health bar
	 * 
	 * @param g			graphics
	 * @param world		the world
	 */
	public void draw(Graphics g, World world) 
	{
		final int SIZE = 15;	//the size of the square
		
		//get the coordinates
		int row = super.getRow();
		int col = super.getCol();
		
		//determine the exact location based on coordinates and dimensions of
		//the world
		int xCoord = col*world.WIDTH + world.WIDTH/2 - SIZE/2;
		int yCoord = row*world.HEIGHT + world.HEIGHT/2 - SIZE/2 + Application.WINDOW_OFFSET;
		
		//set the color
		final Color DESTROYER = new Color(84,55,8);
		
		//draw the destroyer
		g.setColor(DESTROYER);
		g.fillRect(xCoord, yCoord, SIZE, SIZE);
		
		//draw the health bar
		super.draw(g, world);
	}

}
