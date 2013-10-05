package sean.k.uts2120;

import android.graphics.Color;
import android.graphics.Paint.Align;

public class MoneyEffect extends Effect{

	final static float HEIGHT_PERCENT= .03f;
	final static float WIDTH_PERCENT = .03f;
	final static int LIFETIME = 20;
	
	/*
	 * an effect that happens when money is awarded during gameplay.  This
	 * happens for instance when the bus picks up passengers.
	 */
	public MoneyEffect(Game theGame, float xPosition, float yPosition, MoneyDrop theMoney) {
		super(theGame, theMoney.getImageID(), xPosition, yPosition, WIDTH_PERCENT*Game.screenHeight, HEIGHT_PERCENT*Game.screenHeight,LIFETIME);
		paint.setTextSize(Game.screenHeight*HEIGHT_PERCENT);
		paint.setColor(Color.WHITE);
		paint.setTextAlign(Align.CENTER);
		velocityY = Game.gameSpeedNormal*-1f;
	}

	

}