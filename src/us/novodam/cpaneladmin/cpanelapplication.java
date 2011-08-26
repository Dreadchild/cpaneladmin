package us.novodam.cpaneladmin;

import java.util.ArrayList;
import us.novodam.cpaneladmin.objects.cpanelAccount;
import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class cpanelapplication extends Application {
	
	private SQLiteDatabase database;
	private ArrayList<cpanelAccount> accounts;
	
	@Override
	public void onCreate() {
		super.onCreate();
		CpanelSQLiteOpenHelper helper = new CpanelSQLiteOpenHelper(this);
		database = helper.getWritableDatabase();
		if (null == accounts) {
			loadAccounts();
		}
		//why does closing out the help kill the program when trying to insert even though it isn't used again (destroy reference that database uses?)
		//helper.close();
	}
	
	public void setAccounts(ArrayList<cpanelAccount> accounts) {
		this.accounts = accounts;
	}
	
	public ArrayList<cpanelAccount> getAccounts() {
		return accounts;
	}

	private void loadAccounts() {
		accounts = new ArrayList<cpanelAccount>();
		Cursor accountCursor = database.query("accounts", new String[]{"id", "siteName","userName","accessHash"}, null, null, null, null, "id");
		accountCursor.moveToFirst();
		cpanelAccount a;
		if(!accountCursor.isAfterLast()) {
			do {
				long id = accountCursor.getLong(0);
				String siteName = accountCursor.getString(1);
				String userName = accountCursor.getString(2);
				String accessHash = accountCursor.getString(3);
				a = new cpanelAccount(id, siteName, userName, accessHash);
				accounts.add(a);
			} while (accountCursor.moveToNext());
		}
		accountCursor.close();
	}
	
	public boolean accountExists(cpanelAccount a) {
		String sql = String.format("SELECT * FROM accounts as a WHERE a.siteName = \"%s\"", a.getSiteName());
		Cursor acctCursor = database.rawQuery(sql, null);
		acctCursor.moveToFirst();
		if (!acctCursor.isAfterLast()) {
			acctCursor.close();
			return true;
		}
		acctCursor.close();
		return false;
	}
	
	public boolean deleteAccount(cpanelAccount a) {
		String where = String.format("siteName = \"%s\"", a.getSiteName());
		try {
			database.delete("accounts", where, null);
			accounts.remove(a);
		} catch (Exception e) {
			accounts.add(a);
			Log.e("cpanelapplication","account deletion", e);
			return false;
		}
		return true;
	}
	
	public void addAccount(cpanelAccount a) {
		ContentValues values = new ContentValues();
		values.put("siteName", a.getSiteName());
		values.put("userName", a.getUserName());
		values.put("accessHash", a.getAccessHash());
		a.setId(database.insert("accounts", null, values));
		if (a.getId() > 0)
			accounts.add(a);
	}

	public void saveAccount(cpanelAccount a) {
		ContentValues values = new ContentValues();
		values.put("siteName", a.getSiteName());
		values.put("userName", a.getUserName());
		values.put("accessHash", a.getAccessHash());
		String where = String.format("%s = %d", "id", a.getId());
		database.update("accounts", values, where, null);
	}

	public void onTerminate() {
		database.close();
		super.onTerminate();
	}
}
