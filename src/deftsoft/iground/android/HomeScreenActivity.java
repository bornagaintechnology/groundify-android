package deftsoft.iground.android;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphUser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import deftsoft.android.iground.utility.SignupDetail;
import deftsoft.android.iground.utility.Utility;
import deftsoft.android.iground.utility.WebServiceLinks;
import deftsoft.android.iground.utility.WebServiceResponse;
import deftsoft.android.iground.utility.WebServiceResponse.onWebResponse;

public class HomeScreenActivity extends Activity implements OnClickListener,

ConnectionCallbacks, OnConnectionFailedListener, onWebResponse {

	private final String TAG = "HomeScreenActivity";

	private Button createAccountbutton, signInbutton;

	private Context context;

	private String get_id, get_name, get_gender, get_email, get_birthday,

	get_locale, get_location;

	private String LOG_TAG = "MyFACEBOOKINFO";

	private static final int RC_SIGN_IN = 0;

	private SignInButton btnSignIn;

	private GoogleApiClient mGoogleApiClient;

	/**
	 * A flag indicating that a PendingIntent is in progress and prevents us
	 * from starting further intents.
	 */
	private boolean mIntentInProgress;

	private boolean mSignInClicked;

	private ConnectionResult mConnectionResult;

	private String android_id = "";

	String User_ID = "";

	Utility util;

	ArrayList<SignupDetail> GooglePlusLoginDetailList = new ArrayList<SignupDetail>();

	String UserName_FromFacebook, FirstName_FromFacebook,

	LastName_FromFacebook, UserId_FromFacebook, Email_FromFacebook;
	/*
	 * FB Variables
	 */

	private Button fb_loginButton;

	private GraphUser user;

	private ViewGroup controlsContainer;

	SharedPreferences pref;

	SharedPreferences.Editor editor;

	private Session.StatusCallback statusCallback = new SessionStatusCallback();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home_screen);

		widgetIntialize();

		getKeyHash();

		setLisner();

		intializeGooglePlusApi();

	}

	// private void fbSessionMethod(Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	//
	// Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
	// Session session = Session.getActiveSession();
	//
	// if (session == null) {
	//
	// if (savedInstanceState != null) {
	//
	// session = Session.restoreSession(context, null, statusCallback,
	// savedInstanceState);
	// }
	//
	// if (session == null) {
	//
	// session = new Session(context);
	// }
	//
	// Session.setActiveSession(session);
	//
	// if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
	//
	// session.openForRead(new Session.OpenRequest(this)
	// .setPermissions(
	// Arrays.asList("public_profile", "email"))
	// .setCallback(statusCallback));
	// }
	// }
	// }

	private void getKeyHash() {
		// TODO Auto-generated method stub

		try {
			PackageInfo info = getPackageManager().getPackageInfo(

			"com.facebook.samples.loginhowto",

			PackageManager.GET_SIGNATURES);

			for (Signature signature : info.signatures) {

				MessageDigest md = MessageDigest.getInstance("SHA");

				md.update(signature.toByteArray());

				Log.d("KeyHash:",

				Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}
	}

	private void widgetIntialize() {
		// TODO Auto-generated method stub

		context = HomeScreenActivity.this;

		util = new Utility();

		pref = context.getSharedPreferences("Login Remember", 0);

		editor = pref.edit();

		android_id = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);
		createAccountbutton = (Button) findViewById(R.id.createAcount_button);

		signInbutton = (Button) findViewById(R.id.signIn_button);

		btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);

		fb_loginButton = (Button) findViewById(R.id.authButton);

	}

	private void setLisner() {
		// TODO Auto-generated method stub

		createAccountbutton.setOnClickListener(this);

		signInbutton.setOnClickListener(this);

		btnSignIn.setOnClickListener(this);

		fb_loginButton.setOnClickListener(this);

	}

	private void intializeGooglePlusApi() {
		// TODO Auto-generated method stub
		// Initializing google plus api client
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).addApi(Plus.API)
				.addScope(Plus.SCOPE_PLUS_LOGIN).build();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.home_screen, menu);

		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.createAcount_button:

			Intent signupScreen = new Intent(HomeScreenActivity.this,

			SignUpScreenActivity.class);

			startActivity(signupScreen);

			break;

		case R.id.signIn_button:

			if (pref.getBoolean("Already Login", false)) {

				// util.toastMessage(context, "You Have Already Login");

				Toast.makeText(context, "You Have Already Login",

				Toast.LENGTH_LONG).show();

				startActivity(new Intent(HomeScreenActivity.this, Home.class));

			}

			else {

				Intent loginScreen = new Intent(HomeScreenActivity.this,

				LogInScreenActivity.class);

				startActivity(loginScreen);

				finish();
			}

			break;

		case R.id.authButton:

			if (util.isOnline(context)) {

				LoginAndFetchFacebookProfileInfo();

			} else

			{

				util.toastMessage(context,

				"There is no Internet Connection is Available");

			}

			break;

		case R.id.btn_sign_in:

			/*
			 * g+ Login Button
			 */

			if (util.isOnline(context)) {

				signInWithGplus();

			} else {

				util.toastMessage(context,

				"There is no Internet Connection is Available");
			}

			break;

		default:

			break;
		}

	}

	public void makeFacebookMeRequest(final Session session) {

		Request.newMeRequest(session, new Request.GraphUserCallback() {

			@Override
			public void onCompleted(GraphUser user, Response response) {

				if (user != null) {

					String access_token = session.getAccessToken();

					Log.d("Response", "Response=" + response);

					Log.d("AccessToken", access_token);

					UserId_FromFacebook = user.getId();

					UserName_FromFacebook = user.getUsername();

					FirstName_FromFacebook = user.getFirstName();

					LastName_FromFacebook = user.getLastName();

					try {

						Email_FromFacebook = user.asMap().get("email")
								.toString();

						// Toast.makeText(getApplicationContext(),
						// "" + Email_FromFacebook, Toast.LENGTH_LONG)
						// .show();

					} catch (Exception e) {

						// e.printStackTrace();

					}

				}

				MoveToHomeScreenAfterRegistration(UserName_FromFacebook,
						FirstName_FromFacebook, LastName_FromFacebook,
						Email_FromFacebook);

			}

		}).executeAsync();
	}

	private void MoveToHomeScreenAfterRegistration(
			String userName_FromFacebook, String firstName_FromFacebook,
			String lastName_FromFacebook, String email_FromFacebook) {
		// TODO Auto-generated method stub

		Toast.makeText(getApplicationContext(), "" + Email_FromFacebook,
				
				Toast.LENGTH_LONG).show();
		/*
		Toast.makeText(getApplicationContext(), "User Name From FB" + userName_FromFacebook,
				
				Toast.LENGTH_LONG).show();*/
		

		String userName = firstName_FromFacebook + lastName_FromFacebook;

		if (util.isOnline(context)) {

			String loginURLFB = WebServiceLinks.Login + "&username=" +userName
					+ "&password=" + "" + "&email=" + email_FromFacebook
					 + "&devicetoken=" + android_id;

			Log.i(TAG, " FaceBook LogIn WebService Link is  " + loginURLFB);

			
			new WebServiceResponse(context, loginURLFB,HomeScreenActivity.this);

		}

		else {

			util.toastMessage(context,

			"There is no Internet Connection is Available");

		}

	}

	private void LoginAndFetchFacebookProfileInfo() {

		Session currentSession = Session.getActiveSession();

		if (currentSession == null || currentSession.getState().isClosed()) {

			Session session = new Session(getApplicationContext());
			Session.setActiveSession(session);
			currentSession = session;

		}

		if (currentSession.isOpened()) {
			// Do whatever u want. User has logged in

			makeFacebookMeRequest(currentSession);

			// Session session = Session.getActiveSession();
			// if (session != null && session.isOpened()) {
			//
			// Toast.makeText(getApplicationContext(), "session is opened",
			// Toast.LENGTH_LONG).show();
			//
			// /* clear session and again click will show facebook login */
			//
			// session = Session.getActiveSession();
			// if (!session.isClosed()) {
			// session.closeAndClearTokenInformation();
			// }
			//
			// }
			//
			// FacebookAuthentication();

		} else if (!currentSession.isOpened()) {

			FacebookAuthentication();
		}

	}

	private void FacebookAuthentication() {

		// Toast.makeText(getApplicationContext(),
		// "new session was created and requires login", Toast.LENGTH_LONG)
		// .show();

		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

		OpenRequest op = new Session.OpenRequest(HomeScreenActivity.this);

		op.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
		op.setCallback(statusCallback);

		op.setPermissions(Arrays.asList("email", "public_profile"));

		Session session = new Session.Builder(getApplicationContext()).build();

		Session.setActiveSession(session);

		session.openForRead(op);

	}

	private void signInWithGplus() {
		// TODO Auto-generated method stub
		if (!mGoogleApiClient.isConnecting()) {
			mSignInClicked = true;
			resolveSignInError();

		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		mGoogleApiClient.connect();

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}

	}

	/**
	 * Method to resolve any signin errors
	 * */

	private void resolveSignInError() {
		// TODO Auto-generated method stub

		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;

				mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
			} catch (SendIntentException e) {

				mIntentInProgress = false;

				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub

		if (!result.hasResolution()) {

			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
					0).show();
			return;
		}
		if (!mIntentInProgress) {
			// Store the ConnectionResult for later usage

			mConnectionResult = result;

			if (mSignInClicked) {
				// The user has already clicked 'sign-in' so we attempt to
				// resolve all
				// errors until the user is signed in, or they cancel.
				resolveSignInError();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {

		super.onActivityResult(requestCode, responseCode, intent);

		if (requestCode == RC_SIGN_IN) {
			if (responseCode != RESULT_OK) {

				mSignInClicked = false;
			}

			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {

				mGoogleApiClient.connect();
			}
		}

		if (Session.getActiveSession() != null)

			Session.getActiveSession().onActivityResult(this, requestCode,

			responseCode, intent);
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub

		mSignInClicked = false;

		Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

		// Get user's information

		getProfileInformation();

		// startActivity(new Intent(HomeScreenActivity.this,Home.class));

	}

	private void getProfileInformation() {
		// TODO Auto-generated method stub
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {

				Person currentPerson = Plus.PeopleApi

				.getCurrentPerson(mGoogleApiClient);

				String personName = currentPerson.getDisplayName();

				String personPhotoUrl = currentPerson.getImage().getUrl();

				String personGooglePlusProfile = currentPerson.getUrl();

				String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

				Log.i(TAG, "Name: " + personName + ", plusProfile: "
						+ personGooglePlusProfile + ", email: " + email
						+ ", Image: " + personPhotoUrl);
				if (util.isOnline(context)) {

					/*
					 * String googlePlusloginURL = WebServiceLinks.Login +
					 * "&username=" + personName.replaceAll(" ", "%20") +
					 * "&email=" + email + "&password=" + "" + "&type="+"" +
					 * "login" + "&devicetoken=" + android_id;
					 */

					String googlePlusloginURL = WebServiceLinks.Login
							+ "&username=" + personName.replaceAll(" ", "%20")
							+ "&password=" + "" + "&email=" + email  + "&devicetoken=" + android_id;

					Log.i(TAG, "Google Plus LogIn  WebService Link is "
							+ googlePlusloginURL);

					clearGoogleSession();

					new WebServiceResponse(context, googlePlusloginURL,

					HomeScreenActivity.this);

				}

				else {

					util.toastMessage(context,

					"There is no Internet Connection is Available");

				}

			}

			else {

				Toast.makeText(getApplicationContext(),

				"Person information is null", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clearGoogleSession() {
		// TODO Auto-generated method stub

		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			mGoogleApiClient.disconnect();
			mGoogleApiClient.connect();

		}

	}

	@Override
	public void onConnectionSuspended(int cause) {
		// TODO Auto-generated method stub

		mGoogleApiClient.connect();
	}

	@Override
	public void onSuccess(String result) {
		// TODO Auto-generated method stub

		// / Log.i(TAG, "Result is = " + result);

		JSONObject jsonObject;

		try {
			jsonObject = new JSONObject(result);

			if (jsonObject.getString("result").equals("success")) {

				JSONArray data = jsonObject.getJSONArray("data");

				int length = data.length();

				for (int i = 0; i < length; i++) {

					JSONObject jObject = data.getJSONObject(i);

					User_ID = jObject.getString("userid");

				}

				editor.putString("User Id", User_ID);

				editor.commit();

				Log.i(TAG, "User Id is With Remember is = " + User_ID);

				startActivity(new Intent(HomeScreenActivity.this, Home.class));
				finish();

			}

			else {

				util.toastMessage(context, jsonObject.getString("data"));

				Log.i(TAG,
						" Log in Web SerVice Respone of "
								+ jsonObject.getString("data"));

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub

		// Log.i(TAG, "error is = " + error);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(final Session session, SessionState state,
				Exception exception) {

			if (session.isOpened()) {

				
				makeFacebookMeRequest(session);

			
			}

		}
	}

}
