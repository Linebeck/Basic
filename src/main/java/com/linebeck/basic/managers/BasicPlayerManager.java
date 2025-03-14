package com.linebeck.basic.managers;

import com.linebeck.basic.data.BasicPlayerData;

public class BasicPlayerManager {

    private final BasicPlayerData basicPlayerData = new BasicPlayerData();

    public BasicPlayerData getBasicPlayerData() {
        return basicPlayerData;
    }
}
