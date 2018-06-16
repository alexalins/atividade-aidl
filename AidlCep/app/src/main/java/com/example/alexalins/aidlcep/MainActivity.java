package com.example.alexalins.aidlcep;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.alexalins.appcep.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    IMyAidlInterface iMyAidlInterface;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText cep = (EditText) findViewById(R.id.cep);
        Button button = (Button) findViewById(R.id.button);
        final TextView textView = (TextView) findViewById(R.id.detalhes);

        textView.setText(" ");

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                iMyAidlInterface = null;
            }
        };

        if(iMyAidlInterface == null){
            Intent intent = new Intent("appcep.CEPServer");
            intent.setPackage("com.example.alexalins.appcep");

            bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (iMyAidlInterface == null)
                        textView.setText("Nulo");
                    else
                        textView.setText(iMyAidlInterface.RecuperarDados(cep.getText().toString()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
    }
}
