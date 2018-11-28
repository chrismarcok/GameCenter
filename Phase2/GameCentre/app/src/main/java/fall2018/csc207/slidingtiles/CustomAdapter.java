package fall2018.csc207.slidingtiles;

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
import android.widget.Button;
import java.util.List;

class CustomAdapter extends BaseAdapter {

    /**
     * A list of the tile buttons for the slidingTiles game.
     */
    private List<Button> mButtons;
    private int mColumnWidth, mColumnHeight;

    /**
     * A CustomAdapter
     *
     * @param buttons      A list of the tile buttons for the slidingTiles game.
     * @param columnWidth  The width of a column.
     * @param columnHeight The height of a column.
     */
    CustomAdapter(List<Button> buttons, int columnWidth, int columnHeight) {
        mButtons = buttons;
        mColumnWidth = columnWidth;
        mColumnHeight = columnHeight;
    }

    @Override
    public int getCount() {
        return mButtons.size();
    }

    @Override
    public Object getItem(int position) {
        return mButtons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;

        if (convertView == null) {
            button = mButtons.get(position);
        } else {
            button = (Button) convertView;
        }

        android.widget.AbsListView.LayoutParams params =
                new android.widget.AbsListView.LayoutParams(mColumnWidth, mColumnHeight);
        button.setLayoutParams(params);

        return button;
    }
}
