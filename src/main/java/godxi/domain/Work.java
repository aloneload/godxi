package godxi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 作品
 */
@ApiModel(description = "作品")
@Entity
@Table(name = "work")
public class Work implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Uid
     */
    @NotNull
    @ApiModelProperty(value = "Uid", required = true)
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    /**
     * 作品的名字
     */
    @NotNull
    @ApiModelProperty(value = "作品的名字", required = true)
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 作品标题
     */
    @NotNull
    @ApiModelProperty(value = "作品标题", required = true)
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * 视频封面
     */
    @NotNull
    @ApiModelProperty(value = "视频封面", required = true)
    @Column(name = "cover_url", nullable = false)
    private String coverUrl;

    /**
     * 视频
     */
    @NotNull
    @ApiModelProperty(value = "视频", required = true)
    @Column(name = "video_url", nullable = false)
    private String videoUrl;

    /**
     * 有效时间，整数，单位秒
     */
    @NotNull
    @ApiModelProperty(value = "有效时间，整数，单位秒", required = true)
    @Column(name = "duration", nullable = false)
    private Instant duration;

    /**
     * 分类
     */
    @NotNull
    @ApiModelProperty(value = "分类", required = true)
    @Column(name = "categories", nullable = false)
    private String categories;

    /**
     * 标签
     */
    @NotNull
    @ApiModelProperty(value = "标签", required = true)
    @Column(name = "labels", nullable = false)
    private String labels;

    /**
     * 详细描述
     */
    @NotNull
    @ApiModelProperty(value = "详细描述", required = true)
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private Instant createDate;

    @NotNull
    @Column(name = "update_date", nullable = false)
    private Instant updateDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Work userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public Work name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public Work title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public Work coverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
        return this;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Work videoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Instant getDuration() {
        return duration;
    }

    public Work duration(Instant duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Instant duration) {
        this.duration = duration;
    }

    public String getCategories() {
        return categories;
    }

    public Work categories(String categories) {
        this.categories = categories;
        return this;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getLabels() {
        return labels;
    }

    public Work labels(String labels) {
        this.labels = labels;
        return this;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getDescription() {
        return description;
    }

    public Work description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Work createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public Work updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Work work = (Work) o;
        if (work.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), work.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Work{" +
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
