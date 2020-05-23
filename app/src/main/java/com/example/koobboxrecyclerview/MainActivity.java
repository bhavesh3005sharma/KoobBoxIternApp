package com.example.koobboxrecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    Adapter_ adapter_;
    RecyclerView recyclerView;
    ArrayList<ModelElement> list = new ArrayList<>();
    boolean AscendingClicked=false;
    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    ProgressBar progressBar;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);

        recyclerView= findViewById(R.id.recyclerView);
        setRecyclerView(list);
        addDataToList();
    }

    private void addDataToList() {
        if(count<10) {
            count++;
            progressBar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    list.add(new ModelElement("Lion", false, "Dangerous Animal", "2020-05-30", 2, "Animal"));
                    list.add(new ModelElement("Peacock", false, "Animal", "2021-04-24", 0, "Bird"));
                    list.add(new ModelElement("Tiger", false, "Dangerous Animal", "2019-11-30", 1, "Animal"));
                    list.add(new ModelElement("Parrot", false, " Animal", "2020-04-05", 0, "Bird"));
                    list.add(new ModelElement("Fox", false, "Dangerous Animal", "2019-05-20", 2, "Animal"));
                    list.add(new ModelElement("Owl", false, " Animal", "2020-03-20", 1, "Bird"));
                    list.add(new ModelElement("Dog", false, " Animal", "2018-06-20", 0, "Animal"));
                    list.add(new ModelElement("Cow", false, " Animal", "2016-12-05", 2, "Animal"));
                    list.add(new ModelElement("Hen", false, " Animal", "2019-01-06", 1, "Bird"));
                    list.add(new ModelElement("Lion", false, "Dangerous Animal", "2018-03-20", 0, "Animal"));
                    progressBar.setVisibility(View.GONE);
                    adapter_.notifyDataSetChanged();
                }
            }, 3000);
        }

    }

    private void setRecyclerView(ArrayList<ModelElement> list) {
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.hasFixedSize();
        adapter_ = new Adapter_(list,MainActivity.this);
        recyclerView.setAdapter(adapter_);
        adapter_.sort(1);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems + scrollOutItems == totalItems))
                {
                    isScrolling = false;
                    addDataToList();
                }
            }
        });
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ((item.getItemId())){
            case  R.id.priority_sort_mode :
                adapter_.sort(0);
                item.setChecked(true);
                break;
            case  R.id.date_sort_mode :
                adapter_.sort(1);
                item.setChecked(true);
                break;
            case  R.id.title_sort_mode :
                adapter_.sort(2);
                item.setChecked(true);
                break;
            case R.id.animal_filter :
                adapter_.getFilter().filter("Animal");
                item.setChecked(true);
                break;
            case R.id.bird_filter :
                adapter_.getFilter().filter("Bird");
                item.setChecked(true);
                break;
            case R.id.data_filter:
                openAlertDialogue();
                item.setChecked(true);
                break;
            case R.id.type_filter:
                item.setChecked(true);
                break;
            case R.id.no_filter:
                adapter_.getFilter().filter("");
                item.setChecked(true);
                break;
            case R.id.ascending_sort_order:
                Collections.reverse(list);
                adapter_.notifyDataSetChanged();
                if (AscendingClicked==false){
                    item.setChecked(true);
                    AscendingClicked = true;
                }else {
                    item.setChecked(false);
                    AscendingClicked = false;
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openAlertDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.alert_dialogue_layout,null));

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        EditText input_startDate = alertDialog.findViewById(R.id.editTextStartDate);
        EditText input_EndDate = alertDialog.findViewById(R.id.editTextEndDate);
        Button filterBtn = alertDialog.findViewById(R.id.filterBtn);
        Button cancelBtn = alertDialog.findViewById(R.id.cancelBtn);

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startDate = input_startDate.getText().toString();
                String endDate = input_EndDate.getText().toString();
                adapter_.getFilter().filter(startDate+"#DATE_CHECK#"+endDate);
                alertDialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }
}
