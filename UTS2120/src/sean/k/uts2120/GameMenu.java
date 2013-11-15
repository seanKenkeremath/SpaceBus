package sean.k.uts2120;

import android.view.View;

abstract class GameMenu {

	GameActivity activity;
	int flipperIndex;
	long displayDelay;

	public GameMenu(GameActivity activity, int flipperIndex) {
		this.activity = activity;
		this.flipperIndex = flipperIndex;
		this.displayDelay = 0L;
		initialize();
	}

	public void execute() {
		if (displayDelay == 0L) {
			display();
		} else {
			Thread thread = new Thread() {
				@Override
				public void run() {

					try {
						Thread.sleep(displayDelay);
					} catch (InterruptedException e) {
					}
					display();
				}
			};

			thread.run();
		}

	}

	private void display() {

		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				activity.getMenuFlipper().setDisplayedChild(flipperIndex);
				activity.getMenuFlipper().setVisibility(View.VISIBLE);
			}

		});

	}
	
	abstract void initialize();
}
