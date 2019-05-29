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

import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
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
        Cipher rsa = null;


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

                publicKey = (PublicKey) ois.readObject();

                dos.writeInt(1);

                sendEncrypted(editTextDni.getText().toString());
                sendEncrypted(editTextAccesKey.getText().toString());

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


            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }

            return null;


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

        private void sendEncrypted(String message) {

            try {
                rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                rsa.init(Cipher.ENCRYPT_MODE, publicKey);
                byte[] encriptado = rsa.doFinal(message.getBytes());
                dos.writeInt(encriptado.length);
                dos.write(encriptado);

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String decryptMessage() {
            try {
                rsa.init(Cipher.DECRYPT_MODE, publicKey);
                byte[] message = null;
                int length = dis.readInt();
                if (length > 0) {
                    message = new byte[length];
                    dis.readFully(message, 0, message.length);
                }
                byte[] bytesDesencriptados = rsa.doFinal(message);
                String textoDesencripado = new String(bytesDesencriptados);
                System.out.println(textoDesencripado);
                return textoDesencripado;
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }
    }
}
