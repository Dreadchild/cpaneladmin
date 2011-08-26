package us.novodam.cpaneladmin;

import us.novodam.cpaneladmin.activities.addCpanelAccountActivity;
import us.novodam.cpaneladmin.adapters.cpanelAccountListAdapter;
import us.novodam.cpaneladmin.objects.cpanelAccount;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class cpaneladmin extends ListActivity {
	private cpanelapplication app;
	private cpanelAccountListAdapter Adapter;
	private final int MENU_ADD_ACCOUNT = 1;
	private final int MENU_EDIT_ACCOUNT = 2;
	private final int MENU_DELETE_ACCOUNT = 3;

    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.accountlist);
        //setUpViews();
        app = (cpanelapplication)getApplication();
        Adapter = new cpanelAccountListAdapter(this, app.getAccounts());
        setListAdapter(Adapter);
        registerForContextMenu(getListView());
    }
    
	//private void setUpViews() {
    	//keep this for now in case UI changes again
	//}
	
	public boolean onCreateOptionsMenu (Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_ADD_ACCOUNT, Menu.NONE, "Add Account");		
		return true;
	}
	
	
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Account Options");
		menu.add(0, MENU_EDIT_ACCOUNT, Menu.NONE, "Edit Account");
		menu.add(1, MENU_DELETE_ACCOUNT, Menu.NONE, "Delete Account");
	}

	public boolean onOptionsItemSelected(MenuItem i) {
		super.onOptionsItemSelected(i);
		switch(i.getItemId()){
		case(MENU_ADD_ACCOUNT):
			Intent intent = new Intent(cpaneladmin.this, addCpanelAccountActivity.class);
			startActivity(intent);
			return true;
		}
		return false;
	}

	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		cpanelAccount a = (cpanelAccount)getListAdapter().getItem(info.position);
		switch(item.getItemId()) {
		case(MENU_EDIT_ACCOUNT):
			Bundle b = new Bundle();
			b.putString("sn", a.getSiteName());
			b.putString("un", a.getUserName());
			b.putString("ah", a.getAccessHash());
			Intent i = new Intent(cpaneladmin.this, addCpanelAccountActivity.class);
			i.putExtra("com.novodam.cpaneladmin.activeaccount", b);
			startActivity(i);
			return true;
		case(MENU_DELETE_ACCOUNT):
			app.deleteAccount(a);
			Adapter.forcereload();
			return true;
		}
		return false;
	}
	
	protected void onResume() {
		super.onResume();
		Adapter.forcereload();
	}
}