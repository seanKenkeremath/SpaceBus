package sean.k.uts2120;
import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.Log;
import android.util.SparseArray;





public class Game{
	

	
	Player player;
	
	Level currentLevel;
	
	private int score;
	private int gold;
	private float speed;


	
	int comboBonusTimer;
	
	private SparseArray<Bitmap> bitmapCache;
	protected ArrayList<GameAnimation> animations;
	private ArrayList<GameEntity> entities;
	private ArrayList<Asteroid> asteroids;
	private ArrayList<Effect> effects;
	private ArrayList<Enemy> enemies;
	private ArrayList<Shot> laserShots;
	private ArrayList<PickUp> pickUps;
	private ArrayList<EnemyShot> enemyShots;
	private ArrayList<BackgroundImage> backgroundEntities;
	private ArrayList<GameEntity> miscEntities;
	
	private ArrayList<GameEntity> pendingEntities;
	private ArrayList<GameEntity> tempPendingEntities;
	
	private int scoreMultiplier;

	//Touch events do not corresponding correctly if the canvas does not take up the entire screen.  have to adjust touch.getCoordinates() for canvas view
	public static float screenWidth;
	public static float topMarginHeight;
	public static float screenHeight;
	public static float bottomMarginHeight;
	public static float totalHeight;  //screen+margin;
	
	
	final static int ALL_PASSENGER_BONUS = 100;  //DPM bonus received when all passengers are picked up
	
	final static int COMBO_CHAIN_LENGTH = (int)(Math.ceil(20f/Game.THREAD_WAIT_TIME)) + 1;  //the number of frames the combo bonus lasts
	
	//screenHeight Percent per frame
	final static float VELOCITY_SLOW_PERCENT =.05f/Game.THREAD_WAIT_TIME;
	final static float VELOCITY_MEDIUM_PERCENT = .18f/Game.THREAD_WAIT_TIME;
	final static float VELOCITY_FAST_PERCENT = .3f/Game.THREAD_WAIT_TIME;
	
	final static float GAME_SPEED_NORMAL_PERCENT = .06f/Game.THREAD_WAIT_TIME; //the standard speed in which the bus moves forwards relative to screenHeight
	
	//defined upon creation of Game (dependent on screenHeight)
	public static float velocitySlow;
	public static float velocityMed;
	public static float velocityFast;
	public static float tiltSensitivity;
	public static float gameSpeedNormal;

	//the percent of the screen that each margin takes up
	final static float TOP_MARGIN_PERCENT = .1f;
	final static float BOTTOM_MARGIN_PERCENT = .15f;
	
	
	
	final static float THREAD_WAIT_TIME = 10f; //the amount of time that the gamethread waits
	final static float TILT_SENSITIVITY_PERCENT_WIDTH_PER_G = .01f;  //how much the velocity changes based on the accelerometer in G's
	
	// UI Parameters
	final static float HEALTH_BAR_WIDTH_PERCENT_WIDTH = (float)1/(float)12;
	final static float HEALTH_BAR_HEIGHT_PERCENT = .33f;
	final static float BOOST_BAR_WIDTH_PERCENT_WIDTH = (float)1/(float)32;
	final static float BOOST_BAR_HEIGHT_PERCENT = HEALTH_BAR_HEIGHT_PERCENT;
	final static float GOLD_TEXT_HEIGHT_PERCENT_MARGIN= .12f;
	final static float SCORE_TEXT_HEIGHT_PERCENT_MARGIN_TOP=.4f;
	final static float SCORE_MULTIPLIER_TEXT_HEIGHT_PERCENT_MARGIN_TOP = .5f;
	
	Paint healthPaint;
	Paint boostPaint;
	Paint marginPaint;
	Paint scorePaint;
	Paint scoreMultiplierPaint;
	Paint goldPaint;
	Paint passengerPaint;
	
	
	float healthWidth;
	float healthLength;
	float boostWidth;
	float boostLength;
	
	
	
	final static int UVA_ORANGE = 0xffffcc00;
	final static int UVA_BLUE = 0xff330099;
	
	private long upTime;
	final long START_TIME = 0L;
		
