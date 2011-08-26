package us.novodam.cpaneladmin.activities;

import us.novodam.cpaneladmin.R;
import us.novodam.cpaneladmin.cpanelapplication;
import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class CPanelactivity extends Activity {

	public CPanelactivity() {
		super();
	}
	
	protected cpanelapplication getStuffApplication() {
		return (cpanelapplication)getApplication();
	}
	
	protected void displayAccounts(String accounts) {
		final Dialog d = new Dialog(this);
		Window window = d.getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		d.setTitle("Account Actions:");
		d.setContentView(R.layout.functiondialog);
		TextView text = (TextView)d.findViewById(R.id.tvAccounts);
		text.setText(accounts.toString());
		Button btnBack = (Button)d.findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				d.hide();
			}
		});
		d.show();
	}
}
