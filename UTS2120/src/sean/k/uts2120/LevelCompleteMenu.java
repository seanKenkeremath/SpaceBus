package sean.k.uts2120;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LevelCompleteMenu extends GameMenu{

	public LevelCompleteMenu(GameActivity activity, long delay) {
		super(activity, GameActivity.MENU_LEVEL_COMPLETE);
		displayDelay = delay;
	}

	@Override
	void initialize() {
		
		/*	if (thread.getGame().getCurrentLevel().allPassengers()){
		canvas.drawText("Bonus: All Passengers (" + Game.ALL_PASSENGER_BONUS +" DPM)", Game.screenWidth*.5f, Game.topMarginHeight+paint.getTextSize()+bonusPaint.getTextSize(),bonusPaint);
		}
		*/
		
		Button nextLevelButton = (Button)activity.findViewById(R.id.level_complete_screen_next);
		nextLevelButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				activity.getThread().exitLevel(activity.getGame().getCurrentLevel());
				activity.getThread().startLevel(activity.getGame().getCurrentLevel().nextLevel());
				activity.getThread().unpause();
				activity.getMenuFlipper().setVisibility(View.INVISIBLE);

			}
			
		});
		
		Button quitButton = (Button) activity.findViewById(R.id.level_complete_screen_quit);
		quitButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				activity.getThread().quit();

			}
			
		});		
		
		Button upgradesButton = (Button)activity.findViewById(R.id.level_complete_screen_upgrade);
		upgradesButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				activity.getThread().pause(new UpgradeMenu(activity));

			}
			
		});
	}

}