	/*
	 * this is the object that holds all relevant state
	 * information about the game. This is the class 
	 * through which the thread communicates with the
	 * rest of the game.  The Bitmap Cache is also handled
	 * in this class.
	 */
	public Game(){
		
		effects = new ArrayList<Effect>();
		animations = new ArrayList<GameAnimation>();
		bitmapCache = new SparseArray<Bitmap>();
		entities = new ArrayList<GameEntity>();
		laserShots = new ArrayList<Shot>();
		asteroids = new ArrayList<Asteroid>();
		enemies = new ArrayList<Enemy>();
		pickUps = new ArrayList<PickUp>();
		enemyShots = new ArrayList<EnemyShot>();
		backgroundEntities = new ArrayList<BackgroundImage>();
		miscEntities = new ArrayList<GameEntity>();
		
		pendingEntities = new ArrayList<GameEntity>();
		tempPendingEntities = new ArrayList<GameEntity>();
		
	}
	

	public void initialize(){
		
		/*
		 * this method is called every time the game is started or restarted
		 */
		upTime = START_TIME;
		
		goldPaint = new Paint();
		goldPaint.setTextSize(GOLD_TEXT_HEIGHT_PERCENT_MARGIN*Game.bottomMarginHeight);
		goldPaint.setColor(Color.WHITE);
		goldPaint.setStyle(Style.FILL);
		goldPaint.setTextAlign(Align.CENTER);
		
		passengerPaint = new Paint();
		passengerPaint.setTextSize(GOLD_TEXT_HEIGHT_PERCENT_MARGIN*Game.bottomMarginHeight);
		passengerPaint.setColor(Color.WHITE);
		passengerPaint.setStyle(Style.FILL);
		passengerPaint.setTextAlign(Align.CENTER);
		
		goldPaint = new Paint();
		goldPaint.setTextSize(GOLD_TEXT_HEIGHT_PERCENT_MARGIN*Game.bottomMarginHeight);
		goldPaint.setColor(Color.WHITE);
		goldPaint.setStyle(Style.FILL);
		goldPaint.setTextAlign(Align.CENTER);
		
		scorePaint = new Paint();
		scorePaint.setTextSize(SCORE_TEXT_HEIGHT_PERCENT_MARGIN_TOP*Game.topMarginHeight);
		scorePaint.setColor(Color.WHITE);
		scorePaint.setStyle(Style.FILL);
		scorePaint.setTextAlign(Align.CENTER);
		
		scoreMultiplierPaint = new Paint();
		scoreMultiplierPaint.setTextSize(SCORE_MULTIPLIER_TEXT_HEIGHT_PERCENT_MARGIN_TOP*Game.topMarginHeight);
		scoreMultiplierPaint.setColor(Color.WHITE);
		scoreMultiplierPaint.setStyle(Style.FILL);

		healthPaint = new Paint();
		healthPaint.setStyle(Style.FILL);
		healthLength = Game.screenHeight*HEALTH_BAR_HEIGHT_PERCENT;
		healthWidth =	Game.screenWidth*HEALTH_BAR_WIDTH_PERCENT_WIDTH;
		boostPaint = new Paint();
		boostPaint.setStyle(Style.FILL);
		boostLength = Game.screenHeight*BOOST_BAR_HEIGHT_PERCENT;
		boostWidth = Game.screenWidth*BOOST_BAR_WIDTH_PERCENT_WIDTH;
		marginPaint = new Paint();
		marginPaint.setColor(Color.BLACK);
		
		speed = Game.gameSpeedNormal;
		
		scoreMultiplier = 1;
		
		
		score = 0;
		gold = 0;
		comboBonusTimer = 0;

	}
	

	public void update(){ 
		
		//update for general game mechanics that occurs once per frame
		
		if (comboBonusTimer>0){
			comboBonusTimer--;
		}
		
		incUpTime(); 
		
		
	}

	public void incUpTime(){
		upTime++;
	}
	
	public long getUpTime(){
		return upTime;
	}
	
	
	

	public boolean combo(){
		return comboBonusTimer >0;
	}
	
	public void activateComboChain(){
		comboBonusTimer = COMBO_CHAIN_LENGTH;
	}
	
	public Bitmap getBitmap(int imageID){
		return bitmapCache.get(imageID);
	}
	
