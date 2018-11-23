package godxi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 我的背包
 */
@ApiModel(description = "我的背包")
@Entity
@Table(name = "backpack")
public class Backpack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * userId
     */
    @NotNull
    @ApiModelProperty(value = "userId", required = true)
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    /**
     * 总数量
     */
    @NotNull
    @ApiModelProperty(value = "总数量", required = true)
    @Column(name = "total_num", nullable = false)
    private Integer totalNum;

    /**
     * 使用的数量
     */
    @NotNull
    @ApiModelProperty(value = "使用的数量", required = true)
    @Column(name = "used_num", nullable = false)
    private Integer usedNum;

    /**
     * 未使用的数量
     */
    @NotNull
    @ApiModelProperty(value = "未使用的数量", required = true)
    @Column(name = "unused_num", nullable = false)
    private Integer unusedNum;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private Instant createDate;

    @NotNull
    @Column(name = "update_date", nullable = false)
    private Instant updateDate;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Product product;

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

    public Backpack userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public Backpack totalNum(Integer totalNum) {
        this.totalNum = totalNum;
        return this;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getUsedNum() {
        return usedNum;
    }

    public Backpack usedNum(Integer usedNum) {
        this.usedNum = usedNum;
        return this;
    }

    public void setUsedNum(Integer usedNum) {
        this.usedNum = usedNum;
    }

    public Integer getUnusedNum() {
        return unusedNum;
    }

    public Backpack unusedNum(Integer unusedNum) {
        this.unusedNum = unusedNum;
        return this;
    }

    public void setUnusedNum(Integer unusedNum) {
        this.unusedNum = unusedNum;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Backpack createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public Backpack updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public Product getProduct() {
        return product;
    }

    public Backpack product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        Backpack backpack = (Backpack) o;
        if (backpack.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), backpack.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Backpack{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", totalNum=" + getTotalNum() +
            ", usedNum=" + getUsedNum() +
            ", unusedNum=" + getUnusedNum() +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
