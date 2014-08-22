package deftsoft.iground.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import custom.adapter.pckg.MyAdapter;
import deftsoft.android.iground.utility.ChildDetail;
import deftsoft.android.iground.utility.DeleteChildWebResponse;
import deftsoft.android.iground.utility.DeleteChildWebResponse.onDelteWebResponse;
import deftsoft.android.iground.utility.Utility;
import deftsoft.android.iground.utility.WebServiceLinks;
import deftsoft.android.iground.utility.WebServiceResponse;
import deftsoft.android.iground.utility.WebServiceResponse.onWebResponse;

public class DeleteChildScreen extends Activity implements onWebResponse,
		onDelteWebResponse {

	/** Called when the activity is first created. */

	private final String TAG = "DeleteChildScreen";

	private ImageView backButton;

	private ListView childListView;

	ArrayList<ChildDetail> childDetailList = new ArrayList<ChildDetail>();

	private String UserId;

	SharedPreferences pref;

	MyAdapter adapter;

	Context context;

	BridgeClass bridgeclassObj;

	Utility util;

	int deletePosition;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// TODO Auto-generated method stub

		setContentView(R.layout.activity_delete_child);

		widgetIntialize();

		setLisner();

		hitWebServiceMethod();

		setLisneronListMethod();

	}

	private void setLisneronListMethod() {
		// TODO Auto-generated method stub

		childListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			// setting onItemLongClickListener and passing the position to the
			// function

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub

				removeItemFromList(position);

				return true;
			}

			private void removeItemFromList(int position) {
				// TODO Auto-generated method stub

				deletePosition = position;

				AlertDialog.Builder alert = new AlertDialog.Builder(
						DeleteChildScreen.this);

				alert.setTitle("Delete");
				alert.setMessage("Do you want delete this child?");
				alert.setPositiveButton("YES", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TOD O Auto-generated method stub

						// main code on after clicking yes

						if (util.isOnline(context)) {

							String chilid = childDetailList.get(deletePosition)
									.getChildID();

							String hitdeleteChildUrl = WebServiceLinks.Delete_Child
									+ "&userid="
									+ UserId
									+ "&childid="
									+ chilid;

							new DeleteChildWebResponse(context,
									hitdeleteChildUrl, DeleteChildScreen.this);

							/*
							 * childDetailList.remove(deletePosition);
							 * 
							 * adapter.notifyDataSetChanged();
							 * 
							 * adapter.notifyDataSetInvalidated();
							 */

						} else {

							util.toastMessage(context,
									"There is no Internet Connection is Available");
						}
					}
				});
				alert.setNegativeButton("CANCEL", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});

				alert.show();
			}
		});

	}

	private void hitWebServiceMethod() {
		// TODO Auto-generated method stub

		String hitUrl = WebServiceLinks.Select_own_child + "&userid=" + UserId;

		Log.i(TAG, "Get Child FName and Id Url " + hitUrl);

		if (util.isOnline(context)) {

			new WebServiceResponse(context, hitUrl, DeleteChildScreen.this);
		}

		else {

			util.toastMessage(context,
					"There is no Internet Connection is Available");
		}
	}

	private void setAdapter() {
		// TODO Auto-generated method stub

		adapter = new MyAdapter(childDetailList, context);

		childListView.setAdapter(adapter);
	}

	private void setLisner() {
		// TODO Auto-generated method stub

		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();
			}
		});

	}

	private void widgetIntialize() {
		// TODO Auto-generated method stub

		context = DeleteChildScreen.this;

		util = new Utility();

		pref = context.getSharedPreferences("Login Remember", 0);

		UserId = pref.getString("User Id", null);

		Log.i(TAG, " User Id is  " + UserId);

		backButton = (ImageView) findViewById(R.id.back_imageView);

		childListView = (ListView) findViewById(R.id.child_list);

	}

	@Override
	public void onSuccess(String result) {
		// TODO Auto-generated method stub

		Log.i(TAG, "result =" + result);

		JSONObject mainJsonobj;

		JSONArray data;

		childDetailList.clear();

		try {

			mainJsonobj = new JSONObject(result);

			if (mainJsonobj.getString("result").equals("success")) {

				data = mainJsonobj.getJSONArray("data");

				int length = data.length();

				if (length > 0) {

					for (int i = 0; i < length; i++) {

						ChildDetail model = new ChildDetail();

						JSONObject jObject = data.getJSONObject(i);

						model.setChildID(jObject.getString("childid"));

						model.setChildName(jObject.getString("child_fname"));

						childDetailList.add(model);

						Log.i(TAG, "Child Name in the Array List is ="

						+ childDetailList.get(i).getChildName());

						Log.i(TAG, "Child ID in the Array List is ="

						+ childDetailList.get(i).getChildID());
					}

					setAdapter();

				}

				else {

					Toast.makeText(context, "Please Add Child  First",
							Toast.LENGTH_LONG).show();
				}

			}

			else {

				ChildDetail model = new ChildDetail();

				model.setChildID("1");

				model.setChildName("Select Child");

				childDetailList.add(model);

				setAdapter();
			}
		}

		catch (JSONException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

	}

	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub

		Log.i(TAG, "error =" + error);
	}

	@Override
	public void onDeleteSuccess(String result) {
		// TODO Auto-generated method stub
		Log.i(TAG, "nDeleteSuccess= " + result);

		JSONObject jsonObj;

		try {

			jsonObj = new JSONObject(result);

			if (jsonObj.getString("result").equals("success")) {

				String msg = jsonObj.getString("data");

				util.toastMessage(context, msg);

				childDetailList.remove(deletePosition);

				adapter.notifyDataSetChanged();

				adapter.notifyDataSetInvalidated();

			}

			else {

				String msg = jsonObj.getString("data");

				util.toastMessage(context, msg);

			}

		}

		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onDeleteError(String error) {
		// TODO Auto-generated method stub

		Log.i(TAG, "onDeleteError=" + error);
	}

}
