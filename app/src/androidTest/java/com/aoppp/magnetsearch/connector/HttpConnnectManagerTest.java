package com.aoppp.magnetsearch.connector;

import com.aoppp.magnetsearch.domain.Magnet;

import junit.framework.TestCase;

import java.util.List;

public class HttpConnnectManagerTest extends TestCase {

    public void testGet() throws Exception {
        HttpConnnectManager httpConnnectManager = new HttpConnnectManager();
        httpConnnectManager.get("",new MagnetBack() {
            @Override
            public void get(List<Magnet> magnets) {

            }
        });
    }
}