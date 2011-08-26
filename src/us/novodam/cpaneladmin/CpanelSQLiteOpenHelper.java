package us.novodam.cpaneladmin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CpanelSQLiteOpenHelper extends SQLiteOpenHelper {
	
	private static final int VERSION = 1;
	private static final String DB_NAME  = "cpaneladminapp";
	private static final String ACCOUNTS_TABLE  = "accounts";
	private static final String SITES_TABLE = "sites";
	private static final String FUNCTIONS_TABLE = "functs";
	private static final String ACCOUNT_FUNCTIONS_TABLE = "acctfunct";

	public CpanelSQLiteOpenHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("drop table if exists " + ACCOUNTS_TABLE + ";");
		db.execSQL("drop table if exists " + SITES_TABLE + ";");
		db.execSQL("drop table if exists " + FUNCTIONS_TABLE + ";");
		db.execSQL("drop table if exists " + ACCOUNT_FUNCTIONS_TABLE + ";");
		createTables(db);
	}

	private void createTables(SQLiteDatabase db) {
		db.execSQL("create table " + ACCOUNTS_TABLE + " (" +
					"id integer primary key autoincrement not null," +
					"siteName text not null," +
					"userName text not null," +
					"accessHash text not null" +
					");"
				);
		db.execSQL("create table " + SITES_TABLE + " (" +
					"id integer primary key autoincrement not null," +
					"siteName text not null," +
					"url text not null," +
					"accountID integer not null," +
					"diskQuote integer not null," +
					"diskUsed integer not null," +
					"ipaddress text not null," +
					"email text not null," +
					"FOREIGN KEY(accountID) REFERENCES " + ACCOUNTS_TABLE + "(id)" +
					");"
				);
		db.execSQL("create table " + FUNCTIONS_TABLE + " (" +
					"id integer primary key autoincrement not null," +
					"apiCall text not null," +
					"description text" +
					");"
				);
		db.execSQL("create table " + ACCOUNT_FUNCTIONS_TABLE + " (" +
					"id integer primary key autoincrement not null," +
					"accountID not null," +
					"functionID not null," +
					"FOREIGN KEY(accountID) REFERENCES " + ACCOUNTS_TABLE + "(id)," +
					"FOREIGN KEY(functionID) REFERENCES " + FUNCTIONS_TABLE + "(id)" +
					");"
				);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
