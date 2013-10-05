package sean.k.uts2120;

public class DamageExplosion extends Shot{
	
	final static int EXPLOSION_DURATION = (int)(200f/Game.THREAD_WAIT_TIME);
	final static int IMAGE_ID = R.drawable.explosion1;
	float radius;
	float power;
	int duration;
	boolean damageDealt;


	/*
	 * this is the explosion created by your cannon when it hits something.
	 * The damage is dealt accross its entire radius only for 1 frame.
	 */
	public DamageExplosion(Game theGame, int theDamage, float theRadius, float xPosition,
			float yPosition) {
		super(theGame, Laser.EXPLOSION_DAMAGE, Weapon.RANGE_INFINITE, IMAGE_ID,xPosition,yPosition,theRadius*2, theRadius*2);
		
		duration = EXPLOSION_DURATION;
		power = theDamage;
		radius = theRadius;
		damageDealt = false;
	}
	
	@Override
	protected void updateAge(){
		super.updateAge();
		if (age>1){
			damageDealt = true;
		}
		if (age>duration){
			remove = true;
		}
	}
	
	@Override
	public void update(){
		updateAge();
		updateX();
		updateY();
		updateHitBox();
		checkBounds();
	}
	

	@Override
	public void hit(Enemy target){

		if (damageDealt){
			return;
		}
		target.decHealth((int) power);
		
	}

}
