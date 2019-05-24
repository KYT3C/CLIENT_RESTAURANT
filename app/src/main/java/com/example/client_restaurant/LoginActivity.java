package com.example.client_restaurant;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class LoginActivity extends AppCompatActivity {

    EditText editTextDni, editTextAccesKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button buttonLogin = findViewById(R.id.buttonLogin);

        editTextDni = findViewById(R.id.editTextDni);
        editTextAccesKey = findViewById(R.id.editTextAccesKey);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginAsyncTask().execute();
            }
        });
    }

     class LoginAsyncTask extends AsyncTask<String, Void, String>{

        Socket sk;

        DataInputStream dis;
        DataOutputStream dos;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {

            String publicKey;

            try {
                String ip = "192.168.1.200";
                sk = new Socket(ip, 20002);
                System.out.println("Establecida la conexión con " + ip);
                dis = new DataInputStream(sk.getInputStream());
                dos = new DataOutputStream(sk.getOutputStream());

                //Falta un método que cifre los datos.

                //publicKey = dis.readUTF();

                dos.writeUTF(editTextDni.getText().toString());
                dos.writeUTF(editTextAccesKey.getText().toString());

                boolean validatedLogin = dis.readBoolean();

                if(validatedLogin) {

                    System.out.println("Login correcto");

                    //Pasa de Activity.
                }
                else {

                    System.out.println("Login fallido");
                    //Se queda en la Activity y muestra un mensaje de Error.
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return null;
        }

        private String encodeAccesKey( String accesKey){

            return null;
        }
    }
}
