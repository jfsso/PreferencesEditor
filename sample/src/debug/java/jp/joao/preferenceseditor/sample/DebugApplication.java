package jp.joao.preferenceseditor.sample;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class DebugApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Stetho.initializeWithDefaults(this);
	}
}
