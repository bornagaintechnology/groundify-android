//package deftsoft.iground.android;
//
//import java.util.ArrayList;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
//
//import custom.adapter.pckg.GridAdapter;
//import deftsoft.android.iground.utility.GetNewCosequence;
//import deftsoft.android.iground.utility.Utility;
//import deftsoft.android.iground.utility.WebServiceLinks;
//import deftsoft.android.iground.utility.WebServiceResponse;
//import deftsoft.android.iground.utility.WebServiceResponse.onWebResponse;
//import deftsoft.iground.android.CustomizeListView.AbsListViewBaseActivity;
//
//public class ConsequenceImageGridActivity extends AbsListViewBaseActivity
//		implements
//
//		OnClickListener, onWebResponse {
//
//	private static String TAG = "ConsequenceImageGridActivity";
//
//	private Context context;
//
//	private ImageView back;
//
//	ArrayList<Uri> uriArray = new ArrayList<Uri>();
//
//	public static final  String[] imageurl = new String[] {"drawable://"+R.drawable.bed, "drawable://"+R.drawable.vgame,
//		
//		"drawable://"+R.drawable.phone, "drawable://"+R.drawable.tv, "drawable://"+R.drawable.music, "drawable://"+R.drawable.tech
//
//	};
//
//	
//	ArrayList<GetNewCosequence> GetnewCoseqList = new ArrayList<GetNewCosequence>();
//	
//	private DisplayImageOptions options;
//
//	private String UserId;
//
//	SharedPreferences pref;
//
//	Utility util;
//
//	/** Called when the activity is first created. */
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//
//		super.onCreate(savedInstanceState);
//
//		// TODO Auto-generated method stub
//
//		setContentView(R.layout.activity_coseq_image_grid);
//
//		widgetIntialize();
//
//		setListner();
//
//		hitwebServiceMethod();
//
//		// setGridAdapter();
//	}
//
//	private void hitwebServiceMethod() {
//		// TODO Auto-generated method stub
//
//		String hitUrl = WebServiceLinks.Get_Newconsequence + "&userid="
//				+ UserId;
//
//		Log.i(TAG, "Get New Consequences  WebService Link " + hitUrl);
//
//		if (util.isOnline(context)) {
//			
//
//			new WebServiceResponse(context, hitUrl,
//					ConsequenceImageGridActivity.this);
//		}
//
//		else {
//
//			util.toastMessage(context,
//
//			"There is no Internet Connection is Available");
//
//		}
//
//	}
//
//	private void setGridAdapter(ArrayList<GetNewCosequence> getnewCoseqList2) {
//		// TODO Auto-generated method stub
//
//		options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.ic_launcher)
//				.showImageForEmptyUri(R.drawable.ic_launcher)
//				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
//				.cacheOnDisk(true).considerExifParams(true)
//				.displayer(new RoundedBitmapDisplayer(0)).build();
//		((GridView) listView).setAdapter(new GridAdapter(imageurl, context,
//				imageLoader, options, getnewCoseqList2));
//
//	}
//
//	private void setListner() {
//		// TODO Auto-generated method stub
//
//		back.setOnClickListener(this);
//
//	}
//
//	private void widgetIntialize() {
//		// TODO Auto-generated method stub
//
//		util = new Utility();
//
//		context = ConsequenceImageGridActivity.this;
//
//		pref = context.getSharedPreferences("Login Remember", 0);
//
//		UserId = pref.getString("User Id", null);
//
//		Log.i(TAG, " User Id is  " + UserId);
//
//		back = (ImageView) findViewById(R.id.back_imageView);
//
//		listView = (GridView) findViewById(R.id.gridView);
//
//		uriArray.add(Uri
//				.parse("android.resource://deftsoft.iground.android/drawable/bed"));
//		uriArray.add(Uri
//				.parse("android.resource://deftsoft.iground.android/drawable/vgame"));
//		uriArray.add(Uri
//				.parse("android.resource://deftsoft.iground.android/drawable/phone"));
//		uriArray.add(Uri
//				.parse("android.resource://deftsoft.iground.android/drawable/tv"));
//		uriArray.add(Uri
//				.parse("android.resource://deftsoft.iground.android/drawable/music"));
//		uriArray.add(Uri
//				.parse("android.resource://deftsoft.iground.android/drawable/tech"));
//
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//
//		switch (v.getId()) {
//
//		case R.id.back_imageView:
//
//			finish();
//
//			break;
//
//		/*
//		 * case value:
//		 * 
//		 * break;
//		 * 
//		 * case value:
//		 * 
//		 * break;
//		 */
//
//		default:
//			break;
//		}
//	}
//
//	@Override
//	public void onSuccess(String result) {
//		// TODO Auto-generated method stub
//
//		Log.i(TAG, "result = " + result);
//		
//		JSONObject mainJsonObject;
//		
//		JSONArray data;
//
//		GetnewCoseqList.clear();
//
//		try {
//			mainJsonObject = new JSONObject(result);
//
//			if (mainJsonObject.getString("result").equals("success")) {
//
//				data = mainJsonObject.getJSONArray("data");
//
//				int length = data.length();
//
//				if (length > 0) {
//
//					for (int i = 0; i < length; i++) {
//
//						GetNewCosequence model = new GetNewCosequence();
//
//						JSONObject jObject = data.getJSONObject(i);
//
//						model.setUserid(jObject.getString("userid"));
//
//						model.setConsequencename(jObject
//								.getString("consequencename"));
//
//						model.setConsequenceimage(jObject
//								.getString("consequenceimage"));
//
//						GetnewCoseqList.add(model);
//
//					}
//				}
//
//				else {
//
//					util.toastMessage(context,
//
//					"There is No New Consequence Detail");
//				}
//
//				PopulateMyGridView(GetnewCoseqList);
//
//			}
//
//			else {
//				
//				Toast.makeText(context, mainJsonObject.getString("result"),
//						
//						Toast.LENGTH_LONG).show();
//
//				PopulateMyGridView(GetnewCoseqList);
//			}
//		}
//
//		catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	private void PopulateMyGridView(ArrayList<GetNewCosequence> getnewCoseqList2) {
//		// TODO Auto-generated method stub
//
//		if (getnewCoseqList2 != null) {
//			if (!getnewCoseqList2.isEmpty()) {
//
//				for (int i = 0; i < uriArray.size(); i++) {
//
//					if (i != uriArray.size()) {
//
//						GetNewCosequence myCons = new GetNewCosequence();
//						myCons.setConsequenceimage(uriArray.get(i).toString());
//						getnewCoseqList2.add(i, myCons);
//
//					} else {
//
//						GetNewCosequence myCons = new GetNewCosequence();
//						myCons.setConsequenceimage(uriArray.get(i).toString());
//						getnewCoseqList2.add(myCons);
//
//					}
//
//				}
//
//			}
//
//			setGridAdapter(GetnewCoseqList);
//
//		} else {
//
//			getnewCoseqList2 = new ArrayList<GetNewCosequence>();
//			// only initialize existing images
//			for (int i = 0; i < uriArray.size(); i++) {
//
//				if (i != uriArray.size()) {
//
//					GetNewCosequence myCons = new GetNewCosequence();
//					myCons.setConsequenceimage(uriArray.get(i).toString());
//					getnewCoseqList2.add(i, myCons);
//
//				} else {
//
//					GetNewCosequence myCons = new GetNewCosequence();
//					myCons.setConsequenceimage(uriArray.get(i).toString());
//					getnewCoseqList2.add(myCons);
//
//				}
//
//				setGridAdapter(GetnewCoseqList);
//			}
//		}
//
//	}
//
//	@Override
//	public void onError(String error) {
//		// TODO Auto-generated method stub
//
//		Log.i(TAG, "error = " + error);
//
//		ArrayList<GetNewCosequence> getnewCoseqList2 = new ArrayList<GetNewCosequence>();
//		// only initialize existing images
//		for (int i = 0; i < uriArray.size(); i++) {
//
//			if (i != uriArray.size()) {
//
//				GetNewCosequence myCons = new GetNewCosequence();
//				myCons.setConsequenceimage(uriArray.get(i).toString());
//				getnewCoseqList2.add(i, myCons);
//
//			} else {
//
//				GetNewCosequence myCons = new GetNewCosequence();
//				myCons.setConsequenceimage(uriArray.get(i).toString());
//				getnewCoseqList2.add(myCons);
//
//			}
//
//			setGridAdapter(GetnewCoseqList);
//		}
//
//	}
//
//}
