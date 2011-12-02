package service;
import model.Article;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class Service extends IntentService {
	
	public Service() {
		super("Service");
	}

	public static final String BROADCAST_ACTION = "gr.innerlogic.playroom.service";
	
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		sendBroadcastEvent("progress", "start");

		Log.d("Service", "Service starting");
		return super.onStartCommand(intent,flags,startId);
	}


	@Override
	public void onDestroy(){
		sendBroadcastEvent("progress", "stop");
		Log.d("Service", "Service destroyed");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d("Service", "Service started");
		
		// do extreme stuff here
		for (int i = 0; i <= 500; i++) {
			Article a = new Article();
			a.title.set("asfdas dfsdf asdfasdf asdf sdf");
			a.save(getApplicationContext());
		}
		
		sendBroadcastEvent("reload", "article");


	}
	
	/**
	 * Helper method that sends a broadcast event to asynchronously update UI
	 * @param event
	 * @param message
	 */
	private void sendBroadcastEvent(String event, String message) {
		Intent service_intent = new Intent(BROADCAST_ACTION);
		service_intent.putExtra(event, message);
		sendBroadcast(service_intent);
	}


}