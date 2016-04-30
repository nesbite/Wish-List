package pl.edu.agh.io.wishlist.android.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.ViewHolder;
import pl.edu.agh.io.wishlist.android.dagger.DaggerApplication;
import pl.edu.agh.io.wishlist.android.domain.User;

import javax.inject.Inject;

public class UserArrayAdapter extends ArrayAdapter<User> {

    @Inject
    LayoutInflater inflater;

    public UserArrayAdapter(Context context) {
        super(context, R.layout.list_item);

        // Dagger injection
        DaggerApplication.inject(this);
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
        User user = getItem(position);
        ViewHolder holder = (ViewHolder) row.getTag();
        holder.setTitle(user.getUsername());
        holder.setIcon(R.drawable.icon_user_default);

        return row;
    }
}
