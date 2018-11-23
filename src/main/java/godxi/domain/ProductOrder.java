package godxi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import godxi.domain.enumeration.OrderStatus;

/**
 * 订单
 */
@ApiModel(description = "订单")
@Entity
@Table(name = "product_order")
public class ProductOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * UID
     */
    @NotNull
    @ApiModelProperty(value = "UID", required = true)
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    /**
     * 订单状态
     */
    @NotNull
    @ApiModelProperty(value = "订单状态", required = true)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    /**
     * 订单总金额
     */
    @NotNull
    @ApiModelProperty(value = "订单总金额", required = true)
    @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    /**
     * 支付时间
     */
    @NotNull
    @ApiModelProperty(value = "支付时间", required = true)
    @Column(name = "pay_date", nullable = false)
    private Instant payDate;

    /**
     * 取消时间
     */
    @NotNull
    @ApiModelProperty(value = "取消时间", required = true)
    @Column(name = "cancel_date", nullable = false)
    private Instant cancelDate;

    /**
     * 创建时间
     */
    @NotNull
    @ApiModelProperty(value = "创建时间", required = true)
    @Column(name = "create_date", nullable = false)
    private Instant createDate;

    /**
     * 更新时间
     */
    @NotNull
    @ApiModelProperty(value = "更新时间", required = true)
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

    public ProductOrder userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public ProductOrder status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public ProductOrder totalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Instant getPayDate() {
        return payDate;
    }

    public ProductOrder payDate(Instant payDate) {
        this.payDate = payDate;
        return this;
    }

    public void setPayDate(Instant payDate) {
        this.payDate = payDate;
    }

    public Instant getCancelDate() {
        return cancelDate;
    }

    public ProductOrder cancelDate(Instant cancelDate) {
        this.cancelDate = cancelDate;
        return this;
    }

    public void setCancelDate(Instant cancelDate) {
        this.cancelDate = cancelDate;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public ProductOrder createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public ProductOrder updateDate(Instant updateDate) {
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
        ProductOrder productOrder = (ProductOrder) o;
        if (productOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductOrder{" +
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
