import java.util.*; 
import java.io.*;
import java.awt.*;						 
import javax.swing.*;

/******************************************************************************
 * World Class
 * 	The world, the battlefield. Determines the layout of the world from a text
 * file and has a draw() method. Also stores public finals for the number of
 * rows and columns in the world, as well as the dimensions of these (in pixels)
 * for other classes to access.
 * 	Has a variety of getters and setters. Also has a variety of methods that
 * provide information about a given location.
 *
 *****************************************************************************/
public class World 
{
	//final characters
	public static final char PATH = 'p';
	public static final char GROUND = 'g';
	public static final char WATER = 'w';
	public static final char TOWER = 't';
	
	private char[][] world = setWorld();	//the world
	private boolean [][]emptySpaces = setEmptySpaces();	//which spaces are empty
	
	private static final String FILE_NAME = "map.txt";
	private static Scanner file = openTheFile();
	
	//various dimensions
	public final int WIDTH = Application.WINDOW_WIDTH / world[0].length;
	public final int HEIGHT = Application.WINDOW_HEIGHT / world.length;
	public final int OFFSET = Application.WINDOW_OFFSET;
	public final int ROWS = world.length;
	public final int COLS = world[0].length;
	
	/**
	 * Fill the world with the characters in map.txt
	 * 
	 * @return		the world
	 */
	private static char[][] setWorld()
	{
		if (file!= null)
		{
			int numRows = file.nextInt();	//the number of rows
			int numCols = file.nextInt();	//the number of columns
			file.nextLine();
			char worldGrid[][] = new char[numRows][numCols];//the world
			
			//fill each index of the world with the character from map.txt
			for (int row = 0; row < numRows; row++)
			{
				String temp = file.nextLine();
				for (int col = 0; col < numCols; col++)
				{
					worldGrid[row][col] = temp.charAt(col);
				}
			}
			return worldGrid;
		}
		else
		{
			return null;
		}
	}
	
	public boolean isLand(int row, int col)
	{
		if(world[row][col] == GROUND)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isWater(int row, int col)
	{
		if(world[row][col] == WATER)
		{
			return true;
		}
		else
		{
			return false;  
		}
	}
	
	public boolean isEmtpySpace(int row, int col)
	{ 
		return emptySpaces[row][col];
	}
	
	/**
	 * Make a 2D array the same size as the world, and fill it with booleans
	 * saying whether or not that space is empty. 
	 * 
	 * @return		a 2D array of booleans
	 */
	private boolean[][] setEmptySpaces()
	{
		//make an array the same size as the world
		boolean [][] emptySpaces = new boolean[world.length][world[0].length];
		
		//if the space is ground or path, initialize to empty.
		for(int i = 0; i < world.length; i++)
		{
			for(int j = 0; j < world[0].length; j++)
			{
				if(world[i][j] == PATH || world[i][j] == GROUND)
					emptySpaces[i][j] = true;
				else
					emptySpaces[i][j] = false;
			}
		}
		
		//return the array
		return emptySpaces;
	}
	
	/**
	 * Open the request file. Leave file null if the open fails. Method 
	 * courtesy of Mr. Steinberg. Absolutely no original code here.
	 * 
	 * @return the newly opened file, or null if opening failed 
	 */
	private static Scanner openTheFile() 
	{
		Scanner file = null;
		// Open the file.
		try
		{
			file = new Scanner(new File(FILE_NAME)); 
		}
		catch (IOException e) 
		{
			// Something went wrong!
			System.out.println("File error - file not found."); 
		}
		return file; 
	}
	

	
	/**
	 * Draw the world, as a grid.
	 * 
	 * @param g		graphics
	 */
	public void draw(Graphics g)
	{	
		//colors for different types of terrain
		final Color LIGHT_BROWN = new Color(222,184,135);
		final Color DARK_BROWN = new Color(139,69,19);
		
		//draw each terrain spot
		for(int i = 0; i < world.length; i++)
		{
			for(int j = 0; j < world[0].length; j++)
			{
				if(world[i][j] == PATH || world[i][j] == TOWER)
					g.setColor(LIGHT_BROWN);
				else if(world[i][j] == WATER)
					g.setColor(Color.CYAN);
				else if(world[i][j] == GROUND)
					g.setColor(DARK_BROWN);
				g.fillRect(j*WIDTH, i*HEIGHT + Application.WINDOW_OFFSET, WIDTH, HEIGHT);
			}
		}
		
		//draw the grid
		g.setColor(Color.BLACK);
		for(int i = 0; i < world.length; i++)
		{
			for(int j = 0; j < world[0].length; j++)
				g.drawLine(j * WIDTH, i * HEIGHT + OFFSET, 
						j * WIDTH, (i + 1) * HEIGHT + OFFSET);
			g.drawLine(0, i * HEIGHT + OFFSET, 
					world[0].length * WIDTH, i * HEIGHT + OFFSET);
		}
	}
	
	/**
	 * Take in a row and column. Return whether the terrain at that row
	 * and column is both a path and is empty.
	 * 
	 * @param row			the row
	 * @param col			the column
	 * @return				whether the specified spot is empty
	 */
	public boolean isOpenPath(int row, int col)
	{
		//if the row and column is path and is empty, return true. otherwise,
		//return false;
		if(row < 0 || row > world.length || col < 0 || col > world[0].length)
			return false;
		else if(emptySpaces[row][col] && world[row][col] == PATH)
			return true;
		else
			return false;
	}
	
	/**
	 * Take a row and column. Return the terrain type.
	 * 
	 * @param row			the row
	 * @param col			the column
	 * @return				terrain type
	 */
	public char getType(int row, int col)
	{
		return world[row][col];
	}
	
	
	/**
	 * Take a row and column and set whether it is empty or not.
	 * 
	 * @param row			the row
	 * @param col			the column
	 * @param empty			whether it is empty
	 */
	public void setEmptySpaces(int row, int col, boolean empty)
	{
		emptySpaces[row][col] = empty;
	}
	
	public int[] getCoords(int x, int y)
	{
		int [] coords = new int [2];
		for(int row = ROWS - 1; row >= 0; row--)
		{
			if(y < ((row + 1) * HEIGHT + Application.WINDOW_OFFSET))
				coords[0] = row;
		}
		
		for(int col = COLS - 1; col >= 0; col--)
		{
			if(x < ((col + 1) * WIDTH))
				coords[1] = col;
		}
		return coords;
	}
	
	/**
	 * Return the tower's row.
	 */
	public int getTowerRow()
	{
		//find the tower. When found, return the row
		for(int row = 0; row < world.length; row++)
		{
			for(int col = 0; col < world[0].length; col++)
			{
				if(world[row][col] == TOWER)
					return row;
			}
		}
		return -1;
	}
	
	/**
	 * Return the tower's column.
	 */
	public int getTowerCol()
	{
		//find the tower. When found, return the column
		for(int row = 0; row < world.length; row++)
		{
			for(int col = 0; col < world[0].length; col++)
			{
				if(world[row][col] == TOWER)
					return col;
			}
		}
		return -1;
	}
	
	/**
	 * Accessor method.
	 * 
	 * @return		the world
	 */
	public char[][] getWorld()
	{
		return world;
	}
}
