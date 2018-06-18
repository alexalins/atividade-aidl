package com.example.alexalins.appcep;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class RecuperarCep extends Service{
    MyIBind myIBind = new MyIBind();

    protected JSONObject DadosJson(String cep) throws RemoteException {

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(myIBind.RecuperarDados(cep));//criando um objeto recebendo o json como string
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;//retornando json

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class MyIBind extends IMyAidlInterface.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String RecuperarDados(String cep) throws RemoteException {
            //passando url
            String searchUrl = ("https://viacep.com.br/ws/"+cep+"/json/");//passando cep na url
            URL url = null;
            try {
                url = new URL(searchUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection connection = null;//criando conexao
            try {
                connection = (HttpURLConnection) url.openConnection();//conectando
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                //setando dados da conexao
                connection.setReadTimeout(10000 /* milliseconds */ );
                connection.setConnectTimeout(15000 /* milliseconds */ );
                connection.setDoOutput(true);
                connection.connect();
                connection.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedReader br = null;//criando BR pra ler o json
            try {
                br = new BufferedReader(new InputStreamReader(url.openStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringBuilder sb = new StringBuilder(); //configurando a string

            try {

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String jsonString = sb.toString();

            Log.d("TAG", jsonString);
            return jsonString;
        }
    }
}
