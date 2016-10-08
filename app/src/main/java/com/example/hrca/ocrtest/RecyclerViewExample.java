package com.example.hrca.ocrtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RecyclerViewExample extends AppCompatActivity {

    /**
     * RecyclerView: The new recycler view replaces the list view. Its more modular and therefore we
     * must implement some of the functionality ourselves and attach it to our recyclerview.
     * <p/>
     * 1) Position items on the screen: This is done with LayoutManagers
     * 2) Animate & Decorate views: This is done with ItemAnimators & ItemDecorators
     * 3) Handle any touch events apart from scrolling: This is now done in our adapter's ViewHolder
     */

    private ArrayList<Student> mDataSet;

    private Toolbar toolbar;

    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private ArrayList<String> namirnice_list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swiprecyclerview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        Intent i = getIntent();
        namirnice_list = i.getStringArrayListExtra("namirnice");


        // Layout Managers:
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Item Decorator:
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        // mRecyclerView.setItemAnimator(new FadeInLeftAnimator());


        mDataSet = new ArrayList<Student>();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Android Students");

        }

        loadData();

        if (mDataSet.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            tvEmptyView.setVisibility(View.VISIBLE);

        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            tvEmptyView.setVisibility(View.GONE);
        }


        // Creating Adapter object
        SwipeRecyclerViewAdapter mAdapter = new SwipeRecyclerViewAdapter(this, mDataSet);


        // Setting Mode to Single to reveal bottom View for one item in List
        // Setting Mode to Mutliple to reveal bottom Views for multile items in List
        ((SwipeRecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Single);

        mRecyclerView.setAdapter(mAdapter);

        /* Scroll Listeners */
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("RecyclerView", "onScrollStateChanged");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    // load initial data
    public void loadData() {

    String poljeNamirnica[]=null;

        for (String str:namirnice_list
             ) {
            poljeNamirnica= str.split("\\n");
        }

        ArrayList<String> listaStringova=new ArrayList<>();


        for (int i=0;i<namirnice_list.size();i++) {

            Pattern p = Pattern.compile("(?!(([A-Z)|(^A-Z]\\s)|(\\d)))\\w+");
            Matcher matcher=p.matcher(namirnice_list.get(i));
       //     String namirnica=namirnice_list.get(i);

            String namirnica=namirnice_list.get(i);


            if(matcher.find()){
                namirnica=matcher.group(0);

                if(namirnica.contains("Poupa")||namirnica.contains("TALHO")||namirnica.contains("CONGELADOS")||namirnica.contains("ERCEARIA")||namirnica.contains("X")){
                    System.out.println("Izbacena namirnica:"+ namirnica);
                    namirnice_list.remove(i);
                    continue;
                }

                }
//            mDataSet.add(new Student(namirnica, "topkek"));
            mDataSet.add(new Student(namirnica, "topkek"));

        }
        for (String namirni : namirnice_list) {
            System.out.println("Evo je: "+ namirni);
        }

    }

}