	private Bitmap createImage(Context context, int resId,
	        int reqWidth, int reqHeight) {

	    //check dimensions without decoding full bitmap
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeResource(context.getResources(), resId, options);

	    //calculate scaling necessary to meet required dimensions
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    Bitmap roughBMap = BitmapFactory.decodeResource(context.getResources(), resId, options);

	    return roughBMap;
	}
	
	private int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        // Calculate ratios of height and width to requested height and width
        final int heightRatio = Math.round((float) height / (float) reqHeight);
        final int widthRatio = Math.round((float) width / (float) reqWidth);

        // Choose the smallest ratio as inSampleSize value, this will guarantee
        // a final image with both dimensions larger than or equal to the
        // requested height and width.
        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        
    }
       return inSampleSize;
	}
	
	/*
	 * adds images and child images of the given GameEntity to the
	 * Bitmap Cache.  This is called on every entity within a Level.
	 * The number added and total size upon completion is printed to debugger.
	 */
	private int cacheEntityImages(Context context, GameEntity entity){
		int totalCached = 0; //debug variable
		if (entity.getImageID()!=GameEntity.NO_IMAGE && bitmapCache.get(entity.getImageID()) == null){
			Bitmap entityImage = createImage(context, entity.getImageID(),(int)entity.getWidth(),(int)entity.getHeight());
			cacheImage(entity.getImageID(), entityImage);
			totalCached++;
		}
		for (GameEntity child: entity.getChildEntities()){
			totalCached = totalCached+ cacheEntityImages(context, child);
		}
		return totalCached;
	}
	
	/*
	 * add image to bitmap cache
	 */
	private void cacheImage(int imageID, Bitmap bMap){
		bitmapCache.put(imageID, bMap);
	}
	
	/*
	 * remove image and child images of given entity from 
	 * bitmap cache.  The number removed is printed to debugger.
	 */
	private int uncacheEntityImages(GameEntity entity){
		int totalUncached = 0; //debug variable
		for (GameEntity child: entity.getChildEntities()){
			totalUncached = totalUncached + uncacheEntityImages(child);
		}
		if (entity.getImageID()!=GameEntity.NO_IMAGE && bitmapCache.get(entity.getImageID())!=null){
			uncacheImage(entity.getImageID());
			totalUncached++;
		}
		
		return totalUncached;

	}
	
	/*
	 * remove specified image from cache
	 */
	private void uncacheImage(int imageID){
		bitmapCache.remove(imageID);
	}
	
	/*
	 * clear the cache of all images
	 */
	private void clearImageCache(){
		bitmapCache.clear();
	}
	
	
	/*
	 * draws everything relevant to gameplay.  
	 * The entire Level is drawn followed by all GameEntity objects
	 * within that Level.  The UI is also drawn.
	 */
	public void drawLevel(Canvas canvas){
		//draw background
		currentLevel.drawBackground(canvas);
	
		
		
		//draw background images
		int iterator = 0;
	while(iterator <backgroundEntities.size()){
		backgroundEntities.get(iterator).draw(canvas);
		iterator++;
	}
	
	//draw misc
	
	iterator = 0;
	while(iterator <miscEntities.size()){
		miscEntities.get(iterator).draw(canvas);
		iterator++;
	}
		//draw player
	player.draw(canvas);
		
		//draw shots
	iterator = 0;
	while(iterator <laserShots.size()){
		laserShots.get(iterator).draw(canvas);
		iterator++;
	}
	
		//draw cannon
	player.drawWeapon(canvas);		
		
		//draw Pickups
	iterator = 0;
	while(iterator <pickUps.size()){
		pickUps.get(iterator).draw(canvas);
		iterator++;
	}
	
		//draw asteroids
	iterator = 0;
	while(iterator <asteroids.size()){
		asteroids.get(iterator).draw(canvas);
		iterator++;
	}
		//draw Enemies
	iterator = 0;
	while(iterator <enemies.size()){
		enemies.get(iterator).draw(canvas);
		iterator++;
	}
		//draw Enemy Shots
	iterator = 0;
	while(iterator <enemyShots.size()){
		enemyShots.get(iterator).draw(canvas);
		iterator++;
	}
	

	
		//draw Effects
	iterator = 0;
	while(iterator <effects.size()){
		effects.get(iterator).draw(canvas);
		iterator++;
	}
	


		drawHealthBar(canvas);
		
		drawBoostBar(canvas);
		
		drawTopMargin(canvas);
		drawBottomMargin(canvas);
		
		drawScore(canvas);
		
		drawGold(canvas);
		drawPassengerCount(canvas);
		
		currentLevel.drawObjective(canvas);
	}
	
	void drawHealthBar(Canvas canvas){

		double damageRatio = (double)player.getHealth()/(double)player.getMaxHealth();
		if (damageRatio>1.0){
			damageRatio = 1.0;
		}
		
		healthPaint.setColor(Color.GREEN);
		canvas.drawRect(10, Game.screenHeight/2+Game.topMarginHeight - healthLength/2, healthWidth+10,Game.screenHeight/2+Game.topMarginHeight +healthLength/2, healthPaint);
		healthPaint.setColor(Color.RED);
		canvas.drawRect(13, (Game.screenHeight/2+Game.topMarginHeight - healthLength/2)+3, healthWidth+7,(Game.screenHeight/2+Game.topMarginHeight +healthLength/2)-3, healthPaint);
		healthPaint.setColor(Color.GREEN);
		canvas.drawRect(13, (float) (Game.screenHeight/2+Game.topMarginHeight -(damageRatio *healthLength-healthLength/2)+3), healthWidth+7,(Game.screenHeight/2+Game.topMarginHeight +healthLength/2)-3, healthPaint);
	}
	
	private void drawBoostBar(Canvas canvas){
		
		double boostRatio = (double)player.getBooster().getBoostRechargeRatio();
		
		boostPaint.setColor(Color.BLUE);
		canvas.drawRect(10+healthWidth, Game.screenHeight/2+Game.topMarginHeight - boostLength/2, healthWidth+10+boostWidth,Game.screenHeight/2+Game.topMarginHeight +boostLength/2, boostPaint);
		boostPaint.setColor(Color.RED);
		canvas.drawRect(13+healthWidth, (Game.screenHeight/2+Game.topMarginHeight - boostLength/2)+3, healthWidth+7+boostWidth,(Game.screenHeight/2+Game.topMarginHeight +boostLength/2)-3, boostPaint);
		boostPaint.setColor(Color.BLUE);
		canvas.drawRect(13+healthWidth, (float) (Game.screenHeight/2+Game.topMarginHeight -(boostRatio *boostLength-boostLength/2)+3), healthWidth+7+boostWidth,(Game.screenHeight/2+Game.topMarginHeight +boostLength/2)-3, boostPaint);
		
		
	}
	
	void drawScore(Canvas canvas){
		canvas.drawText("DPM: "+score,(float)(Game.screenWidth*.5f), (float)(Game.topMarginHeight*.5f),scorePaint);
		canvas.drawText("x" +getScoreMultiplier(), 0, Game.topMarginHeight*.5f, scoreMultiplierPaint);
	}
	
	private void drawGold(Canvas canvas){
		canvas.drawText("$$: "+gold,(float)(Game.screenWidth*.33f), (float)(Game.totalHeight-Game.bottomMarginHeight*.25f),goldPaint);
	}
	
	private void drawPassengerCount(Canvas canvas){
		canvas.drawText("Passengers: "+player.getPassengerCount(),(float)(Game.screenWidth*.66f), (float)(Game.totalHeight-Game.bottomMarginHeight*.25f),passengerPaint);

	}
	
	private void drawBottomMargin(Canvas canvas){
		canvas.drawRect(0f,Game.totalHeight-Game.bottomMarginHeight,Game.screenWidth,Game.totalHeight, marginPaint);
	}
	
	private void drawTopMargin(Canvas canvas){
		canvas.drawRect(0f, 0f,Game.screenWidth,Game.topMarginHeight,marginPaint);
	}
	
	/*
	 * Images that are persistent throughout the entire game are cached here.
	 * This includes the images of the Player, PickUps, and Effects.
	 */
	public void renderGame(Context context){
		Bitmap copperImage = createImage(context, CopperDrop.IMAGE_ID, (int)(Game.screenHeight*MoneyDrop.WIDTH_PERCENT), (int)(Game.screenHeight*MoneyDrop.HEIGHT_PERCENT));
		cacheImage(CopperDrop.IMAGE_ID,copperImage);
		Bitmap silverImage = createImage(context, SilverDrop.IMAGE_ID, (int)(Game.screenHeight*MoneyDrop.WIDTH_PERCENT), (int)(Game.screenHeight*MoneyDrop.HEIGHT_PERCENT));
		cacheImage(SilverDrop.IMAGE_ID,silverImage);
		Bitmap goldImage = createImage(context, GoldDrop.IMAGE_ID, (int)(Game.screenHeight*MoneyDrop.WIDTH_PERCENT), (int)(Game.screenHeight*MoneyDrop.HEIGHT_PERCENT));
		cacheImage(GoldDrop.IMAGE_ID,goldImage);
		Bitmap emeraldImage = createImage(context, EmeraldDrop.IMAGE_ID, (int)(Game.screenHeight*MoneyDrop.WIDTH_PERCENT), (int)(Game.screenHeight*MoneyDrop.HEIGHT_PERCENT));
		cacheImage(EmeraldDrop.IMAGE_ID,emeraldImage);
		Bitmap purpImage = createImage(context, PurpDrop.IMAGE_ID, (int)(Game.screenHeight*MoneyDrop.WIDTH_PERCENT), (int)(Game.screenHeight*MoneyDrop.HEIGHT_PERCENT));
		cacheImage(PurpDrop.IMAGE_ID,purpImage);
		
		//make entity
		Bitmap cannonImage = createImage(context, Weapon.IMAGE_ID, (int)(Game.screenHeight*Weapon.CANNON_WIDTH_PERCENT), (int)(Game.screenHeight*Weapon.CANNON_HEIGHT_PERCENT));
		cacheImage(Weapon.IMAGE_ID, cannonImage);
		
		Bitmap explosionImage = createImage(context, Explosion.IMAGE_ID, (int)(Explosion.HEIGHT_PERCENT*Game.screenHeight), (int)(Explosion.WIDTH_PERCENT*Game.screenHeight));
		cacheImage(Explosion.IMAGE_ID, explosionImage);
		
		Bitmap laserExplosionImage = createImage(context, DamageExplosion.IMAGE_ID, (int)(Laser.STARTING_EXPLOSION_RADIUS_PERCENT*Game.screenHeight*2), (int)(Laser.STARTING_EXPLOSION_RADIUS_PERCENT*2*Game.screenHeight));
		cacheImage(DamageExplosion.IMAGE_ID, laserExplosionImage);
	}
	
	/*
	 * All Images that will be used in the given Level are rendered.
	 * Upon building a Level, a Set of all GameEntities is created.
	 * This Set can be iterated through to cache all relevant images.
	 */
	public void renderLevel(Context context, Level theLevel){

		int totalCached = 0;
		for (GameEntity entity: theLevel.getAllEntities()){
			totalCached = totalCached+ cacheEntityImages(context, entity);
		}
		totalCached = totalCached+ cacheEntityImages(context, getPlayer());
		
		if (theLevel.getBackgroundImageID()!=GameEntity.NO_IMAGE){
		Bitmap backgroundImage = createImage(context, theLevel.getBackgroundImageID(),(int)Game.screenHeight,(int)Game.screenWidth);
			cacheImage(theLevel.getBackgroundImageID(), backgroundImage);
			totalCached++;
		}
		
		Log.d("Total Images Cached From Level:", totalCached + "");
		Log.d("Total Cache Size: ", "" + bitmapCache.size());
	}
	
	/*
	 * Iteratively removed all GameEntity images within this Level from the cache.
	 */
	public void unrenderLevel(Level theLevel){
		int totalUncached = 0;
		
		for (GameEntity entity: theLevel.getAllEntities()){
			totalUncached += uncacheEntityImages(entity);
		}
		totalUncached += uncacheEntityImages(getPlayer());
		
		if (bitmapCache.get(theLevel.getBackgroundImageID())!=null){
			uncacheImage(theLevel.getBackgroundImageID());
			totalUncached++;
		}
		
		Log.d("Total Images Removed from Cache:", totalUncached+"");
		Log.d("Total Images in Cache:", bitmapCache.size()+"");
	
	}
	
	/*
	 * This is called when entering a menu that has a background image.
	 * It ensures that that the background image will be around only while the screen
	 * is up and that the garbage collector can remove the bitmap when 
	 * a Level is being rendered.
	 */
	public void renderScreen(Context context, GameScreen screen){
		int totalCached = 0;

		if (screen.getBackgroundImageID()!=GameScreen.NO_BACKGROUND_IMAGE){
		Bitmap backgroundImage = createImage(context, screen.getBackgroundImageID(),(int)Game.screenHeight,(int)Game.screenWidth);
			cacheImage(screen.getBackgroundImageID(), backgroundImage);
			totalCached++;
		}
		
		Log.d("Total Images Cached From Screen:", totalCached + "");
		Log.d("Total Cache Size: ", "" + bitmapCache.size());
	}
	
	/*
	 * removes screen graphics from cache.
	 */
	public void unrenderScreen(GameScreen screen){
		int totalUncached = 0;

		if (bitmapCache.get(screen.getBackgroundImageID())!=null){
			uncacheImage(screen.getBackgroundImageID());
			totalUncached++;
		}
		
		Log.d("Total Images Removed from Cache:", totalUncached+"");
		Log.d("Total Images in Cache:", bitmapCache.size()+"");
	
	}
	
	
	
	
	public Player getPlayer(){
		return player;
	}
	
	public float getSpeed(){
		return speed;
	}
	
	public void incSpeed(float theAmount){
		speed = speed+theAmount;
	}
	
	public void setSpeed(float theAmount){
		speed = theAmount;
	}
	
	
	
	
	
	public int getGold(){
		return gold;
	}
	
	public void incGold(int amount){
		gold = gold+amount;
		if (amount>0){
			addAnimation(new IncGoldAnimation(this, goldPaint));
		}
	}

	public void incPassengerCount(int amount){
		player.incPassengerCount(amount);
		if (amount>0){
			addAnimation(new IncGoldAnimation(this, passengerPaint));
		}
	}
	
	public int getScoreMultiplier(){
		return scoreMultiplier;
	}
	
	public void setScoreMultiplier(int amount){
		scoreMultiplier = amount;
	}
	public int getScore(){
		return score;
	}
	
	public void incScore(int amount){
		score = score+amount;
		if (amount>0){
			addAnimation(new IncScoreAnimation(this, scorePaint));
		}
	}
	



	public Level getCurrentLevel(){
		return currentLevel;
	}
	
	public void setLevel(Level theLevel){
		currentLevel = theLevel;
	}
	
	public ArrayList<EnemyShot> getEnemyShots(){
		return enemyShots;
	}
	
	public ArrayList<GameAnimation> getAnimations(){
		return animations;
	}
	
	public void clearAnimations(){
		for (GameAnimation anim: animations){
			anim.end();
		}
		animations.clear();
	}
	
	public ArrayList<Effect> getEffects(){
		return effects;
	}
	
	
	public ArrayList<GameEntity> getEntities(){
		return entities;
	}
	
	public ArrayList<Shot> getLaserShots(){
		return laserShots;
	}
	
	public ArrayList<Asteroid> getAsteroids(){
		return asteroids;
	}
	
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}
	
	public ArrayList<BackgroundImage> getBackgroundEntities(){
		return backgroundEntities;
	}
	
	public ArrayList<PickUp> getPickUps(){
		return pickUps;
	}
	
	public ArrayList<GameEntity> getMiscEntities(){
		return miscEntities;
	}
	
	
	/*
	 * All entities added through game mechanics are stored in
	 * a data structure and added at once at the beginning of the
	 * GameThread during the UpdateGameState phase.
	 */
	public void addEntity(GameEntity newEntity){
		pendingEntities.add(newEntity);
	}
	
	/*
	 * This is called iteratively during the actual UpdateGameState phase
	 * and it adds an entity from the list of pending entities
	 * to the list of all present entities and the list of its
	 * sub type.
	 */
	private void addEntityFromPending(GameEntity newEntity){
		entities.add(newEntity);
		
		
		//Log.d("Added Entity: ",newEntity.getClass().getSimpleName());
		
		
		if (newEntity instanceof Shot){
			laserShots.add((Shot) newEntity);
		} else if (newEntity instanceof Asteroid){
			asteroids.add((Asteroid) newEntity);
		} else if (newEntity instanceof PickUp){
			pickUps.add((PickUp) newEntity);
		} else if (newEntity instanceof Enemy && !(newEntity instanceof Asteroid)){
			enemies.add((Enemy) newEntity);
		} else if (newEntity instanceof Effect){
			effects.add((Effect) newEntity);
		} else if (newEntity instanceof EnemyShot){
			enemyShots.add((EnemyShot) newEntity);
		} else if (newEntity instanceof BackgroundImage){
			backgroundEntities.add((BackgroundImage) newEntity);
		} else{
			miscEntities.add(newEntity);
		}

	}
	
	/*
	 * calls the update() method on all entities during the UpdateGameState
	 * phase and checks these entities for removal.
	 */
	public void updateEntities(){
		Iterator<GameEntity> entityIterator = entities.iterator();
		while (entityIterator.hasNext()) {
			GameEntity entity = entityIterator.next();
			entity.update();
			
			if (entity.remove()) {

				if (entity instanceof Shot) {
					laserShots.remove(entity);
				} else if (entity instanceof Asteroid) {
					asteroids.remove(entity);
				} else if (entity instanceof PickUp) {
					pickUps.remove(entity);
				} else if (entity instanceof Enemy
						&& !(entity instanceof Asteroid)) {
					enemies.remove(entity);
				} else if (entity instanceof Effect) {
					effects.remove(entity);
				} else if (entity instanceof EnemyShot) {
					enemyShots.remove(entity);
				} else if (entity instanceof BackgroundImage){
					backgroundEntities.remove(entity);
				} else{
					miscEntities.remove(entity);
				}

				entityIterator.remove(); // put in gameState?

			}
			
		}
	}
	
	/*
	 * iteratively calls addEntityFromPending() on all entities
	 * in the pending list.
	 */
	public void addEntitiesFromPending(){

		tempPendingEntities.addAll(pendingEntities);
		
		for (GameEntity entity: pendingEntities){
			addEntityFromPending(entity);
		}
		pendingEntities.clear();
		for (GameEntity entity: tempPendingEntities){
			entity.spawn();
		}
			tempPendingEntities.clear();
		}
	
	
	public void addAnimation(GameAnimation newAnimation){
		animations.add(newAnimation);
	}
	
	
	/*
	 * produces a random color
	 */
	public static int randomColor(){
		int color = 0xff000000;
		int randomAdd = 0xffffff;
		randomAdd =(int) (randomAdd*Math.random());
		color = color+randomAdd;
		return color;
	}
	
	/*
	 * the distance between two entities using their x and y coordinates
	 */
	public static float distance(GameEntity e1, GameEntity e2){
		return (float) Math.sqrt(Math.pow(e1.getX()-e2.getX(),2) + Math.pow(e1.getY()-e2.getY(),2));
	}

	/*
	 * removes all entities from all lists.  This is called when a Level
	 * is reset and when the game is reset.
	 */
	private void clearEntities(){
		clearAnimations();
		enemies.clear();
		effects.clear();
		asteroids.clear();
		laserShots.clear();
		entities.clear();
		pickUps.clear();
		enemyShots.clear();
		miscEntities.clear();
		backgroundEntities.clear();
		pendingEntities.clear();
	}
	
	/*
	 * the method called as you are leaving a level
	 */
	public void levelExit(){
		clearEntities();
		player.clearPassengerCount();
		player.reset(false);
	}
	
	
	/*
	 * the method called when you retry a level
	 */
	public void levelReset(){
		//consider deleting
		clearEntities();
		player.clearPassengerCount();
		player.reset(true);
		currentLevel.reset();

	}
	
	/*
	 * the method called when you restart the game
	 */
	public void gameReset(){
		clearEntities();
		player = new Player(this, Game.screenWidth/2);	
		initialize();
	}
	
	/*
	 * the method called when you quit the game
	 */
	public void quit(){/*
		currentLevel = null;
		player = null;
		*/
	}
	
	/*
	 * takes a speed percent which is based on screenHeight
	 * and converts it to an actual speed in pixels
	 */
	static float calcSpeed(float percentOfScreenHeightPerFrame){
		return percentOfScreenHeightPerFrame*Game.screenHeight;
	}
	


	



	
}
