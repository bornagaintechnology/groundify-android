package deftsoft.iground.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Home extends Activity implements OnClickListener {

	private Button addChild_Button, deleteChild_Button, groundChild_Button,
			
	viewConseq_Button;

	private ImageView back;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO Auto-generated method stub

		setContentView(R.layout.activity_child_home);

		widgetIntialize();

		setListener();

	}

	private void setListener() {
		// TODO Auto-generated method stub

		back.setOnClickListener(this);

		addChild_Button.setOnClickListener(this);

		deleteChild_Button.setOnClickListener(this);

		groundChild_Button.setOnClickListener(this);

		viewConseq_Button.setOnClickListener(this);
	}

	private void widgetIntialize() {
		// TODO Auto-generated method stub

		back = (ImageView) findViewById(R.id.back_imageView);

		addChild_Button = (Button) findViewById(R.id.addChild_button);

		deleteChild_Button = (Button) findViewById(R.id.deleteChild_button);

		groundChild_Button = (Button) findViewById(R.id.groundChild_button);

		viewConseq_Button = (Button) findViewById(R.id.viewConseq_button);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.back_imageView:

			finish();

			break;

		case R.id.addChild_button:

			
			startActivity(new Intent(Home.this, AddChildScreen.class));

			break;

		
		case R.id.deleteChild_button:

		
			startActivity(new Intent(Home.this, DeleteChildScreen.class));

			break;

		case R.id.groundChild_button:

			
			startActivity(new Intent(Home.this, GrounChild.class));

			break;

		case R.id.viewConseq_button:
			
			
			startActivity(new Intent(Home.this, ViewConsequence.class));
			


			break;

		
		default:
			
			break;
		}

	}

}
