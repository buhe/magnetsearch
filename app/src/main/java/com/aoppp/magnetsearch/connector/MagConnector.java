package com.aoppp.magnetsearch.connector;

import com.aoppp.magnetsearch.domain.Magnet;

import java.util.List;

/**
 * Created by guguyanhua on 6/23/15.
 */
public interface MagConnector {
    //执行查询
    void search(String text,com.squareup.okhttp.Callback callBack);

    void search(String text,int page,com.squareup.okhttp.Callback callBack);

    //检查connector是否可用
    ConnectState isConnectd();

    //解析链接
    List<Magnet> parse(String httpResource);
}
