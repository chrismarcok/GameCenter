package fall2018.csc207.menu.gameCard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fall2018.csc207.game.GameMainActivity;
import fall2018.csc207.menu.GameMenuActivity;
import fall2018.csc207.slidingtiles.R;

public class GameCardAdapter extends RecyclerView.Adapter<GameCardAdapter.ViewHolder> {
    /**
     * The user's username
     */
    private String username;

    /**
     * The Gamecards which will be shown in GameCentreActivity
     */
    private List<GameCardItem> gameCardItemList;

    /**
     * A GameCardAdapter.
     *
     * @param gameCardItemList A list of gameCards to be displayed.
     * @param username The user's name.
     */
    public GameCardAdapter(List<GameCardItem> gameCardItemList, String username) {
        this.gameCardItemList = gameCardItemList;
        this.username = username;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_card_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameCardItem current = gameCardItemList.get(position);
        holder.bind(current);
    }

    @Override
    public int getItemCount() {
        return gameCardItemList.size();
    }

    /**
     * Custom view holder that will bind the information to the desired view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView gameScore;
        protected TextView gameTitle;
        protected ImageView gameImage;

        /**
         * Object we are clicking to change to that game.
         */
        private ViewHolder(final View itemView) {
            super(itemView);

            gameTitle = itemView.findViewById(R.id.gameName);
            gameScore = itemView.findViewById(R.id.gameScore);
            gameImage = itemView.findViewById(R.id.gameImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), GameMenuActivity.class);
                    intent.putExtra(GameMainActivity.USERNAME, username);
                    intent.putExtra("game", "Sliding Tiles");
                    v.getContext().startActivity(intent);
                }
            });
        }

        /**
         * Set the text and image of the GameCardItem.
         *
         * @param current The GameCardItem we are setting the values of.
         */
        private void bind(GameCardItem current) {
            gameTitle.setText(current.getGameTitle());
            gameScore.setText("HIGHSCORE: " + String.valueOf(current.getScore()));
            gameImage.setImageDrawable(current.getGameImage());
        }
    }
}
