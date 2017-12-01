import java.awt.*;
/******************************************************************************
 *AttackTank Class
 * 	An attacker. It has high defense and health, but lower range, and is slow.
 * Stores data for all stats. Has a constructor that takes a row and column and
 * calls super. Has a draw method.
 *****************************************************************************/
public class AttackTank extends Attacker
{
	//stats used in damage calculation
	private final static int HEALTH = 1200;
	private final static int ATTACK = 67;
	private final static int DEFENSE = 72;
	
	//stats used when determining when to move and fire
	private final static int RANGE = 3;
	private final static int ROF = 20;
	private final static int SPEED = 20;
	
	/**
	 * Take a row and column. Call super with all of the stats shared by
	 * all AttackTanks and the row and column.
	 * 
	 * @param row		the initial row
	 * @param col		the initial column
	 */
	public AttackTank(int row, int col) 
	{
		super(HEALTH, ATTACK, DEFENSE, ROF, RANGE,SPEED, row, col);
	}
	
	/**
	 * Draw method. Draws a square for the AttackTank, and a red health bar
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
		int yCoord = row*world.HEIGHT + world.HEIGHT/2 - SIZE/2 + Application.WINDOW_OFFSET;
		
		//set the color
		final Color TANK = new Color(75,107,9);
		
		//draw the AttackTank
		g.setColor(TANK);
		g.fillRect(xCoord, yCoord, SIZE, SIZE);
		
		//draw the health bar
		super.draw(g, world);
	}
}
