package com.example.projectesupermercat;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;


public class SocketManager {
    private static final String SERVER_URL = "http://dam.inspedralbes.cat:3593/"; // Reemplaza esto con la dirección de tu servidor
    private Socket socket;

    public SocketManager() {
        try {
            socket = IO.socket(SERVER_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        socket.connect();
        Log.d("SocketManager", "Connected to server");
    }

    public void disconnect() {
        socket.disconnect();
        Log.d("SocketManager", "Disconnected from server");
    }

    public void sendRecollitEstat(int idComanda) {
        try {
            JSONObject data = new JSONObject();
            data.put("id", idComanda);
            socket.emit("recogerComanda", data);
            Log.d("SocketManager", "Comanda recollida enviada: "+idComanda);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void sendAutentification(Context context) {
        try {
            JSONObject data = new JSONObject();
            SharedPreferences settings = context.getSharedPreferences("UserInfo", 0);
            data.put("id", settings.getString("id",""));
            socket.emit("autentificacion", data);
            Log.d("SocketManager", "Autentificacion enviada: "+data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setOnMessageReceivedListener(SocketEventListener listener) {
        socket.on("comandaActualitzada", args -> {
            Log.d("SocketManager", "comandaActualitzada");
            if (args.length > 0) {
                String message = args[0].toString();
                listener.onMessageReceived(message);
            }
        });
    }

    public interface SocketEventListener {
        void onMessageReceived(String message);
    }
}