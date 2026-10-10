package api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Book {

    private String isbn;
    private String title;
    private String subTitle;
    private String author;
    private String publisher;
    private String description;
    private String website;
    private int pages;

    @JsonProperty("publish_date")
    private String publishDate;

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDescription() {
        return description;
    }

    public String getWebsite() {
        return website;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public int getPages() {
        return pages;
    }
}