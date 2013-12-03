package sean.k.uts2120;

public class SmallIceAsteroid extends Asteroid{

	final static String NAME = "Small Ice Asteroid";
	final static float HEIGHT_PERCENT= (float)1/(float)16; 
	final static float WIDTH_PERCENT = HEIGHT_PERCENT;
	final static int DAMAGE = Player.STARTING_HEALTH/7;
	final static int HEALTH= 10;
	final static int POINTS = 3;
	
	public SmallIceAsteroid(Game theGame, float xPosition, float yPosition) {
		super(theGame, xPosition, yPosition, Game.screenHeight*WIDTH_PERCENT, Game.screenHeight*HEIGHT_PERCENT, DAMAGE, HEALTH, POINTS);

	}

	@Override
	Asteroid copy() {
		return new SmallIceAsteroid(game, xPos,yPos);
	}

	@Override
	void createLoot() {
		// TODO Auto-generated method stub
		
	}

}
