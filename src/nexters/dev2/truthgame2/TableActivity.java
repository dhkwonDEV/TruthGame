package nexters.dev2.truthgame2;

import net.daum.adam.publisher.AdView;
import net.daum.adam.publisher.AdView.AnimationType;
import net.daum.adam.publisher.AdView.OnAdLoadedListener;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author dhkwon
 * ���� ������ ����Ǵ� ���� ���̺��� �����ִ� ��Ƽ��Ƽ�̴�.
 */

public class TableActivity extends Activity {

	private final int MEMO_ACTIVITY = 0;		//memo_activity�� ����Ű�� ���� ����� ���
	private final int DECISION_ACTIVITY = 1;	//decision_activity�� ����Ű�� ���� �ܼ��� ���
	private boolean mFlag;						//�ڷΰ��� �ι� ���� ���Ḧ ���� flag
	private Handler mHandler;					//�ڷΰ��� �ι� ���� ���Ḧ ���� handler
	private AdView adView;
	private int usedMemo = 0;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table);
		
		//���� ����
		adView = (AdView)findViewById(R.id.adview);
		adView.setRequestInterval(12);
		adView.setAnimationType(AnimationType.FLIP_HORIZONTAL);
		adView.setVisibility(View.VISIBLE);
		adView.setOnAdLoadedListener(new OnAdLoadedListener(){
			public void OnAdLoaded() {
				getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
			}
		});
		
		//�ڷΰ��� �ι� ���� ���Ḧ ���� Handler
		mHandler = new Handler() {
		    @Override
		    public void handleMessage(Message msg) {
		        if(msg.what == 0) {
		            mFlag = false;
		        }
		    }
		};
	}

	//resume�� �ɶ����� ���� table�� ����� memo�� ����(memocount)�� �����Ͽ� ��� ����� �Ǿ�ٸ�
	//decision activity�� ȣ���Ѵ�.
	public void onResume() {
		super.onResume();
		if (this.usedMemo == 5) {
			Intent intent = new Intent(this, DecisionActivity.class);
			startActivityForResult(intent, this.DECISION_ACTIVITY);
		}
	}

	//����ڰ� memo�� Ŭ���Ͽ��� ����� ó���� ���� method
	public void mOnClick(View v) {
		if ((v.getId() == R.id.memo1) || (v.getId() == R.id.memo2)
				|| (v.getId() == R.id.memo3) || (v.getId() == R.id.memo4)
				|| (v.getId() == R.id.memo5)) {

			Button btn = (Button) findViewById(v.getId());

			btn.setBackgroundDrawable(null);	//memo�̹����� ��ְ� ������ �̹����� �����.
			btn.setEnabled(false);				//memo�� Ŭ������ ���ϰ� �����.

			this.usedMemo++;
			
			Intent intent = new Intent(this, MemoActivity.class);
			startActivityForResult(intent, this.MEMO_ACTIVITY);

		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!mFlag) {
				Toast.makeText(this, "'뒤로'버튼을 한번 더 누르시면 종료됩니다.",
						Toast.LENGTH_SHORT).show();
				mFlag = true;
	            mHandler.sendEmptyMessageDelayed(0, 2000);
				return false;
			} else {
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onDestroy() {
	    super.onDestroy();
	    if (adView != null) {
	    	adView.destroy();
	    	adView = null;
	    }
	}
	
	//memo activity�� decision activity�κ��� �ٽ� ���ƿð�츦 ó���ϱ����� method
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if ((requestCode == this.MEMO_ACTIVITY)
				&& (resultCode == RESULT_CANCELED)) {	//����ڰ� �� ������� ���Ͽ��� ���
			QuestionDBhelper.remainedQuestion++;						
		}
		if ((requestCode == this.DECISION_ACTIVITY)
				&& (resultCode == RESULT_OK)) {			//decision activity���� ����ϱ⸦ ������ ���

			//���� table�� ���¸� �ʱ�ȭ�Ѵ�.
			this.usedMemo = 0;					

			Button btn1 = (Button) findViewById(R.id.memo1);
			Button btn2 = (Button) findViewById(R.id.memo2);
			Button btn3 = (Button) findViewById(R.id.memo3);
			Button btn4 = (Button) findViewById(R.id.memo4);
			Button btn5 = (Button) findViewById(R.id.memo5);

			btn1.setEnabled(true);
			btn1.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.memo14));
			btn2.setEnabled(true);
			btn2.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.memo23));
			btn3.setEnabled(true);
			btn3.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.memo23));
			btn4.setEnabled(true);
			btn4.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.memo14));
			btn5.setEnabled(true);
			btn5.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.memo5));
		}
		if ((requestCode == this.DECISION_ACTIVITY)
				&& (resultCode == RESULT_FIRST_USER)) {			//decision activity���� ����ϱ⸦ ������ ���

			this.setResult(RESULT_FIRST_USER);
			this.finish();
		}
	}
	
	
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
		menu.add("Send new Question to developer");
        return true;
    }
	


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Uri uri = Uri.parse("mailto:dev2nexters@gmail.com");
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);
		startActivity(it);
		return super.onOptionsItemSelected(item);
	}

}
