package sean.k.uts2120;

import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;

public class ScreenFlashAnimation extends GameAnimation{
	
	LightingColorFilter filter;
	
	Level target;
	final static float DURATION = 10;
	
	public ScreenFlashAnimation(Game theGame) {
		super(theGame, DURATION, false);
		target = game.getCurrentLevel();
		filter = new LightingColorFilter(Color.WHITE, 500);
	}

	@Override
	void animate() {
		if (age==0){

		}
		
		if (age<duration){
			//target.getPaint().setARGB((int) (255-(DURATION*255f/20f)),255,0,0);
			target.getPaint().setColor(Color.WHITE);
			target.getPaint().setColorFilter(filter);
		age++;
		} else{
			end();
		}
	}

	@Override
	void end() {
		target.setPaint(new Paint());
		remove = true;
	}

}
