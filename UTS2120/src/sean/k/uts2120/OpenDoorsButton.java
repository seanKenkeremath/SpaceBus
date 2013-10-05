package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Paint.Style;

public class OpenDoorsButton extends GameButton{

	final static float HEIGHT_PERCENT_MARGIN = 1f;
	final static float WIDTH_PERCENT_WIDTH = .2f;
	final static String TEXT = "Doors";
	
	/*
	 * the button that picks up nearby passengers.
	 * It is only visible when Player is near a bus stop.
	 */
	public OpenDoorsButton(GameThread theThread) {
		super(theThread, WIDTH_PERCENT_WIDTH*Game.screenWidth, HEIGHT_PERCENT_MARGIN*Game.bottomMarginHeight, Game.screenWidth-(Game.screenWidth*WIDTH_PERCENT_WIDTH)/2, Game.totalHeight-Game.bottomMarginHeight/2);
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(Game.screenWidth*WIDTH_PERCENT_WIDTH/(float)TEXT.length());
		drawnShape = new RectF(hitbox.left,hitbox.top+height/4,hitbox.right,hitbox.bottom-height/4);
		gradientPaint.setShader(new LinearGradient(hitbox.left,hitbox.bottom,hitbox.right+width,hitbox.top-height,0xff002200, 0xff00DD00,Shader.TileMode.MIRROR));
	}

	@Override
	public void draw(Canvas canvas) {
		if (thread.getGame().getPlayer().nearPassengers){
		super.draw(canvas);
		paint.setColor(Color.WHITE);
		canvas.drawText(TEXT,drawnShape.centerX(), drawnShape.centerY(),paint);
		//canvas.drawText(thread.getGame().getCurrentWeapon().getName(),drawnShape.centerX(), drawnShape.centerY(),paint);
		}
		
		
	}

	@Override
	void click() {
		if (thread.getGame().getPlayer().nearPassengers){
		thread.getGame().getPlayer().openDoors();
		}
		
	}

}
