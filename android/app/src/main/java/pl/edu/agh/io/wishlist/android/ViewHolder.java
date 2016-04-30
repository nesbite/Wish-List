package pl.edu.agh.io.wishlist.android;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

@SuppressWarnings("WeakerAccess")
public class ViewHolder {

    @Bind(R.id.icon)
    ImageView icon;

    @Bind(R.id.title)
    TextView title;

    public ViewHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public void setTitle(CharSequence title) {
        this.title.setText(title);
    }

    public void setIcon(Drawable drawable) {
        this.icon.setImageDrawable(drawable);
    }

    public void setIcon(int resId) {
        this.icon.setImageResource(resId);
    }

}

