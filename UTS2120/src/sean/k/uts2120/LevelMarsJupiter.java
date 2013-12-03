package sean.k.uts2120;

import java.util.ArrayList;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

public class LevelMarsJupiter extends Level{

	final static float DISTANCE_PERCENT = 100f;
	ArrayList<RectF> stars;
	final static int MARS_IMAGE_ID = R.drawable.mars;
	
	final static float FRACTION_MARS_SHRINK = 2f;
	final static float MARS_TRANSLATE_X = Game.screenWidth*-3;
	final static float MARS_TRANSLATE_Y = Game.screenHeight/2;
	final static float MARS_PERSIST_PERCENT = 30f;

	
	final static int JUPITER_IMAGE_ID = R.drawable.jupiter;
	final float FRACTION_JUPITER_EXPAND = 1.2f;
	final static int NUMBER_STARS = 65;
	final static int NUMBER_LARGE_ASTEROIDS = 30;
	final float MAX_STAR_RADIUS_PERCENT = .004f;

	final int NUMBER_ENEMIES_ZONE_ONE = 20;
	final static float ZONE_ONE_START_DISTANCE_PERCENT = 25f;
	final static float ZONE_ONE_END_DISTANCE_PERCENT = 40f;
	
	final int NUMBER_MED_ASTEROIDS_ZONE_TWO = 25;
	final int NUMBER_SMALL_ASTEROIDS_ZONE_TWO = 50;
	final static float ZONE_TWO_START_DISTANCE_PERCENT = 44f;
	final static float ZONE_TWO_END_DISTANCE_PERCENT = 75f;
	
	final int NUMBER_ENEMIES_ZONE_THREE= 30;
	final static float ZONE_THREE_START_DISTANCE_PERCENT = 80f;
	final static float ZONE_THREE_END_DISTANCE_PERCENT = 95f;


	
	public LevelMarsJupiter(Game theGame) {
		super(theGame, "Mars to Jupiter", GameEntity.NO_IMAGE, DISTANCE_PERCENT);
		
	}

	@Override
	void drawBackground(Canvas c) {
		
		for (RectF star: stars){
			c.drawRect(star,paint);
		}
	}

