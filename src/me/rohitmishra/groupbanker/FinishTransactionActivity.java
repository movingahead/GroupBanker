package me.rohitmishra.groupbanker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

public class FinishTransactionActivity extends Activity { 
	private GroupBankerApplication mApplication ;
	private static String TAG = "FinishTransactionActivity" ;
	
	private TextView mDescription ;
	private TextView mAmount ;
	private String []selectedIds ;
	private TextView[] names ;
	private EditText[] paid ;
	private EditText userPaid ;
	
	private int mID = 1234567 ;
	
	@Override
	public void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.finishtransaction);
		
		mApplication = (GroupBankerApplication) getApplication();
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		String description = bundle.getString("description");
		String amount = bundle.getString("amount");
		selectedIds = bundle.getStringArray("selectedIds");
		
		ScrollView scrollView = new ScrollView(this);
		RelativeLayout relativeLayout = new RelativeLayout(this) ;
		// relativeLayout.setOrientation(relativeLayout.VERTICAL);
		scrollView.addView(relativeLayout);
		
		
		// ViewGroup relativeLayout = (ViewGroup) findViewById(R.id.paid_relativeLayout);
		// Log.d(TAG, "relativeLayout made");
		
		//mDescription = (TextView) findViewById(R.id.description) ;
		//mAmount = (TextView) findViewById(R.id.amount);
		
		mDescription = new TextView(this);
		
		mAmount = new TextView(this);
		
		mDescription.setText(description);
		mDescription.setId(mID);	// Set a random id for alignment 
		
		mAmount.setText("Amount = Rs. " + amount);
		mAmount.setId(getId());
		
		final RelativeLayout.LayoutParams paramsDescription = 
			new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		paramsDescription.addRule(RelativeLayout.ALIGN_LEFT);
		//paramsDescription.setMargins(0, 0, 200, 30) ;	
		
		final RelativeLayout.LayoutParams paramsAmount = 
			new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		paramsAmount.addRule(RelativeLayout.ALIGN_LEFT);
		paramsAmount.addRule(RelativeLayout.BELOW, mDescription.getId());
		
		
		relativeLayout.addView(mDescription, paramsDescription);
		relativeLayout.addView(mAmount, paramsAmount);
		
		
		TextView formTitle = new TextView(this) ;
		formTitle.setId(getId());
		formTitle.setText(R.string.finish_question);
		
		final RelativeLayout.LayoutParams paramsFormTitle = 
			new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		//paramsFormTitle.addRule(RelativeLayout.ALIGN_LEFT);
		paramsFormTitle.addRule(RelativeLayout.BELOW, mAmount.getId());
		
		relativeLayout.addView(formTitle, paramsFormTitle);
		
		/* Set up a separate textview and edittext for the user 
		 * We are getting username from GroupBankerApplication
		 */
		
		
		TextView userName = new TextView(this) ;
		userName.setId(getId());
		userName.setText(mApplication.getUserName());
		Log.v(TAG, "username value:" + mApplication.getUserName()) ;
		userName.setPadding(10, 30, 60, 0);
		
		final RelativeLayout.LayoutParams paramsUserName = 
			new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		paramsUserName.addRule(RelativeLayout.ALIGN_LEFT);
		paramsUserName.addRule(RelativeLayout.BELOW, formTitle.getId());
		//paramsUserName.width = 60 ;
		relativeLayout.addView(userName, paramsUserName);
		
		
		userPaid = new EditText(this);
		userPaid.setId(getId());

		//TODO Will be good if we can control the width without hardcoding
		
		final RelativeLayout.LayoutParams paramsUserPaid = 
			new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		paramsUserPaid.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		paramsUserPaid.addRule(RelativeLayout.RIGHT_OF, userName.getId());
		paramsUserPaid.addRule(RelativeLayout.ALIGN_TOP, userName.getId());
		relativeLayout.addView(userPaid, paramsUserPaid);
		
		// Now lets set the textViews and edittexts for all who have paid 
		
		names = new TextView[selectedIds.length] ;
		paid = new EditText[selectedIds.length] ;
		TextView rowTextView;
		EditText rowEditText ;
		int i ;
	
		for (i = 0; i <  selectedIds.length; i++)	{ 
			rowTextView = new TextView(this) ;
			rowTextView.setId(getId());
			
			rowEditText = new EditText(this) ;
			rowEditText.setId(getId());
			
			String name = mApplication.getFriendsDbAdapter().fetchFriendName(selectedIds[i]);
			rowTextView.setText(name);
			
			//rowTextView.setMinimumWidth(50);
			rowTextView.setPadding(10, 30, 60, 0);
			
		//	rowEditText.setMinWidth(200);
			
			final RelativeLayout.LayoutParams paramsTextView = 
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			
			// The first TextView will be below userName TextView. Rest below names[i-1]
			if ( i == 0)	{
				paramsTextView.addRule(RelativeLayout.BELOW, userName.getId());
			}
			else {
				paramsTextView.addRule(RelativeLayout.BELOW, names[i-1].getId());
			}
			paramsTextView.addRule(RelativeLayout.ALIGN_LEFT);
			
			
			final RelativeLayout.LayoutParams paramsEditText = 
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			paramsEditText.addRule(RelativeLayout.RIGHT_OF, rowTextView.getId());
			paramsEditText.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			paramsEditText.addRule(RelativeLayout.ALIGN_TOP, rowTextView.getId());
			
		
			relativeLayout.addView(rowTextView, paramsTextView);
			relativeLayout.addView(rowEditText, paramsEditText);
			
			names[i] = rowTextView ;
			paid[i] = rowEditText ;
		}
		
		this.setContentView(scrollView) ;
	}
	
	public int getId()	{
		mID = mID + 1 ;
		return mID ;
	}

}
