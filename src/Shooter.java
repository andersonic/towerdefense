import java.util.ArrayList;
import java.awt.*;						 

import javax.swing.*;

/******************************************************************************
 * Shooter Class
 * 	Superclass for all defensive shooters, attackers, and the tower. In addition to 
 * all the data, the shooter class contains a draw method that draws the health bar
 * for each attacker, defender, and the tower. This class also has a constructor
 * that takes in all the data and initializes variables, and has a target priority.
 * Target priority calculates which attacker/defender is closer and decides
 * which one to shoot first.
 *******************************************************************************/

public abstract class Shooter 
{
	private int currentHealth;
	private final int HP;
	private final int ATK;
	private final int DEF;
	private final int TPF;		//rate of fire
	private final int RANGE;
	private int row;
	private int col;
	
	/**
	 * Initialize variables
	 * 
	 * @param health
	 * @param attack
	 * @param defense
	 * @param timePerFire			time per shot
	 * @param range
	 * @param initRow
	 * @param initCol
	 */
	
	public Shooter(int health, int attack, int defense, int timePerFire, int range, int initRow, int initCol)
	{
		HP = health;
		currentHealth = health;
		ATK = attack;
		DEF = defense;
		TPF = timePerFire;
		RANGE = range;
		row = initRow;
		col = initCol;
	}
	
	public abstract int targetPriority();
	
	/**
	 * Draw the health bar of each object
	 * 
	 * @param g		graphics
	 * @param world     World
	 */
	public void draw(Graphics g, World world)
	{
		final int HP_BAR_LENGTH = 20;
		final int HP_BAR_HEIGHT = 5;
		
		int hpX = col*world.WIDTH + world.WIDTH/2 - HP_BAR_LENGTH/2;
		int hpY = row*world.HEIGHT + world.HEIGHT/2 - HP_BAR_HEIGHT/2 + Application.WINDOW_OFFSET;
		
		g.setColor(Color.RED);
		g.drawRect(hpX, hpY, HP_BAR_LENGTH, HP_BAR_HEIGHT);
		g.fillRect(hpX,hpY, (int) (HP_BAR_LENGTH * healthPercent()), HP_BAR_HEIGHT);
	}

	/**
	 * If there is a target, the object shoots at it.
	 * This method reduces the health of the target.
	 * 
	 * @param target    Shooter
	 */
	public void shoot(Shooter target)
	{
		if(target != null)
		{
			int damage = ATK - target.getDef();
			target.reduceHealth(damage);
		}
	}
	
	/**
	 * Can take a list of shooters, it picks which object
	 * to shoot and shoots it.
	 * 
	 * @param targets    ArrayList<Shooter>
	 */
	
	public void shoot(ArrayList<Shooter> targets)
	{
		Shooter target = chooseTarget(targets);
		shoot(target);
	}
	
	public boolean isDead()
	{
		return currentHealth <= 0;
	}

	public void reduceHealth(int damage)
	{
		if(damage > 0)
			currentHealth -= damage;
	}
	
	public boolean inRange(Shooter target)
	{
		int xDif = Math.abs(row - target.getRow());
		int yDif = Math.abs(col - target.getCol());
		return (xDif + yDif) <= RANGE;
	}
	
	/**
	 * Takes an bunch of targets and picks which one to shoot at.
	 * 
	 * @param enemies	all enemy things
	 * @return				which target to shoot at
	 */
	public Shooter chooseTarget(ArrayList<Shooter> enemies) 
	{
		//this will be a list of targets in range
		ArrayList<Shooter> targets = new ArrayList<Shooter>();
		
		//add each enemy that is in range to targets
		for(Shooter aTarget : enemies)
		{
			if(inRange(aTarget))
				targets.add(aTarget);
		}
		
		if(targets.size() != 0)
		{
			Shooter topPriority = targets.get(0);	//the one to shoot at
			
			//check each target in range
			for(Shooter aTarget : targets)
			{
				if(aTarget.targetPriority() < topPriority.targetPriority())
					topPriority = aTarget;
			}
			
			//the top priority target
			return topPriority;
		}
		return null;
	}
	
	public void setCoords(int newRow, int newCol)
	{
		row = newRow;
		col = newCol;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public int getDef()
	{
		return DEF;
	}
	
	public int getROF()
	{
		return TPF;
	}
	
	public double healthPercent()
	{
		return ((double) currentHealth) / ((double) HP);
	}
	
	public int getCurrentHealth()
	{
		return currentHealth;
	}
	
}
