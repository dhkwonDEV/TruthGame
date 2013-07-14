/**
 * 
 */
package nexters.dev2.truthgame2;

import net.daum.adam.publisher.AdView;
import net.daum.adam.publisher.AdView.AnimationType;
import net.daum.adam.publisher.AdView.OnAdLoadedListener;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

/**
 * @author dhkwon
 * ���� ���? �ش��ϴ� Ŭ�����μ� ��� ������ ��� �̹��� ��ü�� �ǹǷ� ���ٸ� �ڵ�� ���.
 * �ٸ� �ڷΰ��� ��ư�� ���� ��츸 ó�����ָ� �ȴ�.
 */
public class DescriptionActivity extends Activity {
	private AdView adView;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_description);
		
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
		if(v.getId() == R.id.btndescriptionback){
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
