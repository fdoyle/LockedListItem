package com.lacronicus.lockedlistviewdemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class LockedListItemActivity extends Activity {

    public static final int BIG_VIEW_POSITION = 50;

    ListView lv;
    View fancyView;
    View fancyViewText;

    NcaaAdapter adapter;

    ArrayList<String> content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);
        fancyView = findViewById(R.id.fancy_text_container);
        fancyViewText = findViewById(R.id.fancy_text);
        content = new ArrayList<String>();
        for(int i = 0; i != 100; i++) {
            content.add(String.valueOf(i));
        }
        adapter = new NcaaAdapter();

        lv.setAdapter(adapter);

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean itemIsOffTop = BIG_VIEW_POSITION < firstVisibleItem+1;
                boolean itemIsOffBottom = BIG_VIEW_POSITION >= firstVisibleItem + visibleItemCount - 1;
                if(!itemIsOffTop && !itemIsOffBottom) {
                    //VIEW IS VISIBLE
                    int indexOfFancyListItem = BIG_VIEW_POSITION - firstVisibleItem;
                    int positionY = lv.getChildAt(indexOfFancyListItem).getTop();
                    fancyView.animate().translationY(positionY).setDuration(0).start();

                } else if (itemIsOffTop) {
                    //VIEW IS ABOVE
                    int topPosition = 0;
                    fancyView.animate().translationY(topPosition).setDuration(0).start();

                } else {
                    //VIEW IS BELOW
                    int bottomPosition = lv.getBottom() - fancyViewText.getHeight();
                    fancyView.animate().translationY(bottomPosition).setDuration(0).start();
                }
            }
        });
    }


    public class NcaaAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return content.size();
        }

        @Override
        public Object getItem(int position) {
            return content.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = (TextView) getLayoutInflater().inflate(R.layout.list_item_text, parent, false);
            tv.setText((String) getItem(position));
            return tv;
        }
    }
}
