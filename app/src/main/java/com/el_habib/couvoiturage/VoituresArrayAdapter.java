package com.el_habib.couvoiturage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.el_habib.couvoiturage.model.Voiture;

import java.util.List;

/**
 * Created by el-habib on 13/11/16.
 */

public class VoituresArrayAdapter extends ArrayAdapter<Voiture> {

    public VoituresArrayAdapter(Context context, List<Voiture> voitures){
        super(context,R.layout.voiture_item_layout,voitures);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout view = null;
        if(convertView == null){
            view = new LinearLayout(getContext());
            LayoutInflater li =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            li.inflate(R.layout.voiture_item_layout,view,true);
        }else{
            view = (LinearLayout)convertView;
        }

        TextView marqueVoitureTextView = (TextView)view.findViewById(R.id.marqueVoitureTextView);
        TextView colorVoitureTextView = (TextView)view.findViewById(R.id.colorVoitureTextView);
        NetworkImageView imageNetworkImageView = (NetworkImageView)view.findViewById(R.id.imageNetworkImageView);

        Voiture v = getItem(position);

        marqueVoitureTextView.setText(v.getMarque());
        colorVoitureTextView.setText(v.getColor());
        imageNetworkImageView.setImageUrl(v.getUrlImage(),Couvoiturage.mImageLoader);

        return view;
    }
}
