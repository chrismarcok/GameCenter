package fall2018.csc207.game;

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
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    /**
     * A list of tiles for a game.
     */
    private List mTiles;
    private int mColumnWidth, mColumnHeight;

    /**
     * A CustomAdapter
     *
     * @param buttons      A list of the tile buttons for the slidingTiles game.
     * @param columnWidth  The width of a column.
     * @param columnHeight The height of a column.
     */
     public CustomAdapter(List buttons, int columnWidth, int columnHeight) {
        mTiles = buttons;
        mColumnWidth = columnWidth;
        mColumnHeight = columnHeight;
    }

    @Override
    public int getCount() {
        return mTiles.size();
    }

    @Override
    public Object getItem(int position) {
        return mTiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object tile;

        if (convertView == null) {
            tile = mTiles.get(position);
        } else {
            if (convertView instanceof Button)
                tile = (Button) convertView;
            else
                tile = (TextView) convertView;
        }

        android.widget.AbsListView.LayoutParams params =
                new android.widget.AbsListView.LayoutParams(mColumnWidth, mColumnHeight);
        if (tile instanceof Button){
            ((Button)tile).setLayoutParams(params);
            return (Button)tile;
        }
        else {
            ((TextView)tile).setLayoutParams(params);
            return (TextView)tile;
        }



    }
}
