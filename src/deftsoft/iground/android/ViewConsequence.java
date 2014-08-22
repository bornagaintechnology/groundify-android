package deftsoft.iground.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import custom.adapter.pckg.MyLazyAdapter;

import deftsoft.android.iground.utility.Utility;
import deftsoft.android.iground.utility.ViewConseqDetail;
import deftsoft.android.iground.utility.WebServiceLinks;
import deftsoft.android.iground.utility.WebServiceResponse;
import deftsoft.android.iground.utility.WebServiceResponse.onWebResponse;
import deftsoft.iground.android.CustomizeListView.AbsListViewBaseActivity;

public class ViewConsequence extends AbsListViewBaseActivity implements
		onWebResponse {

	private static String TAG = "ViewConsequence";

	private ImageView back;

	SharedPreferences pref;

	Context context;
	
	private DisplayImageOptions options;
	
	private String UserId;

	Utility util;

	ArrayList<ViewConseqDetail> ViewConseqList = new ArrayList<ViewConseqDetail>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO Auto-generated method stub

		setContentView(R.layout.activity_view_conseq);

		widgetIntialize();

		setListner();

		hitWebServiceMethod();

	}

	private void hitWebServiceMethod() {
		// TODO Auto-generated method stub

		String hitUrl = WebServiceLinks.View_Consequence + "&userid=" + UserId;

		Log.i(TAG, "View Consequence WebService Link " + hitUrl);

		if (util.isOnline(context)) {

			new WebServiceResponse(context, hitUrl, ViewConsequence.this);
		}

		else {

			util.toastMessage(context,
					"There is no Internet Connection is Available");
		}
	}

	private void setListner() {
		// TODO Auto-generated method stub
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();
			}
		});
	}

	private void widgetIntialize() {
		// TODO Auto-generated method stub

		context = ViewConsequence.this;

		util = new Utility();

		pref = context.getSharedPreferences("Login Remember", 0);

		UserId = pref.getString("User Id", null);

		Log.i(TAG, " User Id is  " + UserId);

		listView = (ListView) findViewById(R.id.list_conseq);

		back = (ImageView) findViewById(R.id.back_imageView);

	}

	@Override
	public void onSuccess(String result) {
		// TODO Auto-generated method stub

		Log.i(TAG, "result =" + result);

		JSONObject mainJsonObject;
		JSONArray data;

		ViewConseqList.clear();

		try {
			mainJsonObject = new JSONObject(result);

			if (mainJsonObject.getString("result").equals("success")) {

				data = mainJsonObject.getJSONArray("data");

				int length = data.length();

				if (length > 0) {

					for (int i = 0; i < length; i++) {

						ViewConseqDetail model = new ViewConseqDetail();

						JSONObject jObject = data.getJSONObject(i);

						model.setUserid(jObject.getString("userid"));

						model.setChild_name(jObject.getString("child_name"));

						model.setDate_time(jObject.getString("date_time"));

						model.setCon_datetime(jObject.getString("con_datetime"));

						model.setConsequence(jObject.getString("consequence"));

						model.setImage(jObject.getString("image"));

						ViewConseqList.add(model);

					}

				}

				else {

					util.toastMessage(context, "There is No Consequence");

				}

				PopulateMyListDat(ViewConseqList);

			}

			else {

				Toast.makeText(context, mainJsonObject.getString("result"),
						Toast.LENGTH_LONG).show();

			}

		}

		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void PopulateMyListDat(ArrayList<ViewConseqDetail> myviewConseqList) {
		// TODO Auto-generated method stub

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(0)).build();

		MyLazyAdapter myAdap = new MyLazyAdapter(ViewConsequence.this,
				myviewConseqList, imageLoader, options);
		((ListView) listView).setAdapter(myAdap);
		myAdap.notifyDataSetChanged();

	}

	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		Log.i(TAG, "error =" + error);
	}

}
