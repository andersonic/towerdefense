import java.awt.*;	 					 
import javax.swing.*;
/******************************************************************************
 * Striker Class
 * 	An attacker. It very high attack, but very low defense and health. Stores 
 * data for all stats. Has a constructor that takes a row and column and calls 
 * super. Has a draw method.
 *****************************************************************************/
public class Striker extends Attacker
{
	//stats used in damage calculation
	private final static int HEALTH = 700;
	private final static int ATTACK = 90;
	private final static int DEFENSE = 15;
	
	//stats used when determining when to move and fire
	private final static int RANGE = 4;
	private final static int TPF = 4;
	private final static int TPM = 5;

	/**
	 * Take a row and column. Call super with all of the stats shared by
	 * all Striker and the row and column.
	 * 
	 * @param row		the initial row
	 * @param col		the initial column
	 */
	public Striker(int row, int col) 
	{
		super(HEALTH, ATTACK, DEFENSE, TPF, RANGE, TPM, row, col);
	}
	
	/**
	 * Draw method. Draws a square for the striker, and a red health bar
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
		
		//set color
		final Color STRIKER = new Color(148,157,0);
		g.setColor(STRIKER);
		
		//draw striker
		g.fillRect(xCoord, yCoord, SIZE, SIZE);
		
		//draw health bar
		super.draw(g, world);
	}
}
