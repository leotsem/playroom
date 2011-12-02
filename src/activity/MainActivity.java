package activity;

import gr.innerlogic.playroom.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import model.Article;
import android.app.Activity;
import service.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.orm.androrm.DatabaseAdapter;
import com.orm.androrm.Model;
import com.orm.androrm.QuerySet;

public class MainActivity extends Activity {
	
	private QuerySet<Article> articles;
	private Timer timer;
	
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.hasExtra("reload")) {
			Log.d("Broadcast", "Received call from service, update UI");

			// reload data to be used in adapter notifydatachanged event
			articles = Article.objects(context, Article.class);
			
			updateUI();
		}
	}

	private void updateUI() {
		Toast.makeText(getApplicationContext(), "Total articles: " + articles.count(), Toast.LENGTH_SHORT).show();
		
	}
};

	private void initTimer() {
		if (timer != null) {
			timer.cancel(); // remove previous timers
		}
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Log.i("Service", "Timer launched Service. Next run in 5000ms.");
				startService(new Intent(getApplicationContext(), Service.class));
			}
		}, 0, 5000);
	}	
	
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
        
		// initialize service
		registerReceiver(broadcastReceiver, new IntentFilter(Service.BROADCAST_ACTION));

		initTimer();
        
    }
    
}