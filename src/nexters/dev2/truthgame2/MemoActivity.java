package nexters.dev2.truthgame2;

import java.util.Random;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MemoActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_memo);
		        

		Random rd = new Random();
		int rid = rd.nextInt(QuestionDBhelper.remainedQuestion);
		int id = QuestionDBhelper.getId(rid);

		QuestionDBhelper dbhelper = new QuestionDBhelper(this);
		SQLiteDatabase db = dbhelper.getReadableDatabase();

		Cursor cursor = db.query(QuestionDBhelper.TABLE_QUESTIONS, null,
				QuestionDBhelper.QKEY_ID + "=?", new String[] { String.valueOf(id) },
				null, null, null);
		
		cursor.moveToFirst();

		String sentence = cursor.getString(cursor
				.getColumnIndex(QuestionDBhelper.QKEY_SENTENCE));
		int glass = Integer.valueOf(cursor.getString(cursor
				.getColumnIndex(QuestionDBhelper.QKEY_GLASS)));
		
		cursor.close();
		db.close();

		TextView qtv = (TextView) findViewById(R.id.questionview);
		qtv.setText(sentence + "");

		// �� ������ ��� �������� �׸��� ���� �ڵ�
		if (glass == 1) {
			ImageView iv1 = (ImageView) findViewById(R.id.Glass1);
			iv1.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.glass));
		} else if (glass == 2) {
			ImageView iv1 = (ImageView) findViewById(R.id.Glass1);
			iv1.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.glass));
			ImageView iv2 = (ImageView) findViewById(R.id.Glass2);
			iv2.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.glass));
		} else {
			ImageView iv1 = (ImageView) findViewById(R.id.Glass1);
			iv1.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.glass));
			ImageView iv2 = (ImageView) findViewById(R.id.Glass2);
			iv2.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.glass));
			ImageView iv3 = (ImageView) findViewById(R.id.Glass3);
			iv3.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.glass));
		}
	}

	public void mOnClick(View v) {
		if (v.getId() == R.id.Yes) { // ����� ���� ���
			this.setResult(RESULT_OK);
			this.finish();
		}
		if (v.getId() == R.id.No) { // ����� ������ ���
			this.setResult(RESULT_CANCELED);
			this.finish();
		}
	}

	// �ڷΰ��� ��ư���� ������ �� ��Բ� �ϱ� ���� �ڵ�
	public boolean onKeyDown(int KeyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			if (KeyCode == KeyEvent.KEYCODE_BACK) {
				return false;
			}
		}
		return super.onKeyDown(KeyCode, event);
	}

}
