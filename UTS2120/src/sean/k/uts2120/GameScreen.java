package sean.k.uts2120;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.view.MotionEvent;

abstract class GameScreen {

	protected ArrayList<GameButton> buttons;
	protected GameThread thread;
	protected Paint paint;
	protected Matrix paintMatrix;
	final static int BUTTON_PADDING = 10;
	final static int NO_BACKGROUND_IMAGE = -1;
	protected int backgroundImage;
	protected float heightRatio;
	protected float widthRatio;
	int buttonWaitTime;
	
	//update? could allow animations and splashscreen.  would call during update game state, etc, 
	//could put separate animations

	/*
	 * This is the abstract class that implements different screens to be used
	 * in a game.  This class has communication with the thread and holds GameButton objects.
	 * By default, the touch() method click() on all child buttons and passes the x and y 
	 * coordinates of that event.  Enter() and Exit() are called when the screen is first
	 * created and changed respectively.  a waitTime can be specified that prevents drawing of buttons
	 * and any interaction with the screen until that time is up.  This can be used
	 * to create splash screens or prevent users from accidentally clicking things.
	 */
	public GameScreen(GameThread theThread, int backgroundImageID, int waitTime) {
		thread = theThread;
		buttons = new ArrayList<GameButton>();
		paint = new Paint();
		paint.setStyle(Style.FILL);
		paint.setTextAlign(Align.CENTER);
		paintMatrix = new Matrix();
		backgroundImage = backgroundImageID;
		buttonWaitTime = waitTime;
	}

	
	public void update(){
		if (buttonWaitTime>0){
			buttonWaitTime--;
		}
	}

	public void drawBackground(Canvas canvas){
		Bitmap bitmap = thread.getGame().getBitmap(backgroundImage);
		if (bitmap!=null){
			
			paintMatrix.reset();
			paintMatrix.postScale(widthRatio, heightRatio);
			//paintMatrix.postTranslate(xPos-width/2, yPos-height/2);
			
			//paint.setColor(Color.WHITE);
			canvas.drawBitmap(bitmap,paintMatrix,paint);
			
		}
	}
	
	public void exit(){
		thread.getGame().unrenderScreen(this);
	}
	
	public void enter(Context context){
		thread.getGame().renderScreen(context, this);
		
		Bitmap bitmap = thread.getGame().getBitmap(backgroundImage);
		if (bitmap!=null){
		float bMapHeight = bitmap.getHeight();
		float bMapWidth = bitmap.getWidth();
		
		
		heightRatio = Game.totalHeight/bMapHeight;
		widthRatio = Game.screenWidth/bMapWidth;
	}
	}
	
	public int getBackgroundImageID(){
		return backgroundImage;
	}

	public void draw(Canvas canvas) {
		drawBackground(canvas);
		if (buttonWaitTime==0){
			drawPanel(canvas);
			for (GameButton button : buttons) {
				button.draw(canvas);
			}
		}

	}
	
	public void drawPanel(Canvas canvas){
		//only implement on menu screens (change to abstract)
	}

	public void touch(MotionEvent event) {

		if (buttonWaitTime>0){
			return;
		}
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			float xCoor = event.getX();
			float yCoor = event.getY();

			for (GameButton button : buttons) {
				if (button.contains(xCoor, yCoor)) {
					button.click();
				}
			}

		}

	}
}
