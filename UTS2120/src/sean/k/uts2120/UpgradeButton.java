package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;

abstract class UpgradeButton extends GameButton{

	final static float WIDTH_PERCENT_WIDTH = .22f;
	final static float HEIGHT_PERCENT_WIDTH = .22f;
	//private Bitmap bitmap;
	public boolean selected;
	//public Matrix paintMatrix;
	public float heightRatio;
	public float widthRatio;
	

	/*
	 * implementation of GameButton that holds an upgrade() function.  These 
	 * are the buttons present in the UpgradeScreen.
	 */
	public UpgradeButton(GameThread theThread, float xPos, float yPos) {
		super(theThread, Game.screenWidth*WIDTH_PERCENT_WIDTH, Game.screenWidth*HEIGHT_PERCENT_WIDTH, xPos, yPos);
		/*
		bitmap = thread.getGame().getBitmap(theBitmapName);
		
		if (bitmap!=null){
		float bMapHeight = thread.getGame().getBitmap(theBitmapName).getHeight();
		float bMapWidth = thread.getGame().getBitmap(theBitmapName).getWidth();
		
		
		heightRatio = height/bMapHeight;
		widthRatio = width/bMapWidth;
		}
		paintMatrix = new Matrix();
		*/
		selected = false;
		
		paint.setTextSize(height/2);
	}

	@Override
	public void draw(Canvas canvas) {

		canvas.drawRect(hitbox, gradientPaint);

		drawIcon(canvas);
		/*
		 * if (bitmap!=null){ //c.drawBitmap(bitmap, xPos-width/2,
		 * yPos-height/2, paint);
		 * 
		 * paintMatrix.reset(); paintMatrix.postScale(widthRatio, heightRatio);
		 * paintMatrix.postTranslate(hitbox.centerX()-width/2,
		 * hitbox.centerY()-height/2);
		 * 
		 * //for debugging hitboxes paint.setColor(Color.RED); for (int i = 0;
		 * i<hitBox.size(); i++){ c.drawRect(hitBox.get(i), paint); }
		 * 
		 * paint.setColor(Color.WHITE);
		 * canvas.drawBitmap(bitmap,paintMatrix,paint);
		 * 
		 * 
		 * }
		 */

		if (selected) {
			paint.setColor(Color.YELLOW);
			canvas.drawRect(hitbox, paint);
		}

	}
	
	abstract void drawIcon(Canvas canvas);

	@Override
	void click() {
		
		if (!selected && available()){
		((UpgradeScreen) thread.getScreen()).select(this);
		} 
	}
	
	abstract void upgrade();
	
	abstract int getPrice();
	
	abstract String getMessage();
	
	abstract boolean available();

}
