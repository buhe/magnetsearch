package com.aoppp.magnetsearch.connector;

import com.aoppp.magnetsearch.domain.Magnet;
import com.google.common.collect.Lists;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guguyanhua on 6/23/15.
 */
public class BtCherryConnector implements MagConnector {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    Pattern title = Pattern.compile("<h5 class=\"h\">(.*)</h5>");
    Pattern url = Pattern.compile(".*(magnet:\\?xt=.*)\">");
    OkHttpClient client = new OkHttpClient();
    @Override
    public void search(String text,com.squareup.okhttp.Callback callBack) {
       search(text,0,callBack);
    }

    @Override
    public void search(String text,int page,com.squareup.okhttp.Callback callBack) {
        if(page > 0){
            Request request = new Request.Builder()
                    .url("http://www.btcherry.net/search?keyword="+text + "&p="+ page)
                    .get()
                    .build();
            client.newCall(request).enqueue(callBack);
        }else{
            Request request = new Request.Builder()
                    .url("http://www.btcherry.net/search?keyword="+text)
                    .get()
                    .build();
            client.newCall(request).enqueue(callBack);
        }

    }

    @Override
    public ConnectState isConnectd() {
        return new ConnectState(true,100,null);
    }

    @Override
    public List<Magnet> parse(String httpResource) {
//        System.out.println(httpResource);
        List<Magnet> results = Lists.newArrayList();
        Matcher matcher = url.matcher(httpResource);
        Matcher titleMatcher = title.matcher(httpResource);
        while(matcher.find()){
            Magnet one = new Magnet();
            String url = matcher.group(1);
            one.setUrl(url);
            if(titleMatcher.find()){
                String title = titleMatcher.group(1);
                one.setTitle(title);
            }
            results.add(one);
        }

        return results;
    }
}
