package com.thomas.video.bean;

/**
 * @author Thomas
 * @describe
 * @date 2019/7/31
 * @updatelog
 * @since
 */
public class DialogItemBean {
    private String name;
    private String value;
    private boolean isChoice;

    public DialogItemBean(String name, String value) {
        this.name = name;
        this.value = value;
        this.isChoice = false;
    }

    public DialogItemBean(String name, String value, boolean isChoice) {
        this.name = name;
        this.value = value;
        this.isChoice = isChoice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isChoice() {
        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }

    @Override
    public String toString() {
        return "DialogItemBean{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", isChoice=" + isChoice +
                '}';
    }
}
