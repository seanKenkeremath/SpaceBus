package sean.k.uts2120;
import java.util.ArrayList;
import java.util.HashSet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;


abstract class GameEntity implements Comparable<GameEntity>{
	
	
	final static int NO_IMAGE = 0;
	protected float xPos;
	protected float yPos;
	protected long birth;
	protected long age;
	protected Game game;
	protected float velocityX;
	protected float velocityY;
	protected boolean remove;
	protected float width;
	protected float height;
	protected HashSet<GameEntity> childEntities;
	
	float widthRatio;
	float heightRatio; //relative to bitmap height (typically does not change)
	
	protected boolean enemy;
	protected int health;
	protected int maxHealth;
	
	protected float spawnPosition;
	
	protected Paint paint;
	protected Matrix paintMatrix;
	
	int imageID;
	Bitmap bitmap;
	ArrayList<Rect> hitBox; //each Rect is part of the hitbox
	int color;

	
	/*
	 * this abstract class is used to implement any entity in the game.
	 * This may be an effect, the Player, Enemies, obstacles, or background 
	 * images.  Anything that interacts with the Game state is an instance of GameEntity.
	 * Each GameEntity has an update() method that is called on each
	 * iteration of the GameThread.  Spawn() is called when the entity is actually
	 * put into the game (rather than the object itself being created).  All entities
	 * are first put into a pending queue and then spawned at the beginning of the
	 * thread iteration during the UpdateGameState phase.  Velocity specifies
	 * how fast the entity moves in pixels.  This value, as most dimensional values
	 * must be represented using a static fraction of screenHeight and then calculated
	 * at runtime to be the same across all screen dimensions.  
	 * The bitmap resource ID provided will be used to cache this entities bitmap
	 * upon entering a Level that contains the entity.  Any child entity such as 
	 * a projectile that also has a bitmap must be loaded into the Set childEntities 
	 * within the constructor.  It is recommended that width and height be calculated
	 * as a function of a single screen dimension such as height to ensure 
	 * the game is the entity will appear the same on all dimensions.  The hitbox
	 * to detect collisions is represented as an ArrayList of Rect objects.  By
	 * default the hitbox is a single rectangle of the specified dimensions.
	 * The default draw() method consists of drawing the bitmap from the cache and
	 * using Matrix transformations to translate and scale the image to the dimensions
	 * of the entity.
	 */
	public GameEntity(Game theGame, int theImageID, float xPosition, float yPosition, float theWidth, float theHeight){
		
		color = Color.GREEN;
		game = theGame;
		enemy = false;
		xPos = xPosition;
		yPos = yPosition;
		age = 0L;
		width = theWidth;
		height = theHeight;
		velocityX = 0f;
		imageID = theImageID;
		childEntities = new HashSet<GameEntity>();
		
		velocityY = 0f;

		health = 100;
		maxHealth = 100;
		
		int leftSide = (int) (xPos-width/2);
		int rightSide = (int) (xPos+width/2);
		int bottomSide = (int) (yPos+height/2);
		int topSide = (int) (yPos-height/2);
		
		paint = new Paint();
		paintMatrix = new Matrix();
		
		hitBox = new ArrayList<Rect>();
		hitBox.add(new Rect(leftSide,topSide,rightSide,bottomSide));
		

		
		remove = false;
		
	}
	
	public HashSet<GameEntity> getChildEntities(){
		return childEntities;
	}
	
	public Game getGame(){
		return game;
	}
	
	public void animate(){

	}
	
	public void spawn(){
		birth = game.getUpTime();
		bitmap = game.getBitmap(imageID);
		if (bitmap!=null){
		float bMapHeight = bitmap.getHeight();
		float bMapWidth = bitmap.getWidth();
		
		
		heightRatio = height/bMapHeight;
		widthRatio = width/bMapWidth;
		}
	}
	
	public void setSpawnPosition(float spawnPos){
		spawnPosition = spawnPos;
	}
	
	public float getSpawnPosition(){
		return spawnPosition;
	}
	
	public void draw(Canvas c){

		if (bitmap!=null){
			
			paintMatrix.reset();
			paintMatrix.postScale(widthRatio, heightRatio);
			paintMatrix.postTranslate(xPos-width/2, yPos-height/2);
			
			//for debugging hitboxes
			
			/*
			paint.setColor(Color.RED);
			for (int i = 0; i<hitBox.size(); i++){
				c.drawRect(hitBox.get(i), paint);
			}
			*/
			
			
			paint.setColor(Color.WHITE);
			c.drawBitmap(bitmap,paintMatrix,paint);
			
		}
	}
	
	public void update(){
	
		updateAge();
		updateX();
		updateY();
		updateHitBox();
		checkBounds();
			
	}

