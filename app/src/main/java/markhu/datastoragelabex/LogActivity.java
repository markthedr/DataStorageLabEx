package markhu.datastoragelabex;


import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class LogActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(android.R.layout.list_content);
        LogDBHelper dbHelper = new LogDBHelper(this);
        setListAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,dbHelper.getDbRows()));
    }
}
