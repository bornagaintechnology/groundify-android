package deftsoft.android.iground.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

public class HttpUploader extends AsyncTask<String, Void, String> {

	private static String TAG = " HttpUploader";

	private final String PLEASE_WAIT = "Uploading  Please wait...";

	private ProgressDialog mProgressDialog;

	private String myImagePath;

	private Context context;

	InputStream inputStream;

	// private String imageuploadUrl =
	// "http://deftsoft.info/familytree//iGroundApp/images/upload.php";

	private String upLoadServerUri = "http://qrfitnesssolutions.com/arttravels/upload_image.php";

	private String outPut = "a=";

	private boolean isresult = false;

	public interface onAsyncResponse {

		public void onUploadSuccess(String result);

		public void onUploadError(String error);
	}

	private onAsyncResponse listener = null;

	public HttpUploader(Context con, String myPath, onAsyncResponse callback) {
		// TODO Auto-generated constructor stub

		this.myImagePath = myPath;

		context = con;

		mProgressDialog = new ProgressDialog(context);

		mProgressDialog.setMessage(PLEASE_WAIT);

		mProgressDialog.setCanceledOnTouchOutside(false);

		mProgressDialog.show();

		listener = callback;

	}

	@Override
	protected String doInBackground(String... myImagePath) {
		// TODO Auto-generated method stub

		for (String sdPath : myImagePath) {

			outPut=uploadFile(sdPath);
		}
		return outPut;
	}

	public String uploadFile(String myImagePath) {

		String the_string_response = null;

		Bitmap bitmap = BitmapFactory.decodeFile(myImagePath);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		boolean what = bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); // compress
																				// to

		System.out.println("what          " + what);// which format
													// you want.
		byte[] byte_arr = stream.toByteArray();

		String image_str = Base64.encodeBytes(byte_arr);

		System.out.println("image_str     " + image_str);

		final ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("image", image_str));

		try {
			HttpClient httpclient = new DefaultHttpClient();

			System.out.println("upLoadServerUri            " + upLoadServerUri);

			HttpPost httppost = new HttpPost(upLoadServerUri);

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpclient.execute(httppost);

			the_string_response = EntityUtils.toString(response.getEntity());

			isresult = true;

			Log.i(TAG, " Respone From Server " + the_string_response);

		} catch (final Exception e) {

			Log.i(TAG, " exception Msg During upload Image " + e.getMessage());

			the_string_response = "Upload Unsuccessful";

			isresult = false;

		}

		return the_string_response;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();

		}

		if (isresult) {
			listener.onUploadSuccess(result);
		} else {

			listener.onUploadError(result);
		}

		Toast.makeText(context, "Get ResPonser is  " + outPut,
				Toast.LENGTH_LONG).show();

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
}
