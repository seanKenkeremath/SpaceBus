package sean.k.uts2120;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;

abstract class Level {

	int backgroundImageID;
	Game game;
	Paint paint;
	Matrix paintMatrix;
	Paint objectivePaint;
	String name;
	
	final static int NO_IMAGE = 0;

	
	final static float OBJECTIVE_TEXT_HEIGHT_PERCENT_MARGIN= .12f;
	
	protected float distance;
	protected float traveled;
	protected float heightRatio;
	protected float widthRatio;
	private ArrayList<GameEntity> tempEntities; //for sorting
	private Queue<GameEntity> levelEntities; //maps percent completion to spawned entities
	private HashSet<GameEntity> allEntities;
	private int totalPassengers;
	final static float TEXT_HEIGHT_PERCENT_MARGIN = .2f;
	
	/*
	 * accelerometer debug values;
	 */
	protected float tiltLastX;
	protected float tiltDeltaX;
	protected float tiltLastY;
	protected float tiltDeltaY;
	protected float tiltLastZ;
	protected float tiltDeltaZ;
	
	
	/*
	 * this abstract class represents a Level or Map within the game.  The methods
	 * that must be implemented are buildLevel() where individual entities and there
	 * locations within the map are specified and getObjectiveString() which returns a 
	 * String that represents whatever goal you must complete and how close you are to
	 * completing it.  The distance you specify for a level is in units of screenHeight.
	 * A value of 1f for distance means that the level is over when you have passed
	 * 1 distance equal to the height of your screen.  This is also the value you enter
	 * during the buildLevel() method.  This unit is converted to pixels at runtime.
	 * This object also holds a resource ID representing a bitmap background if there is one.
	 * If you do not want to use a background bitmap, specify this using the NO_IMAGE 
	 * value.  You must also implement the drawBackgroundMethod() which can use the background
	 * bitmap or not and the NextLevel() method which returns a reference to the next level.  
	 * You also must implement the boolean method isComplete() that contains the conditions
	 * for completion of the Level.
	 */
	public Level(Game theGame, String name, int theBackgroundImageID, float theDistancePercent){
		this.name = name;
		backgroundImageID = theBackgroundImageID;
		game = theGame;
		paint = new Paint();
		paintMatrix = new Matrix();
		
		objectivePaint = new Paint();
		objectivePaint.setTextSize(OBJECTIVE_TEXT_HEIGHT_PERCENT_MARGIN*Game.bottomMarginHeight);
		objectivePaint.setColor(Color.WHITE);
		objectivePaint.setStyle(Style.FILL);
		objectivePaint.setTextAlign(Align.CENTER);
		
		levelEntities = new LinkedList<GameEntity>();
		tempEntities = new ArrayList<GameEntity>();
		allEntities = new HashSet<GameEntity>();
		distance = theDistancePercent *Game.screenHeight;

		reset();
	}
	
	/*
	 * this class is called when a level is first started or is reset.
	 * It determines how to scale the backgroundImage if there is one and 
	 * calls the spawn method on Player.
	 */
	public void initialize(){
		if (game.getBitmap(backgroundImageID)!=null){
		float bMapHeight = game.getBitmap(backgroundImageID).getHeight();
		float bMapWidth = game.getBitmap(backgroundImageID).getWidth();
		
		
		heightRatio = Game.screenHeight/bMapHeight;
		widthRatio = Game.screenWidth/bMapWidth;
		paintMatrix.postScale(widthRatio, heightRatio);
		paintMatrix.postTranslate(0, Game.topMarginHeight);
		} else{
			heightRatio = 0;
			widthRatio = 0;
		}
		
		game.getPlayer().spawn();
	}

	abstract void drawBackground(Canvas c); //draws background
	
	abstract void buildLevel(); //addEntities that will appear using addEntityToBuild();

