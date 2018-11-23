package godxi.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Backpack entity.
 */
public class BackpackDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer totalNum;

    @NotNull
    private Integer usedNum;

    @NotNull
    private Integer unusedNum;

    @NotNull
    private Instant createDate;

    @NotNull
    private Instant updateDate;

    private Long productId;

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

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getUsedNum() {
        return usedNum;
    }

    public void setUsedNum(Integer usedNum) {
        this.usedNum = usedNum;
    }

    public Integer getUnusedNum() {
        return unusedNum;
    }

    public void setUnusedNum(Integer unusedNum) {
        this.unusedNum = unusedNum;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BackpackDTO backpackDTO = (BackpackDTO) o;
        if (backpackDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), backpackDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BackpackDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", totalNum=" + getTotalNum() +
            ", usedNum=" + getUsedNum() +
            ", unusedNum=" + getUnusedNum() +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", product=" + getProductId() +
            "}";
    }
}
