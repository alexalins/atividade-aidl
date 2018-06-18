package com.example.alexalins.appcep;

import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btn = (ImageButton) findViewById(R.id.btn);
        final EditText logradouro = (EditText) findViewById(R.id.logradouro);
        final EditText complemento = (EditText) findViewById(R.id.complemento);
        final EditText bairro = (EditText) findViewById(R.id.bairro);
        final EditText cidade = (EditText) findViewById(R.id.cidade);
        final EditText estado = (EditText) findViewById(R.id.estado);
        final EditText cep = (EditText) findViewById(R.id.cep);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //criando thread
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            RecuperarCep recuperarCep = new RecuperarCep();
                            final JSONObject jsonObject = recuperarCep.DadosJson(cep.getText().toString());

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //setando na tela
                                    try {
                                        logradouro.setText(jsonObject.getString("logradouro"));
                                        complemento.setText(jsonObject.getString("complemento"));
                                        bairro.setText(jsonObject.getString("bairro"));
                                        cidade.setText(jsonObject.getString("localidade"));
                                        estado.setText(jsonObject.getString("uf"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }


                            });
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    }).start();
            }
        });
    }
}
