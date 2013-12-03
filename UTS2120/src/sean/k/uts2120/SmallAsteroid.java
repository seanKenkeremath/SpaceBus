package sean.k.uts2120;

public class SmallAsteroid extends Asteroid{

	final static float HEIGHT_PERCENT= (float)1/(float)16; 
	final static float WIDTH_PERCENT = HEIGHT_PERCENT;
	final static int DAMAGE = Player.STARTING_HEALTH/8;
	final static int HEALTH= 10;
	final static int POINTS = 1;
	
	public SmallAsteroid(Game theGame, float xPosition, float yPosition) {
		super(theGame, xPosition, yPosition, Game.screenHeight*WIDTH_PERCENT, Game.screenHeight*HEIGHT_PERCENT,DAMAGE,HEALTH, POINTS);

	}

	@Override
	Asteroid copy() {
		return new SmallAsteroid(game,xPos,yPos);
	}

	@Override
	void createLoot() {
		// TODO Auto-generated method stub
		
	}

}
