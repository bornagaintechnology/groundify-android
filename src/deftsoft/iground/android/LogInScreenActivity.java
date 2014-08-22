package deftsoft.iground.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import deftsoft.android.iground.utility.SignupDetail;
import deftsoft.android.iground.utility.Utility;
import deftsoft.android.iground.utility.WebServiceLinks;
import deftsoft.android.iground.utility.WebServiceResponse;
import deftsoft.android.iground.utility.WebServiceResponse.onWebResponse;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.provider.Settings.Secure;

public class LogInScreenActivity extends Activity implements OnClickListener,
		onWebResponse {

	private final String TAG = "LogInScreenActivity";

	EditText userNameEditText, passwordEditText;

	Button loginButton, remembarmeButton;

	private ImageView back;

	private String android_id = "";

	Utility util;

	Context context;

	SharedPreferences pref;

	SharedPreferences.Editor editor;

	int userNameLength = 0;

	int passwordlength = 0;

	private boolean isrememberButton = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO Auto-generated method stub

		setContentView(R.layout.activity_manual_login);
		widgetInitialize();
		setLisner();
	}

	private void widgetInitialize() {
		// TODO Auto-generated method stub

		context = LogInScreenActivity.this;

		pref = context.getSharedPreferences("Login Remember", 0);

		editor = pref.edit();

		util = new Utility();

		android_id = Secure.getString(context.getContentResolver(),

		Secure.ANDROID_ID);

		Log.i(TAG, " Device Id is =" + android_id);

		back = (ImageView) findViewById(R.id.back_imageView);

		userNameEditText = (EditText) findViewById(R.id.loginUserName_editText);

		passwordEditText = (EditText) findViewById(R.id.loginPassword_editText);

		loginButton = (Button) findViewById(R.id.login_button);

		remembarmeButton = (Button) findViewById(R.id.remember_button);

	}

	private void setLisner() {
		// TODO Auto-generated method stub

		back.setOnClickListener(this);

		loginButton.setOnClickListener(this);

		remembarmeButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.login_button:

			if (userNameEditText.getText().toString().trim().length() > 0

			&& passwordEditText.getText().toString().trim().length() > 0) {

				if (util.isOnline(context)) {

					String loginUrl = WebServiceLinks.Login + "&username="
							+ userNameEditText.getText().toString().trim()
							+ "&password="
							+ passwordEditText.getText().toString().trim()
							+ "&type=" + "login" + "&devicetoken=" + android_id;
					Log.i(TAG, "Login Url is " + loginUrl);

					new WebServiceResponse(context, loginUrl,

					LogInScreenActivity.this);
				}

				else {

					util.toastMessage(context, "There is No Internet Available");
				}

			}

			else {

				util.toastMessage(context,

				"Please Enter User Name and Password");

			}

			break;

		case R.id.remember_button:

			isrememberButton = true;

			// util.toastMessage(context, "RemmBer Button is Pressed");

			break;

		case R.id.back_imageView:

			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onSuccess(String result) {
		// TODO Auto-generated method stub

		Log.i(TAG, "result = " + result);

		JSONObject jsonObject;

		JSONObject jsonDataobj;

		try {
			jsonObject = new JSONObject(result);

			if (jsonObject.getString("result").equals("success")) {

				util.toastMessage(context, jsonObject.getString("result"));

				util.toastMessage(context, jsonObject.getString("result"));

				Log.i( TAG, "Login Satatus is =" + jsonObject.getString("result"));

				jsonDataobj = jsonObject.getJSONObject("data");

				String UserName = jsonDataobj.getString("username");

				String Email = jsonDataobj.getString("email");

				String Father_Fname = jsonDataobj.getString("father_fname");

				String Father_Lname = jsonDataobj.getString("father_lname");

				String Mother_Fname = jsonDataobj.getString("mother_fname");

				String Mother_Lname = jsonDataobj.getString("mother_lname");

				String Street_Address = jsonDataobj.getString("street_address");

				String City = jsonDataobj.getString("city");

				String Home_Phone = jsonDataobj.getString("home_phone");

				String Work_Phone = jsonDataobj.getString("work_phone");

				String State = jsonDataobj.getString("state");

				String Zip = jsonDataobj.getString("zip");

				String Device_Token = jsonDataobj.getString("devicetoken");

				String Share_Info = jsonDataobj.getString("share_inforamtion");

				String User_ID = jsonDataobj.getString("userid");

				if (isrememberButton) {

					editor.putBoolean("Already Login", true);

					editor.putString("User Id", User_ID);

					editor.commit();

					Log.i(TAG, "User Id is With Remember is = " + User_ID);

					isrememberButton = false;

					Intent homeScrren = new Intent(LogInScreenActivity.this,
							Home.class);

					startActivity(homeScrren);

					finish();

				}

				else {

					editor.putString("User Id", User_ID);

					editor.commit();

					Log.i(TAG, "User Id is With Remember is = " + User_ID);

					Intent homeScrren = new Intent(LogInScreenActivity.this,
							
							Home.class);

					startActivity(homeScrren);

					finish();
				}

			}

			else {

				 util.toastMessage(context, jsonObject.getString("data"));

				 clearmethod();

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void clearmethod() {
		// TODO Auto-generated method stub
		
		userNameEditText.setText("");

		passwordEditText.setText("");

	}

	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub

		Log.i(TAG, "error = " + error);

	}

}
