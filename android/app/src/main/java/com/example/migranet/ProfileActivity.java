package com.example.migranet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ProfileActivity extends AppCompatActivity {
    Boolean is_friend;
    TextView name_view;
    TextView birthday_view;
    LinearLayout layout;
    EditText message_view;

    String session;
    String friend_id;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name_view = (TextView)findViewById(R.id.name_view);
        birthday_view = (TextView)findViewById(R.id.birthday_view);
        layout = (LinearLayout)findViewById(R.id.private_messages);
        message_view = (EditText) findViewById(R.id.message_view);


        user_id = ((MigraNet)this.getApplication()).getEvent();
        session = ((MigraNet)this.getApplication()).getSession();
        user_id = ((MigraNet)this.getApplication()).getUserId();


    }

    public void click_send(View view){
        if (is_friend){
            send_message(null);
        } else{
            send_request(null);
        }
    }

    public void send_request(View view){
        JSONObject params = new JSONObject();
        try {
            params.put("user_session", Long.parseLong(session));
            params.put("action_id", Long.parseLong(friend_id));
        } catch (JSONException e){
            e.printStackTrace();
        }
        JSONRPCSender sender = new JSONRPCSender("http://81.91.176.31:9989/");
        Future<JSONObject> result = sender.send_json("friends.send_request_to_add", params);
        try {
            JSONObject answer = result.get();
            //time_view.setText(params.toString()+"\n"+answer.toString());

            Log.v(null,params.toString());
            Log.v(null,answer.toString());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       // refresh(null);
    }

    public void send_message(View view){

        String message = message_view.getText().toString();
        JSONObject params = new JSONObject();
        try {
            params.put("user_session", Long.parseLong(session));
            //params.put("chat_id", Long.parseLong(chat));
            params.put("message", message);
        } catch (JSONException e){
            e.printStackTrace();
        }
        JSONRPCSender sender = new JSONRPCSender("http://81.91.176.31:9989/");
        Future<JSONObject> result = sender.send_json("chat.send_message", params);
        try {
            JSONObject answer = result.get();
            //time_view.setText(params.toString()+"\n"+answer.toString());

            Log.v(null,params.toString());
            Log.v(null,answer.toString());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //refresh(null);

    }


}