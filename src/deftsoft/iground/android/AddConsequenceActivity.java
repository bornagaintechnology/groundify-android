package deftsoft.iground.android;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import deftsoft.android.iground.utility.HttpUploader;
import deftsoft.android.iground.utility.HttpUploader.onAsyncResponse;
import deftsoft.android.iground.utility.ImageFilePath;
import deftsoft.android.iground.utility.Utility;
import deftsoft.android.iground.utility.WebServiceLinks;
import deftsoft.android.iground.utility.WebServiceResponse;
import deftsoft.android.iground.utility.WebServiceResponse.onWebResponse;

public class AddConsequenceActivity extends Activity implements
		OnClickListener, onWebResponse, onAsyncResponse {

	private static String TAG = "AddConsequenceActivity";

	private ImageView back;

	private ImageView imageToDisplay;

	private Button chooseImagebutton, addConseq;

	private EditText conseqNameediText;

	private String imagepath = null;

	private String upLoadServerUri = null;

	private int serverResponseCode = 0;

	private ProgressDialog dialog = null;

	String userId;

	SharedPreferences pref;

	Context context;

	Utility util;

	InputStream inputStream;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO Auto-generated method stub
		setContentView(R.layout.activity_add_consequence);

		widgetIntialize();

		setListner();
	}

	private void setListner() {
		// TODO Auto-generated method stub

		back.setOnClickListener(this);

		chooseImagebutton.setOnClickListener(this);

		addConseq.setOnClickListener(this);
	}

	private void widgetIntialize() {
		// TODO Auto-generated method stub

		util = new Utility();

		context = AddConsequenceActivity.this;

		pref = context.getSharedPreferences("Login Remember", 0);

		userId = pref.getString("User Id", null);

		Log.i(TAG, " User Id is From Login Screen is " + userId);

		back = (ImageView) findViewById(R.id.back_imageView);

		imageToDisplay = (ImageView) findViewById(R.id.image_display);

		conseqNameediText = (EditText) findViewById(R.id.conseq_editText);

		chooseImagebutton = (Button) findViewById(R.id.choose_button);

		addConseq = (Button) findViewById(R.id.addConseq_button);

		// upLoadServerUri =
		// "http://deftsoft.info/familytree//iGroundApp/uploads.php";

		upLoadServerUri = "http://qrfitnesssolutions.com/arttravels/upload_image.php";

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.back_imageView:

			finish();

			break;

		case R.id.choose_button:

			if (conseqNameediText.getText().toString().trim().length() > 0) {

				getImageFromGalary();

			} else {

				Toast.makeText(context, "Please Fill the Consequence Name ",
						Toast.LENGTH_LONG).show();
			}

			break;

		case R.id.addConseq_button:

			if (conseqNameediText.getText().toString().trim().length() > 0) {

				if (util.isOnline(context)) {

					if (!(imagepath==null)) {
						
						new HttpUploader(context, imagepath,
								AddConsequenceActivity.this).execute(imagepath);
					} else {

						
						util.toastMessage(context,
								"Please First Choose the Image");
					}
					
					
				

					/*
					 * String NewConseqUrl = WebServiceLinks.Set_Conseg_Image +
					 * "&userid=" + userId + "&consequence_name=" +
					 * conseqNameediText.getText().toString().trim() +
					 * "&consequence_image=" + "";
					 */
					/*
					 * new WebServiceResponse(context, NewConseqUrl,
					 * AddConsequenceActivity.this);
					 */

				} else {

					util.toastMessage(context,
							"There is No Internet Connection");

				}

			} else {

				Toast.makeText(context, "Please Fill the Consequence Name ",
						Toast.LENGTH_LONG).show();

			}

			break;

		default:

			break;
		}

	}

	private void getImageFromGalary() {
		// TODO Auto-generated method stub

		Intent intent = new Intent();

		intent.setType("image/*");

		intent.setAction(Intent.ACTION_GET_CONTENT);

		startActivityForResult(

		Intent.createChooser(intent, "Complete action using"), 1);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1 && resultCode == RESULT_OK) {

			Uri selectedImageUri = data.getData();

			int currentapiversion = android.os.Build.VERSION.SDK_INT;

			if (currentapiversion >= 19) {

				imagepath = ImageFilePath.getPath(getApplicationContext(),
						selectedImageUri);

				Log.i(TAG, "Uploading file path From KitKAt Device :  "
						+ imagepath);

				Toast.makeText(
						context,
						" Uploading file path From KitKAt Device : "
								+ imagepath,

						Toast.LENGTH_LONG).show();

			} else {

				imagepath = getPath(selectedImageUri);

				Toast.makeText(context, "Uploading file path:" + imagepath,

				Toast.LENGTH_LONG).show();

				Log.i(TAG, "Uploading file path:  " + imagepath);

			}

			imageToDisplay.setVisibility(View.VISIBLE);

			imageToDisplay.setImageURI(selectedImageUri);

		}

	}

	public String getPath(Uri contentURI) {

		Cursor cursor = null;

		try {
			String[] proj = { MediaStore.Images.Media.DATA };
			cursor = context.getContentResolver().query(contentURI, proj, null,
					null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	@Override
	public void onSuccess(String result) {
		// TODO Auto-generated method stub

		Log.i(TAG, "Success = " + result);

		try {

			JSONObject jobject = new JSONObject(result);

			if (jobject.getString("result").equalsIgnoreCase("success")) {

				Toast.makeText(context, jobject.getString("data"),
						Toast.LENGTH_LONG).show();

				resetFeild();
				
				finish();

			} else {

				Toast.makeText(context, jobject.getString("data"),
						Toast.LENGTH_LONG).show();
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void resetFeild() {
		// TODO Auto-generated method stub

		conseqNameediText.setText(" ");
		
		imageToDisplay.setVisibility(View.INVISIBLE);

	}

	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub

		Log.i(TAG, "error = " + error);

	}

	@Override
	public void onUploadSuccess(String result) {
		// TODO Auto-generated method stub

		Log.i(TAG, "Uploaded Image Success Response is  " + result);

		Toast.makeText(context,
				"Uploaded Image Success Response is  " + result,

				Toast.LENGTH_LONG).show();

		if (!(result == null))

		{

			newConsequenceWebServiceMethod(result);
		}

		else {

			Toast.makeText(context, "Server not Returned Uploaded Image Name ",
					Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public void onUploadError(String error) {
		// TODO Auto-generated method stub

	}

	private void newConsequenceWebServiceMethod(String result) {
		// TODO Auto-generated method stub

		if (util.isOnline(context)) {

			String imagename = "http://qrfitnesssolutions.com/arttravels/"+result;

			String NewConseqUrl = WebServiceLinks.Set_Conseg_Image
					+ "&userid="
					+ userId
					+ "&consequence_name="
					+ conseqNameediText.getText().toString().trim()
							.replaceAll(" ", "%20") + "&consequence_image="
					+ imagename;

			Log.i(TAG, "New Consequence WebserviceLink is " + NewConseqUrl);

			new WebServiceResponse(context, NewConseqUrl,
					AddConsequenceActivity.this);

		} else {

			util.toastMessage(context, "There is No Internet Connection");

		}

	}

}
