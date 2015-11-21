package markhu.datastoragelabex;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;


public class LogDBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "logging";
    private static final String BUTTON_LOGS_TABLE_NAME = "button_logs";
    private static final String KEY_BUTTON_TEXT = "button_text";
    private static final String KEY_NUM_CLICKS = "num_clicks";

    private static final String BUTTON_LOGS_TABLE_CREATE = "CREATE TABLE " + BUTTON_LOGS_TABLE_NAME
            + " (" + KEY_BUTTON_TEXT + " TEXT UNIQUE, " + KEY_NUM_CLICKS + " INTEGER);";

    private static final String INCREMENTAL_VAL_QUERY = "INSERT OR REPLACE INTO " +
            BUTTON_LOGS_TABLE_NAME + "(" + KEY_BUTTON_TEXT + "," + KEY_NUM_CLICKS +
            ") VALUES ( ? , COALESCE((SELECT " + KEY_NUM_CLICKS + " + 1 FROM " + BUTTON_LOGS_TABLE_NAME
            + "WHERE " + KEY_BUTTON_TEXT + " =?), 1));";

    private static final String GET_ALL_QUERY = "select * from " + BUTTON_LOGS_TABLE_NAME;
    private static final String GET_ALL_CONNECTOR = " was clicked ";


    public LogDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BUTTON_LOGS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void incrementButtonCount(String buttonText){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(INCREMENTAL_VAL_QUERY, new String[]{buttonText, buttonText});
        db.close();
    }

    public List<String> getDbRows(){
        SQLiteDatabase reader = getReadableDatabase();
        Cursor result = reader.rawQuery(GET_ALL_QUERY, null);
        List<String> resultList = new ArrayList<String>();
        while(result.moveToNext()){
            resultList.add(result.getString(result.getColumnIndex(KEY_BUTTON_TEXT)) + GET_ALL_CONNECTOR + result.getInt(result.getColumnIndex(KEY_NUM_CLICKS)));
        }
        return resultList;
    }

}
