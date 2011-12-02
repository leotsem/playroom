package activity;

import gr.innerlogic.playroom.R;

import java.util.ArrayList;
import java.util.List;

import model.Article;
import android.app.Activity;
import android.os.Bundle;

import com.orm.androrm.DatabaseAdapter;
import com.orm.androrm.Model;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	// initialize androrm
    	DatabaseAdapter.setDatabaseName("datastore.db");
    	
    	List<Class<? extends Model>> models = new ArrayList<Class<? extends Model>>();
        models.add(Article.class);
         
        DatabaseAdapter adapter = new DatabaseAdapter(getApplicationContext());
        adapter.setModels(models);
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}