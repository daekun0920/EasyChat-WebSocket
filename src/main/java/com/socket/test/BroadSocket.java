package com.socket.test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

@ServerEndpoint("/broadsocket")
public class BroadSocket {
    //user session list
    static List<Session> sessionUsers = Collections.synchronizedList(new ArrayList<>());
    
    /**
     * insert the session into an array when connected.
     * @param userSession 
     */
    @OnOpen
    public void handleOpen(Session userSession, EndpointConfig config){    	
    	try {
    		userSession.getUserProperties().put((String) HomeController.session.getAttribute("userid"), true);
    		
		} catch (Exception e) {
			e.printStackTrace();
		}

    	sessionUsers.add(userSession);
    }
    /**
     * called when receives message.
     * @param message\
     * @param userSession
     * @throws IOException
     */
    @OnMessage
    public void handleMessage(String message,Session userSession) throws IOException{
    	JSONObject obj = new JSONObject(message);
    	
        // Send the data to selected user.
        Iterator<Session> iterator = sessionUsers.iterator();
        while(iterator.hasNext()){
        	Session sess = iterator.next();
        	
            if (sess.getUserProperties().containsKey(obj.getString("sendTo"))) {
            	sess.getBasicRemote().sendText(buildJsonData(obj.getString("sendTo"), obj.getString("message")));
            }
        }
    }
    /**
     * remove the user from session user list when the websocket is closed.
     * @param userSession
     */
    @OnClose
    public void handleClose(Session userSession){
        sessionUsers.remove(userSession);
    }
    
    /**
     * turn String type message into JSON format.
     * @param username
     * @param message
     * @return
     */
    public String buildJsonData(String username,String message){
        JsonObject jsonObject = Json.createObjectBuilder().add("message", message).build();
        StringWriter stringwriter =  new StringWriter();
        try(JsonWriter jsonWriter = Json.createWriter(stringwriter)){
                jsonWriter.write(jsonObject);
        };
        return stringwriter.toString();
    }
}
