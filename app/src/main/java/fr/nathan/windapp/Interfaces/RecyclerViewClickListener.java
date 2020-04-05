package fr.nathan.windapp.Interfaces;

import android.view.View;

import java.text.ParseException;

public interface RecyclerViewClickListener {
    void onClick(View view, int position) throws ParseException;
}
