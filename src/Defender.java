/******************************************************************************
 * Defender Class
 * 	Superclass for all defensive shooters, except the tower. In addition to 
 * Shooter data, all defenders have a cost and a terrain type. Contains a
 * constructor that takes in all data and initializes variables, and has a
 * target priority. For lack of a better way to do it, defender target priority
 * is calculated in the same way as attacker target priority.
 *******************************************************************************/
public abstract class Defender extends Shooter
{
	public final int COST;	//how much it costs to build
	public final char TYPE;	//what terrain type a defender can be built on

	/**
	 * Initialize variables
	 * 
	 * @param health
	 * @param attack
	 * @param defense
	 * @param TPS			time per shot
	 * @param range
	 * @param cost
	 * @param type
	 * @param row
	 * @param col
	 */
	public Defender(int health, int attack, int defense, int TPS, int range, 
			int cost, char type, int row, int col)
	{
		super(health, attack, defense, TPS, range, row, col);
		COST = cost;
		TYPE = type;
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
	 * Return true if type is the same as TYPE. Otherwise, return false.
	 * 
	 * @param type		the type to check
	 * @return			whether the given type is the same as this defender's
	 * 					terrain type
	 */
	//public abstract boolean typeMatch(char type);
}
