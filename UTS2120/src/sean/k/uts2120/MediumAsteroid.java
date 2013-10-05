package sean.k.uts2120;

public class MediumAsteroid extends Asteroid{
	
	final static float HEIGHT_PERCENT= (float)1/(float)10; 
	final static float WIDTH_PERCENT = HEIGHT_PERCENT;
	final static int DAMAGE = Player.STARTING_HEALTH/5;
	final static int HEALTH= SmallAsteroid.HEALTH*3;
	final static int POINTS = 4;

	public MediumAsteroid(Game theGame, float xPosition, float yPosition) {
		super(theGame, xPosition, yPosition, Game.screenHeight*WIDTH_PERCENT, Game.screenHeight*HEIGHT_PERCENT, DAMAGE, HEALTH, POINTS);

	}

	@Override
	Asteroid copy() {
		return new MediumAsteroid(game,xPos,yPos);
	}

}
