package hk.code4.newsdiffhk.DAO;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import hk.code4.newsdiffhk.BuildConfig;
import hk.code4.newsdiffhk.Constance;
import hk.code4.newsdiffhk.Model.News;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by allen517 on 23/6/15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class TestDataManager {
    Constance mNetworkController;
    News news;

    @Before
    public void setUp() throws Exception {
        mNetworkController = Constance.getInstance();
//        mNetworkController.getAllNews();
    }

    @Test
    public void testData() throws Exception {
        do {
        } while ( !mNetworkController.isLoading());
        news = mNetworkController.allNews;

        assertTrue(news != null);
        assertEquals(20, news.getMeta().getCount());
    }

//    public void onEvent()
}