import java.awt.Color;
import java.awt.Graphics;
/******************************************************************************
 *Sniper Class
 * 	An attacker. It has high range and attack, but has lower defense and health.
 * Stores data for all stats. Has a constructor that takes a row and column and
 * calls super. Has a draw method.
 *****************************************************************************/
public class Sniper extends Attacker
{
	//stats used in damage calculation
	private static final int HEALTH = 700;
	private final static int ATTACK = 85;
	private final static int DEFENSE = 25;

	//stats used when determining when to move and fire
	private final static int RANGE = 6;
	private final static int TPF = 2;  //time per fire
	private final static int TPM = 5;

	/**
	 * Take a row and column. Call super with all of the stats shared by
	 * all Snipers and the row and column.
	 * 
	 * @param row		the initial row
	 * @param col		the initial column
	 */
	public Sniper(int row, int col) 
	{
		super(HEALTH, ATTACK, DEFENSE, TPF, RANGE, TPM, row, col);
	}


	/**
	 * Draw method. Draws a square for the sniper, and a red health bar
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
		final Color SNIPER = new Color(71,27,27);

		//draw the sniper
		g.setColor(SNIPER);
		g.fillRect(xCoord, yCoord, SIZE, SIZE);

		//draw the health bar
		super.draw(g, world);
	}
}
