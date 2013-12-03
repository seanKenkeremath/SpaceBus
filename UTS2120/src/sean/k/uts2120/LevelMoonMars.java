package sean.k.uts2120;

import java.util.ArrayList;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

public class LevelMoonMars extends Level{

	final static float DISTANCE_PERCENT = 40f;
	ArrayList<RectF> stars;
	final static int MOON_IMAGE_ID = R.drawable.moon;
	final static int MARS_IMAGE_ID = R.drawable.mars;
	final float FRACTION_MOON_SHRINK = .1f;
	final static float MOON_PERSIST_DISTANCE_PERCENT = 18f;
	final float FRACTION_MARS_EXPAND = 1.5f;
	final static int NUMBER_STARS = 65;
	final float MAX_STAR_RADIUS_PERCENT = .004f;
	final int NUMBER_ENEMIES_ZONE_ONE = 40;
	final static float ZONE_ONE_START_DISTANCE_PERCENT = 5.5f;
	final static float ZONE_ONE_END_DISTANCE_PERCENT = 20.5f;	
	/*
	 * implementation of Level class. Travel from Moon to Mars
	 */
	public LevelMoonMars(Game theGame) {
		super(theGame, "Moon to Mars", GameEntity.NO_IMAGE, DISTANCE_PERCENT);
		
	}

	@Override
	void drawBackground(Canvas c) {
		
		for (RectF star: stars){
			c.drawRect(star,paint);
		}
	}

	@Override
	void buildLevel() {
		
		
		
		addEntityToBuild(0f, new BackgroundImage(game, MARS_IMAGE_ID, Game.screenWidth/2, Game.screenHeight*3/4, Game.screenWidth, Game.screenWidth, DISTANCE_PERCENT*Game.screenHeight,.005f, FRACTION_MARS_EXPAND, 10f, 0, 0));
		addEntityToBuild(0f, new BackgroundImage(game, MOON_IMAGE_ID, Game.screenWidth/2, Game.screenHeight+Game.topMarginHeight, Game.screenWidth, Game.screenWidth, MOON_PERSIST_DISTANCE_PERCENT*Game.screenHeight,1f, FRACTION_MOON_SHRINK, 1f, 0,Game.screenHeight/2));

		
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
		
		addEntityToBuild(3f, new AsteroidBelt(game,true,.2f*Game.screenHeight,AsteroidBelt.THIN_DENSITY/2,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT/2), beltAsteroidsMed));
		//addEntityToBuild(5f, new AsteroidBelt(game,false,.15f*Game.screenHeight,AsteroidBelt.MEDIUM_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT/2),beltAsteroidsMixed));
		//addEntityToBuild(6f, new AsteroidBelt(game,true,.2f*Game.screenHeight,AsteroidBelt.THIN_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT),beltAsteroidsSmall));
		//addEntityToBuild(10f, new AsteroidBelt(game,false,.2f*Game.screenHeight,AsteroidBelt.MEDIUM_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_MEDIUM_PERCENT),beltAsteroidsSmall));

		
		addEntityToBuild(4.5f, new BusStop(game, Game.screenWidth*2/3,Game.topMarginHeight,5));

		
		double xMultiplier;
		double distanceMultiplier;
		
		int numberEnemies = (int) (NUMBER_ENEMIES_ZONE_ONE);
		
		

		//zone one start 5.5f
		
		float bugZoneStart = ZONE_ONE_START_DISTANCE_PERCENT;
		float bugZoneEnd = ZONE_ONE_END_DISTANCE_PERCENT;
		float bugZoneDist = bugZoneEnd-bugZoneStart;
		for (int i = 0; i <numberEnemies; i++){
			xMultiplier = Math.random();
			distanceMultiplier = Math.random();
			
			addEntityToBuild((float) (bugZoneStart+bugZoneDist*distanceMultiplier), new BugEnemy(game,(float) (Game.screenWidth*xMultiplier),Game.topMarginHeight));
		}
		
		//zone one end 20.5f
		
		
		addEntityToBuild (22f, new LargeAsteroid(game, (float) (Game.screenWidth*Math.random()), Game.topMarginHeight));
		
		addEntityToBuild (23f, new LargeAsteroid(game, (float) (Game.screenWidth*Math.random()), Game.topMarginHeight));

		
		addEntityToBuild(24f, new BusStop(game, Game.screenWidth/3,Game.topMarginHeight,5));
		
		addEntityToBuild(25f, new AOEEnemy(game,Game.screenWidth/5,Game.topMarginHeight));
		addEntityToBuild(26f, new AOEEnemy(game,Game.screenWidth*4/5,Game.topMarginHeight));

		addEntityToBuild(26.5f, new SilverDrop(game, Game.screenWidth*3/5, Game.topMarginHeight));
		addEntityToBuild(26.55f, new GoldDrop(game, Game.screenWidth*3/5, Game.topMarginHeight));
		addEntityToBuild(26.6f, new SilverDrop(game, Game.screenWidth*3/5, Game.topMarginHeight));
		
		addEntityToBuild(26.55f, new EmeraldDrop(game, Game.screenWidth*2/5, Game.topMarginHeight));

		addEntityToBuild(26.5f, new SilverDrop(game, Game.screenWidth*1/5, Game.topMarginHeight));
		addEntityToBuild(26.55f, new GoldDrop(game, Game.screenWidth*1/5, Game.topMarginHeight));
		addEntityToBuild(26.6f, new SilverDrop(game, Game.screenWidth*1/5, Game.topMarginHeight));
		
		addEntityToBuild(30f, new BusStop(game, Game.screenWidth*3/4,Game.topMarginHeight,6));

		addEntityToBuild(31f, new AOEEnemy(game,Game.screenWidth/2,Game.topMarginHeight));
		addEntityToBuild(32f, new AOEEnemy(game,Game.screenWidth*4/5,Game.topMarginHeight));
		addEntityToBuild(33f, new AOEEnemy(game,Game.screenWidth/4,Game.topMarginHeight));
		addEntityToBuild(34f, new AOEEnemy(game,Game.screenWidth*3/4,Game.topMarginHeight));
		
		addEntityToBuild (36f, new LargeAsteroid(game, (float) (Game.screenWidth*Math.random()), Game.topMarginHeight));
		addEntityToBuild (36.5f, new LargeAsteroid(game, (float) (Game.screenWidth*Math.random()), Game.topMarginHeight));
		addEntityToBuild (37f, new LargeAsteroid(game, (float) (Game.screenWidth*Math.random()), Game.topMarginHeight));
		addEntityToBuild (37.5f, new LargeAsteroid(game, (float) (Game.screenWidth*Math.random()), Game.topMarginHeight));
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
		return new LevelMarsJupiter(game);
	}

}
