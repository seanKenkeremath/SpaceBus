package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;

public class Laser extends Weapon{
	
	
	final static float WIDTH_PERCENT = .02f;
	final static float HEIGHT_PERCENT = .02f;
	final static int IMAGE_ID = R.drawable.laser2;

	
	int powerLevel;
	final static int MAX_POWER_LEVEL = 3;
	final static int STARTING_DAMAGE = SmallAsteroid.HEALTH;
	
	int speedLevel;
	final static int MAX_SPEED_LEVEL = 3;
	final static float STARTING_SPEED_PERCENT = Game.VELOCITY_MEDIUM_PERCENT;
	final static int STARTING_RELOAD_TIME = (int) (400f/Game.THREAD_WAIT_TIME);
	
	boolean explosive;
	float explosionRadius;
	final static int EXPLOSION_DAMAGE = SmallAsteroid.HEALTH*3;
	final static float STARTING_EXPLOSION_RADIUS_PERCENT = .08f;

	
	/*
	 * This class represents the weapon the Player has.  It holds parameters
	 * for firing rate, power, and explosive radius.  Each of these attributes 
	 * begin at a certain level (lvl1 ) and are upgraded separately to lvl 3
	 */
	public Laser(Player thePlayer, int thePowerLevel, int theSpeedLevel, boolean isExplosive) {
		super(thePlayer, IMAGE_ID, STARTING_DAMAGE, Game.calcSpeed(STARTING_SPEED_PERCENT), (int) (WIDTH_PERCENT*Game.screenHeight), (int)(HEIGHT_PERCENT*Game.screenHeight), STARTING_RELOAD_TIME, Weapon.RANGE_INFINITE, 1);
		powerLevel = 1;
		while (powerLevel<thePowerLevel){
			upgradePower();
		}
		speedLevel = 1;
		while (speedLevel<theSpeedLevel){
			upgradeSpeed();
			speedLevel++;
		}
		explosive = isExplosive;
		explosionRadius = STARTING_EXPLOSION_RADIUS_PERCENT * Game.screenHeight;
	}

	@Override
	public
	void shoot(float xCoor, float yCoor) {

		float touchDeltaY = (player.getCannonY())
				- yCoor;
		float touchDeltaX = xCoor - player.getCannonX();

		
		double angle = Math.acos(touchDeltaX
				/ (Math.sqrt(Math.pow(touchDeltaX, 2)
						+ Math.pow(touchDeltaY, 2))));
		setAngle(angle);
		
		if (untilReloaded>0){

		} else {
			
			
			untilReloaded = reloadTime;
		// create shot
		Shot shot = new LaserShot(player.getGame(), this, player.getCannonX(), player.getCannonY());

		shot.setVelocityY((float) (speed * Math
				.sin(angle))*-1);
		shot.setVelocityX((float) (speed * Math
				.cos(angle)));

		player.getGame().addEntity(shot);
		
		}
		
	}

	
	public void upgradePower(){
		damage = damage +STARTING_DAMAGE/2;
		explosionRadius = explosionRadius*4/3;
		powerLevel++;
		
	}
	
	public int getPowerLevel(){
		return powerLevel;
	}

	public void upgradeExplosive(){
		explosive = true;
	}
	
	public void upgradeSpeed(){
		reloadTime = reloadTime*3/4;
		speed = speed*4/3;
		speedLevel++;
	}
	public int getSpeedLevel(){
		return speedLevel;
	}


	@Override
	void draw(Canvas canvas, float xCoor, float yCoor) {
		paintMatrix.reset();
		paintMatrix.postScale(widthRatio, heightRatio);
		paintMatrix.postRotate((float) ((Math.PI/2-angle)/(Math.PI*2)*360),cannonWidth/(float)2,cannonHeight*3/4);
		paintMatrix.postTranslate(xCoor-cannonWidth/2, yCoor-cannonHeight*3/4);

		//weaponPaint.setColor(Game.UVA_BLUE);
		//canvas.drawCircle(player.getCannonX(), player.getCannonY(), CANNON_BASE_RADIUS_PERCENT_CANNON_WIDTH*cannonWidth, weaponPaint);
		weaponPaint.setColor(Color.WHITE);
		if (cannonBitmap!=null){
		canvas.drawBitmap(cannonBitmap, paintMatrix, weaponPaint);
		}
	}

	@Override
	void impact(float xCoor, float yCoor) {
		if (explosive){
		player.getGame().addEntity(new DamageExplosion(player.getGame(), damage, explosionRadius, xCoor,yCoor));
		}
	}
	



}
