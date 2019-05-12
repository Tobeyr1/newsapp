package com.newsapp.android.TitleUtils;

import java.util.ArrayList;

public class ChannelManager {
    private static ArrayList<ChannelItem> userChannels;
    static {
        userChannels = new ArrayList<ChannelItem>();
        userChannels.add(new ChannelItem(1,"头条"));
        userChannels.add(new ChannelItem(2,"社会"));
        userChannels.add(new ChannelItem(3,"国际"));
        userChannels.add(new ChannelItem(4,"时尚"));
        userChannels.add(new ChannelItem(5,"体育"));
        userChannels.add(new ChannelItem(6,"军事"));
        userChannels.add(new ChannelItem(7,"体育"));
    }
    public static void setUserChannels(ArrayList<ChannelItem> userChannels){
        ChannelManager.userChannels = userChannels;
    }

    public static ArrayList<ChannelItem> getUserChannels() {
        return userChannels;
    }
}
