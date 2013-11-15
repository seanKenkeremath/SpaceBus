package sean.k.uts2120;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class UpgradeGridAdapter extends BaseAdapter{
	private Context context;
	private UpgradeMenu menu;
	
	public UpgradeGridAdapter(Context context, UpgradeMenu menu) {
		this.context = context;
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

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View gridView;
		


		if (convertView == null) {

			gridView = new View(context);

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
