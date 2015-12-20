package jp.joao.preferenceseditor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.jakewharton.processphoenix.ProcessPhoenix;

import static android.content.Context.MODE_PRIVATE;

public class PreferencesEditorReceiver extends BroadcastReceiver {

	private static final String TAG = PreferencesEditorReceiver.class.getSimpleName();

	private static final String ACTION_PUT = ".sp.PUT";
	private static final String ACTION_REMOVE = ".sp.REMOVE";
	private static final String ACTION_CLEAR = ".sp.CLEAR";

	private static final String EXTRA_NAME = "name";
	private static final String EXTRA_KEY = "key";
	private static final String EXTRA_VALUE = "value";
	private static final String EXTRA_RESTART = "restart";

	@Override
	public void onReceive(Context context, Intent intent) {
		String name = intent.getStringExtra(EXTRA_NAME);
		String key = intent.getStringExtra(EXTRA_KEY);
		Object value = intent.hasExtra(EXTRA_VALUE) ? intent.getExtras().get(EXTRA_VALUE) : null;
		boolean restart = intent.getBooleanExtra(EXTRA_RESTART, false);

		SharedPreferences sp = name != null ? context.getSharedPreferences(name, MODE_PRIVATE) : PreferenceManager.getDefaultSharedPreferences(context);

		if(intent.getAction().endsWith(ACTION_PUT)) {
			if (key == null || value == null) {
				Log.w(TAG, "Key and value must not be null.");
				return;
			}

			if (value instanceof String)
				sp.edit().putString(key, (String) value).commit();
			else if (value instanceof Boolean)
				sp.edit().putBoolean(key, (boolean) value).commit();
			else if (value instanceof Float)
				sp.edit().putFloat(key, (float) value).commit();
			else if (value instanceof Integer)
				sp.edit().putInt(key, (int) value).commit();
			else if (value instanceof Long)
				sp.edit().putLong(key, (long) value).commit();

			Log.i(TAG, "put: " + key + "=" + value + " (" + (name != null ? name : "default") + ")");

			if (restart) {
				Log.i(TAG, "restarting...");
				ProcessPhoenix.triggerRebirth(context);
			}
		} else if (intent.getAction().endsWith(ACTION_REMOVE)) {
			if (key == null) {
				Log.w(TAG, "Key must not be null.");
				return;
			}

			sp.edit().remove(key).commit();

			Log.i(TAG, "removed: " + key + " (" + (name != null ? name : "default") + ")");

			if (restart) {
				Log.i(TAG, "restarting...");
				ProcessPhoenix.triggerRebirth(context);
			}
		} else if (intent.getAction().endsWith(ACTION_CLEAR)) {
			sp.edit().clear().commit();

			Log.i(TAG, "cleared: " + (name != null ? name : "default"));

			if (restart) {
				Log.i(TAG, "restarting...");
				ProcessPhoenix.triggerRebirth(context);
			}
		} else {
			Log.w(TAG, "Unknown action.");
		}
	}
}
