package com.example.koobboxrecyclerview;

public class ModelElement {
    String title;

    public boolean isSelected() {
        return isSelected;
    }

    public ModelElement(String title, boolean isSelected, String subTitle, String date, int priority, String type) {
        this.title = title;
        this.isSelected = isSelected;
        this.subTitle = subTitle;
        this.date = date;
        this.priority = priority;
        this.type = type;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    boolean isSelected=false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public void setType(String type) {
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    String subTitle;
    String date;
    int priority;
    String type;
}
