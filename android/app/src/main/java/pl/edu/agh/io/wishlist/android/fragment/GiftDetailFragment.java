package pl.edu.agh.io.wishlist.android.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.activity.GiftDetailsActivity;
import pl.edu.agh.io.wishlist.android.domain.Gift;

public class GiftDetailFragment extends Fragment {

    @Bind(R.id.giftImage)
    ImageView giftImage;

    @Bind(R.id.description)
    TextView description;

    private Gift gift;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the dummy content specified by the fragment
        // arguments. In a real-world scenario, use a Loader
        // to load content from a content provider.
        gift = (Gift) getArguments().getSerializable(GiftDetailsActivity.GIFT_EXTRA);

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
        View rootView = inflater.inflate(R.layout.gift_detail, container, false);

        // ButterKnife injection
        ButterKnife.bind(this, rootView);

        // Show the dummy content as text in a TextView.
        giftImage.setImageResource(R.drawable.icon_gift_default);
        if (gift != null) {
            description.setText(gift.getDescription());
        }

        return rootView;
    }
}
