package bestaveiro.autumncourse2017.Drinking_G2K;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import bestaveiro.autumncourse2017.R;

public class Get2KnowGameList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get2_know_game_list);

        ListView lv = (ListView) findViewById(R.id.list_view_get2know);
        lv.setAdapter(new ListViewAdapter());
        lv.setDivider(null);


        Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/neo.ttf");
        TextView tv=(TextView) findViewById(R.id.Get2KnowGameTitleFont);
        tv.setTypeface(tf);
    }

    class ListViewAdapter extends BaseAdapter
    {

        Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/neo.ttf");
        String[] all_games;


        public ListViewAdapter() {

            all_games=getResources().getStringArray(R.array.all_get2Know_games);////////

        }

        @Override
        public int getCount() {
            return 16;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            View v = view;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getBaseContext());
                v = vi.inflate(R.layout.list_view_item, null);
            }
            ((Button)v.findViewById(R.id.buttonBeerRoulette)).setText(all_games[position]);
            ((Button)v.findViewById(R.id.buttonBeerRoulette)).setTypeface(tf);
            ((Button)v.findViewById(R.id.buttonBeerRoulette)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent tmp=new Intent(Get2KnowGameList.this, DrinkingGameDescription.class);
                    startActivity(tmp);
                }
            });
            return v;
        }
    }
}
