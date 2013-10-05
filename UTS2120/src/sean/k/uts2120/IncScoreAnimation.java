package sean.k.uts2120;

import android.graphics.Color;
import android.graphics.Paint;

public class IncScoreAnimation extends GameAnimation{
	
	int scoreColor = Color.GREEN;
	Paint scorePaint;
	final static float DURATION = 20;
	
	/*
	 * makes the score display flash green
	 */
	public IncScoreAnimation(Game theGame, Paint theScorePaint) {
		super(theGame, DURATION, true);
		scorePaint = theScorePaint;
	}

	@Override
	void animate() {
		if (age==0){

		}
		
		if (age<duration){
			scorePaint.setColor(scoreColor);
		age++;
		} else{
			end();
		}
	}

	@Override
	void end() {
		scorePaint.setColor(Color.WHITE);
		remove = true;
	}

}