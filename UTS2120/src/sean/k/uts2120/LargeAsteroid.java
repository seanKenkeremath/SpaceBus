package sean.k.uts2120;

import android.graphics.Rect;

public class LargeAsteroid extends Asteroid{

	final static float HEIGHT_PERCENT= (float)1/(float)5;
	final static float WIDTH_PERCENT = HEIGHT_PERCENT;
	final static int DAMAGE= Player.STARTING_HEALTH/3;
	final static int HEALTH = SmallAsteroid.HEALTH*4;
	final static int POINTS = 10;
	
	public LargeAsteroid(Game theGame, float xPosition, float yPosition) {
		super(theGame, xPosition, yPosition, Game.screenHeight*WIDTH_PERCENT, Game.screenHeight*HEIGHT_PERCENT, DAMAGE, HEALTH, POINTS);
		hitBox.clear();
		Rect rightSide = new Rect((int)xPos,(int)(yPos-height/4),(int)(xPos+width/2),(int)(yPos+height/2)); //l,t,r,b
		Rect leftSide = new Rect((int)(xPos-width/2),(int)(yPos-2*height/5),(int)(xPos+width/4),(int)(yPos+height/4));
		hitBox.add(rightSide);
		hitBox.add(leftSide);
	}

	protected void updateHitBox(){ 
		

		hitBox.get(0).set((int)xPos,(int)(yPos-height/4),(int)(xPos+width/2),(int)(yPos+height/2));
		hitBox.get(1).set((int)(xPos-width/2),(int)(yPos-2*height/5),(int)(xPos+width/4),(int)(yPos+height/4));
		
	}

	@Override
	Asteroid copy() {
		return new LargeAsteroid(game, xPos, yPos);
	}

	@Override
	public void createLoot() {
		game.addEntity(new GoldDrop(game, xPos, yPos));
	}
	
	
}
