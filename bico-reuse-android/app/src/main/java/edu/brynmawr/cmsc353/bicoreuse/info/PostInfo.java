package edu.brynmawr.cmsc353.bicoreuse.info;

import java.util.Date;

public class PostInfo {
    private String postId;
    private String postTitle;
    private String postDescription;
    private Integer postPrice;
    private Date postDate;
    private String postStatus;
    private UserInfo postSeller;

    public PostInfo(String id, String title, String description, Integer price,
                    Date date, String status, UserInfo seller) {

    }
}
