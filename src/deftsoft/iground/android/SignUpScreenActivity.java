package deftsoft.iground.android;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import deftsoft.android.iground.utility.SignupDetail;
import deftsoft.android.iground.utility.Utility;
import deftsoft.android.iground.utility.WebServiceLinks;
import deftsoft.android.iground.utility.WebServiceResponse;
import deftsoft.android.iground.utility.WebServiceResponse.onWebResponse;

public class SignUpScreenActivity extends Activity implements onWebResponse {

	private final String TAG = "SignUpScreenActivity";

	private EditText faterFirstName, fatherLastName, motherFirstName,
			motherLastName, streetAddress, city, state, zip, homePhone,
			workPhone, emailAdrees, usaerName, password;
	private CheckBox checkInfo;

	private Button submitButton;

	private ImageView back;

	private Context context;

	private Utility util;

	private String android_id = "";

	ArrayList<SignupDetail> signupList = new ArrayList<SignupDetail>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO Auto-generated method stub

		setContentView(R.layout.activity_signup_screen);

		widgetIntialize();
		notEmptyCheckMethod();
		setLestner();
	}

	private void setLestner() {
		// TODO Auto-generated method stub

		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();
			}
		});

		submitButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (notEmptyCheckMethod()) {

					if (util.isEmailValid(context, emailAdrees.getText()
							.toString())) {

						if (util.isOnline(context)) {

							if (checkInfo.isChecked()) {
								String signUpUrl;
								try {
									signUpUrl = WebServiceLinks.Signup
											+ "&username="
											+ URLEncoder.encode(usaerName
													.getText().toString()
													.trim(), "utf-8")
											+ "&password="
											+ password.getText().toString()
													.trim()
											+ "&email="
											+ emailAdrees.getText().toString()
													.trim()
											+ "&father_fname="
											+ faterFirstName.getText()
													.toString().trim()
											+ "&father_lname="
											+ fatherLastName.getText()
													.toString().trim()
											+ "&mother_fname="
											+ motherFirstName.getText()
													.toString().trim()
											+ "&mother_lname="
											+ motherLastName.getText()
													.toString().trim()
											+ "&street_address="
											+ URLEncoder.encode(streetAddress
													.getText().toString()
													.trim(), "utf-8")
											+ "&city="
											+ URLEncoder
													.encode(city.getText()
															.toString().trim(),
															"utf-8")
											+ "&home_phone="
											+ homePhone.getText().toString()
													.trim()
											+ "&work_phone="
											+ workPhone.getText().toString()
													.trim()
											+ "&share_inforamtion="
											+ "Yes"
											+ "&state="
											+ URLEncoder
													.encode(state.getText()
															.toString().trim(),
															"utf-8") + "&zip="
											+ zip.getText().toString().trim()
											+ "&devicetoken=" + android_id;

									Log.i(TAG,
											" Signup WebService Link with Yes "
													+ signUpUrl);

									new WebServiceResponse(context, signUpUrl,
											SignUpScreenActivity.this);

								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block

									e.printStackTrace();
								}

							}

							else {

								String signUpUrl;
								try {
									signUpUrl = WebServiceLinks.Signup
											+ "&username="
											+ URLEncoder.encode(usaerName
													.getText().toString()
													.trim(), "utf-8")
											+ "&password="
											+ password.getText().toString()
													.trim()
											+ "&email="
											+ emailAdrees.getText().toString()
													.trim()
											+ "&father_fname="
											+ faterFirstName.getText()
													.toString().trim()
											+ "&father_lname="
											+ fatherLastName.getText()
													.toString().trim()
											+ "&mother_fname="
											+ motherFirstName.getText()
													.toString().trim()
											+ "&mother_lname="
											+ motherLastName.getText()
													.toString().trim()
											+ "&street_address="
											+ URLEncoder.encode(streetAddress
													.getText().toString()
													.trim(), "utf-8")
											+ "&city="
											+ URLEncoder
													.encode(city.getText()
															.toString().trim(),
															"utf-8")
											+ "&home_phone="
											+ homePhone.getText().toString()
													.trim()
											+ "&work_phone="
											+ workPhone.getText().toString()
													.trim()
											+ "&share_inforamtion="
											+ "No"
											+ "&state="
											+ URLEncoder
													.encode(state.getText()
															.toString().trim(),
															"utf-8") + "&zip="
											+ zip.getText().toString().trim()
											+ "&devicetoken=" + android_id;

									Log.i(TAG,
											" Signup WebService Link with No "
													+ signUpUrl);

									new WebServiceResponse(context, signUpUrl,
											SignUpScreenActivity.this);

								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}

						}

						else

						{

							util.toastMessage(context,

							"There is no Internet Connection is Available");

						}

					}

					else {

						util.toastMessage(context,

						"Please Enter a Valid Email Id");
					}

				}

				else {

					util.toastMessage(context,
							"Please Do not leave any field empty ");

				}

			}

		});
	}

	private void widgetIntialize() {
		// TODO Auto-generated method stub

		context = SignUpScreenActivity.this;

		util = new Utility();

		android_id = Secure.getString(context.getContentResolver(),

		Secure.ANDROID_ID);

		back = (ImageView) findViewById(R.id.back_imageView);

		faterFirstName = (EditText) findViewById(R.id.father_fName);

		fatherLastName = (EditText) findViewById(R.id.father_lName);

		motherFirstName = (EditText) findViewById(R.id.mother_fName);

		motherLastName = (EditText) findViewById(R.id.mother_lName);

		streetAddress = (EditText) findViewById(R.id.streetAdd_editText);

		city = (EditText) findViewById(R.id.city_editText);

		state = (EditText) findViewById(R.id.state_editText);

		zip = (EditText) findViewById(R.id.zip_editText);

		homePhone = (EditText) findViewById(R.id.homePhone_editText);

		workPhone = (EditText) findViewById(R.id.workPhone_editText);

		emailAdrees = (EditText) findViewById(R.id.email_editText);

		usaerName = (EditText) findViewById(R.id.userName_editText);

		password = (EditText) findViewById(R.id.password_edittext);

		checkInfo = (CheckBox) findViewById(R.id.accept_checkbox);

		submitButton = (Button) findViewById(R.id.submit_button);
	}

	private boolean notEmptyCheckMethod() {

		// TODO Auto-generated method stub

		int ffNameLength = faterFirstName.getText().toString().length();

		int fLNameLength = fatherLastName.getText().toString().length();

		int mfNameLength = motherFirstName.getText().toString().length();

		int mlNameLength = motherLastName.getText().toString().length();

		int sAddLength = streetAddress.getText().toString().length();

		int cityLength = city.getText().toString().toString().length();

		int stateLength = state.getText().toString().length();

		int zipLength = zip.getText().toString().length();

		int hpLength = homePhone.getText().toString().length();

		int wpLength = workPhone.getText().toString().length();

		int emailLength = emailAdrees.getText().toString().length();

		int uNameLength = usaerName.getText().toString().length();

		int pswdLength = password.getText().toString().length();

		if (ffNameLength > 0 && fLNameLength > 0 && mfNameLength > 0
				&& mlNameLength > 0 && sAddLength > 0 && cityLength > 0
				&& stateLength > 0 && zipLength > 0 && hpLength > 0
				&& wpLength > 0 && emailLength > 0 && uNameLength > 0
				&& pswdLength > 0)

		{

			return true;
		}

		else {

			return false;
		}

	}

	@Override
	public void onSuccess(String result) {
		// TODO Auto-generated method stub

		Log.i(TAG, "Result is =" + result);

		JSONObject jsonObject;

		signupList.clear();

		try {
			jsonObject = new JSONObject(result);

			if (jsonObject.getString("result").equals("success")) {

				util.toastMessage(context, jsonObject.getString("result"));

				JSONArray data = jsonObject.getJSONArray("data");

				int length = data.length();

				for (int i = 0; i < length; i++) {

					SignupDetail model = new SignupDetail();

					JSONObject jObject = data.getJSONObject(i);

					model.setUsername(jObject.getString("username"));

					model.setEmail("email");

					model.setFather_fname("father_fname");

					model.setFather_lname("father_lname");

					model.setMother_fname("mother_fname");

					model.setMother_lname("mother_lname");

					model.setStreet_address("street_address");

					model.setCity("city");

					model.setHome_phone("home_phone");

					model.setWork_phone("work_phone");

					model.setState("state");

					model.setZip("zip");

					model.setDevicetoken("devicetoken");

					model.setShare_inforamtion("share_inforamtion");

					model.setUserid("userid");

					signupList.add(model);

				}

				clearMethod();

				startActivity(new Intent(SignUpScreenActivity.this,

				ThankYouActivity.class));

				finish();

			}

			else {

				util.toastMessage(context, jsonObject.getString("data"));

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

	}

	private void clearMethod() {
		// TODO Auto-generated method stub

		faterFirstName.setText("");

		fatherLastName.setText("");

		motherFirstName.setText("");

		motherLastName.setText("");

		streetAddress.setText("");

		city.setText("");

		state.setText("");

		zip.setText("");

		homePhone.setText("");

		workPhone.setText("");

		emailAdrees.setText("");

		usaerName.setText("");

		password.setText("");

		checkInfo.setChecked(false);

	}

	@Override
	public void onError(String error) {

		// TODO Auto-generated method stub

		Log.i(TAG, "error =  " + error);
	}
}
