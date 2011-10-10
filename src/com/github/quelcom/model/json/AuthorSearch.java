package com.github.quelcom.model.json;

import java.io.Serializable;
import java.util.ArrayList;

public class AuthorSearch implements Serializable {

    private static final long serialVersionUID = 1L;
    private String pauseid;
    private String name;
    private String dir;
    private ArrayList<String> email;
    private ArrayList<String> website;
    
    public String getName() {
        return name;
    }
    
    public String getDir() {
        return dir;
    }
    
    public String getPauseId() {
        return pauseid;
    }
    
    public ArrayList<String> getEmail() {
        return email;
    }

    public ArrayList<String> getWebsite() {
        return website;
    }
}