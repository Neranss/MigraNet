package com.example.migranet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FindFriendsActivity extends AppCompatActivity {

    String session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        session=((MigraNet)this.getApplication()).getSession();

        refresh(null);
    }

    public void refresh(View view){
        JSONObject params = new JSONObject();
        try {
            params.put("user_session", Long.parseLong(session));
        } catch (JSONException e){
            e.printStackTrace();
        }

        JSONRPCSender sender = new JSONRPCSender("http://81.91.176.31:9989/");
        Future<JSONObject> result = sender.send_json("friends.find", params);
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                try {
                    JSONObject answer = result.get();
                    if (answer.has("result")){
                        Log.v(null,answer.toString());



//                        LayoutInflater inflater = getLayoutInflater();
//
//                        for (int i=0;i<messages.length();i++){
//                            JSONObject item = messages.getJSONObject(i);
//
//                            View new_view = inflater.inflate(R.layout.message,layout);
//
//                            CardView card_view =(CardView) layout.getChildAt(i);
//                            card_view.setTag(item.getString("from_user"));
//
//                            String from_user = item.getString("from_user");
//                            String sender_name = "Deleted user";
//                            for (int j=0;j<users_json.length();j++){
//                                JSONObject user = users_json.getJSONObject(j);
//                                if (user.getString("user_id")==from_user){
//                                    sender_name=user.getString("first_name")+" "+user.getString("second_name");
//                                }
//                            }
//
//                            TextView name_view1 = (TextView)   ((CardView)card_view).getChildAt(0);
//                            name_view1.setText(sender_name);
//
//
//
//                            TextView description_view1 = (TextView)   ((CardView)card_view).getChildAt(1);
//                            description_view1.setText(item.getString("message"));
//                        }


                    } else{
                        //goto_events(null);
//                        description_view.setText(answer.toString());
                    }
                } catch ( ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void goto_home(View view){
        Intent intent = new Intent(FindFriendsActivity.this, MainActivity.class);
        startActivity(intent);

    }
}