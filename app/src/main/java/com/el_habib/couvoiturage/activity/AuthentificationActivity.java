package com.el_habib.couvoiturage.activity;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.el_habib.couvoiturage.Couvoiturage;
import com.el_habib.couvoiturage.R;
import com.el_habib.couvoiturage.VolleyCallBack;

import org.json.JSONException;
import org.json.JSONObject;


public class AuthentificationActivity extends AppCompatActivity {

    FloatingActionButton enregistrerFloatingActionButton = null;
    EditText emailEditText = null;
    EditText motDePasseEditText = null;
    ProgressBar progressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

        enregistrerFloatingActionButton = (FloatingActionButton)findViewById(R.id.enregistrerFloatingActionButton);
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        motDePasseEditText = (EditText)findViewById(R.id.motDePasseEditText);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);


        enregistrerFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                ((Couvoiturage)getApplication()).getServices().authentification(emailEditText.getText().toString(),
                        motDePasseEditText.getText().toString(), new VolleyCallBack<Boolean>() {
                            @Override
                            public void onSuccess(Boolean result) {
                                if(result){
                                    Class<?> activitySuivant = ((Couvoiturage)getApplication()).getActivityDesirer()== null ? PrincipalActivity.class : ((Couvoiturage)getApplication()).getActivityDesirer();
                                    startActivity(new Intent(getApplicationContext(),activitySuivant));
                                }else{
                                    Toast.makeText(getApplicationContext(),"Votre email et/ou votre mot de passe sont incorrect",Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void errorNetwork() {
                                Toast.makeText(getApplicationContext(),"Erreur de connexion",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void sameOpperation() {
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.authentification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about_item) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
