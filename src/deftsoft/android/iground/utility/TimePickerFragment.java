package deftsoft.android.iground.utility;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class TimePickerFragment extends DialogFragment {

	private int hour, mint, second;

	OnTimeSetListener ontimeSet;

	public TimePickerFragment() {

	}

	public void setCallBack(OnTimeSetListener ontime) {
		ontimeSet = ontime;
	}

	@Override
	public void setArguments(Bundle args) {
		// TODO Auto-generated method stub
		super.setArguments(args);

		hour = args.getInt("hour");
		mint = args.getInt("mintue");

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		return new TimePickerDialog(getActivity(), ontimeSet, hour, mint, false);
	}

}
