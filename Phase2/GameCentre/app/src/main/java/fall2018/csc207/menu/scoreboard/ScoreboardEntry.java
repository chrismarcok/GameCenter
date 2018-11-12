package fall2018.csc207.menu.scoreboard;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * An entry for the Scoreboard.
 */
public class ScoreboardEntry implements Parcelable, Comparable<ScoreboardEntry> {
    private String name;
    private int score;

    public ScoreboardEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Gets the name of this entry.
     *
     * @return The name of the entry.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the score of this entry.
     *
     * @return The score of this entry.
     */
    public int getScore() {
        return score;
    }


    /**
     * Info about the parcelable.
     *
     * @return An int describing the parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Called by Android whenever this needs to placed into a parcel.
     *
     * @param parcel The parcel to write into.
     * @param flags  Info about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeInt(score);
    }

    /**
     * Compares this to another ScoreboardEntry.
     *
     * @param scoreboardEntry The other entry to compare to.
     * @return The result of the comparison.
     */
    @Override
    public int compareTo(@NonNull ScoreboardEntry scoreboardEntry) {
        return Integer.compare(score, scoreboardEntry.score);
    }

    /**
     * Used by Android to create a ScoreboardEntry from a parcelable.
     */
    public static final Parcelable.Creator<ScoreboardEntry> CREATOR
            = new Parcelable.Creator<ScoreboardEntry>() {

        /**
         * Creates a new ScoreboardEntry from a parcel.
         * @param in The parcel to create a ScoreboardEntry from.
         * @return The newly created ScoreboardEntry.
         */
        public ScoreboardEntry createFromParcel(Parcel in) {
            return new ScoreboardEntry(in.readString(), in.readInt());
        }

        /**
         * Creates a new ScoreboardEntry array given a size.
         * @param size The size of the array.
         * @return A new ScoreboardEntry array with size "size".
         */
        @Override
        public ScoreboardEntry[] newArray(int size) {
            return new ScoreboardEntry[size];
        }
    };
}
