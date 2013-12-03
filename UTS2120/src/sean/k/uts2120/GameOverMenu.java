package sean.k.uts2120;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GameOverMenu extends GameMenu{

	public GameOverMenu(GameActivity activity) {
		super(activity, GameActivity.MENU_GAME_OVER);
	}

	@Override
	void initialize() {
		
		final TextView gameOverBonusText = (TextView)activity.findViewById(R.id.game_over_screen_bonus);
		String allBonuses = "DPM: " + activity.getGame().getScore() + "\n";
		if (activity.getGame().newHighScore){
			allBonuses += "NEW HIGH SCORE\n";
		}
		
		final String bonusString = allBonuses;
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				gameOverBonusText.setText(bonusString);
			}

		});
		
		Button gameOverScreenQuit = (Button) activity.findViewById(R.id.game_over_screen_quit);
		gameOverScreenQuit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				activity.getThread().quit();
			}
			
		});		
	}

}
