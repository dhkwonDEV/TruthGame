package nexters.dev2.truthgame2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.daum.adam.publisher.AdView;
import net.daum.adam.publisher.AdView.AnimationType;
import net.daum.adam.publisher.AdView.OnAdLoadedListener;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

//���ø����̼� �������� �Ǵ� Activity
public class MainActivity extends Activity {

	private AdView adView;
	private final int TABLE_ACTIVITY = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ���� ����
		adView = (AdView) findViewById(R.id.adview);
		adView.setRequestInterval(12);
		adView.setAnimationType(AnimationType.FLIP_HORIZONTAL);
		adView.setVisibility(View.VISIBLE);
		adView.setOnAdLoadedListener(new OnAdLoadedListener() {
			public void OnAdLoaded() {
				getWindow().setFlags(
						WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);

			}
		});

		this.initGame();
	}

	public void copyDataBase() {
		// DB
		File folder = new File(QuestionDBhelper.DATABASE_PATH);
		folder.mkdirs();
		File outfile = new File(QuestionDBhelper.DATABASE_PATH
				+ QuestionDBhelper.DATABASE_NAME);

		AssetManager am = this.getResources().getAssets();

		InputStream is;
		try {
			is = am.open(QuestionDBhelper.DATABASE_NAME,
					AssetManager.ACCESS_BUFFER);
			long filesize = is.available();
			byte[] tempdata = new byte[(int) filesize];
			is.read(tempdata);
			is.close();

			outfile.createNewFile();
			FileOutputStream fo = new FileOutputStream(outfile);
			fo.write(tempdata);

			fo.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void initGame() {
		this.copyDataBase();

		QuestionDBhelper dbhelper = new QuestionDBhelper(this);
		SQLiteDatabase db = dbhelper.getReadableDatabase();

		Cursor cursor = db.query(QuestionDBhelper.TABLE_QUESTIONS, null, null,
				null, null, null, null);

		QuestionDBhelper.remainedQuestion = cursor.getCount();
		QuestionDBhelper.totalQuestion = cursor.getCount();
		QuestionDBhelper.initMap();

		cursor.close();
		db.close();
	}

	public void mOnClick(View v) {
		if (v.getId() == R.id.btngamestart) { // ���� ���� ��ư�� ���� ���
			Intent intent = new Intent(this, TableActivity.class);
			startActivityForResult(intent, this.TABLE_ACTIVITY);
			this.finish();
		}
		if (v.getId() == R.id.btngamedescription) { // ���� ������ ������ ���
			Intent intent = new Intent(this, DescriptionActivity.class);
			startActivity(intent);
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if ((requestCode == this.TABLE_ACTIVITY)
				&& (resultCode == RESULT_CANCELED)) { // ����ڰ� �� �������
														// ���Ͽ��� ���
			this.finish(); // QuestionNum�� �������μ� ������ ���� �ٽ� ���ü� �ְ�
							// �Ѵ�.
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
