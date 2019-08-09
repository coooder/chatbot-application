package com.example.jiehui.chatrobot;

public class ListData {

    public static final int SEND = 1;

    public static final int RECEIVER = 2;

    private String text;

    private int flag;

    public ListData(String text) {
        setContent(text);
    }

    public String getContent() {
        return text;
    }

    public void setContent(String content) {
        this.text = content;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
