package deftsoft.android.iground.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class Utility {

	private final String TAG = "Utility";

	private boolean mConnected = false;

	public boolean isOnline(Context context) {
	
		try {
			 
			
			Log.i(TAG, "Detect Connection");

			ConnectivityManager connectivityManager = (ConnectivityManager) context

			.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();
			
			mConnected = networkInfo != null && networkInfo.isAvailable()
					
					&& networkInfo.isConnected();
			
			Log.i(TAG, "mConnected = " + mConnected);

		} catch (Exception e) {

			mConnected = false;
			
			e.printStackTrace();
		}
		
		Log.i(TAG, "mConnected = " + mConnected);

		return mConnected;
	}

	public void toastMessage(Context cont, String message) {

		Toast.makeText(cont, message, Toast.LENGTH_LONG).show();

	}

	public boolean isEmailValid (Context context, String email )
	
	{
	
	  String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
   
	  CharSequence inputStr = email;
                     
      Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
   
      Matcher matcher = pattern.matcher(inputStr);
	
		if (matcher.matches())
		
		{
			
			return true ;
			
		}
		
		else {

			
			return false ;
		}
		
		
		
	}
	
	
	
}
	
	

