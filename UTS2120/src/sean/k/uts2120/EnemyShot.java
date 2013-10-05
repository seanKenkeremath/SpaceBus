package sean.k.uts2120;

public class EnemyShot extends GameEntity{

	float speed;
	int damage;
	
	/*
	 * an entity that damages the Player based on specified parameters
	 */
	public EnemyShot(Game theGame, int theImageID, float xPosition, float yPosition, float theWidth, float theHeight, float theSpeed, int theDamage) {
		super(theGame, theImageID, xPosition, yPosition, theWidth, theHeight);
		
		speed = theSpeed;
		damage = theDamage;

	}
	

	public int getDamage(){
		return damage;
	}
	
	public float getSpeed(){
		return speed;
	}

}
