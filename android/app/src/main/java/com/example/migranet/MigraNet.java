package com.example.migranet;

import android.app.Application;

public class MigraNet extends Application {

    private String session;
    private String chosen_event;
    private String chosen_chat;
    private String user_id;

    private Double latitude;
    private Double longitude;
    private String event_name;

    private String chosen_friend;

    public void setEventName(String value){
        event_name = value;
    }
    public String getEventName(){
        return event_name;
    }

    public void setCoords(Double latitude_value, Double longitude_value){
        latitude=latitude_value;
        longitude=longitude_value;
    }
    public Double getLat(){
        return latitude;
    }
    public Double getLong(){
        return longitude;
    }

    public String getFriendId(){
        return chosen_friend;
    }
    public void setFriendId(String value){
        chosen_friend = value;
    }

    public String getUserId(){
        return user_id;
    }
    public void setUserId(String value){
        user_id = value;
    }

    public String getChat(){
        return chosen_chat;
    }
    public void setChat(String value){
        chosen_chat = value;
    }

    public String getEvent(){
        return chosen_event;
    }
    public void setEvent(String value){
        chosen_event = value;
    }

    public String getSession(){
        return session;
    }

    public void setSession(String value){
        session = value;
    }
}
