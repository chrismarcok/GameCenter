package fall2018.csc207.twentyfortyeight;

/*
Taken from:
https://github.com/DaveNOTDavid/
sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/CustomAdapter.java

This Class is an overwrite of the Base Adapter class
It is designed to aid setting the button sizes and positions in the GridView
 */


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

class TileAdapter extends BaseAdapter {

    /**
     * A list of the tile buttons for the slidingTiles game.
     */
    private List<TextView> textViews;
    private int mColumnWidth, mColumnHeight;

    /**
     * A CustomAdapter
     *
     * @param textViews     A list of the tile textviews for the 2048 game.
     * @param columnWidth  The width of a column.
     * @param columnHeight The height of a column.
     */
    TileAdapter(List<TextView> textViews, int columnWidth, int columnHeight) {
        this.textViews = textViews;
        mColumnWidth = columnWidth;
        mColumnHeight = columnHeight;
    }

    @Override
    public int getCount() {
        return textViews.size();
    }

    @Override
    public Object getItem(int position) {
        return textViews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;

        if (convertView == null) {
            textView = textViews.get(position);
        } else {
            textView = (TextView) convertView;
        }

        android.widget.AbsListView.LayoutParams params =
                new android.widget.AbsListView.LayoutParams(mColumnWidth, mColumnHeight);
        textView.setLayoutParams(params);

        return textView;
    }
}
