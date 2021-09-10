package com.example.android.readit;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BookInfo {

    private String title;
    private String subtitle;
    private String description;
    private String infoLink;
    private String previewLink;
    private ArrayList<String> authors;
    private String publisher;
    private String publishedDate;
    private int pageCount;
    private String thumbnail;
    private String buyLink;

    public BookInfo (@Nullable  String title, @Nullable String subtitle, @Nullable String description,@Nullable String infoLink,@Nullable String previewLink
            ,@Nullable ArrayList<String> authors,@Nullable String publisher,
                     @Nullable  String publishedDate,@Nullable int pageCount
            ,@Nullable String thumbnail,@Nullable String buyLink) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.infoLink = infoLink;
        this.previewLink = previewLink;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.pageCount = pageCount;
        this.thumbnail = thumbnail;
        this.buyLink = buyLink;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDescription() {
        return description;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getBuyLink() {
        return buyLink;
    }
}
