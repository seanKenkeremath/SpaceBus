package sean.k.uts2120;

abstract class UpgradeButton {


	UpgradeMenu menu;
	Game game;
	public boolean selected;

	
	public UpgradeButton(UpgradeMenu menu, Game game) {

		selected = false;
		this.menu = menu;
		this.game = game;
		
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

}
