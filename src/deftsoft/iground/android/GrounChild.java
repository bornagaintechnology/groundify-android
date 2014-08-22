package deftsoft.iground.android;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import custom.adapter.pckg.MySpinnerAdapter;
import deftsoft.android.iground.utility.DatePickerFragment;
import deftsoft.android.iground.utility.DeleteChildWebResponse;
import deftsoft.android.iground.utility.TimePickerFragment;
import deftsoft.android.iground.utility.Utility;
import deftsoft.android.iground.utility.WebServiceLinks;
import deftsoft.android.iground.utility.WebServiceResponse;
import deftsoft.android.iground.utility.WebServiceResponse.onWebResponse;
import android.support.v4.app.FragmentActivity;

@SuppressLint("SimpleDateFormat")
public class GrounChild extends FragmentActivity implements OnClickListener,

onWebResponse, OnItemSelectedListener {

	private final String TAG = "GrounChild Activity";

	private Context context;

	private ImageView backButton;

	private Spinner selectchild_Spinner;

	private Button continueButton, endButton;

	private EditText currentDateTime, timeEditText, dateEditText;

	private ArrayList<String> childFirstNameList, childIdList;

	SharedPreferences pref;

	Calendar cal;

	BridgeClass bridgeclassObj;

	private String UserId, ChildName, childIdString;

	Utility util;

	Thread myThread = null;

	String data = "";

	String selectChildId = "";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO Auto-generated method stub

		setContentView(R.layout.activity_ground_child);

		widgetIntialize();

		setListner();

		// setTime();

		hitwebServiceMethod();

	}

	private void hitwebServiceMethod() {
		// TODO Auto-generated method stub

		String hitUrl = WebServiceLinks.Select_own_child + "&userid=" + UserId;

		Log.i(TAG, "Get Child FName and Id Url " + hitUrl);

		if (util.isOnline(context)) {

			new WebServiceResponse(context, hitUrl, GrounChild.this);
		}

		else {

			util.toastMessage(context,

			"There is no Internet Connection is Available");
		}

	}

	private void setTime() {
		// TODO Auto-generated method stub

		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy h:mm:a");

		String formattedDate = df.format(cal.getTime());

		currentDateTime.setText("Current Date&Time " + formattedDate);

		currentDateTime.setFocusable(false);
	}

	private void setSpinnerAdapter() {
		// TODO Auto-generated method stub

		MySpinnerAdapter mySpinAdap = new MySpinnerAdapter(GrounChild.this,

		childFirstNameList);

		selectchild_Spinner.setAdapter(mySpinAdap);

		mySpinAdap.notifyDataSetChanged();

		selectchild_Spinner.setSelection(0);

	}

	private void setListner() {
		// TODO Auto-generated method stub

		backButton.setOnClickListener(this);

		continueButton.setOnClickListener(this);

		endButton.setOnClickListener(this);

		currentDateTime.setOnClickListener(this);

		selectchild_Spinner.setOnItemSelectedListener(this);

		timeEditText.setOnClickListener(this);

		dateEditText.setOnClickListener(this);

	}

	private void widgetIntialize() {
		// TODO Auto-generated method stubs

		util = new Utility();

		context = GrounChild.this;

		pref = context.getSharedPreferences("Login Remember", 0);

		UserId = pref.getString("User Id", null);

		Log.i(TAG, " User Id is  " + UserId);

		backButton = (ImageView) findViewById(R.id.back_imageView);

		childFirstNameList = new ArrayList<String>();

		childIdList = new ArrayList<String>();

		selectchild_Spinner = (Spinner) findViewById(R.id.selectchild_Spinner);

		currentDateTime = (EditText) findViewById(R.id.dateTime_editText);

		timeEditText = (EditText) findViewById(R.id.time_editText);

		dateEditText = (EditText) findViewById(R.id.date_editText);

		continueButton = (Button) findViewById(R.id.continue_button);

		endButton = (Button) findViewById(R.id.conseqEnd_button);

		cal = Calendar.getInstance();

		Runnable myRunnableThread = new CountDownRunner();

		myThread = new Thread(myRunnableThread);

		myThread.start();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.back_imageView:

			finish();

			break;

		case R.id.continue_button:

			checkConditionMethod();

			break;

		case R.id.conseqEnd_button:

			break;

		case R.id.dateTime_editText:

			break;

		case R.id.date_editText:

			showDatePicker();

			break;

		case R.id.time_editText:

			showTimePicker();
			break;

		default:

			break;
		}

	}

	private void checkConditionMethod() {
		// TODO Auto-generated method stub

		String CurrentDateTime = currentDateTime();

		int dateText = dateEditText.getText().toString().length();

		int timeText = timeEditText.getText().toString().length();

		String endDate = dateEditText.getText().toString().trim();

		String endTime = timeEditText.getText().toString().trim();

		if (!(data == null)) {

			if (!data.equalsIgnoreCase("Select Child")) {

				if (timeText > 0) {

					if (dateText > 0) {

						Intent conseqGridScreen = new Intent(GrounChild.this,
								CopyOfConsequenceImageGridActivity.class);

						conseqGridScreen.putExtra("Child Name", data);

						conseqGridScreen.putExtra("Child ID", selectChildId);

						conseqGridScreen.putExtra("End Time", endTime);

						conseqGridScreen.putExtra("End Date", endDate);

						conseqGridScreen.putExtra("Start Date Time",
								CurrentDateTime);

						startActivity(conseqGridScreen);

					}

					else {

						Toast.makeText(context,
								"Please First Select Consequence End Date ",
								Toast.LENGTH_LONG).show();

					}

				} else {

					Toast.makeText(context,
							"Please First Select Consequence End Time ",
							Toast.LENGTH_LONG).show();

				}

			} else {

				Toast.makeText(context,
						"Please first Select Child Name from Dropdown ",
						Toast.LENGTH_LONG).show();

			}

		}

		else {

			Toast.makeText(context, "Please first Add Child ",
					Toast.LENGTH_LONG).show();
		}

	}

	private String currentDateTime() {
		// TODO Auto-generated method stub

		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy h:mm:a");

		String formattedDate = df.format(cal.getTime());

		return formattedDate;
	}

	private void showTimePicker() {
		// TODO Auto-generated method stub

		TimePickerFragment time = new TimePickerFragment();

		Calendar calender = Calendar.getInstance();

		Bundle args = new Bundle();

		args.putInt("hour", calender.get(Calendar.HOUR));
		args.putInt("minute", calender.get(Calendar.MINUTE));

		time.setArguments(args);

		time.setCallBack(ontimeSet);

		time.show(getSupportFragmentManager(), "Time Picker");

	}

	OnTimeSetListener ontimeSet = new OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub

			Integer minutes;
			int hour;
			String am_pm;
			if (hourOfDay > 12) {
				hour = hourOfDay - 12;
				am_pm = "PM";
			} else {
				hour = hourOfDay;
				am_pm = "AM";

			}

			if (minute < 10) {

				switch (minute) {
				case 0:

					timeEditText.setText(hour + " : " + 0 + minute + " "
							+ am_pm);

					timeEditText.setFocusable(false);
					break;

				case 1:

					timeEditText.setText(hour + " : " + 0 + minute + " "
							+ am_pm);
					timeEditText.setFocusable(false);
					break;

				case 2:

					timeEditText.setText(hour + " : " + 0 + minute + " "
							+ am_pm);

					timeEditText.setFocusable(false);
					break;

				case 3:

					timeEditText.setText(hour + " : " + 0 + minute + " "
							+ am_pm);

					timeEditText.setFocusable(false);
					break;

				case 4:

					timeEditText.setText(hour + " : " + 0 + minute + " "
							+ am_pm);
					break;

				case 5:

					timeEditText.setText(hour + " : " + 0 + minute + " "
							+ am_pm);
					break;

				case 6:

					timeEditText.setText(hour + " : " + 0 + minute + " "
							+ am_pm);

					timeEditText.setFocusable(false);

					break;

				case 7:

					timeEditText.setText(hour + " : " + 0 + minute + " "
							+ am_pm);

					timeEditText.setFocusable(false);
					break;

				case 8:

					timeEditText.setText(hour + " : " + 0 + minute + " "
							+ am_pm);

					timeEditText.setFocusable(false);
					break;

				case 9:

					timeEditText.setText(hour + " : " + 0 + minute + " "
							+ am_pm);

					timeEditText.setFocusable(false);
					break;

				default:

					break;
				}

			}

			else {

				timeEditText.setText(hour + " : " + minute + " " + am_pm);

				timeEditText.setFocusable(false);
			}

		}
	};

	private void showDatePicker() {
		// TODO Auto-generated method stub
		DatePickerFragment date = new DatePickerFragment();

		/**
		 * Set Up Current Date Into dialog
		 */
		Calendar calender = Calendar.getInstance();
		Bundle args = new Bundle();

		args.putInt("year", calender.get(Calendar.YEAR));

		args.putInt("month", calender.get(Calendar.MONTH));
		args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
		date.setArguments(args);
		/**
		 * Set Call back to capture selected date
		 */
		date.setCallBack(ondate);
		date.show(getSupportFragmentManager(), "Date Picker");
	}

	OnDateSetListener ondate = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {

			dateEditText.setText(String.valueOf(dayOfMonth) + "/"
					+ String.valueOf(monthOfYear + 1) + "/"
					+ String.valueOf(year));
			dateEditText.setFocusable(false);

		}

	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		 setTime();
		 hitwebServiceMethod();

	}

	@Override
	public void onSuccess(String result) {
		// TODO Auto-generated method stub
		Log.i(TAG, "result =" + result);

		try {

			JSONObject mainJsonobj = new JSONObject(result);

			if (mainJsonobj.getString("result").equals("success")) {

				childFirstNameList.clear();

				childFirstNameList.add("Select Child");

				childIdList.clear();

				childIdList.add("Select Child Id");

				JSONArray data = mainJsonobj.getJSONArray("data");

				int length = data.length();

				if (length > 0) {
					for (int i = 0; i < length; i++) {

						JSONObject jObject = data.getJSONObject(i);

						String childFirstName = jObject
								.getString("child_fname");

						String chilID = jObject.getString("childid");

						childFirstNameList.add(childFirstName);

						childIdList.add(chilID);
					}

					Log.i(TAG, "Child Name in the Array List is ="

					+ childFirstNameList);

					Log.i(TAG, "Child ID in the Array List is =" + childIdList);

					setSpinnerAdapter();

				}

				else {

					Toast.makeText(context, "Please Add Child  First",

					Toast.LENGTH_LONG).show();
					
					addChildDialogMethod();
				}

			}

			else {

				childFirstNameList.add("Select Child");

				childIdList.add("1");

				Toast.makeText(context, mainJsonobj.getString("result"),

				Toast.LENGTH_LONG).show();

				setSpinnerAdapter();

			}

		}

		catch (JSONException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

	}

	private void addChildDialogMethod() {
		// TODO Auto-generated method stub
		
		
		AlertDialog.Builder alert = new AlertDialog.Builder(
				GrounChild.this);
		alert.setTitle("First Add Child");
		alert.setMessage("Please Press Ok to Add Child");
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(GrounChild.this,AddChildScreen.class));
				
				dialog.dismiss();
				
				
			}
		});
		
		alert.show();

	}

	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub

		Log.i(TAG, "error =" + error);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {

		data = selectchild_Spinner.getItemAtPosition(pos).toString();

		Toast.makeText(GrounChild.this, data, Toast.LENGTH_SHORT).show();

		selectChildId = childIdList.get(pos);

		Toast.makeText(GrounChild.this,
				"Selected Child Id Is " + selectChildId, Toast.LENGTH_SHORT)
				.show();

		Log.i(TAG, " Child Id is =" + selectChildId);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	public void doWork() {
		runOnUiThread(new Runnable() {
			public void run() {
				try {

					SimpleDateFormat df = new SimpleDateFormat(
							"dd/MM/yyyy h:mm:a");

					String formattedDate = df.format(cal.getTime());

					currentDateTime.setText("Current Date&Time "
							+ formattedDate);

					currentDateTime.setFocusable(false);

				} catch (Exception e) {
				}
			}
		});
	}

	class CountDownRunner implements Runnable {
		// @Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					doWork();
					Thread.sleep(1000 * 60); // Pause of 1 Second
				} catch (InterruptedException e) {

					Thread.currentThread().interrupt();
				}

				catch (Exception e) {
				}
			}
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		myThread.interrupt();
	}

}
