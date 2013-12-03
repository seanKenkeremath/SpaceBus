package sean.k.uts2120;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GameCompleteMenu extends GameMenu{

	public GameCompleteMenu(GameActivity activity, long delay) {
		super(activity, GameActivity.MENU_GAME_COMPLETE);
		displayDelay = delay;
	}

	@Override
	void initialize() {
		
		
		final TextView levelBonusText = (TextView)activity.findViewById(R.id.game_complete_screen_bonus);
		String allBonuses = "DPM: " + activity.getGame().getScore() + "\n";
		if (activity.getGame().newHighScore){
			allBonuses += "NEW HIGH SCORE\n";
		}
		if (activity.getGame().getCurrentLevel().allPassengers()){
			
			allBonuses += "Level Bonus: All Passengers (" + Game.ALL_PASSENGER_BONUS +" DPM)\n";
			}
		
		final String bonusString = allBonuses;
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				levelBonusText.setText(bonusString);
			}

		});
		
		Button quitButton = (Button) activity.findViewById(R.id.game_complete_screen_quit);
		quitButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				activity.getThread().quit();

			}
			
		});		
		
	}

}
