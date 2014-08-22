package deftsoft.iground.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

public class ThankYouActivity extends Activity {

	private ImageView back;
	
	private WebView thanks_msg ;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO Auto-generated method stub
		setContentView(R.layout.activity_thank_you);
		
		widgetIntialize();
		
		setListener();

	}

	private void setListener() {
		// TODO Auto-generated method stub
		back.setOnClickListener(new View.OnClickListener() {
		
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
			}
		});
	}

	private void widgetIntialize() {
		// TODO Auto-generated method stub
		
		back = (ImageView)findViewById(R.id.back_imageView);
		
		thanks_msg =(WebView)findViewById(R.id.msg_text);
		
		thanks_msg.loadUrl("file:///android_asset/thanksMessage.html");
		
	}

}
