package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player extends GameEntity{
	
	
	

	

	final static int IMAGE_ID = R.drawable.bus;
	final static int DEAD_IMAGE_ID = Explosion.IMAGE_ID;
	final static int STARTING_HEALTH = SmallAsteroid.HEALTH*10;
	
	final static float HEIGHT_PERCENT = 0.14f; //percent of screenheight that bus's height is
	final static float BOOSTER_HEIGHT_PERCENT = .03f;
	final static float WIDTH_PERCENT = .07f;
	
	final static float SHOT_ORIGIN_PERCENT_BODY = .5f;
	
	final static int DOORS_OPEN_DURATION = 5;
	final static int NEAR_PASSENGERS_DURATION = 5;
	/*
	final static float HITBOX_BOTTOM_PERCENT = .3f; //percentage of center from bottom that hitbox covers
	final static float HITBOX_TOP_PERCENT = .9f;
	final static float HITBOX_LEFT_PERCENT = .9f;
	final static float HITBOX_RIGHT_PERCENT = .8f;
	*/
	
	final static float MAX_VELOCITY_X_PERCENT_WIDTH = .03f;
	private float maxVelocityX;
	final static float MAX_ACCELERATION_X_PERCENT_WIDTH = .001f;
	private float maxAccelerationX;

	
	public boolean dead;  //whether the player has been killed. checked every loop of thread.
	
	private int passengerCount;
	
	
	public Armor armor; //what percent of damage you prevent (starts at 0f)
	public Booster booster; //parameters of the Boost action
	
	private Laser laser;
	
	private float cannonX;
	private float cannonY;
	
	
	/*
	 * this class holds all information about the entity that is controlled by
	 * the person playing.  This class holds an Armor class, Booster class, Laser class
	 * that can be upgraded separately.  The draw() method of this entity calls
	 * the draw() method of those three things.
	 */
	
	public Player(Game theGame, float xPosition) {
		super(theGame, IMAGE_ID, xPosition, Game.topMarginHeight+Game.screenHeight-Game.screenHeight*(HEIGHT_PERCENT/2+Booster.BOOSTER_RADIUS_PERCENT*2), Game.screenHeight*WIDTH_PERCENT, Game.screenHeight*HEIGHT_PERCENT);
		color = Color.RED;
		//tiltSensitivity = 5f;
		maxHealth = STARTING_HEALTH;
		maxVelocityX = Game.screenWidth*MAX_VELOCITY_X_PERCENT_WIDTH;
		maxAccelerationX = Game.screenWidth*MAX_ACCELERATION_X_PERCENT_WIDTH;
		armor = new Armor(this,1);
		booster = new Booster(game, this, 1);		
		laser = new Laser(this, 1,1,true);
		childEntities.add(new LaserShot(game,laser,0,0));		
		cannonY = yPos +height/2 - SHOT_ORIGIN_PERCENT_BODY*height;
		cannonX = xPos;
		
		
		reset(true);
	}

	@Override
	public void spawn(){
		super.spawn();
		laser.spawn();
	}
	
	public void die(){
		game.addEntity(new Explosion(game,xPos,yPos));
	}
	
	public void reset(boolean resetHealth){
		if (resetHealth){
			health = maxHealth;
		}
		paint = new Paint();
		dead = false;
		passengerCount = 0;
		velocityY = 0f;
		booster.reset();
	}
	
	@Override 
	public void draw(Canvas canvas){
		if (!dead){
		super.draw(canvas);
		//armor.draw(canvas);
		booster.draw(canvas);
		}
	}
	
	public void drawWeapon(Canvas canvas){
		if (!dead){
		laser.draw(canvas, cannonX, cannonY);
		}
	}
	
	/*
	@Override
	protected void updateHitBox(){
		
		int leftSide = (int) (xPos-width/2*HITBOX_LEFT_PERCENT);
		int rightSide = (int) (xPos+width/2*HITBOX_RIGHT_PERCENT);
		int topSide = (int) (yPos-height/2*HITBOX_TOP_PERCENT);
		int bottomSide = (int) (yPos+height/2*HITBOX_BOTTOM_PERCENT);
		hitBox.get(0).set(leftSide,topSide,rightSide,bottomSide);
	}
	*/
	
	@Override
	public void decHealth(int decAmount){
		super.decHealth(decAmount);
		if (health<=0){
			dead = true;
		}
	}
	
	public void repair(){
		health = maxHealth;
	}
	
	public void damage(int damageAmount){
		decHealth(armor.protect(damageAmount));
	}
	
	/*
	 * called when Player runs into an enemy.
	 * If the boosters are on it kills that enemy and points are awarded.
	 */
	public void collide(Enemy collision){
		if (booster.active) {
			collision.destroy(true);
		} else {
			damage(collision.getDamage());
			collision.destroy(false);
		}
	}
	
	public Booster getBooster(){
		return booster;
	}
	
	public Armor getArmor(){
		return armor;
	}
	public int getArmorLevel(){
		return armor.getLevel();
	}
	
	@Override
	protected void updateX(){
		super.updateX();
		if (xPos<(width/2)){
			xPos = width/2;
		}
		if (xPos>(Game.screenWidth-width/2)){
			xPos = Game.screenWidth-width/2;
		}
	}
	
	

	@Override
	public void incVelocityX(float amount){
		super.incVelocityX(amount);
		if (velocityX>maxVelocityX){
			velocityX = maxVelocityX;
		}
		if (velocityX<maxVelocityX*-1){
			velocityX = maxVelocityX *-1;
		}
	}
	
	@Override
	protected void updateY(){

	}
	
	@Override
	public void update() {
		super.update();
		
		cannonY = yPos +height/2 - SHOT_ORIGIN_PERCENT_BODY*height;
		cannonX = xPos;

		
		booster.update();
		
		laser.update();
		

	}
	

	public Laser getLaser(){
		return laser;
	}
	
	public float getCannonX(){
		return cannonX;
	}
	public float getCannonY(){
		return cannonY;
	}
	
	
	public int getPassengerCount(){
		return passengerCount;
	}
	
	
	public void incPassengerCount(int amount){
		passengerCount = passengerCount+amount;
	}
	
	public void clearPassengerCount(){
		passengerCount = 0;
	}
	
	/*
	public float getTiltSensitivity(){
		return tiltSensitivity;
	}
	*/
	
	public float getMaxAccelerationX(){
		return maxAccelerationX;
	}
	


}
