package deftsoft.iground.android;

import iground.child.interfaces.Consequnceselected;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import custom.adapter.pckg.GridAdapter;
import deftsoft.android.iground.utility.GetNewCosequence;
import deftsoft.android.iground.utility.SetConSeqWebServiceWebResponse;
import deftsoft.android.iground.utility.SetConSeqWebServiceWebResponse.onSetConSeqWeResponse;
import deftsoft.android.iground.utility.Utility;
import deftsoft.android.iground.utility.WebServiceLinks;
import deftsoft.android.iground.utility.WebServiceResponse;
import deftsoft.android.iground.utility.WebServiceResponse.onWebResponse;
import deftsoft.iground.android.R.dimen;
import deftsoft.iground.android.CustomizeListView.AbsListViewBaseActivity;

public class CopyOfConsequenceImageGridActivity extends AbsListViewBaseActivity
		implements

		OnClickListener, onWebResponse, Consequnceselected,
		onSetConSeqWeResponse {

	private static String TAG = "ConsequenceImageGridActivity";

	private Context context;

	private ImageView back;

	private Button setConseqButton;

	ArrayList<Uri> uriArray = new ArrayList<Uri>();

	public static final Integer[] imageurl = new Integer[] { R.drawable.bed,
			R.drawable.vgame,

			R.drawable.phone, R.drawable.tv, R.drawable.music, R.drawable.tech };

	ArrayList<GetNewCosequence> GetnewCoseqList = new ArrayList<GetNewCosequence>();

	private DisplayImageOptions options;

	private String UserId;

	SharedPreferences pref;

	Utility util;

	String childName = "";

	String endTime = "";

	String endDate = "";

	String startDateTime = "";

	String ChildId = "";

	String conseqName = "";

	String conseqImageName = "";

	int arraySiz = 0;

	private ArrayList<String> predifinedConseqName, predifinedConseqImageName;

	String SelectedConsequence, SelectedImageName;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// TODO Auto-generated method stub

		setContentView(R.layout.activity_coseq_image_grid);

		widgetIntialize();

		getIntenData();

		setListner();

		hitwebServiceMethod();

		// setGridAdapter();
	}

	private void getIntenData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();

		childName = intent.getStringExtra("Child Name");

		ChildId = intent.getStringExtra("Child ID");

		startDateTime = intent.getStringExtra("Start Date Time");

		endTime = intent.getStringExtra("End Time");

		endDate = intent.getStringExtra("End Date");

		Log.i(TAG, " Child Name Is " + childName);

		Log.i(TAG, "Child Id is = " + ChildId);

		Log.i(TAG, " Start Dtae Time is =" + startDateTime);

		Log.i(TAG, " End Time Is " + endTime);

		Log.i(TAG, " End  Date Is " + endDate);

	}

	private void hitwebServiceMethod() {
		// TODO Auto-generated method stub

		String hitUrl = WebServiceLinks.Get_Newconsequence + "&userid="
				+ UserId;

		Log.i(TAG, "Get New Consequences  WebService Link " + hitUrl);

		if (util.isOnline(context)) {

			new WebServiceResponse(context, hitUrl,
					CopyOfConsequenceImageGridActivity.this);

		}

		else {

			util.toastMessage(context,

			"There is no Internet Connection is Available");

		}

	}

	private void setGridAdapter(ArrayList<GetNewCosequence> getnewCoseqList2) {
		// TODO Auto-generated method stub

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(0)).build();

		GridAdapter gridAdapter = new GridAdapter(imageurl, context,
				imageLoader, options, getnewCoseqList2, predifinedConseqName,
				predifinedConseqImageName);

		gridAdapter
				.CallBackConsequence(CopyOfConsequenceImageGridActivity.this);
		((GridView) listView).setAdapter(gridAdapter);

	}

	private void setListner() {
		// TODO Auto-generated method stub

		back.setOnClickListener(this);

		setConseqButton.setOnClickListener(this);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				if (arg2 == arg0.getCount() - 1) {

					Toast.makeText(context, " Item Id Is " + arg2,
							Toast.LENGTH_SHORT).show();

					Intent AddConseqScreen = new Intent(
							CopyOfConsequenceImageGridActivity.this,
							AddConsequenceActivity.class);

					startActivity(AddConseqScreen);

				}
			}
		});

	}

	private void widgetIntialize() {
		// TODO Auto-generated method stub

		util = new Utility();

		predifinedConseqName = new ArrayList<String>();

		predifinedConseqImageName = new ArrayList<String>();

		context = CopyOfConsequenceImageGridActivity.this;

		pref = context.getSharedPreferences("Login Remember", 0);

		UserId = pref.getString("User Id", null);

		Log.i(TAG, " User Id is  " + UserId);

		back = (ImageView) findViewById(R.id.back_imageView);

		setConseqButton = (Button) findViewById(R.id.setConseq_button);

		listView = (GridView) findViewById(R.id.gridView);

		uriArray.add(Uri
				.parse("android.resource://deftsoft.iground.android/drawable/bed"));
		uriArray.add(Uri
				.parse("android.resource://deftsoft.iground.android/drawable/vgame"));
		uriArray.add(Uri
				.parse("android.resource://deftsoft.iground.android/drawable/phone"));
		uriArray.add(Uri
				.parse("android.resource://deftsoft.iground.android/drawable/tv"));
		uriArray.add(Uri
				.parse("android.resource://deftsoft.iground.android/drawable/music"));
		uriArray.add(Uri
				.parse("android.resource://deftsoft.iground.android/drawable/tech"));

		predifinedConseqName.add(0, "Bed Time");

		predifinedConseqName.add(1, "Play Video Game");

		predifinedConseqName.add(2, "Phone Call");

		predifinedConseqName.add(3, "Watch Tv");

		predifinedConseqName.add(4, "Listen Music");

		predifinedConseqName.add(5, "Learn Technology");

		predifinedConseqImageName.add(0, "Bed Image");

		predifinedConseqImageName.add(1, "Play VideoGame Image");

		predifinedConseqImageName.add(2, "Phone Call Image");

		predifinedConseqImageName.add(3, "Watch Tv Image");

		predifinedConseqImageName.add(4, "Listen Music Image");

		predifinedConseqImageName.add(5, "Learn Technology Image");

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.back_imageView:

			finish();

			break;

		case R.id.setConseq_button:

			int conseqNameLength = conseqName.length();
			int consegImageNameLength = conseqImageName.length();

			if (util.isOnline(context)) {

				if (conseqNameLength > 0 && consegImageNameLength > 0) {

					String SetConSeqUrl = WebServiceLinks.Add_Consequence
							+ "&userid=" + UserId + "&child_name=" + childName
							+ "&date_time="
							+ startDateTime.replaceAll(" ", "%20")
							+ "&con_datetime=" + endDate.replaceAll(" ", "%20")
							+ endTime.replaceAll(" ", "%20") + "&consequence="
							+ conseqName.replaceAll(" ", "%20") + "&image="
							+ conseqImageName.replaceAll(" ", "%20")
							+ "&childid=" + ChildId;

					Log.i(TAG, "Set Conseq WebService Link" + SetConSeqUrl);

					new SetConSeqWebServiceWebResponse(context, SetConSeqUrl,
							CopyOfConsequenceImageGridActivity.this);

				}

				else {

					util.toastMessage(context,
							"Please First Select  the Consequnce");
				}

			} else {

				util.toastMessage(context, "There is no Internet Available");
			}

			break;

		default:

			break;

		}

	}

	@Override
	public void onSuccess(String result) {
		// TODO Auto-generated method stub

		Log.i(TAG, "result = " + result);

		JSONObject mainJsonObject;

		JSONArray data;

		GetnewCoseqList.clear();

		try {
			mainJsonObject = new JSONObject(result);

			if (mainJsonObject.getString("result").equals("success")) {

				data = mainJsonObject.getJSONArray("data");

				int length = data.length();

				if (length > 0) {

					for (int i = 0; i < length; i++) {

						GetNewCosequence model = new GetNewCosequence();

						JSONObject jObject = data.getJSONObject(i);

						model.setUserid(jObject.getString("userid"));

						model.setConsequencename(jObject
								.getString("consequencename"));

						model.setConsequenceimage(jObject
								.getString("consequenceimage"));

						GetnewCoseqList.add(model);

					}
				}

				else {

					util.toastMessage(context,

					"There is No New Consequence Detail");
				}

				PopulateMyGridView(GetnewCoseqList);

			}

			else {

				Toast.makeText(context, mainJsonObject.getString("result"),

				Toast.LENGTH_LONG).show();

				PopulateMyGridView(GetnewCoseqList);
			}
		}

		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void PopulateMyGridView(ArrayList<GetNewCosequence> getnewCoseqList2) {
		// TODO Auto-generated method stub

		// if (getnewCoseqList2 != null) {
		// if (!getnewCoseqList2.isEmpty()) {
		//
		// for (int i = 0; i < uriArray.size(); i++) {
		//
		// if (i != uriArray.size()) {
		//
		// GetNewCosequence myCons = new GetNewCosequence();
		// myCons.setConsequenceimage(uriArray.get(i).toString());
		// getnewCoseqList2.add(i, myCons);
		//
		// } else {
		//
		// GetNewCosequence myCons = new GetNewCosequence();
		// myCons.setConsequenceimage(uriArray.get(i).toString());
		// getnewCoseqList2.add(myCons);
		//
		// }
		//
		// }
		//
		// }
		//
		// setGridAdapter(GetnewCoseqList);
		//
		// } else {

		// getnewCoseqList2 = new ArrayList<GetNewCosequence>();
		// // only initialize existing images
		// for (int i = 0; i < uriArray.size(); i++) {
		//
		// if (i != uriArray.size()) {
		//
		// GetNewCosequence myCons = new GetNewCosequence();
		// myCons.setConsequenceimage(uriArray.get(i).toString());
		// getnewCoseqList2.add(i, myCons);
		//
		// } else {
		//
		// GetNewCosequence myCons = new GetNewCosequence();
		// myCons.setConsequenceimage(uriArray.get(i).toString());
		// getnewCoseqList2.add(myCons);
		//
		// }
		//
		// setGridAdapter(GetnewCoseqList);
		// }
		// }
		setGridAdapter(GetnewCoseqList);
	}

	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub

		Log.i(TAG, "error = " + error);

		ArrayList<GetNewCosequence> getnewCoseqList2 = new ArrayList<GetNewCosequence>();
		// only initialize existing images
		for (int i = 0; i < uriArray.size(); i++) {

			if (i != uriArray.size()) {

				GetNewCosequence myCons = new GetNewCosequence();
				myCons.setConsequenceimage(uriArray.get(i).toString());
				getnewCoseqList2.add(i, myCons);

			} else {

				GetNewCosequence myCons = new GetNewCosequence();
				myCons.setConsequenceimage(uriArray.get(i).toString());
				getnewCoseqList2.add(myCons);

			}

			setGridAdapter(GetnewCoseqList);
		}

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

		hitwebServiceMethod();

	}

	@Override
	public void consequnceselectedMethod(String Name, String ImageName) {
		// TODO Auto-generated method stub

		conseqName = Name;

		conseqImageName = ImageName;

		System.out.println("Name                       " + Name);
		System.out.println("Name       ImageName       " + ImageName);

	}

	@Override
	public void onSetConSeqSuccess(String result) {
		// TODO Auto-generated method stub

		Log.i(TAG, "result SetConseqSucces = " + result);

		try {

			JSONObject jsonObject = new JSONObject(result);

			if (jsonObject.getString("result").equalsIgnoreCase("success")) {

				util.toastMessage(context, jsonObject.getString("data"));

			} else {

				util.toastMessage(context, jsonObject.getString("data"));

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
		
			e.printStackTrace();
		}

	}

	@Override
	public void onSetConSeqError(String error) {
		// TODO Auto-generated method stub

		Log.i(TAG, "error SetConseqSucces = " + error);

	}
}
