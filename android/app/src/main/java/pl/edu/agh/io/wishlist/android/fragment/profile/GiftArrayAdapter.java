package pl.edu.agh.io.wishlist.android.fragment.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.ui.ViewHolder;
import pl.edu.agh.io.wishlist.android.domain.Gift;

import javax.inject.Inject;

public class GiftArrayAdapter extends ArrayAdapter<Gift> {

    private LayoutInflater inflater;

    @Inject
    public GiftArrayAdapter(Context context, LayoutInflater inflater) {
        super(context, R.layout.list_item);

        this.inflater = inflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        // reuse views
        if (row == null) {
            row = inflater.inflate(R.layout.list_item, parent, false);

            // view holder
            ViewHolder viewHolder = new ViewHolder(row);
            row.setTag(viewHolder);
        }

        // fill data
        Gift gift = getItem(position);
        ViewHolder holder = (ViewHolder) row.getTag();
        holder.setTitle(gift.getName());
        holder.setIcon(R.drawable.icon_gift_default);

        return row;
    }
}
