package com.telran.borislav.masterhairsalonproject.Models;

/**
 * Created by Boris on 09.06.2017.
 */

public class StateVO {
    private String title;
    private boolean selected;

    public StateVO() {
    }

    public StateVO(String title, boolean selected) {
        this.title = title;
        this.selected = selected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
