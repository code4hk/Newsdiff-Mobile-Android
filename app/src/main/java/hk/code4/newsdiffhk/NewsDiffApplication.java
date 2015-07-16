package hk.code4.newsdiffhk;

import android.app.Application;

/**
 * Created by allen517 on 28/6/15.
 */
public class NewsDiffApplication extends Application {
    public void onCreate() {
        super.onCreate();
//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(
//                                Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(
//                                Stetho.defaultInspectorModulesProvider(this))
//                        .build());
    }
}