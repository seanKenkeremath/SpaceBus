package sean.k.uts2120;

public class LaserShot extends Shot{
	

	Laser laser;
	
	/*
	 * this is an instance of a shot fired by the player.
	 * the player's laser is passed to this object to determine explosion and damage.
	 */
	public LaserShot(Game theGame, Laser theLaser, float xPosition, float yPosition) {
		super(theGame, theLaser.getDamage(), theLaser.getRange(),theLaser.getImageID(), xPosition, yPosition ,theLaser.getWidth(), theLaser.getHeight());		
		laser = theLaser;
	}
	
	@Override
	public void hit(Enemy target){
		super.hit(target);
		laser.impact(xPos, yPos);
	}

}
