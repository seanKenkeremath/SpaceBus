package sean.k.uts2120;

import java.util.ArrayList;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

public class LevelOne extends Level{

	final static int BACKGROUND_IMAGE_ID= GameEntity.NO_IMAGE;
	final static float DISTANCE_PERCENT = 12f;
	final static float FRACTION_EARTH_SHRINK = 2f;
	final static float EARTH_TRANSLATE_Y = Game.screenHeight*2;
	final static float FRACTION_MOON_EXPAND = 4f;
	final static float MOON_TRANSLATE_Y = Game.screenHeight;
	final static int EARTH_IMAGE_ID = R.drawable.earth;
	final static int MOON_IMAGE_ID = R.drawable.moon;
	ArrayList<RectF> stars;
	final static int NUMBER_STARS = 35;
	final float MAX_STAR_RADIUS_PERCENT = .003f;
	final int MAX_ENEMIES = 20;
	
	

	/*
	 * implementation of Level class.  Travel from Earth to the Moon
	 */
	public LevelOne(Game theGame) {
		super(theGame, BACKGROUND_IMAGE_ID, DISTANCE_PERCENT);
		
	}

	@Override
	void drawBackground(Canvas c) {
		for (RectF star: stars){
			c.drawRect(star,paint);
		}
		
	}
	
	@Override
	public void update(){
		super.update();

	}



	@Override
	boolean isComplete() {
		return traveled>=distance;
	}
	
	@Override
	public void reset(){
		super.reset();

		//better memory management?
		stars = new ArrayList<RectF>();
		stars.clear();
		double radiusMultiplier;
		double xMultiplier;
		double yMultiplier;
		for (int i = 0; i<NUMBER_STARS; i++){
			radiusMultiplier = Math.random();
			xMultiplier = Math.random();
			yMultiplier = Math.random();
			float radius = (float) (radiusMultiplier*MAX_STAR_RADIUS_PERCENT*Game.screenHeight);
			float xCoor = (float) (xMultiplier*Game.screenWidth);
			float yCoor = (float) (yMultiplier*Game.screenHeight+Game.topMarginHeight);
			RectF star = new RectF(xCoor-radius,yCoor-radius,xCoor+radius,yCoor+radius);
			stars.add(star);
		}
		
	}

	@Override
	void buildLevel() {
		
		addEntityToBuild(0f, new BackgroundImage(game, EARTH_IMAGE_ID, Game.screenWidth/2, Game.screenHeight+Game.topMarginHeight, Game.screenWidth, Game.screenWidth, DISTANCE_PERCENT*Game.screenHeight,1f,FRACTION_EARTH_SHRINK, 1f, 0,EARTH_TRANSLATE_Y));
		addEntityToBuild(5f, new BackgroundImage(game, MOON_IMAGE_ID, Game.screenWidth/2, Game.topMarginHeight-Game.screenWidth/2, Game.screenWidth/2, Game.screenWidth/2, (DISTANCE_PERCENT-5f)*Game.screenHeight,1f,FRACTION_MOON_EXPAND, 1f, 0,MOON_TRANSLATE_Y));

		
		addEntityToBuild(2f, new SmallAsteroid(game,Game.screenWidth/3,Game.topMarginHeight));

		addEntityToBuild(4f, new BusStop(game, Game.screenWidth*2/3,Game.topMarginHeight,3));
		
		addEntityToBuild(8f, new BusStop(game, Game.screenWidth*2/3,Game.topMarginHeight,2));
		addEntityToBuild(11f, new BusStop(game, Game.screenWidth*2/3,Game.topMarginHeight,4));
		
		ArrayList<Asteroid> beltAsteroids = new ArrayList<Asteroid>();
		beltAsteroids.add(new SmallAsteroid(game,0f,0f));
		addEntityToBuild(2.8f, new AsteroidBelt(game,true,.2f*Game.screenHeight,AsteroidBelt.THIN_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT), beltAsteroids));
		addEntityToBuild(5f, new AsteroidBelt(game,true,.2f*Game.screenHeight,AsteroidBelt.MEDIUM_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT),beltAsteroids));
		addEntityToBuild(7f, new AsteroidBelt(game,true,.2f*Game.screenHeight,AsteroidBelt.THICK_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT),beltAsteroids));
		addEntityToBuild(10f, new AsteroidBelt(game,false,.2f*Game.screenHeight,AsteroidBelt.MEDIUM_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT),beltAsteroids));
		
		
		
		double numberEnemiesMultiplier = Math.random();
		int numberEnemies = (int) (numberEnemiesMultiplier*MAX_ENEMIES);
		
		double xMultiplier;
		double distanceMultiplier;
		
		Log.d("NUMEN", ""+numberEnemies);
		for (int i = 0; i <numberEnemies; i++){
			xMultiplier = Math.random();
			distanceMultiplier = Math.random();
			
			addEntityToBuild((float) (DISTANCE_PERCENT*distanceMultiplier), new BasicEnemy(game,(float) (Game.screenWidth*xMultiplier),Game.topMarginHeight));
		}
		
	}

	@Override
	String getObjectiveString() {
		return "Objective: "+Math.round(traveled/distance*100) +"%";
	}

	@Override
	Level nextLevel() {
		return new LevelTwo(game);
	}
	

}
