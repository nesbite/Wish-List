package pl.edu.agh.io.wishlist.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import pl.edu.agh.io.wishlist.android.domain.Gift;

public class ItemDetailFragment extends Fragment {

    private Gift gift;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the dummy content specified by the fragment
        // arguments. In a real-world scenario, use a Loader
        // to load content from a content provider.
        gift = (Gift) getArguments().getSerializable(ItemDetailActivity.GIFT_EXTRA);

        if (gift != null) {
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(gift.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (gift != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(gift.getDescription());
        }

        return rootView;
    }
}
