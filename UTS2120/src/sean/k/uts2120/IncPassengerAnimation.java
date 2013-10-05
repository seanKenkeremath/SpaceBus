package sean.k.uts2120;

import android.graphics.Color;
import android.graphics.Paint;

public class IncPassengerAnimation extends GameAnimation{
	
	int passColor = Color.GREEN;
	Paint passPaint;
	final static float DURATION = 20;
	
	/*
	 * makes the passenger display flash green
	 */
	public IncPassengerAnimation(Game theGame, Paint thePassengerPaint) {
		super(theGame, DURATION, true);
		passPaint = thePassengerPaint;
	}

	@Override
	void animate() {
		if (age==0){

		}
		
		if (age<duration){
			//target.getPaint().setARGB((int) (255-(DURATION*255f/20f)),255,0,0);
			passPaint.setColor(passColor);
		age++;
		} else{
			end();
		}
	}

	@Override
	void end() {
		passPaint.setColor(Color.WHITE);
		remove = true;
	}

}