	/*
	 * returns textual representation of objective that will be printed.
	 * This is called separately at every iteration of the main loop in GameThread.
	 */
	abstract String getObjectiveString(); 
	/*
	 * return a reference to the next Level
	 */
	abstract Level nextLevel();
	
	/*
	 * called at every iteration of GameThread during UpdateGameState().
	 * By default, this method updates the distance traveled and spawns entities
	 * from the spawn queue if you have reached their spawn point.
	 */
	public void update(){
		traveled = traveled+game.getSpeed(); 
		spawnEntities();
	}
	
	public String getName(){
		return name;
	}
	
	public int getTotalPassengers(){
		return totalPassengers;
	}
	
	public void incTotalPassengers(int amount){
		totalPassengers = totalPassengers+amount;
	}
	
	/*
	 * adds the specified entity to an array of temporary entities which
	 * will later be sorted by spawn point and put into the spawn queue.
	 * This also adds the entity to the Set of all entities used the cache the
	 * Level.
	 */
	protected void addEntityToBuild(float distanceInScreenHeights, GameEntity addEntity){
		addEntity.setSpawnPosition(distanceInScreenHeights*Game.screenHeight);
		tempEntities.add(addEntity);
		allEntities.add(addEntity);
	}
	
	
	/*
	 * this is called in the update() method.
	 * It looks at the next entity in the spawn queue
	 * which is sorted by spawn distance and sees if you have
	 * reached that point.  If you have it pops it out of the queue.
	 */
	private void spawnEntities(){
		//create enemies, events, etc
		if (levelEntities.peek()==null){
			return;
		}
		
		if (traveled>=levelEntities.peek().getSpawnPosition()){
			game.addEntity(levelEntities.poll());
		}

	}
	/*
	 * conditions for Level completion
	 */
	abstract boolean isComplete();
	
	public int getBackgroundImageID(){
		return backgroundImageID;
	}
	
	/*
	 * this condition is checked upon Level completion to
	 * see if the All Passengers Bonus should be awarded.
	 */
	public boolean allPassengers(){
		return game.getPlayer().getPassengerCount() == totalPassengers && totalPassengers>0 ;
	}
	
	/*
	 * called upon completion of level.  This clears relevant objects and
	 * awards bonuses.
	 */
	public void complete(){
		game.clearAnimations();
		if(allPassengers()){
			game.incScore(Game.ALL_PASSENGER_BONUS);
		}
	}
	
	public float getDistance(){
		return distance;
	}
	
	public float getTraveled(){
		return traveled;
	}
	
	
	public Paint getPaint(){
		return paint;
	}
	
	private void clearSpawnQueue(){
		levelEntities.clear();
		tempEntities.clear();
		allEntities.clear();
	}
	
	/*
	 * clears everything and rebuilds the level.
	 */
	public void reset(){
		traveled = 0;
		totalPassengers = 0;

		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(TEXT_HEIGHT_PERCENT_MARGIN*Game.bottomMarginHeight);
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.FILL);
		clearSpawnQueue();
		buildLevel();
		Collections.sort(tempEntities);
		for (GameEntity addEntity: tempEntities){
			levelEntities.add(addEntity);
		}
	}
	
	
	void drawObjective(Canvas c) {

		c.drawText(getObjectiveString(),Game.screenWidth/2,Game.totalHeight-Game.bottomMarginHeight/2, objectivePaint);
		
	}
	
	
	public void setPaint(Paint newPaint){
		paint = newPaint;
	}
	
	/*
	 * returns the set of all entities used to cache images within the level
	 */
	public HashSet<GameEntity> getAllEntities(){
		return allEntities;
	}
	
	public void debugAccelerometer(float lastX, float deltX, float lastY, float deltY, float lastZ, float deltZ){
		tiltLastX = lastX;
		tiltDeltaX = deltX;
		tiltLastY = lastY;
		tiltDeltaY = deltY;
		tiltLastZ = lastZ;
		tiltDeltaZ = deltZ;
	}
}


