package markhu.datastoragelabex;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "saved_last.txt";
    private static final String SHARED_PREF_NAME = "shared_prefs";
    private static final String KEY_TIME_STAMP = "KEY_TIME_STAMP";
    private SharedPreferences mSharedPrefs;
    private LogDBHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPrefs = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        mDatabaseHelper = new LogDBHelper(this);
    }

    public void buttonClick(View view){
        Long sharedVal = mSharedPrefs.getLong(KEY_TIME_STAMP, -1);
        mDatabaseHelper.incrementButtonCount((String) ((Button)view).getText());

    }
}
