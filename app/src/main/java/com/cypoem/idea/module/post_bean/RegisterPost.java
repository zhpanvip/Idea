package com.cypoem.idea.module.post_bean;

import java.io.File;

/**
 * Created by zhpan on 2017/7/2.
 */

public class RegisterPost {
    private String phone;
    private String password;
    private File uploadFile;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public File getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
    }
}
