package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.RectF;

public class PauseButton extends GameButton{
	
	final static float HEIGHT_PERCENT_MARGIN = 1f;
	final static float WIDTH_PERCENT_WIDTH = .2f;
	
	/*
	 * pauses the thread. Appears during gameplay
	 */
	public PauseButton(GameThread theThread) {
		super(theThread, WIDTH_PERCENT_WIDTH*Game.screenWidth, HEIGHT_PERCENT_MARGIN*Game.bottomMarginHeight, (Game.screenWidth*WIDTH_PERCENT_WIDTH)/2, Game.totalHeight-Game.bottomMarginHeight/2);
		//gameplay = true;
		paint.setColor(Color.WHITE);
		paint.setTextSize(HEIGHT_PERCENT_MARGIN/4 * Game.bottomMarginHeight);
		paint.setStyle(Style.FILL);
		drawnShape = new RectF(hitbox.left,hitbox.top+height/4,hitbox.right,hitbox.bottom-height/4);
		
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		canvas.drawText("||",drawnShape.centerX(),drawnShape.centerY(),paint);
	}

	@Override
	void click() {
		thread.pause(new PausedScreen(thread));
		
	}

}
