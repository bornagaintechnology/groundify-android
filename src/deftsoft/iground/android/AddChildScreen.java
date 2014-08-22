package deftsoft.iground.android;

import java.util.ArrayList;
import java.util.Calendar;
import org.json.JSONException;
import org.json.JSONObject;

import deftsoft.android.iground.utility.Utility;
import deftsoft.android.iground.utility.WebServiceLinks;
import deftsoft.android.iground.utility.WebServiceResponse;
import deftsoft.android.iground.utility.WebServiceResponse.onWebResponse;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class AddChildScreen extends Activity implements OnClickListener,
		onWebResponse {

	private final String TAG = "AddChildScreen Activity";

	private ImageView backButton;

	private EditText childFirstName, childLastName;

	private Spinner month_Spinner, day_Spinner, year_Spinner, gender_Spinner;

	private Button submitButton;

	private String month, day, year, gender;

	Utility util;

	Context context;

	String android_id;

	String userId;

	ArrayList<String> yearList;

	Integer yearPostion;

	SharedPreferences pref;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO Auto-generated method stub

		setContentView(R.layout.activity_add_child);

		wedgitIntialize();

		setList();

		setLisner();

		setSpinnerAdapter();

		setListeneronSpinner();
	}

	private void setList() {
		// TODO Auto-generated method stub

		yearList.add(0, "Year");

		Calendar calendar = Calendar.getInstance();

		int year = calendar.get(Calendar.YEAR);

		for (int i = 1990; i < year + 1; i++) {

			yearList.add(Integer.toString(i));

		}

		Log.i(TAG, "Year In the Array List is =" + yearList + "\n");

	}

	private void setLisner() {
		// TODO Auto-generated method stub

		backButton.setOnClickListener(this);

		submitButton.setOnClickListener(this);

	}

	private void wedgitIntialize() {
		// TODO Auto-generated method stub

		context = AddChildScreen.this;

		pref = context.getSharedPreferences("Login Remember", 0);

		userId = pref.getString("User Id", null);

		Log.i(TAG, " User Id is From Login Screen is " + userId);

		util = new Utility();

		android_id = Secure.getString(context.getContentResolver(),

		Secure.ANDROID_ID);

		Log.i(TAG, " Device Id is =" + android_id);

		backButton = (ImageView) findViewById(R.id.back_imageView);

		childFirstName = (EditText) findViewById(R.id.child_fName);

		childLastName = (EditText) findViewById(R.id.child_lName);

		month_Spinner = (Spinner) findViewById(R.id.month_spiner);

		day_Spinner = (Spinner) findViewById(R.id.day_spiner);

		year_Spinner = (Spinner) findViewById(R.id.year_spiner);

		gender_Spinner = (Spinner) findViewById(R.id.sex_spiner);

		submitButton = (Button) findViewById(R.id.submit_button);

		yearList = new ArrayList<String>();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.back_imageView:

			finish();

			break;

		case R.id.submit_button:

			if (notEmptyCheckMethod()) {

				if (!month.equalsIgnoreCase("Month")) {

					if (!day.equalsIgnoreCase("Day")) {

						if (!year.equalsIgnoreCase("Year")) {

							if (!gender.equalsIgnoreCase("Sex")) {

								if (util.isOnline(context)) {

									String

									addChildUrl = WebServiceLinks.AddChild +

									"&userid=" + userId + "&child_fname=" +

									childFirstName.getText().toString().trim() +

									"&child_lname=" +

									childLastName.getText().toString().trim()
											+ "&dob=" +

											yearPostion + "/" + day + "/"
											+ year + "/" + "&gender=" + "male";

									Log.i(TAG, "Add Child Url is "
											+ addChildUrl);

									new WebServiceResponse(context,
											addChildUrl, AddChildScreen.this);

								} else {

									util.toastMessage(context,
											"There is no Internet Available");
								}

							}

							else {

								util.toastMessage(context,
										"Please Select the Gender");

							}

						}

						else {
							util.toastMessage(context,
									"Please Select the Year of Birth");
						}
					}

					else {

						util.toastMessage(context,
								"Please Select the Day of Birth");

					}

				}

				else {

					util.toastMessage(context,
							"Please Select the Month of Birth");

				}

			}

			else {

				util.toastMessage(context,
						"Please Enter Child First Name Or Child Last Name");
			}

			break;

		default:

			break;
		}
	}

	private boolean notEmptyCheckMethod() {
		// TODO Auto-generated method stub

		if (childFirstName.getText().toString().length() > 0
				&& childLastName.getText().toString().length() > 0) {

			return true;
		}

		else {

			return false;
		}

	}

	private void setSpinnerAdapter() {
		// TODO Auto-generated method stub

		month_Spinner.setAdapter(new ArrayAdapter<String>(context,

		android.R.layout.simple_list_item_1, getResources()

		.getStringArray(R.array.month)));

		day_Spinner.setAdapter(new ArrayAdapter<String>(context,

		android.R.layout.simple_list_item_1, getResources()

		.getStringArray(R.array.day)));

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,

		android.R.layout.simple_spinner_item, yearList);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		year_Spinner.setAdapter(dataAdapter);

		gender_Spinner.setAdapter(new ArrayAdapter<String>(context,

		android.R.layout.simple_list_item_1, getResources()

		.getStringArray(R.array.gender)));
	}

	private void setListeneronSpinner() {
		// TODO Auto-generated method stub

		month_Spinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						System.out.println("arg1       "
								+ arg0.getItemAtPosition(arg2));

						month = (String) arg0.getItemAtPosition(arg2);

						yearPostion = arg2;

						Log.i(TAG, " Month is Selected =" + month);

						Log.i(TAG, " Month is Selected =" + yearPostion);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		day_Spinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub

						System.out.println("arg1       "
								+ arg0.getItemAtPosition(arg2));

						day = (String) arg0.getItemAtPosition(arg2);

						Log.i(TAG, " Day is Selected =" + day);

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		year_Spinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub

						System.out.println("arg1       "
								+ arg0.getItemAtPosition(arg2));

						year = (String) arg0.getItemAtPosition(arg2);

						Log.i(TAG, " Year is Selected =" + year);

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		gender_Spinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub

						System.out.println("arg1       "
								+ arg0.getItemAtPosition(arg2));

						gender = (String) arg0.getItemAtPosition(arg2);

						Log.i(TAG, " Gender is Selected =" + gender);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

	}

	@Override
	public void onSuccess(String result) {
		// TODO Auto-generated method stub

		Log.i(TAG, "result = " + result);

		try {

			JSONObject jsonObject = new JSONObject(result);

			if (jsonObject.getString("result").equalsIgnoreCase("success")) {

				util.toastMessage(context, jsonObject.getString("data"));

				clearMethod();

			} else {

				util.toastMessage(context, jsonObject.getString("data"));

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void clearMethod() {
		// TODO Auto-generated method stub

		childFirstName.setText("");

		childLastName.setText("");

		setSpinnerAdapter();

	}

	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub

		Log.i(TAG, "error = " + error);

	}

}
