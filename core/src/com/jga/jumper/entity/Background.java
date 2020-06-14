package com.jga.jumper.entity;

public class Background {

    // == attributes ==
    private float width;
    private float height;

    private float xPos;
    private float yPos;

    // == constructors ==

    public Background() {

    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }
}
