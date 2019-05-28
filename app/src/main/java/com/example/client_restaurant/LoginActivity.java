package com.example.client_restaurant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.ObjectInputStream;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.io.FileInputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.spec.KeySpec;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

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
    class LoginAsyncTask extends AsyncTask<String, Void, String> {

        Socket sk;
        PublicKey publicKey;
        DataInputStream dis;
        DataOutputStream dos;
        ObjectInputStream ois;


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
            try {
                String ip = "192.168.137.1";
                sk = new Socket(ip, 20002);
                System.out.println("Establecida la conexión con " + ip);
                dis = new DataInputStream(sk.getInputStream());
                dos = new DataOutputStream(sk.getOutputStream());
                ois = new ObjectInputStream(sk.getInputStream());
                try {
                    Cipher rsa = null;
                    rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                    //Recibo la clave publica del servidor
                    publicKey = (PublicKey) ois.readObject();
                    System.out.println(publicKey.toString());
                    //Creo un mensaje para enviar
                    String message = "pero weno willy como tu por aqui compañero";
                    //Encripto el mensaje con la clave publica mediante RSA
                    rsa.init(Cipher.ENCRYPT_MODE, publicKey);
                    byte[] encriptado = rsa.doFinal(message.getBytes());
                    dos.writeInt(encriptado.length);
                    dos.write(encriptado);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Falta un método que cifre los datos.

                //publicKey = dis.readUTF();

                dos.writeUTF(editTextDni.getText().toString());
                dos.writeUTF(editTextAccesKey.getText().toString());

                int validatedLogin = dis.readInt();

                if (validatedLogin == 1) {

                    Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                    startActivity(intent);
                }
                if (validatedLogin == 2) {

                    showUsernameOrPasswordError();
                }
                if (validatedLogin == 3) {

                    showEmptyFieldsError();
                }


            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return null;


        }

        private String publicKey(DataInputStream dis) {
            try {
                return dis.readUTF();
            } catch (Exception e) {
                return null;
            }
        }

        private void showUsernameOrPasswordError() {

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

        private void showEmptyFieldsError() {

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

        private PublicKey loadPublicKey(String fileName) throws Exception {
            FileInputStream fis = new FileInputStream(fileName);
            int numBtyes = fis.available();
            byte[] bytes = new byte[numBtyes];
            fis.read(bytes);
            fis.close();

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            KeySpec keySpec = new X509EncodedKeySpec(bytes);
            PublicKey keyFromBytes = keyFactory.generatePublic(keySpec);
            return keyFromBytes;
        }

        private void sendEncrypted() {

        }
    }
}
