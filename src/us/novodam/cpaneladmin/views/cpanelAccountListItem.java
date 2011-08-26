package us.novodam.cpaneladmin.views;

import us.novodam.cpaneladmin.objects.cpanelAccount;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class cpanelAccountListItem extends RelativeLayout {
	private cpanelAccount a;
	private TextView et;

	public cpanelAccountListItem(Context context) {
		super(context);
	}

	public cpanelAccountListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public cpanelAccountListItem(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	protected void onFinishInflate() {
		super.onFinishInflate();
		et = (TextView)findViewById(android.R.id.text1);
	}
	
	public void setAccount(cpanelAccount a) {
		this.a = a;
		et.setText(this.a.getSiteName());
	}
	
	public cpanelAccount getTask() {
		return a;
	}
}
