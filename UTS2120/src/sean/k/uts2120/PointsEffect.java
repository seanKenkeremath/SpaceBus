package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;

public class PointsEffect extends Effect{

	final static float HEIGHT_PERCENT= .03f;
	final static float WIDTH_PERCENT = 0f;
	final static int LIFETIME = 20;
	private int color;
	private int points;
	
	/*
	 * visual Effect created when score points are awarded during the Game.
	 */
	public PointsEffect(Game theGame, int thePoints, float xPosition, float yPosition, int effectColor) {
		super(theGame, GameEntity.NO_IMAGE, xPosition, yPosition, WIDTH_PERCENT*Game.screenHeight, HEIGHT_PERCENT*Game.screenHeight,LIFETIME);
		paint.setTextSize(Game.screenHeight*HEIGHT_PERCENT);
		paint.setColor(Color.WHITE);
		paint.setTextAlign(Align.CENTER);
		velocityY = Game.gameSpeedNormal*-1f;
		points = thePoints;
		color = effectColor;
		paint.setColor(color);
	}
	
	@Override 
	public void draw(Canvas canvas){

		canvas.drawText(""+points,xPos-width/2, yPos-height/2, paint);
	}
	

}
