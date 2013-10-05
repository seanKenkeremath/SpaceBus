package sean.k.uts2120;

public class Shot extends GameEntity{
	
	int damage;
	int range;
	int distance;


	/*
	 * an entity fired by a Weapon.  Each instance holds a reference to that Weapon
	 * which can obtain parameters.
	 */
	public Shot(Game theGame, int theDamage, int theRange, int theImageID,float xPosition, float yPosition,float theWidth, float theHeight) {
		super(theGame, theImageID, xPosition, yPosition ,theWidth , theHeight);
		
		damage = theDamage;
		range = theRange;
		distance = 0;

	}

	
	@Override
	public void update(){
		super.update();
		
		setY(yPos+ (game.getSpeed()*-1)); //cancel out GameEntity's velocity to mimic ship moving forward
		
		distance++;

		if (distance>range && range!=Weapon.RANGE_INFINITE){
			remove = true;
		}
		
		
	}
	

	
	public void hit(Enemy target){
		target.decHealth(damage);
		setRemove(true);
	}

}