	@Override
	void buildLevel() {
		
		
		double xMultiplier;
		double distanceMultiplier;
		
		
		addEntityToBuild(0f, new BackgroundImage(game, MARS_IMAGE_ID, 0, Game.screenHeight+Game.topMarginHeight, Game.screenWidth, Game.screenWidth, MARS_PERSIST_PERCENT*Game.screenHeight,1f,FRACTION_MARS_SHRINK, 1f, MARS_TRANSLATE_X,MARS_TRANSLATE_Y));

		addEntityToBuild(0f, new BackgroundImage(game, JUPITER_IMAGE_ID, Game.screenWidth/2, Game.screenHeight*3/4, Game.screenWidth, Game.screenWidth, DISTANCE_PERCENT*Game.screenHeight,.005f, FRACTION_JUPITER_EXPAND, 20f, 0, 0));

		int numberAsteroids = (int) (NUMBER_LARGE_ASTEROIDS);
		
		Log.d(GameActivity.DEBUG,"Spawning Large Asteroids.  Amount = "+ numberAsteroids);
		for (int i = 0; i <numberAsteroids; i++){
			xMultiplier = Math.random();
			distanceMultiplier = Math.random();
			
			addEntityToBuild((float) (DISTANCE_PERCENT*distanceMultiplier), new LargeAsteroid(game,(float) (Game.screenWidth*xMultiplier),Game.topMarginHeight));
		}
		
		
		
		ArrayList<Asteroid> beltAsteroidsMixed = new ArrayList<Asteroid>();
		ArrayList<Asteroid> beltAsteroidsSmall = new ArrayList<Asteroid>();
		ArrayList<Asteroid> beltAsteroidsMed = new ArrayList<Asteroid>();
		SmallAsteroid small = new SmallAsteroid(game,0f,0f);
		MediumAsteroid med = new MediumAsteroid(game,0f,0f);
		beltAsteroidsSmall.add(small);
		beltAsteroidsMixed.add(small);
		beltAsteroidsMixed.add(small);
		beltAsteroidsMixed.add(med);
		beltAsteroidsMed.add(med);
		
		addEntityToBuild(3f, new BusStop(game, Game.screenWidth/3,Game.topMarginHeight,7));

		
		addEntityToBuild(4f, new AsteroidBelt(game,true,.2f*Game.screenHeight,AsteroidBelt.THIN_DENSITY/2,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT/2), beltAsteroidsMed));
		addEntityToBuild(6f, new AsteroidBelt(game,false,.15f*Game.screenHeight,AsteroidBelt.MEDIUM_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT/2),beltAsteroidsMixed));
		addEntityToBuild(8f, new AsteroidBelt(game,true,.2f*Game.screenHeight,AsteroidBelt.THIN_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT),beltAsteroidsSmall));
		addEntityToBuild(12f, new AsteroidBelt(game,false,.2f*Game.screenHeight,AsteroidBelt.MEDIUM_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_MEDIUM_PERCENT),beltAsteroidsSmall));
		
		addEntityToBuild(14f, new BusStop(game, Game.screenWidth*3/4,Game.topMarginHeight,3));
		
		addEntityToBuild(20f, new AsteroidBelt(game,true,.2f*Game.screenHeight,AsteroidBelt.THIN_DENSITY/2,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT/2), beltAsteroidsMed));
		addEntityToBuild(20.5f, new AsteroidBelt(game,false,.15f*Game.screenHeight,AsteroidBelt.MEDIUM_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT/2),beltAsteroidsMixed));
		addEntityToBuild(20.8f, new AsteroidBelt(game,false,.3f*Game.screenHeight,AsteroidBelt.THIN_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT/2),beltAsteroidsSmall));

		
		addEntityToBuild(20.3f, new GoldDrop(game, Game.screenWidth/6, Game.topMarginHeight));
		addEntityToBuild(20.4f, new SilverDrop(game, Game.screenWidth*5/6, Game.topMarginHeight));
		addEntityToBuild(20.1f, new SilverDrop(game, Game.screenWidth*5/6, Game.topMarginHeight));

		
		addEntityToBuild(23f, new BusStop(game, Game.screenWidth/3,Game.topMarginHeight,2));

		//25f zone one start
		
		int numberEnemiesZoneOne = (int) (NUMBER_ENEMIES_ZONE_ONE);
		
		//Log.d(GameActivity.DEBUG, "Number Enemies = "+numberEnemies);
		float bugZoneOneStart = ZONE_ONE_START_DISTANCE_PERCENT;
		float bugZoneOneEnd = ZONE_ONE_END_DISTANCE_PERCENT;
		float bugZoneDist = bugZoneOneEnd-bugZoneOneStart;
		for (int i = 0; i <numberEnemiesZoneOne; i++){
			xMultiplier = Math.random();
			distanceMultiplier = Math.random();
			
			addEntityToBuild((float) (bugZoneOneStart+bugZoneDist*distanceMultiplier), new BugEnemy(game,(float) (Game.screenWidth*xMultiplier),Game.topMarginHeight));
		}
		
		
		//40f zone one end
		
		addEntityToBuild(41f, new AsteroidBelt(game,true,.2f*Game.screenHeight,AsteroidBelt.THIN_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT),beltAsteroidsSmall));
		addEntityToBuild(42f, new AsteroidBelt(game,false,.2f*Game.screenHeight,AsteroidBelt.MEDIUM_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_MEDIUM_PERCENT),beltAsteroidsSmall));
		
		
		addEntityToBuild(42.5f, new SilverDrop(game, Game.screenWidth/2, Game.topMarginHeight));
		addEntityToBuild(42.6f, new SilverDrop(game, Game.screenWidth/2+Game.screenWidth/32, Game.topMarginHeight));
		addEntityToBuild(42.6f, new SilverDrop(game, Game.screenWidth/2-Game.screenWidth/32, Game.topMarginHeight));
		addEntityToBuild(42.7f, new SilverDrop(game, Game.screenWidth/2+Game.screenWidth/16, Game.topMarginHeight));
		addEntityToBuild(42.7f, new SilverDrop(game, Game.screenWidth/2-Game.screenWidth/16, Game.topMarginHeight));
		addEntityToBuild(42.8f, new SilverDrop(game, Game.screenWidth/2+Game.screenWidth/8, Game.topMarginHeight));
		addEntityToBuild(42.8f, new EmeraldDrop(game, Game.screenWidth/2, Game.topMarginHeight)); //center
		addEntityToBuild(42.8f, new SilverDrop(game, Game.screenWidth/2-Game.screenWidth/8, Game.topMarginHeight));
		addEntityToBuild(42.9f, new SilverDrop(game, Game.screenWidth/2+Game.screenWidth/16, Game.topMarginHeight));
		addEntityToBuild(42.9f, new SilverDrop(game, Game.screenWidth/2-Game.screenWidth/16, Game.topMarginHeight));
		addEntityToBuild(43f, new SilverDrop(game, Game.screenWidth/2+Game.screenWidth/32, Game.topMarginHeight));
		addEntityToBuild(43f, new SilverDrop(game, Game.screenWidth/2-Game.screenWidth/32, Game.topMarginHeight));
		addEntityToBuild(43.1f, new SilverDrop(game, Game.screenWidth/2, Game.topMarginHeight));
		
		addEntityToBuild(42.9f, new BusStop(game, Game.screenWidth/5,Game.topMarginHeight,3));

		
		//44f zone two start
		
		int numberMedAsteroids = (int) (NUMBER_MED_ASTEROIDS_ZONE_TWO);
		int numberSmallAsteroids = (int) (NUMBER_SMALL_ASTEROIDS_ZONE_TWO);
		
		//Log.d(GameActivity.DEBUG, "Number Enemies = "+numberEnemies);
		float astZoneStart = ZONE_TWO_START_DISTANCE_PERCENT;
		float astZoneEnd = ZONE_TWO_END_DISTANCE_PERCENT;
		float astZoneDist = astZoneEnd-astZoneStart;
		for (int i = 0; i <numberMedAsteroids; i++){
			xMultiplier = Math.random();
			distanceMultiplier = Math.random();
			
			addEntityToBuild((float) (astZoneStart+astZoneDist*distanceMultiplier), new MediumAsteroid(game,(float) (Game.screenWidth*xMultiplier),Game.topMarginHeight));
		}
		for (int i = 0; i <numberSmallAsteroids; i++){
			xMultiplier = Math.random();
			distanceMultiplier = Math.random();
			
			addEntityToBuild((float) (astZoneStart+astZoneDist*distanceMultiplier), new SmallAsteroid(game,(float) (Game.screenWidth*xMultiplier),Game.topMarginHeight));
		}
		
		
		//75f zone two end

		//80f zone three start
		
		int numberEnemiesZoneThree = (int) (NUMBER_ENEMIES_ZONE_THREE);
		
		//Log.d(GameActivity.DEBUG, "Number Enemies = "+numberEnemies);
		float bugZoneThreeStart = ZONE_THREE_START_DISTANCE_PERCENT;
		float bugZoneThreeEnd = ZONE_THREE_END_DISTANCE_PERCENT;
		float bugZoneThreeDist = bugZoneThreeEnd-bugZoneThreeStart;
		for (int i = 0; i <numberEnemiesZoneThree; i++){
			xMultiplier = Math.random();
			distanceMultiplier = Math.random();
			
			addEntityToBuild((float) (bugZoneThreeStart+bugZoneThreeDist*distanceMultiplier), new BugEnemy(game,(float) (Game.screenWidth*xMultiplier),Game.topMarginHeight));
		}
		
		//95f zone three end
		

	}

	@Override
	public void reset(){
		super.reset();
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
	boolean isComplete() {
		return traveled>=distance;
	}

	@Override
	String getObjectiveString() {
		return "Objective: "+Math.round(traveled/distance*100) +"%";
	}

	@Override
	Level nextLevel() {
		return null;
	}

}
