package com.example.migranet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class LoginActivity extends AppCompatActivity {
    EditText email_view;
    EditText password_view;


    TextView status_view;
    public static String session="";
    public static String error="";
    public static String user_id="";

    public void goto_register(View view){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void set_data(String session, String user_id){
        ((MigraNet)this.getApplication()).setSession(session);
        ((MigraNet)this.getApplication()).setUserId(user_id);
    }
    public void login(View view) throws JSONException {



        String login = email_view.getText().toString();
        String password = password_view.getText().toString();




        JSONObject user = new JSONObject();
        try {
            user.put("email", login);
            user.put("password", password);
        } catch (JSONException e){
            e.printStackTrace();
        }

        JSONRPCSender sender = new JSONRPCSender("http://81.91.176.31:9989/");
        Future<JSONObject> result = sender.send_json("user.login_by_email", user);

        new Thread(new Runnable() {
            public void run() {

                try{
                JSONObject answer = result.get();
                Log.v(null,"Login result "+answer.toString());

                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            //status_view.setText(decodedString);
                            try {

                                if (answer.has("result")){
                                    Log.v(null,answer.toString());
                                    //status_view.setText("Your user session is "+answer.getJSONObject("result").getString("user_session"));
                                    session=answer.getJSONObject("result").getString("user_session");
                                    user_id=answer.getJSONObject("result").getString("user_id");
                                    set_data(session,user_id);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);

                                } else{
                                    if (answer.has("error")) {
                                        error=answer.getJSONObject("error").getString("message");
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    });


                } catch ( ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();



        if (error!=""){
            status_view.setText(error);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_view = (EditText)findViewById(R.id.email);
        password_view = (EditText)findViewById(R.id.password);

        status_view = (TextView)findViewById(R.id.status_view);



    }
}