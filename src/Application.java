import java.awt.*;						 
import java.util.ArrayList;

import javax.swing.*;

import java.util.*;
import java.awt.event.*;

/******************************************************************************
 *Alice Yao + Jun Ru Anderson
 *Application Class
 * 	In this game, we play Tower Defense. Tower Defense has attackers,
 * defenders, and a tower. The goal of the game is to keep the tower
 * alive by placing defenders along the path to shoot the attackers
 * before they kill the tower.
 *****************************************************************************/

public class Application extends JFrame implements MouseListener
{
	private static enum SelectedObject 
	{
		NONE,
		WARSHIP,
		WARRIOR
	};
	
	private static World myWorld = new World();
	
	//Setting the window screen 
	public static final int WINDOW_OFFSET = 20;
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 400;
	private static ArrayList<Shooter> attackers = new ArrayList<Shooter>(); //ArrayList of attackers
	private static ArrayList<Shooter> defenders = new ArrayList<Shooter>(); //ArrayList of defenders
	private static Tower myTower = new Tower(myWorld.getTowerRow(), myWorld.getTowerCol()); //new tower
	
	private static final Warship origWarship = new Warship(9, 18); //Warship (to be copied/replicated)
	private static final Warrior origWarrior = new Warrior(8, 19); //Warrior (to be copied/replicated)
	private static SelectedObject selectedObject = SelectedObject.NONE;
	
	private static int money = 80; //Money limits the amount of defenders allowed to be built
	
	public static void main(String[] args) 
	{	
		Application gp = new Application();
		gp.addMouseListener(gp);
		
		gp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gp.setTitle("Tower Defense");			// Optional title
		gp.setSize(WINDOW_WIDTH, WINDOW_HEIGHT + WINDOW_OFFSET); // Set the width and height of the window
		gp.setVisible(true);					// Display the window
		
		//Adding defenders and attackers
		defenders.add(myTower);
		defenders.add(origWarship);
		defenders.add(origWarrior);
		attackers.add(new Sniper(0, 19));
		attackers.add(new Striker(0, 18));
		attackers.add(new Striker(1, 19));
		attackers.add(new AttackTank(0, 17));
		attackers.add(new AttackTank(1, 18));
		attackers.add(new AttackTank(2, 19));
		attackers.add(new Destroyer(0, 16));
		attackers.add(new Destroyer(1, 17));
		attackers.add(new Destroyer(2, 18));
		attackers.add(new Destroyer(3, 19));

		takeTurns(gp);
		
	}
	
	public static void takeTurns(Application myApp)
	{
		int i = 0;
		
		//while the game is not over, have every shooter take its turn
		while(!myTower.isDead() && !attackers.isEmpty())
		{
			//slow it down so people can see stuff happening
			try 
			{
				Thread.sleep(100);
			} 
			catch (Exception e) 
			{
			
			}
			//have attackers take turn
			attackersStuff(i);
			
			//have defenders take turn
			defenderStuff(i);
			i++;
			
			//repaint
			myApp.repaint();
		}
	}
	
	public static void attackersStuff(int i)
	{
		//move each attacker. Determine whether to move based on speed
		Iterator<Shooter> atkItr = attackers.iterator();
		while(atkItr.hasNext())
		{
			Attacker attacker = (Attacker) atkItr.next();
			if(!attacker.isDead())
			{
				if(i % attacker.getTimePerMove() == 0)
					attacker.move(myWorld, myTower);
				if(i % attacker.getROF() == 0)
					attacker.shoot(defenders);
			}
			else
			{
				atkItr.remove();
				myWorld.setEmptySpaces(attacker.getRow(), attacker.getCol(), true);
			}
		}
	}
	
	public static void defenderStuff(int i)
	{
		//Shoot attackers, determined by speed
		Iterator<Shooter> defItr = defenders.iterator();
		while(defItr.hasNext())
		{
			Shooter defender = defItr.next();
			if(!defender.isDead())
			{
				if(i % defender.getROF() == 0)
					defender.shoot(attackers);
			}	
			else
			{
				defItr.remove();
			}
		}
	}
	
	public void paint(Graphics g)
	{
		//draw world
		myWorld.draw(g);
		
		//Show user how much money they have left
		drawMoney(g);
		
		//draw attackers
		for(Shooter attacker : attackers)
		{
			attacker.draw(g, myWorld);
		}
		//draw defenders
		for(Shooter defender : defenders)
		{
			defender.draw(g, myWorld);
		}
	}
	
	private void drawMoney(Graphics g)
	{
		final int HORIZONTAL_TEXT_OFFSET = 350;
		final int VERTICAL_TEXT_OFFSET = 10;
		final int TEXT_SIZE = 30;
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Courier", Font.BOLD, TEXT_SIZE));
		g.drawString("Money remaining:\n" + money, HORIZONTAL_TEXT_OFFSET, WINDOW_HEIGHT + WINDOW_OFFSET - VERTICAL_TEXT_OFFSET);
	}
	
	private void build(int row, int col)
	{
		//building new Warriors
		if(selectedObject == SelectedObject.WARRIOR && myWorld.isLand(row, col) && myWorld.isEmtpySpace(row, col)
				&& Warrior.COST <= money)
		{ 
			defenders.add(new Warrior(row, col)); //adding defenders where user dropped
			money -= Warrior.COST; //subtracting the total cost of warrior
			myWorld.setEmptySpaces(row, col, false); //no longer e
		}
		//building new Warships
		else if(selectedObject == SelectedObject.WARSHIP && myWorld.isWater(row, col)
				&& Warship.COST <= money)
		{
			defenders.add(new Warship(row, col));
			money -= Warship.COST;
			myWorld.setEmptySpaces(row, col, false);
		}	
		
		selectedObject = SelectedObject.NONE;
		
	}
	
	private boolean origWarshipClicked(int row, int col)
	{
		if(origWarship.getCol() == col && origWarship.getRow() == row) 
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}
	
	private boolean origWarriorClicked(int row, int col)
	{
		if(origWarrior.getRow() == row && origWarrior.getCol() == col)
		{ 
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		int x = e.getX();
		int y = e.getY();
		int []coords = myWorld.getCoords(x, y);
		
		if(origWarriorClicked(coords[0], coords[1]))
		{
			selectedObject = SelectedObject.WARRIOR;
		}
		else if (origWarshipClicked(coords[0], coords[1]))
		{
			selectedObject = SelectedObject.WARSHIP;
		}
		else 
		{
			selectedObject = SelectedObject.NONE;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		int x = e.getX();
		int y = e.getY();
		int []coords = myWorld.getCoords(x, y);
		
		if(selectedObject != SelectedObject.NONE)
		{
			build(coords[0], coords[1]);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
	}
}
