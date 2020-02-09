package com.cblib.util;

import java.io.Serializable;

public class CBFiles implements Serializable {

    private static final long serialVersionUID = 1062932558499055322L;

    private String name;
    private String path;

    public CBFiles(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
