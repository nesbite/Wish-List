package pl.edu.agh.io.wishlist.android.ui.drawer;

import android.view.*;
import android.widget.BaseAdapter;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.ui.ViewHolder;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class DrawerListAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private final List<MenuItem> navDrawerItems;

    @Inject
    public DrawerListAdapter(LayoutInflater inflater, Menu drawerMenu) {
        this.inflater = inflater;
        navDrawerItems = new ArrayList<>();

        for (int i = 0; i < drawerMenu.size(); i++) {
            navDrawerItems.add(drawerMenu.getItem(i));
        }
    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(R.drawable.drawer_selector);

            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        MenuItem menuItem = navDrawerItems.get(position);

        viewHolder.setIcon(menuItem.getIcon());
        viewHolder.setTitle(menuItem.getTitle());

        return view;
    }
}
