package sean.k.uts2120;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;


public class ColorCircle extends GameEntity{
	
	int lifeTime;
	
	public ColorCircle(Game theGame, float xPosition, float yPosition, int theWidth, int theHeight) {
		super(theGame, GameEntity.NO_IMAGE, xPosition, yPosition, theWidth, theHeight);
		color = Color.RED;
		lifeTime = 1000;
		//velocityX = (float) randomVelocity();
		//velocityY = (float) randomVelocity();
		velocityY =  Game.THREAD_WAIT_TIME;
		//velocityY = 10;
		width = theWidth;
		height = theHeight;
		paint.setColor(color);
		paint.setStyle(Style.FILL);
	}

	@Override
	public void draw(Canvas c) {

		c.drawCircle((float)xPos, (float)yPos, width/2, paint);
		
	}
	

	
	public double randomAngle(){
		return Math.random()*2*Math.PI;
	}
	
	public double randomVelocity(){
		return Math.random()*10;
	}

	@Override
	public void update() {
		super.update();
		if (age>lifeTime){
			remove = true;
		}
		
		//incVelocityY((float) (Game.GRAVITY));

		
	}


}
