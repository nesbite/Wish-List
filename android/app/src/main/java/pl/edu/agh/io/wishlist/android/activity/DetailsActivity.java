package pl.edu.agh.io.wishlist.android.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.fragment.ErrorFragment;
import pl.edu.agh.io.wishlist.android.fragment.GiftDetailFragment;
import pl.edu.agh.io.wishlist.android.fragment.ProfileFragment;

public class DetailsActivity extends AppCompatActivity {

    public static final int GIFT_DETAIL = 1;
    public static final int PROFILE = 2;

    public static final String BUNDLE = "bundle";
    public static final String FRAGMENT_TYPE = "fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        // initialize fragment
        Fragment fragment;

        int type = getIntent().getIntExtra(FRAGMENT_TYPE, 0);
        switch (type) {
            case GIFT_DETAIL:
                fragment = new GiftDetailFragment();
                break;
            case PROFILE:
                fragment = new ProfileFragment();
                break;
            default:
                fragment = new ErrorFragment();
        }

        Bundle args = getIntent().getBundleExtra(BUNDLE);
        fragment.setArguments(args);

        getFragmentManager().beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown.
            navigateUpTo(new Intent(this, NavigationActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
