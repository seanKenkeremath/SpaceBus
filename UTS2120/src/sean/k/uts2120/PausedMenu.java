package sean.k.uts2120;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PausedMenu extends GameMenu{

	public PausedMenu(GameActivity activity) {
		super(activity, GameActivity.MENU_PAUSE);
	}

	@Override
	void initialize() {
		Button pausedScreenResume = (Button) activity.findViewById(R.id.paused_screen_resume);
		pausedScreenResume.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				activity.getThread().unpause();
				activity.getMenuFlipper().setVisibility(View.INVISIBLE);

			}
			
		});
		
		Button pausedScreenQuit = (Button) activity.findViewById(R.id.paused_screen_quit);
		pausedScreenQuit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				activity.getThread().quit();

			}
			
		});		
	}

}
