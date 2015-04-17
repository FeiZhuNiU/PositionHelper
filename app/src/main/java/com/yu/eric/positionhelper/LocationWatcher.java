package com.yu.eric.positionhelper;



/**
 * Created by lliyu on 4/15/2015.
 */

/*
 * observer pattern
 * classes which needs to listen to the location change need to implement this interface
 *
 */

public interface LocationWatcher {
    void update();
}
