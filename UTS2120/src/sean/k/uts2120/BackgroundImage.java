package sean.k.uts2120;

import android.graphics.Canvas;

public class BackgroundImage extends GameEntity{

	float initialWidth;	//the starting width of the image
	float initialHeight; //the starting height of the image
	
	float baseY;	//beginning yPosition, used to calculate yTranslation
	float baseX; //beginning xPosition, used to calculate xTranslation
	float xTranslate; //how much the image will move left or right
	float yTranslate; //how much the image will move up or down
	float endingScale; //float representing percent the image will shrink or expand
	float startingScale; //float representing beginning scale of image based on width/height
	float growthFactor;
	float distance; //total distance that the image will be changed over
	
	/*	Images that do not interact with other game entities and are drawn in the background.  
	 * 	BaseHeight and baseWidth are used for bitmap resolution.
	 * 
	 */
	public BackgroundImage(Game theGame, int theImageID, float xPosition,
			float yPosition, float baseWidth, float baseHeight, float theTotalDistance, float startScale, float endScale, float scaleGrowthFactor, float theXTranslate, float theYTranslate) {
		super(theGame, theImageID, xPosition, yPosition, baseWidth, baseHeight);
		initialWidth = width *startScale;
		initialHeight = height *startScale;
		xTranslate = theXTranslate;
		yTranslate = theYTranslate;
		baseX = xPosition;
		baseY = yPosition;
		endingScale = endScale;
		startingScale = startScale;
		growthFactor = scaleGrowthFactor;
		distance = theTotalDistance;
	}

	
	@Override
	public void draw(Canvas c){
		if (bitmap!=null){
		float bMapHeight = bitmap.getHeight();
		float bMapWidth = bitmap.getWidth();
		
		
		heightRatio = height/bMapHeight;
		widthRatio = width/bMapWidth;
		}
		
		super.draw(c);
	}
	
	@Override
	public void update(){
		setY(baseY+yTranslate*(game.getCurrentLevel().getTraveled()-spawnPosition)/distance); //baseY + yTranslate/fractionDistance
		setX(baseX+xTranslate*(game.getCurrentLevel().getTraveled()-spawnPosition)/distance); //baseY + yTranslate/fractionDistance

		scale((float) ((endingScale/startingScale-1f)*Math.pow(((game.getCurrentLevel().getTraveled()-spawnPosition)/distance), growthFactor))); //
		if (getY()-getHeight()/2>Game.screenHeight+Game.topMarginHeight){
			remove= true;
		}
		
	}
	
	public void scale(float fractionScale){
		width = initialWidth + initialWidth*(fractionScale);
		height =initialHeight + initialHeight*(fractionScale);
	}
}
