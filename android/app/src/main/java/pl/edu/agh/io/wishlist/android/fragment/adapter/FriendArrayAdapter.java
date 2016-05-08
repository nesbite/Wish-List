package pl.edu.agh.io.wishlist.android.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.domain.User;
import pl.edu.agh.io.wishlist.android.ui.ViewHolder;

import javax.inject.Inject;

public class FriendArrayAdapter extends ArrayAdapter<User> {

    private LayoutInflater inflater;

    @Inject
    public FriendArrayAdapter(Context context, LayoutInflater inflater) {
        super(context, R.layout.gift_list_item);

        this.inflater = inflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        // reuse views
        if (row == null) {
            row = inflater.inflate(R.layout.gift_list_item, parent, false);

            // view holder
            ViewHolder viewHolder = new ViewHolder(row);
            row.setTag(viewHolder);
        }

        // fill data
        User user = getItem(position);
        if (user != null) {
            ViewHolder holder = (ViewHolder) row.getTag();
            holder.setTitle(user.getUsername());
            holder.setIcon(R.drawable.icon_user_default);
        }

        return row;
    }
}