	protected void updateAge(){
		age = game.getUpTime()-birth;
	}
	protected void updateX(){
		setX(xPos+velocityX);
	}
	protected void updateY(){
		setY(yPos+game.getSpeed()+velocityY);
	}
	
	protected void updateHitBox(){ //default hitbox is just one rectangle around image, must overwrite entire method for multiple hitboxes
		
		
		int leftSide = (int) (xPos-width/2);
		int rightSide = (int) (xPos+width/2);
		int topSide = (int) (yPos-height/2);
		int bottomSide = (int) (yPos+height/2);
		hitBox.get(0).set(leftSide,topSide,rightSide,bottomSide);
		
	}
	
	protected void checkBounds(){
		if ((xPos-width/2)>Game.screenWidth || (xPos+width/2)<0 || (yPos-height/2)>(Game.screenHeight+Game.bottomMarginHeight) || (yPos+height/2)<Game.topMarginHeight){
			remove = true;
		}
	}
	
	public boolean remove(){
		return remove;
	}
	
	
	public Matrix getPaintMatrix(){
		return paintMatrix;
	}
	
	public void setPaintMatrix(Matrix newMatrix){
		paintMatrix = newMatrix;
	}
	public Paint getPaint(){
		return paint;
	}
	
	public int getImageID(){
		return imageID;
	}
	
	public void setPaint(Paint newPaint){
		paint = newPaint;
	}
	
	public void setRemove(boolean toRemove){
		remove = toRemove;
	}
	public void setX(float newX){
		xPos = newX;
	}
	
	public float getX(){
		return xPos;
	}
	
	public void setY(float newY){
		yPos = newY;
	}
	
	public float getY(){
		return yPos;
	}
	
	public void setVelocityX(float newVelocityX){
		velocityX = newVelocityX;
	}
	
	public void incVelocityX(float addVelocityX){
		velocityX = velocityX + addVelocityX; 
	}
	
	public float getVelocityX(){
		return velocityX;
	}
	
	public void setVelocityY(float newVelocityY){
		velocityY = newVelocityY;
	}
	
	public void incVelocityY(float addVelocityY){
		velocityY = velocityY + addVelocityY; 
	}
	
	public float getVelocityY(){
		return velocityY;
	}

	/*
	public float getAngle(){
		float magnitude = (float) Math.sqrt(velocityX*velocityX + velocityY + velocityY);
		if (magnitude==0f){
			return (0);
		}
		return (float) Math.acos(velocityX/magnitude);
	
	}
	*/
	
	public long getBirth(){
		return birth;
	}
	
	public long getAge(){
		return age;
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
	
	public ArrayList<Rect> getHitBox(){
		return hitBox;
		
	}

	public void changeColor(int newColor){
		color = newColor;
	}
	
	public int getColor(){
		return color;
	}
	
	public boolean insideOf(float xCoor, float yCoor){
		boolean inside = false;
		for (Rect rect: hitBox){
			if (rect.contains((int)xCoor, (int)yCoor)){
				inside = true;
			}
		}
		return inside;
	}
	
	public boolean intersects(ArrayList<Rect> otherHitBox){
		boolean intersect = false;
		for (Rect rect: hitBox){
			for (Rect otherRect: otherHitBox){
				if (Rect.intersects(rect, otherRect)){
					intersect = true;
				}
			}
		}
		return intersect;
	}
	
	/*
	public boolean contains(ArrayList<Rect> otherHitBox){
		//if number of hitboxes contained is equal to the total number of other hitboxes
		//then contains is true (if entire otherHitBox is contained)
		int otherHitBoxes = otherHitBox.size(); 
		int numberHitBoxesContained = 0;
		for (Rect rect: hitBox){
			for (Rect otherRect:otherHitBox){
				if (rect.contains(otherRect)){
					numberHitBoxesContained++;
				}
			}
		}
		return hitBox.contains(otherHitBox);
	}
	*/
	
	public boolean isEnemy(){
		return enemy;
	}
	
	public void decHealth(int decAmount){
		health = health - decAmount;
		if (health<0){
			health = 0;
		}
		game.addAnimation(new HitAnimation(game, this));
		//game.addAnimation(new ScreenFlashAnimation(game));

	}
	
	
	public void incHealth(int incAmount){
		health = health +incAmount;

		if (health>maxHealth){
			health=maxHealth;
		}
	}
	
	
	public int getHealth(){
		return health;
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}
	
	
	public void setBitmap(Bitmap bMap){
		bitmap = bMap;
	}
	
	@Override
	public int compareTo(GameEntity other){
		if (this.getSpawnPosition() == other.getSpawnPosition()){
			return 0;
		}else if (this.getSpawnPosition() > other.getSpawnPosition()){
			return 1;
		} else{
			return -1;
		}
		
	}
	
}
