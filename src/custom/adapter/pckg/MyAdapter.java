package custom.adapter.pckg;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import deftsoft.android.iground.utility.ChildDetail;
import deftsoft.iground.android.R;

public class MyAdapter extends BaseAdapter {

	Context context;

	ArrayList<ChildDetail> childDetails;

	private final String TAG = "MyAdapter";

	public MyAdapter(ArrayList<ChildDetail> childDetailList, Context context) {
		// TODO Auto-generated constructor stub

		this.childDetails = childDetailList;

		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return childDetails.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return childDetails.get(position).getChildName();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return Integer.parseInt(childDetails.get(position).getChildID());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder;

		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {

			holder = new ViewHolder();

			convertView = layoutInflater.inflate(R.layout.listinflate, null);

			holder.text = (TextView) convertView

			.findViewById(R.id.listinflate_textView);

			convertView.setTag(holder);

		}

		else {

			holder = (ViewHolder) convertView.getTag();

		}

		holder.text.setText(childDetails.get(position).getChildName());

		return convertView;
	}

	public class ViewHolder {

		private TextView text;
	}
}
