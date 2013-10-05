package sean.k.uts2120;

public class Explosion extends Effect{

	final static float HEIGHT_PERCENT= .1f;
	final static float WIDTH_PERCENT = .1f;
	final static int IMAGE_ID = R.drawable.explosion2;
	final static int LIFETIME = 20;
	
	
	/*
	 * the effect that happens when something is destroyed
	 */
	public Explosion(Game theGame, float xPosition, float yPosition) {
		super(theGame, IMAGE_ID, xPosition, yPosition, WIDTH_PERCENT*Game.screenHeight, HEIGHT_PERCENT*Game.screenHeight,LIFETIME);

	}
	

}
