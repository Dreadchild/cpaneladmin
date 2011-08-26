package us.novodam.cpaneladmin.adapters;

import java.util.ArrayList;
import us.novodam.cpaneladmin.R;
import us.novodam.cpaneladmin.objects.cpanelAccount;
import us.novodam.cpaneladmin.views.cpanelAccountListItem;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class cpanelAccountListAdapter extends BaseAdapter {
	
	private ArrayList<cpanelAccount> accounts;
	private Context context;

	public cpanelAccountListAdapter(Context context, ArrayList<cpanelAccount> accounts) {
		super();
		this.accounts = accounts;
		this.context = context;
	}

	@Override
	public int getCount() {
		return accounts.size();
	}

	@Override
	public Object getItem(int position) {
		return (null == accounts) ? null : accounts.get(position);
	}

	@Override
	public long getItemId(int position) {
		//bad, fix later
		return position;
	}

	public void forcereload() {
		notifyDataSetChanged();
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		cpanelAccountListItem accountListItem;
		if (null == convertView) {
			accountListItem = (cpanelAccountListItem)View.inflate(context, R.layout.accountlistitem, null);
		} else {
			accountListItem=(cpanelAccountListItem)convertView;
		}
		accountListItem.setAccount(accounts.get(position));
		return accountListItem;
	}
}
