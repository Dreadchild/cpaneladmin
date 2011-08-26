package us.novodam.cpaneladmin.activities;

import us.novodam.cpaneladmin.R;
import us.novodam.cpaneladmin.objects.cpanelAccount;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addCpanelAccountActivity extends CPanelactivity {
	
	private Button btnSave;
	private Button btnTest;
	private Button btnCancel;
	private EditText txtSite;
	private EditText txtUser;
	private EditText txtHash;
	private String siteURL = "";
	private String userName = "";
	private String hashCode = "";
	private cpanelAccount acct;
	private boolean doesExist = false;

	public addCpanelAccountActivity() {
		super();
	}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addaccount);
        setUpViews();
    }
    
	private void setUpViews() {		
		btnTest = (Button)findViewById(R.id.btnTest);
		btnSave = (Button)findViewById(R.id.btnSave);
		btnCancel = (Button)findViewById(R.id.btncancel);
		txtSite = (EditText)findViewById(R.id.txtSiteName);
		txtUser = (EditText)findViewById(R.id.txtUserName);
		txtHash = (EditText)findViewById(R.id.txtAccessHash);
		
		if (null != this.getIntent().getExtras()) {
			Bundle b = this.getIntent().getBundleExtra("com.novodam.cpaneladmin.activeaccount");
			String sURL = b.getString("sn");
			String uName = b.getString("un");
			String hCode = b.getString("ah");
			txtSite.setText(sURL);
			txtUser.setText(uName);
			txtHash.setText(hCode);
		}
		
		btnTest.setOnClickListener(new View.OnClickListener() {	
			public void onClick(View v) {
				siteURL = txtSite.getText().toString();
				userName = txtUser.getText().toString();
				hashCode = txtHash.getText().toString();
				acct = new cpanelAccount(siteURL, userName, hashCode);
				displayAccounts(acct.checkFunctionality(false));
			}
		});
		btnSave.setOnClickListener(new View.OnClickListener() {	
			public void onClick(View v) {
				siteURL = txtSite.getText().toString();
				userName = txtUser.getText().toString();
				hashCode = txtHash.getText().toString();
				acct = new cpanelAccount(siteURL, userName, hashCode);
				acct.checkFunctionality(true);
				doesExist = getStuffApplication().accountExists(acct);
				try {
					if (!doesExist) {
						getStuffApplication().addAccount(acct);
						finish();
					} else if (doesExist) {
						getStuffApplication().saveAccount(acct);
						finish();
					}
				} catch (Exception e) {
					Log.e("addCpanelAccountActivity.setUpViews", "checking doesExist fubared:", e);
					//finish();
				}
			}
		});
		
		btnCancel.setOnClickListener(new View.OnClickListener() {	
			public void onClick(View v) {
					finish();
			}
		});
	}


}