package sean.k.uts2120;

import android.graphics.Color;

public class MainMenuScreen extends GameScreen{

	int backgroundColor;
	final static int TEXT_SIZE = 70;
	final static int BUTTON_WAIT_TIME = (int) (1000f/Game.THREAD_WAIT_TIME);
	
/*
 * the screen and splash screen when the game is first opened or when gameplay
 * is quit.  It contains a bitmap background image.
 */
	public MainMenuScreen(GameThread theThread) {
		super(theThread, R.drawable.menuscreen, BUTTON_WAIT_TIME);
		buttons.add(new NewGameButton(thread, Game.screenHeight/2));
		backgroundColor = Color.parseColor("#ff000000");
		paint.setTextSize(TEXT_SIZE);
		paint.setColor(Color.WHITE);

	}
	


}
