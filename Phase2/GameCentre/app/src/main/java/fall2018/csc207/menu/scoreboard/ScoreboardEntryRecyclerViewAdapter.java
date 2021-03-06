package fall2018.csc207.menu.scoreboard;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import fall2018.csc207.slidingtiles.R;

/**
 * {@link RecyclerView.Adapter} that can display a ScoreboardEntry.
 * Class partially generated by Android Studio.
 */
public class ScoreboardEntryRecyclerViewAdapter extends
        RecyclerView.Adapter<ScoreboardEntryRecyclerViewAdapter.ViewHolder> {

    private final List<ScoreboardEntry> values;

    /**
     * A ScoreboardEntryRecyclerViewAdapter.
     *
     * @param items The items to display in this RecyclerView.
     */
    ScoreboardEntryRecyclerViewAdapter(List<ScoreboardEntry> items) {
        values = items;
    }

    /**
     * Called by Android when we want to create a new ViewHolder.
     *
     * @param parent   The parent view of the ViewHolder.
     * @param viewType The type of the view.
     * @return The created ViewHolder.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called by Android whenever a new ViewHolder is being created/recycled and requires new data.
     *
     * @param holder   The ViewHolder that requires data.
     * @param position The position of the element to display.
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // We add 1 to position because we're not displaying the first entry in the list.
        // e.g. when Android wants the element in position 0, we feed it the element in position 1.
        position++;

        // Sets the items displayed for the views (which are gotten through ViewHolder).
        holder.idView.setText(String.valueOf(position + 1));
        holder.scoreView.setText(String.valueOf(values.get(position).getScore()));
        holder.nameView.setText(values.get(position).getName());
    }

    /**
     * Gets the number of items we have to display.
     *
     * @return The number of items we have to display.
     */
    @Override
    public int getItemCount() {
        return values.size() - 1;
    }

    /**
     * Describes an item's view inside the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView idView;
        final TextView scoreView;
        final TextView nameView;

        ViewHolder(View view) {
            super(view);

            idView = view.findViewById(R.id.item_number);
            scoreView = view.findViewById(R.id.score);
            nameView = view.findViewById(R.id.name);
        }

        /**
         * A string representation of this object.
         * @return A string representation of this object.
         */
        @Override
        public String toString() {
            return super.toString() + " '" + scoreView.getText() + "'";
        }
    }
}
