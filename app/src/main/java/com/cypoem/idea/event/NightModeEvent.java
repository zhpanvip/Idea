package com.cypoem.idea.event;

/**
 * Created by zhpan on 2017/6/2.
 * Description: night mode event for EventBus
 */
public class NightModeEvent {
    public boolean isNight;

    public NightModeEvent(boolean isNight) {
        this.isNight = isNight;
    }
}
