package sean.k.uts2120;

import android.graphics.Color;
import android.graphics.Paint;

public class IncGoldAnimation extends GameAnimation{
	
	int goldColor = Color.GREEN;
	Paint goldPaint;
	final static float DURATION = 20;
	
	/*
	 * makes the money display flash green
	 */
	public IncGoldAnimation(Game theGame, Paint theGoldPaint) {
		super(theGame, DURATION, true);
		goldPaint = theGoldPaint;
	}

	@Override
	void animate() {
		if (age==0){

		}
		
		if (age<duration){
			//target.getPaint().setARGB((int) (255-(DURATION*255f/20f)),255,0,0);
			goldPaint.setColor(goldColor);
		age++;
		} else{
			end();
		}
	}

	@Override
	void end() {
		goldPaint.setColor(Color.WHITE);
		remove = true;
	}

}
