package nexters.dev2.truthgame2;

import net.daum.adam.publisher.AdView;
import net.daum.adam.publisher.AdView.AnimationType;
import net.daum.adam.publisher.AdView.OnAdLoadedListener;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

public class DecisionActivity extends Activity {
	private AdView adView;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_decision);		
		
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
        
	}
	
	public void mOnClick(View v){
		if(v.getId() == R.id.btnexit){		//�������Ḧ ������� activitylist�� �ִ� activity�� ��� �����Ѵ�.
			this.setResult(RESULT_FIRST_USER);
			this.finish();
		}
		if (v.getId() == R.id.btncontinue) {//���Ӱ���� ���� ���
			this.setResult(RESULT_OK);
			this.finish();
		}
	}
	
	
	//�ڷΰ��� ��ư�� ��ȿȭ�ϱ� ���� �ڵ�
	public boolean onKeyDown(int KeyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			if (KeyCode == KeyEvent.KEYCODE_BACK) {
				return false;
			}
		}
		return super.onKeyDown(KeyCode, event);
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
