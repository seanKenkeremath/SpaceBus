package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Paint.Align;
import android.graphics.Rect;

abstract class GameButton {
	
	protected Rect hitbox;
	protected GameThread thread;
	protected float width;
	protected float height;
	protected Paint paint;
	protected Paint gradientPaint;
	RectF drawnShape;

	
	/*
	 * This is an abstract class for all buttons in the UI.  To implement 
	 * this class you must implement click() which is the method
	 * that is run when that button is pressed.  You may also overwrite draw()
	 * to change how the button is drawn.  The X, Y, width and height of the button
	 * must be provided in the constructor.
	 */
	public GameButton(GameThread theThread, float theWidth, float theHeight, float xPos, float yPos){
		
		width = theWidth;
		height = theHeight;
		hitbox = new Rect((int)(xPos-width/2), (int)(yPos-height/2), (int)(xPos+width/2), (int)(yPos+height/2));
		thread = theThread;
		paint = new Paint();
		paint.setTextAlign(Align.CENTER);
		gradientPaint = new Paint();
		drawnShape = new RectF(hitbox);
		gradientPaint.setShader(new LinearGradient(hitbox.left,hitbox.bottom,hitbox.right+width,hitbox.top-height,Color.BLACK, Color.WHITE,Shader.TileMode.MIRROR));
	}
	
	public void draw(Canvas canvas){
		canvas.drawRoundRect(drawnShape,(float)hitbox.width()*.1f,(float)hitbox.height()*10,gradientPaint);
	}
	abstract void click();
	
	public boolean contains(float xCoor, float yCoor){
		return hitbox.contains((int)xCoor,(int)yCoor);
	}

	
	public Rect getHitBox(){
		return hitbox;
	}
	
	
	
}
