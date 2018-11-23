package godxi.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Work entity.
 */
public class WorkDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer userId;

    @NotNull
    private String name;

    @NotNull
    private String title;

    @NotNull
    private String coverUrl;

    @NotNull
    private String videoUrl;

    @NotNull
    private Instant duration;

    @NotNull
    private String categories;

    @NotNull
    private String labels;

    @NotNull
    private String description;

    @NotNull
    private Instant createDate;

    @NotNull
    private Instant updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Instant getDuration() {
        return duration;
    }

    public void setDuration(Instant duration) {
        this.duration = duration;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkDTO workDTO = (WorkDTO) o;
        if (workDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", name='" + getName() + "'" +
            ", title='" + getTitle() + "'" +
            ", coverUrl='" + getCoverUrl() + "'" +
            ", videoUrl='" + getVideoUrl() + "'" +
            ", duration='" + getDuration() + "'" +
            ", categories='" + getCategories() + "'" +
            ", labels='" + getLabels() + "'" +
            ", description='" + getDescription() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
