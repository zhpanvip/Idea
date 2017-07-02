package com.cypoem.idea.module.post_bean;

import java.io.File;

/**
 * Created by zhpan on 2017/7/2.
 */

public class EverydaySayPost {
    private File photo;
    private String content;
    private String pen_name;

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPen_name() {
        return pen_name;
    }

    public void setPen_name(String pen_name) {
        this.pen_name = pen_name;
    }
}
