package sean.k.uts2120;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;

abstract class Weapon {

	final static int IMAGE_ID = R.drawable.cannon;
	final static float CANNON_HEIGHT_PERCENT = .07f;
	final static float CANNON_WIDTH_PERCENT = .03f;
	final static float CANNON_BASE_RADIUS_PERCENT_CANNON_WIDTH = .5f;

	Player player;
	int damage;
	float speed;
	int shotWidth;
	int shotHeight;
	int range;
	int untilReloaded; //count down for reload
	int reloadTime;  //reload duration
	int imageID;
	
	Bitmap cannonBitmap;
	Matrix paintMatrix;
	Paint weaponPaint;
	
	
	int cannonWidth;
	int cannonHeight;
	
	float widthRatio;
	float heightRatio; //relative to bitmap height (typically does not change)
	
	double angle;
	
	final static int RANGE_INFINITE = -1;
	
	/*
	 * abstract class that represents a weapon Player can use.  In this game there
	 * is only 1 implementation.  It holds a damage value, speed value, reload time, range
	 * and what Upgrade Level to begin at.
	 */
	public Weapon(Player thePlayer, int theImageID, int theDamage, float theSpeed, int theWidth, int theHeight,int theReload, int theRange, int theLevel){
		damage = theDamage;
		speed = theSpeed;
		shotWidth = theWidth;
		shotHeight = theHeight;
		player = thePlayer;
		range = theRange;
		untilReloaded = 0;
		reloadTime = theReload;
		imageID = theImageID;

		angle = Math.PI/2;
		weaponPaint = new Paint();
		weaponPaint.setColor(Color.BLACK);
		weaponPaint.setStyle(Style.FILL);

		paintMatrix = new Matrix();
		cannonWidth = (int) (Game.screenHeight*CANNON_WIDTH_PERCENT);
		cannonHeight = (int) (Game.screenHeight*CANNON_HEIGHT_PERCENT);



	}
	
	public void spawn(){
		cannonBitmap = player.getGame().getBitmap(IMAGE_ID);
		if (cannonBitmap!=null){
		float bMapHeight = cannonBitmap.getHeight();
		float bMapWidth = cannonBitmap.getWidth();
		
		
		heightRatio = cannonHeight/bMapHeight;
		widthRatio = cannonWidth/bMapWidth;
		}
	}
	public int getImageID(){
		return imageID;
	}
	
	public void setAngle(double setAngle){
		angle = setAngle;
	}
	
	public int getRange(){
		return range;
	}

	
	public int getWidth(){
		return shotWidth;
	}
	
	public int getHeight(){
		return shotHeight;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public int setDamage(int damageAmount){
		return damage = damageAmount;
	}
	
	public float getSpeed(){
		return speed;
	}
	
	public void setSpeed(float theSpeed){
		speed = theSpeed;
	}
	
	
	public Bitmap getBitmap(){
		return cannonBitmap;
	}
	
	
	public void update(){
		if (untilReloaded>0){
			untilReloaded--;
		}
	}
	
	abstract void impact(float xCoor, float yCoor);
	abstract void shoot(float xCoor, float yCoor);
	
	abstract void draw(Canvas canvas, float xCoor, float yCoor);
	
}
