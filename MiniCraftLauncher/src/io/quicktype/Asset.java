package io.quicktype;

import com.fasterxml.jackson.annotation.*;
import java.time.OffsetDateTime;

public class Asset {
    private String browserDownloadURL;
    private String contentType;
    private OffsetDateTime createdAt;
    private long downloadCount;
    private long id;
    private Object label;
    private String name;
    private String nodeID;
    private long size;
    private String state;
    private OffsetDateTime updatedAt;
    private Author uploader;
    private String url;

    @JsonProperty("browser_download_url")
    public String getBrowserDownloadURL() { return browserDownloadURL; }
    @JsonProperty("browser_download_url")
    public void setBrowserDownloadURL(String value) { this.browserDownloadURL = value; }

    @JsonProperty("content_type")
    public String getContentType() { return contentType; }
    @JsonProperty("content_type")
    public void setContentType(String value) { this.contentType = value; }

    @JsonProperty("created_at")
    public OffsetDateTime getCreatedAt() { return createdAt; }
    @JsonProperty("created_at")
    public void setCreatedAt(OffsetDateTime value) { this.createdAt = value; }

    @JsonProperty("download_count")
    public long getDownloadCount() { return downloadCount; }
    @JsonProperty("download_count")
    public void setDownloadCount(long value) { this.downloadCount = value; }

    @JsonProperty("id")
    public long getID() { return id; }
    @JsonProperty("id")
    public void setID(long value) { this.id = value; }

    @JsonProperty("label")
    public Object getLabel() { return label; }
    @JsonProperty("label")
    public void setLabel(Object value) { this.label = value; }

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("node_id")
    public String getNodeID() { return nodeID; }
    @JsonProperty("node_id")
    public void setNodeID(String value) { this.nodeID = value; }

    @JsonProperty("size")
    public long getSize() { return size; }
    @JsonProperty("size")
    public void setSize(long value) { this.size = value; }

    @JsonProperty("state")
    public String getState() { return state; }
    @JsonProperty("state")
    public void setState(String value) { this.state = value; }

    @JsonProperty("updated_at")
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    @JsonProperty("updated_at")
    public void setUpdatedAt(OffsetDateTime value) { this.updatedAt = value; }

    @JsonProperty("uploader")
    public Author getUploader() { return uploader; }
    @JsonProperty("uploader")
    public void setUploader(Author value) { this.uploader = value; }

    @JsonProperty("url")
    public String getURL() { return url; }
    @JsonProperty("url")
    public void setURL(String value) { this.url = value; }
}
