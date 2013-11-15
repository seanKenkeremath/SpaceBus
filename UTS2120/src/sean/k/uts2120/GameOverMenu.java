package sean.k.uts2120;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameOverMenu extends GameMenu{

	public GameOverMenu(GameActivity activity) {
		super(activity, GameActivity.MENU_GAME_OVER);
	}

	@Override
	void initialize() {
		Button gameOverScreenQuit = (Button) activity.findViewById(R.id.game_over_screen_quit);
		gameOverScreenQuit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				activity.getThread().quit();
			}
			
		});		
	}

}
