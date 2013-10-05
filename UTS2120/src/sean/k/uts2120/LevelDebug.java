package sean.k.uts2120;

import android.graphics.Canvas;

public class LevelDebug extends Level{

	public LevelDebug(Game theGame) {
		super(theGame, Level.NO_IMAGE, 100f);

	}

	@Override
	void drawBackground(Canvas c) {

	}

	@Override
	void buildLevel() {

	}

	@Override
	String getObjectiveString() {
		/*String accelerometer = "X: "+Math.round(tiltLastX)+"(" + tiltDeltaX+"), Y: "
				+Math.round(tiltLastY)+"(" + tiltDeltaY+"), Z: " +Math.round(tiltLastZ) +"("+tiltDeltaZ+")";*/
		String accelerometer = "X: "+Math.round(tiltLastX)+" Y: "
				+Math.round(tiltLastY)+" Z: " +Math.round(tiltLastZ) +" VEL: "+Math.round(game.getPlayer().getVelocityX());

		return accelerometer;
	}

	@Override
	Level nextLevel() {

		return this;
	}

	@Override
	boolean isComplete() {

		return false;
	}

}
