package custom.adapter.pckg;

import java.util.ArrayList;

import deftsoft.iground.android.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MySpinnerAdapter extends BaseAdapter {

	private ArrayList<String> childFirstNameList;
	private Context current;
	private LayoutInflater inflater;

	public MySpinnerAdapter(Context con, ArrayList<String> childFirstNameList) {

		this.current = con;
		this.childFirstNameList = childFirstNameList;

		inflater = (LayoutInflater) current
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return childFirstNameList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return childFirstNameList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder;

		if (convertView == null) {

			convertView = inflater.inflate(R.layout.listinflate, null);
			holder = new ViewHolder();
			holder.listinflate_textView = (TextView) convertView
					.findViewById(R.id.listinflate_textView);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.listinflate_textView.setText(childFirstNameList.get(position));
		
		holder.listinflate_textView.setTextColor(Color.parseColor("#000000"));
		
		holder.listinflate_textView.setTextSize(17);

		return convertView;

	}

	private class ViewHolder {

		TextView listinflate_textView;
	}

}
