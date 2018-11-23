package godxi.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import godxi.domain.enumeration.OrderStatus;

/**
 * A DTO for the ProductOrder entity.
 */
public class ProductOrderDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer userId;

    @NotNull
    private OrderStatus status;

    @NotNull
    private BigDecimal totalAmount;

    @NotNull
    private Instant payDate;

    @NotNull
    private Instant cancelDate;

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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Instant getPayDate() {
        return payDate;
    }

    public void setPayDate(Instant payDate) {
        this.payDate = payDate;
    }

    public Instant getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Instant cancelDate) {
        this.cancelDate = cancelDate;
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

        ProductOrderDTO productOrderDTO = (ProductOrderDTO) o;
        if (productOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductOrderDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", status='" + getStatus() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", payDate='" + getPayDate() + "'" +
            ", cancelDate='" + getCancelDate() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
