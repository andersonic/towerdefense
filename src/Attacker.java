/******************************************************************************
 * Attacker Class
 * 	Superclass for all attackers. Has data from Shooter, as well as an int that
 * represents time per move for the attacker. Includes a constructor, a move
 * method that has the attacker attempt to move closer to the tower, a target
 * priority, which is roughly based on how close it is to the tower. There is
 * also an accessor method for rate of movement.
 *
 *****************************************************************************/
public abstract class Attacker extends Shooter
{
	final int TPM; //time per move
	
	/**
	 * Constructor. Take in all the data for an attacker and assign it to the
	 * appropriate variables.
	 * 
	 * @param health
	 * @param attack
	 * @param defense
	 * @param rate
	 * @param TPS				time per shot
	 * @param TPM				time per move
	 * @param row
	 * @param col
	 */
	public Attacker(int health, int attack, int defense, int tpf, int range, 
			int tpm, int row, int col)
	{
		super(health, attack, defense, tpf, range, row, col);
		TPM = tpm;
	}
	
	/**
	 * Target priority is based on proximity to the tower. Since the tower
	 * is placed on the left, column is used as a proxy for distance from
	 * tower.
	 */
	public int targetPriority()
	{
		return super.getCol();
	}
	
	/**
	 * Attempt to move closer to the tower. Prioritize a horizontal move if
	 * possible. Do not move if the tower is in range.
	 * 
	 * @param world			the world in which to move
	 * @param tower			the tower to approach
	 */
	public void move(World world, Tower tower)
	{
		//variables relating to position
		int towerRow = tower.getRow();
		int towerCol = tower.getCol();
		int myRow = super.getRow();
		int myCol = super.getCol();
		
		//vacate current location
		world.setEmptySpaces(myRow, myCol, true);
		
		//set new location
		if(super.inRange(tower))
			;
		else if(myCol - towerCol > 0 && world.isOpenPath(myRow, myCol - 1))
			myCol--;
		else if(myCol - towerCol < 0 && world.isOpenPath(myRow, myCol + 1))
			myCol++;
		else if(myRow - towerRow < 0 && world.isOpenPath(myRow + 1, myCol))
			myRow++;
		else if(myRow - towerRow > 0 && world.isOpenPath(myRow - 1, myCol))
			myRow--;

		//actually move
		super.setCoords(myRow, myCol);
		
		//tell the world that new location is now occupied
		world.setEmptySpaces(myRow, myCol, false);
	}
	
	/**
	 * Getter
	 * 
	 * @return			time per move
	 */
	public int getTimePerMove()
	{
		return TPM;
	}
}
