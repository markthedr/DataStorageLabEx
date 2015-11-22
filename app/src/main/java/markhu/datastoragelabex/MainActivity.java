package markhu.datastoragelabex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

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

    public void onClick(View view){
        Long sharedVal = mSharedPrefs.getLong(KEY_TIME_STAMP, -1);
        mDatabaseHelper.incrementButtonCount((String) ((Button)view).getText());
        switch (view.getId()){
            case R.id.btnShowLast:
                if(sharedVal < 0 ){
                    return;
                }
                Date lastdate = new Date(sharedVal);
                Toast.makeText(view.getContext(), lastdate.toString(), Toast.LENGTH_LONG).show();
                break;
            case R.id.btnSaveLast:
                SharedPreferences.Editor editor = mSharedPrefs.edit();
                Date currentTime = new Date();
                editor.putLong(KEY_TIME_STAMP, currentTime.getTime());
                editor.commit();
                break;
            case R.id.btnDumpLast:
                if(sharedVal < 0){
                    return;
                }
                try{
                    FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnReadLast:
                try{
                    FileInputStream fis = openFileInput(FILE_NAME);
                    StringBuilder stringBuffer = new StringBuilder();
                    int readByte;
                    while((readByte = fis.read()) != -1){
                        stringBuffer.append(readByte);
                    }
                    fis.close();
                    Toast.makeText(view.getContext(),stringBuffer.toString(),Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_settings){
            Intent intent = new Intent(MainActivity.this, LogActivity.class);
            MainActivity.this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
