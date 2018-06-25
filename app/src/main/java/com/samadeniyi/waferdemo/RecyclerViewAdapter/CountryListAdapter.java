package com.samadeniyi.waferdemo.RecyclerViewAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.samadeniyi.waferdemo.Data.Model.EuCountries;
import com.samadeniyi.waferdemo.R;

import java.util.List;

import static android.content.ContentValues.TAG;


public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryViewHolder> {

    private Context context;
    private List<EuCountries> list;

    public CountryListAdapter(Context context, List<EuCountries> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.countries_list_item, parent, false);

        return new CountryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        EuCountries item = list.get(position);
        holder.countryName.setText(item.getName());
        holder.countryLanguage.setText(item.getLanguages()[0].getName());
        holder.countryCurrency.setText(item.getCurrencies()[0].getName());
        holder.firstLetter.setText(item.getName().substring(0,1));


    //TODO: display country's svg flag
    //        new DownloadImageFromUrlTask(holder.countryFlag).execute(item.getFlag());


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeItem (int position){
        Log.i(TAG, "before removeItem: "+list.size());
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
        Log.i(TAG, "after removeItem : "+list.size());

    }

    public void restoreItem (EuCountries item, int position){
        list.add(position,item);
        notifyItemInserted(position);
        notifyDataSetChanged();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder{
        public TextView countryName, countryCurrency, countryLanguage, firstLetter;
        public ImageView countryFlag;
        public RelativeLayout viewBackground, viewForeground;

        public CountryViewHolder(View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.country_name);
            countryCurrency = itemView.findViewById(R.id.country_currency);
            countryLanguage = itemView.findViewById(R.id.country_language);
            countryFlag = itemView.findViewById(R.id.country_flag);
            firstLetter = itemView.findViewById(R.id.country_name_first_letter);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }
}
