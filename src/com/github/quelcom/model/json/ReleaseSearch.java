package com.github.quelcom.model.json;

import java.io.Serializable;


public class ReleaseSearch implements Serializable {
    private static final long serialVersionUID = 1L;
    private String download_url;
    private String archive;
    private String version;
    private String status;
 //   private Date date;
    private String name;
    private String author;

    public String getDownloadUrl() {
        return download_url;
    }
    
    public String getArchive() {
        return archive;
    }
    
    public String getVersion() {
        return version;
    }
    
    public String getStatus() {
        return status;
    }
    
  //  public Date getDate() {
  //      return date;
  //  }
    
    public String getName() {
        return name;
    }
    
    public String getAuthor() {
        return author;
    }
}
