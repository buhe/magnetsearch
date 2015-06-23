package com.aoppp.magnetsearch.connector;

import com.aoppp.magnetsearch.domain.Magnet;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guguyanhua on 6/23/15.
 */
public class HttpConnnectManager {
    private static HttpConnnectManager instance;
    List<MagConnector> magConnectorList = new ArrayList<MagConnector>();
    MagConnector master = null;
    public HttpConnnectManager() {
        magConnectorList.add(new BtCherryConnector());
        master = select();
    }

    public static synchronized HttpConnnectManager instance(){
        if(instance == null){
            instance = new HttpConnnectManager();
        }
        return instance;
    }

    public MagConnector select(){
        long minTimeout = Long.MAX_VALUE;
        MagConnector selectd = null;
        for(MagConnector connector : magConnectorList){
            ConnectState state = connector.isConnectd();
            if(state.success){
                if(state.speed < minTimeout) {
                    minTimeout = state.speed;
                    selectd = connector;
                }
            }
        }
        return selectd;
    }

    public void get(String text,final MagnetBack magnetBack){
        master.search(text,new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String string = response.body().string();
                List<Magnet> magnets = master.parse(string);
                magnetBack.get(magnets);
            }
        });
    }

    public void get(String text,int page,final MagnetBack magnetBack){
        master.search(text,page,new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String string = response.body().string();
                List<Magnet> magnets = master.parse(string);
                magnetBack.get(magnets);
            }
        });
    }

    public static void main(String[] args) {
        HttpConnnectManager httpConnnectManager = new HttpConnnectManager();
        httpConnnectManager.get("呵呵呵",new MagnetBack() {
            @Override
            public void get(List<Magnet> magnets) {

            }
        });
    }
}
