package hk.code4.newsdiffhk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class NewsDetailActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM_ID = "oid";
    public static final String EXTRA_ITEM_TITLE = "title";
    public static final String EXTRA_ITEM_REVISION = "total_revision";
    public static final String EXTRA_ITEM_IS_SECRET_MODE = "secret_mode";

    public static void start(Context context, String oid, String title, int total_revision, boolean secret_mode) {
        Intent launchIntent = new Intent(context, NewsDetailActivity.class);
        launchIntent.putExtra(EXTRA_ITEM_ID, oid);
        launchIntent.putExtra(EXTRA_ITEM_TITLE, title);
        launchIntent.putExtra(EXTRA_ITEM_REVISION, total_revision);
        launchIntent.putExtra(EXTRA_ITEM_IS_SECRET_MODE, secret_mode);

        context.startActivity(launchIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(bundle.getString(EXTRA_ITEM_TITLE));

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, NewsDetailFragment.newInstance(bundle.getString(EXTRA_ITEM_ID), bundle.getInt(EXTRA_ITEM_REVISION), bundle.getBoolean(EXTRA_ITEM_IS_SECRET_MODE)))
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
