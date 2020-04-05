package fr.nathan.windapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.text.ParseException;
import java.util.List;

import fr.nathan.windapp.Models.Prevision;
import fr.nathan.windapp.Models.Setting;
import fr.nathan.windapp.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private List<Prevision> dataPrevisionList;

    public RecyclerViewAdapter(List<Prevision> dataPrevisionList) {
        this.dataPrevisionList = dataPrevisionList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerViewHolder viewHolder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_content, parent, false);
        viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        String text_degrees = Setting.TEMPERATURE_IS_IN_CELSIUS ? String.valueOf((int) (dataPrevisionList.get(position).getMain().getFeels_like()) + "°C") : String.valueOf((int) (dataPrevisionList.get(position).getMain().getFeels_like() * 1.8 + 32) + "°F");

        switch (dataPrevisionList.get(position).getWeather().get(0).getIcon()) {
            case "01d":
                holder.im_icon.setImageResource(R.drawable.one_d);
                break;
            case "02d":
                holder.im_icon.setImageResource(R.drawable.two_d);
                break;
            case "03d":
            case "03n":
                holder.im_icon.setImageResource(R.drawable.three_d);
                break;
            case "04d":
            case "04n":
                holder.im_icon.setImageResource(R.drawable.four_d);
                break;
            case "09d":
            case "09n":
                holder.im_icon.setImageResource(R.drawable.nine_d);
                break;
            case "10d":
                holder.im_icon.setImageResource(R.drawable.ten_d);
                break;
            case "11d":
                holder.im_icon.setImageResource(R.drawable.eleven_d);
                break;
            case "13d":
                holder.im_icon.setImageResource(R.drawable.thirteen_d);
                break;
            case "50d":
                holder.im_icon.setImageResource(R.drawable.fifty_d);
                break;
            case "01n":
                holder.im_icon.setImageResource(R.drawable.one_n);
                break;
            case "02n":
                holder.im_icon.setImageResource(R.drawable.two_n);
                break;
            case "10n":
                holder.im_icon.setImageResource(R.drawable.ten_n);
            default:
                break;
        }
        holder.tv_degrees.setText(text_degrees);
        try {
            holder.tv_date.setText(String.valueOf(dataPrevisionList.get(position).getFormatedDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return dataPrevisionList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tv_degrees, tv_date;
        ImageView im_icon;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_degrees = itemView.findViewById(R.id.tv_degrees);
            tv_date = itemView.findViewById(R.id.tv_date);
            im_icon = itemView.findViewById(R.id.im_icon);
        }
    }

}
