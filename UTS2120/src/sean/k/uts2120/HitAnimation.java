package sean.k.uts2120;

import android.graphics.Color;
import android.graphics.LightingColorFilter;

public class HitAnimation extends GameAnimation{
	
	LightingColorFilter filter;
	
	GameEntity target;
	final static float DURATION = 10;
	
	/*
	 * this is the animation that is created when an entity is hit
	 * upon creation of this animation, a filter is applied to the paint
	 * of the entity that makes it appear red.
	 */
	public HitAnimation(Game theGame, GameEntity theTarget) {
		super(theGame, DURATION, false);
		target = theTarget;
		filter = new LightingColorFilter(Color.RED, 1);
	}

	@Override
	void animate() {
		if (age==0){

		}
		
		if (age<duration){
			//target.getPaint().setARGB((int) (255-(DURATION*255f/20f)),255,0,0);
			target.getPaint().setColor(Color.RED);
			target.getPaint().setColorFilter(filter);
		age++;
		} else{
			end();
		}
	}

	@Override
	void end() {
		target.getPaint().setColorFilter(null);
		target.getPaint().setColor(Color.WHITE);
		remove = true;
	}

}
