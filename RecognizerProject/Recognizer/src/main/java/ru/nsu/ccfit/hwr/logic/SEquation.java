package ru.nsu.ccfit.hwr.logic;

import java.io.Serializable;

/**
 * Created by Баира on 21.12.13.
 */
public class SEquation implements Serializable{
    private String equation;
    public SEquation(String s) {
        this.equation = s;
    }
    public void setString(String s) {
        this.equation = s;
    }

    @Override
    public String toString() {
        return equation;
    }
}
