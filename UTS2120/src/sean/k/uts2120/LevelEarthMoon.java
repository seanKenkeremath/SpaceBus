package sean.k.uts2120;

import java.util.ArrayList;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

public class LevelEarthMoon extends Level{

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
	final int MAX_ASTEROIDS = 20;
	final int MAX_ENEMIES = 6;
	
	

	/*
	 * implementation of Level class.  Travel from Earth to the Moon
	 */
	public LevelEarthMoon(Game theGame) {
		super(theGame, "Earth to Moon",BACKGROUND_IMAGE_ID, DISTANCE_PERCENT);
		
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

		
		addEntityToBuild(4f, new BusStop(game, Game.screenWidth*2/3,Game.topMarginHeight,3));
		
		addEntityToBuild(8.2f, new BusStop(game, Game.screenWidth/3,Game.topMarginHeight,2));
		addEntityToBuild(11f, new BusStop(game, Game.screenWidth*2/3,Game.topMarginHeight,4));
		
		
		addEntityToBuild(8f, new GoldDrop(game, Game.screenWidth*3/4, Game.topMarginHeight));
		addEntityToBuild(8.1f, new GoldDrop(game, Game.screenWidth*3/4+Game.screenWidth/32, Game.topMarginHeight));
		addEntityToBuild(8.2f, new GoldDrop(game, Game.screenWidth*3/4+Game.screenWidth/16, Game.topMarginHeight));
		addEntityToBuild(8.3f, new GoldDrop(game, Game.screenWidth*3/4+Game.screenWidth/8, Game.topMarginHeight));
		addEntityToBuild(8.4f, new GoldDrop(game, Game.screenWidth*3/4+Game.screenWidth/16, Game.topMarginHeight));
		addEntityToBuild(8.5f, new GoldDrop(game, Game.screenWidth*3/4+Game.screenWidth/32, Game.topMarginHeight));
		addEntityToBuild(8.6f, new GoldDrop(game, Game.screenWidth*3/4, Game.topMarginHeight));


		

		ArrayList<Asteroid> beltAsteroids = new ArrayList<Asteroid>();
		beltAsteroids.add(new SmallAsteroid(game,0f,0f));

		addEntityToBuild(6f, new AsteroidBelt(game,true,.2f*Game.screenHeight,AsteroidBelt.THICK_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT),beltAsteroids));
		addEntityToBuild(6f, new SilverDrop(game, Game.screenWidth/2, Game.topMarginHeight));
		addEntityToBuild(6f, new SilverDrop(game, Game.screenWidth/4, Game.topMarginHeight));
		addEntityToBuild(6f, new SilverDrop(game, Game.screenWidth*3/4, Game.topMarginHeight));

		
		
		/*
		addEntityToBuild(2.8f, new AsteroidBelt(game,true,.2f*Game.screenHeight,AsteroidBelt.THIN_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT), beltAsteroids));
		addEntityToBuild(5f, new AsteroidBelt(game,true,.2f*Game.screenHeight,AsteroidBelt.MEDIUM_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT),beltAsteroids));
		addEntityToBuild(7f, new AsteroidBelt(game,true,.2f*Game.screenHeight,AsteroidBelt.THICK_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT),beltAsteroids));
		addEntityToBuild(10f, new AsteroidBelt(game,false,.2f*Game.screenHeight,AsteroidBelt.MEDIUM_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT),beltAsteroids));
		*/
		
		
		
		
		double numberEnemiesMultiplier = Math.random();
		int numberEnemies = (int) (numberEnemiesMultiplier*MAX_ENEMIES);
		
		double numberAsteroidsMultiplier = Math.random();
		int numberAsteroids = (int) (numberAsteroidsMultiplier*MAX_ASTEROIDS);
		
		double xMultiplier;
		double distanceMultiplier;
		
		Log.d(GameActivity.DEBUG,"Spawning Enemies.  Amount = "+ numberEnemies);
		for (int i = 0; i <numberEnemies; i++){
			xMultiplier = Math.random();
			distanceMultiplier = Math.random();
			
			addEntityToBuild((float) (DISTANCE_PERCENT*distanceMultiplier), new BasicEnemy(game,(float) (Game.screenWidth*xMultiplier),Game.topMarginHeight));
		}
		
		Log.d(GameActivity.DEBUG,"Spawning Asteroids.  Amount = "+ numberAsteroids);
		for (int i = 0; i <numberAsteroids; i++){
			xMultiplier = Math.random();
			distanceMultiplier = Math.random();
			
			addEntityToBuild((float) (DISTANCE_PERCENT*distanceMultiplier), new SmallAsteroid(game,(float) (Game.screenWidth*xMultiplier),Game.topMarginHeight));
		}
		
	}

	@Override
	String getObjectiveString() {
		return "Objective: "+Math.round(traveled/distance*100) +"%";
	}

	@Override
	Level nextLevel() {
		return new LevelMoonMars(game);
	}
	

}
