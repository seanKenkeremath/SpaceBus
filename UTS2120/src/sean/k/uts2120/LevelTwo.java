package sean.k.uts2120;

import java.util.ArrayList;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

public class LevelTwo extends Level{

	final static float DISTANCE_PERCENT = 18f;
	ArrayList<RectF> stars;
	final static int MOON_IMAGE_ID = R.drawable.moon;
	final static int MARS_IMAGE_ID = R.drawable.mars;
	final float FRACTION_MOON_SHRINK = .1f;
	final float FRACTION_MARS_EXPAND = 1.5f;
	final static int NUMBER_STARS = 65;
	final float MAX_STAR_RADIUS_PERCENT = .004f;
	
	/*
	 * implementation of Level class. Travel from Moon to Mars
	 */
	public LevelTwo(Game theGame) {
		super(theGame, GameEntity.NO_IMAGE, DISTANCE_PERCENT);
		
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
		addEntityToBuild(0f, new BackgroundImage(game, MOON_IMAGE_ID, Game.screenWidth/2, Game.screenHeight+Game.topMarginHeight, Game.screenWidth, Game.screenWidth, DISTANCE_PERCENT*Game.screenHeight,1f, FRACTION_MOON_SHRINK, 1f, 0,Game.screenHeight/2));

		addEntityToBuild(8f, new BusStop(game, Game.screenWidth*2/3,Game.topMarginHeight,1));
		
		addEntityToBuild (16f, new LargeAsteroid(game, (float) (Game.screenWidth*Math.random()), Game.topMarginHeight));
		addEntityToBuild(14f, new BusStop(game, Game.screenWidth/3,Game.topMarginHeight,5));
		addEntityToBuild(15f, new BusStop(game, Game.screenWidth*3/4,Game.topMarginHeight,6));
		
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
		addEntityToBuild(5f, new AsteroidBelt(game,false,.15f*Game.screenHeight,AsteroidBelt.MEDIUM_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT/2),beltAsteroidsMixed));
		addEntityToBuild(6f, new AsteroidBelt(game,true,.2f*Game.screenHeight,AsteroidBelt.THIN_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_SLOW_PERCENT),beltAsteroidsSmall));
		addEntityToBuild(10f, new AsteroidBelt(game,false,.2f*Game.screenHeight,AsteroidBelt.MEDIUM_DENSITY,Game.calcSpeed(AsteroidBelt.VELOCITY_MEDIUM_PERCENT),beltAsteroidsSmall));

		double xMultiplier;
		double numberEnemiesMultiplier = Math.random();
		int numberEnemies = (int) (numberEnemiesMultiplier*25);
		
		
		double distanceMultiplier;
		
		Log.d("NUMEN", ""+numberEnemies);
		float bugZoneStart = 8.5f;
		float bugZoneEnd = 13.5f;
		float bugZoneDist = bugZoneEnd-bugZoneStart;
		for (int i = 0; i <numberEnemies; i++){
			xMultiplier = Math.random();
			distanceMultiplier = Math.random();
			
			addEntityToBuild((float) (bugZoneStart+bugZoneDist*distanceMultiplier), new BugEnemy(game,(float) (Game.screenWidth*xMultiplier),Game.topMarginHeight));
		}
		
		
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
		return new LevelOne(game);
	}

}
