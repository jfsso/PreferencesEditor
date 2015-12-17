package jp.joao.android.sharedprefseditor.tools;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.jakewharton.processphoenix.ProcessPhoenix;

import jp.joao.android.sharedprefseditor.BuildConfig;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefsEditorReceiver extends BroadcastReceiver {

	private static final String TAG = SharedPrefsEditorReceiver.class.getSimpleName();

	private static final String ACTION_PUT = BuildConfig.APPLICATION_ID + ".sp.PUT";
	private static final String ACTION_REMOVE = BuildConfig.APPLICATION_ID + ".sp.REMOVE";
	private static final String ACTION_CLEAR = BuildConfig.APPLICATION_ID + ".sp.CLEAR";

	private static final String EXTRA_NAME = "name";
	private static final String EXTRA_KEY = "key";
	private static final String EXTRA_VALUE = "value";
	private static final String EXTRA_RESTART = "restart";

	@SuppressLint("CommitPrefEdits")
	@Override
	public void onReceive(Context context, Intent intent) {
		String name = intent.getStringExtra(EXTRA_NAME);
		String key = intent.getStringExtra(EXTRA_KEY);
		Object value = intent.hasExtra(EXTRA_VALUE) ? intent.getExtras().get(EXTRA_VALUE) : null;
		boolean restart = intent.getBooleanExtra(EXTRA_RESTART, false);

		SharedPreferences sp = name != null ? context.getSharedPreferences(name, MODE_PRIVATE) : PreferenceManager.getDefaultSharedPreferences(context);

		switch (intent.getAction()) {
			case ACTION_PUT:
				if (key == null || value == null) {
					Log.w(TAG, "Key and value must not be null.");
					return;
				}

				if (value instanceof String)
					sp.edit().putString(key, (String)value).commit();
				else if (value instanceof Boolean)
					sp.edit().putBoolean(key, (boolean)value).commit();
				else if (value instanceof Float)
					sp.edit().putFloat(key, (float)value).commit();
				else if (value instanceof Integer)
					sp.edit().putInt(key, (int)value).commit();
				else if (value instanceof Long)
					sp.edit().putLong(key, (long)value).commit();

				Log.i(TAG, "put: " + key + "=" + value + " (" + (name != null ? name : "default") + ")");

				if (restart) {
					Log.i(TAG, "restarting...");
					ProcessPhoenix.triggerRebirth(context);
				}

				break;
			case ACTION_REMOVE:
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

				break;
			case ACTION_CLEAR:
				sp.edit().clear().commit();

				Log.i(TAG, "cleared: " + (name != null ? name : "default"));

				if (restart) {
					Log.i(TAG, "restarting...");
					ProcessPhoenix.triggerRebirth(context);
				}

				break;
		}
	}
}
