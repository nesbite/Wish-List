package pl.edu.agh.io.wishlist.android.ui.fab;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import pl.edu.agh.io.wishlist.android.R;

abstract public class AbstractFabListener implements View.OnClickListener {

    private Context context;
    private LayoutInflater inflater;

    private EditText snackSearch;

    public AbstractFabListener(Context context, LayoutInflater layoutInflater) {
        this.inflater = layoutInflater;
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        // Create the Snackbar
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE);
        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        // Hide the text
        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);
        textView.setLayoutParams(new LinearLayout.LayoutParams(0, 0));

        // Inflate our custom view
        View snackView = inflater.inflate(R.layout.snackbar_search, null);

        snackSearch = (EditText) snackView.findViewById(R.id.snackbar_search);
        snackSearch.setImeOptions(EditorInfo.IME_ACTION_DONE);

        // Add the view to the Snackbar's layout
        layout.addView(snackView, 0);

        // Set action
        snackbar.setAction("SEARCH", new FabSearchListener());
        snackbar.setActionTextColor(Color.WHITE);

        // Show the Snackbar
        snackbar.show();
    }

    /* Template method */
    public abstract void onAction(String text);

    private class FabSearchListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            onAction(snackSearch.getText().toString());
        }
    }
}