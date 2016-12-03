package com.el_habib.couvoiturage.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.el_habib.couvoiturage.Couvoiturage;
import com.el_habib.couvoiturage.R;
import com.el_habib.couvoiturage.VoituresArrayAdapter;
import com.el_habib.couvoiturage.VolleyCallBack;
import com.el_habib.couvoiturage.model.Voiture;

import java.util.List;

public class ListVoituresActivity extends AppCompatActivity {


    ListView voituresListView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_voitures);

        voituresListView =(ListView)findViewById(R.id.voituresListView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        ((Couvoiturage)getApplication()).getServices().getVoitures(new VolleyCallBack<List<Voiture>>() {
            @Override
            public void sameOpperation() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(List<Voiture> result) {
                voituresListView.setVisibility(View.VISIBLE);
                voituresListView.setAdapter(new VoituresArrayAdapter(getApplicationContext(),result));
            }

            @Override
            public void errorNetwork() {
                Toast.makeText(getApplicationContext(),"erreur de connexion",Toast.LENGTH_LONG).show();
            }
        });

    }
}
