package hk.code4.newsdiffhk.Model;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Type;
import java.util.List;

import hk.code4.newsdiffhk.BuildConfig;

/**
 * Created by allen517 on 17/6/15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.KITKAT, manifest = "src/main/AndroidManifest.xml")
public class TestPublisher {

    String json = "[{\"code\": \"tvb\", \"name\": \"\\u7121\\u7dab\\u65b0\\u805e\"}, {\"code\": \"singtao\", \"name\": \"\\u661f\\u5cf6\\u65e5\\u805e\"}, {\"code\": \"apple\", \"name\": \"\\u860b\\u679c\\u65e5\\u5831\"}]";

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGson() throws Exception {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Publisher>>(){}.getType();
        List<Publisher> lcs = gson.fromJson(json, collectionType);

        assertTrue("tvb".equals(lcs.get(0).getCode()));
    }
}
