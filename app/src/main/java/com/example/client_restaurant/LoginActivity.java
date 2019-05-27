package com.example.client_restaurant;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
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

    //Edit Text del "Usuario" y la "Contraseña".
    private EditText editTextDni, editTextAccesKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Button para hacer Login.
        Button buttonLogin = findViewById(R.id.buttonLogin);

        //Edit Text del "Usuario" y la "Contraseña".
        editTextDni = findViewById(R.id.editTextDni);
        editTextAccesKey = findViewById(R.id.editTextAccesKey);

        //Cuando se hace click en el button se crea la conexión.
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginAsyncTask().execute();
            }
        });
    }

     @SuppressLint("StaticFieldLeak")
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
                String ip = "192.168.12.200";
                sk = new Socket(ip, 20002);
                System.out.println("Establecida la conexión con " + ip);
                dis = new DataInputStream(sk.getInputStream());
                dos = new DataOutputStream(sk.getOutputStream());

                //Falta un método que cifre los datos.

                //publicKey = dis.readUTF();

                dos.writeUTF(editTextDni.getText().toString());
                dos.writeUTF(editTextAccesKey.getText().toString());

                int validatedLogin = dis.readInt();

                if(validatedLogin == 1) {

                    Intent intent = new Intent(LoginActivity.this,HomePageActivity.class);
                    startActivity(intent);
                }
                if (validatedLogin == 2){

                    showUsernameOrPasswordError();
                }
                if (validatedLogin == 3){

                    showEmptyFieldsError();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return null;
        }

        private String encodeAccesKey( String accesKey){

            return null;
        }

        private void showUsernameOrPasswordError(){

            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                    alertDialogBuilder.setMessage("You have entered an invalid username or password"); // Para no dar más información de la necesaria.
                    alertDialogBuilder.setCancelable(true);

                    alertDialogBuilder.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        }

         private void showEmptyFieldsError(){

             runOnUiThread(new Runnable() {

                 @Override
                 public void run() {

                     AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                     alertDialogBuilder.setMessage("There can not be empty fields");
                     alertDialogBuilder.setCancelable(true);

                     alertDialogBuilder.setPositiveButton(
                             "Ok",
                             new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int id) {
                                     dialog.cancel();
                                 }
                             });

                     AlertDialog alertDialog = alertDialogBuilder.create();
                     alertDialog.show();
                 }
             });
         }
    }
}
