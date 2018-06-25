package com.samadeniyi.waferdemo;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.samadeniyi.waferdemo.Data.Model.EuCountries;
import com.samadeniyi.waferdemo.Data.Remote.APIService;
import com.samadeniyi.waferdemo.Data.Remote.ApiUtils;
import com.samadeniyi.waferdemo.Helper.RecyclerItemTouchHelper;
import com.samadeniyi.waferdemo.Helper.RecyclerItemTouchHelperListener;
import com.samadeniyi.waferdemo.RecyclerViewAdapter.CountryListAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelperListener {

    public RecyclerView  recyclerView;
    public List<EuCountries> list;
    public CountryListAdapter adapter;
    public CoordinatorLayout rootlayout;

    APIService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = ApiUtils.getAPIService();

        recyclerView  = (RecyclerView)findViewById(R.id.recycler_view);
        rootlayout = (CoordinatorLayout)findViewById(R.id.rootlayout);
        list = new ArrayList<>();
        adapter =  new CountryListAdapter(this, list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback ItemTouchHelperCallback
                = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(ItemTouchHelperCallback).attachToRecyclerView(recyclerView);

        getEuCountries();
    }

    private void getEuCountries() {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching EU countries...");
        progressDialog.show();
        mService.getEuCountries()
                .enqueue(new Callback<List<EuCountries>>() {
                    @Override
                    public void onResponse(Call<List<EuCountries>> call, Response<List<EuCountries>> response) {
                        list.clear();
                        list.addAll(response.body());
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<List<EuCountries>> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof CountryListAdapter.CountryViewHolder){
            String name = list.get(viewHolder.getAdapterPosition()).getName();
            final EuCountries deletedItem = list.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            adapter.removeItem(deletedIndex);

            Snackbar snackbar = Snackbar.make(rootlayout, name + " removed from list!", Snackbar.LENGTH_SHORT);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.restoreItem(deletedItem,deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.GREEN);
            snackbar.show();
        }
    }
}
