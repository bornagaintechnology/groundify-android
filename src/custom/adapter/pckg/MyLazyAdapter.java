package custom.adapter.pckg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import deftsoft.android.iground.utility.ViewConseqDetail;
import deftsoft.iground.android.R;

public class MyLazyAdapter extends BaseAdapter {

	private ArrayList<ViewConseqDetail> myviewConseqList;
	private Context current;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public MyLazyAdapter(Context con,
			ArrayList<ViewConseqDetail> myviewConseqList,
			ImageLoader imageLoader, DisplayImageOptions options) {
		// TODO Auto-generated constructor stub

		this.current = con;
		this.myviewConseqList = myviewConseqList;
		this.imageLoader = imageLoader;
		this.options = options;

		inflater = (LayoutInflater) current
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myviewConseqList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return myviewConseqList.get(position);
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

			convertView = inflater.inflate(R.layout.listrow_inflate, null);
			holder = new ViewHolder();
			holder.child_nameText = (TextView) convertView
					.findViewById(R.id.child_nameText);

			holder.conseq_nameText = (TextView) convertView
					.findViewById(R.id.conseq_nameText);

			holder.coseq_endtext = (TextView) convertView
					.findViewById(R.id.coseq_endtext);
			holder.list_image = (ImageView) convertView
					.findViewById(R.id.list_image);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.child_nameText.setText(myviewConseqList.get(position)
				.getChild_name());
		holder.conseq_nameText.setText(myviewConseqList.get(position)
				.getConsequence());
		holder.coseq_endtext.setText(myviewConseqList.get(position)
				.getCon_datetime());
		imageLoader.displayImage(myviewConseqList.get(position).getImage(),
				holder.list_image, options, animateFirstListener);

		return convertView;

	}

	private class ViewHolder {

		TextView child_nameText, conseq_nameText, coseq_endtext;
		ImageView list_image;
	}

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

}
