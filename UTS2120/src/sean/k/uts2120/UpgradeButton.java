package sean.k.uts2120;

abstract class UpgradeButton {


	UpgradeMenu menu;
	Game game;
	public boolean selected;
	private int iconImageID;

	
	public UpgradeButton(UpgradeMenu menu, Game game, int iconImageID) {

		selected = false;
		this.menu = menu;
		this.game = game;
		this.iconImageID = iconImageID;
		
	}

	

	void click() {
		
		
		if (!selected && available()){
		menu.select(this);
		} 
		
	}
	
	
		
	abstract void upgrade();
	
	abstract int getPrice();
	
	abstract String getMessage();
	
	abstract boolean available();
	
	public int getIconID(){
		return iconImageID;
	}

}
