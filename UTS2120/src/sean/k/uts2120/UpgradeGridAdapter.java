package sean.k.uts2120;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UpgradeGridAdapter extends BaseAdapter{
	private GameActivity activity;
	private UpgradeMenu menu;
	
	public UpgradeGridAdapter(GameActivity activity, UpgradeMenu menu) {
		this.activity = activity;
		this.menu = menu;
	}

	// Total number of things contained within the adapter
	public int getCount() {
		return menu.getUpgradeButtons().size();
	}

	//
	public Object getItem(int position) {
		return menu.getUpgradeButtons().get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View gridView;
		


		if (convertView == null) {

			gridView = new View(activity);

			// get layout from inventoryitem.xml
			gridView = inflater.inflate(R.layout.upgrade_grid_item, null);
			



			//Item item = universe.inventory.get(position);

			//imageView.setImageResource(item.getImage());
			

		} else {
			gridView = (View) convertView;
		}
		
		// set image based on selected text
		View upgrade = gridView
				.findViewById(R.id.upgrade_grid_item);
		ImageView upgradeIcon = (ImageView) gridView.findViewById(R.id.upgrade_grid_item_image);

		UpgradeButton button = menu.getUpgradeButtons().get(position);
		
		//if upgrade not available
		if (!button.available()){
			upgrade.setVisibility(View.INVISIBLE);
		} else{
			upgrade.setVisibility(View.VISIBLE);
		}
		if (button.selected){
			upgrade.setBackgroundColor(Color.YELLOW);
			upgradeIcon.setVisibility(View.INVISIBLE);
		} else{
			upgrade.setBackgroundColor(Color.TRANSPARENT);
			upgradeIcon.setVisibility(View.VISIBLE);

		}
		

		return gridView;
	}
}
