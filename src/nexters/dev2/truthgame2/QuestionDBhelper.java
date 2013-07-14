/**
 * 
 */
package nexters.dev2.truthgame2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author dhkwon
 */

public class QuestionDBhelper extends SQLiteOpenHelper {

	public static final String DATABASE_PATH = "/data/data/nexters.dev2.truthgame2/databases/";
	public static final String DATABASE_NAME = "truthgame.db";
	
	public static final String TABLE_QUESTIONS = "Questions";
	
	public static final String QKEY_ID = "id";
	public static final String QKEY_SENTENCE = "sentence";
	public static final String QKEY_GLASS = "glass";
	
	public static int totalQuestion;
	public static int remainedQuestion;
	public static int[] idmap;
	
	public QuestionDBhelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	public static void initMap(){
		idmap = new int[totalQuestion];
		for(int i=0; i<totalQuestion; i++){
			idmap[i] = i+1;
		}
	}
	
	public static int getId(int rid){
		int ret = idmap[rid];
		idmap[rid] = idmap[remainedQuestion-1];
		idmap[QuestionDBhelper.remainedQuestion-1] = ret;
		
		remainedQuestion--;
		
		if(remainedQuestion == 0){
			remainedQuestion = totalQuestion;
		}
		
		return ret;
	}

}
