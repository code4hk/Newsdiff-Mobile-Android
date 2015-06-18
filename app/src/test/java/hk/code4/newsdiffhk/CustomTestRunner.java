package hk.code4.newsdiffhk;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.FileFsFile;
import org.robolectric.res.FsFile;
import org.robolectric.util.Logger;
import org.robolectric.util.ReflectionHelpers;

/**
 * Created by allen517 on 17/6/15.
 */
public class CustomTestRunner  extends RobolectricTestRunner {
    private static final String BUILD_OUTPUT = "build/intermediates";

    public CustomTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    protected AndroidManifest getAppManifest(Config config) {
        if(config.constants() == Void.class) {
            Logger.error("Field \'constants\' not specified in @Config annotation", new Object[0]);
            Logger.error("This is required when using RobolectricGradleTestRunner!", new Object[0]);
            throw new RuntimeException("No \'constants\' field in @Config annotation!");
        } else {
            String type = this.getType(config);
            String flavor = this.getFlavor(config);
            String packageName = this.getPackageName(config);
            FileFsFile res;
            if(FileFsFile.from(new String[]{"/app/build/intermediates", "res"}).exists()) {
                res = FileFsFile.from(new String[]{"/app/build/intermediates", "res", flavor, type});
            } else {
                res = FileFsFile.from(new String[]{"/app/build/intermediates", "bundles", flavor, type, "res"});
            }

            FileFsFile assets;
            if(FileFsFile.from(new String[]{"/app/build/intermediates", "assets"}).exists()) {
                assets = FileFsFile.from(new String[]{"/app/build/intermediates", "assets", flavor, type});
            } else {
                assets = FileFsFile.from(new String[]{"/app/build/intermediates", "bundles", flavor, type, "assets"});
            }

            FileFsFile manifest;
//            if(FileFsFile.from(new String[]{"/app//build/intermediates", "manifests"}).exists()) {
//                manifest = FileFsFile.from(new String[]{"/app/build/intermediates", "manifests", "full", flavor, type, "AndroidManifest.xml"});
//            } else {
//                manifest = FileFsFile.from(new String[]{"/app/build/intermediates", "bundles", flavor, type, "AndroidManifest.xml"});
//            }
            manifest = FileFsFile.from(new String[]{"/app/build/intermediates", "manifests", "full", flavor, type, "AndroidManifest.xml"});

            Logger.debug("Robolectric assets directory: " + assets.getPath(), new Object[0]);
            Logger.debug("   Robolectric res directory: " + res.getPath(), new Object[0]);
            Logger.debug("   Robolectric manifest path: " + manifest.getPath(), new Object[0]);
            Logger.debug("    Robolectric package name: " + packageName, new Object[0]);
            return new AndroidManifest(manifest, res, assets, packageName);
        }
    }

    private String getType(Config config) {
        try {
            return (String) ReflectionHelpers.getStaticField(config.constants(), "BUILD_TYPE");
        } catch (Throwable var3) {
            return null;
        }
    }

    private String getFlavor(Config config) {
        try {
            return (String)ReflectionHelpers.getStaticField(config.constants(), "FLAVOR");
        } catch (Throwable var3) {
            return null;
        }
    }

    private String getPackageName(Config config) {
        try {
            String e = config.packageName();
            return e != null && !e.isEmpty()?e:(String)ReflectionHelpers.getStaticField(config.constants(), "APPLICATION_ID");
        } catch (Throwable var3) {
            return null;
        }
    }
}