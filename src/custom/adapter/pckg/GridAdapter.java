package custom.adapter.pckg;

import iground.child.interfaces.Consequnceselected;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import deftsoft.android.iground.utility.GetNewCosequence;
import deftsoft.iground.android.R;

public class GridAdapter extends BaseAdapter {

	Context context;

	// ArrayList<Uri> uriArray;

	private Integer[] imageurl;

	private ImageLoader imageLoader;

	private DisplayImageOptions options;

	private ArrayList<GetNewCosequence> getnewCoseqList2;

	private ArrayList<String> predefinedConseqName;

	private ArrayList<String> predefinedConseqImageName;

	int arraySiz = 0;

	int finalsize = 0;

	Consequnceselected consequnceselected;

	public GridAdapter(Integer[] imageurl2, Context context,

	ImageLoader imageLoader, DisplayImageOptions options,

	ArrayList<GetNewCosequence> getnewCoseqList2,
			ArrayList<String> predfineConseqName,
			ArrayList<String> predfineConseqImagename) {

		// TODO Auto-generated constructor stub

		this.imageurl = imageurl2;

		arraySiz = imageurl.length;

		this.imageLoader = imageLoader;

		this.options = options;

		this.context = context;

		this.getnewCoseqList2 = getnewCoseqList2;

		this.predefinedConseqName = predfineConseqName;

		this.predefinedConseqImageName = predfineConseqImagename;

		System.out.println("size1      " + arraySiz);

		System.out.println("size1      " + getnewCoseqList2.size());

		finalsize = (arraySiz + getnewCoseqList2.size() + 1);

		System.out.println("size1      " + finalsize);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return finalsize;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub

		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		final ViewHolder holder;

		if (view == null) {
			System.out.println("here 3 ");

			holder = new ViewHolder();

			view = layoutInflater.inflate(R.layout.customgrid_layout, null);

			holder.thumbImage = (ImageView) view.findViewById(R.id.thumbImage);

			holder.checkBox = (CheckBox) view.findViewById(R.id.itemCheckBox);

			holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
			view.setTag(holder);

		} else {

			holder = (ViewHolder) view.getTag();
		}

		holder.checkBox.setId(position);

		holder.thumbImage.setId(position);

		holder.checkBox.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				CheckBox cb = (CheckBox) v;
				int id = cb.getId();

			}
		});

		// if (position > 4) {

		// imageLoader.displayImage(getnewCoseqList2.get(position)
		// .getConsequenceimage().toString(), holder.imageView,
		// options, new SimpleImageLoadingListener() {
		// @Override
		// public void onLoadingStarted(String imageUri, View view) {
		// holder.progressBar.setProgress(0);
		// holder.progressBar.setVisibility(View.VISIBLE);
		// }
		//
		// @Override
		// public void onLoadingFailed(String imageUri, View view,
		// FailReason failReason) {
		// holder.progressBar.setVisibility(View.GONE);
		// }
		//
		// @Override
		// public void onLoadingComplete(String imageUri,
		// View view, Bitmap loadedImage) {
		// holder.progressBar.setVisibility(View.GONE);
		// }
		// }, new ImageLoadingProgressListener() {
		// @Override
		// public void onProgressUpdate(String imageUri,
		// View view, int current, int total) {
		// holder.progressBar.setProgress(Math.round(100.0f
		// * current / total));
		// }
		// });
		// } else {

		System.out.println("size    1         " + position);
		System.out.println("size    2         " + arraySiz);
		System.out.println("size    3         " + getnewCoseqList2.size());

		if (position < arraySiz) {

			holder.thumbImage.setBackgroundResource(imageurl[position]);
			holder.progressBar.setVisibility(View.GONE);

		}

		else if (position >= arraySiz
				&& position < arraySiz + getnewCoseqList2.size()) {
			// imageLoader.displayImage(getnewCoseqList2.get(position).getConsequenceimage(),
			// holder.thumbImage, options,
			// new SimpleImageLoadingListener() {
			// public void onLoadingStarted(String imageUri, View view) {
			// holder.progressBar.setProgress(0);
			// holder.progressBar.setVisibility(View.VISIBLE);
			// }
			//
			// @Override
			// public void onLoadingFailed(String imageUri, View view,
			// FailReason failReason) {
			// holder.progressBar.setVisibility(View.GONE);
			// }
			//
			// @Override
			// public void onLoadingComplete(String imageUri, View view,
			// Bitmap loadedImage) {
			// holder.progressBar.setVisibility(View.GONE);
			// }
			// }, new ImageLoadingProgressListener() {
			// @Override
			// public void onProgressUpdate(String imageUri, View view,
			// int current, int total) {
			// holder.progressBar.setProgress(Math.round(100.0f
			// * current / total));
			// }
			// });

			System.out.println("avi  " + "      " + (position - arraySiz));
			System.out.println("avi  " + "      "
					+ getnewCoseqList2.get(1).getConsequenceimage());

			imageLoader.displayImage(getnewCoseqList2.get(1)
					.getConsequenceimage(), holder.thumbImage, options);

		}

		else if (position > (arraySiz + getnewCoseqList2.size() - 1)) {
			holder.thumbImage.setBackgroundResource(R.drawable.choose);
			holder.progressBar.setVisibility(View.GONE);
			holder.checkBox.setVisibility(View.GONE);

		}

		holder.checkBox.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (position < arraySiz) {

					consequnceselected.consequnceselectedMethod(
							predefinedConseqName.get(position),
							predefinedConseqImageName.get(position));

				}

				else if (position >= arraySiz
						&& position < arraySiz + getnewCoseqList2.size()) {
					consequnceselected.consequnceselectedMethod(
							getnewCoseqList2.get(position - arraySiz)
									.getConsequencename(), getnewCoseqList2
									.get(position - arraySiz)
									.getConsequenceimage());
				}
			}
		});

		// }

		return view;
	}

	class ViewHolder {

		ImageView thumbImage;
		ProgressBar progressBar;
		CheckBox checkBox;
	}

	public void CallBackConsequence(Consequnceselected consequnceselected) {
		// TODO Auto-generated method stub

		this.consequnceselected = consequnceselected;
	}
}
