package godxi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * 素材模板
 */
@ApiModel(description = "素材模板")
@Entity
@Table(name = "material")
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 玩法或者活动分类
     */
    @NotNull
    @ApiModelProperty(value = "玩法或者活动分类", required = true)
    @Column(name = "jhi_type", nullable = false)
    private String type;

    /**
     * 素材id
     */
    @NotNull
    @ApiModelProperty(value = "素材id", required = true)
    @Column(name = "material_id", nullable = false)
    private String materialId;

    /**
     * 素材名称
     */
    @NotNull
    @ApiModelProperty(value = "素材名称", required = true)
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 产品定价
     */
    @NotNull
    @ApiModelProperty(value = "产品定价", required = true)
    @Column(name = "original_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal originalPrice;

    /**
     * 产品描述
     */
    @NotNull
    @ApiModelProperty(value = "产品描述", required = true)
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * 资源url
     */
    @NotNull
    @ApiModelProperty(value = "资源url", required = true)
    @Column(name = "resource_url", nullable = false)
    private String resourceUrl;

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

    public String getType() {
        return type;
    }

    public Material type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaterialId() {
        return materialId;
    }

    public Material materialId(String materialId) {
        this.materialId = materialId;
        return this;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getName() {
        return name;
    }

    public Material name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public Material originalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
        return this;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getDescription() {
        return description;
    }

    public Material description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public Material resourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
        return this;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Material createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public Material updateDate(Instant updateDate) {
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
        Material material = (Material) o;
        if (material.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), material.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Material{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", materialId='" + getMaterialId() + "'" +
            ", name='" + getName() + "'" +
            ", originalPrice=" + getOriginalPrice() +
            ", description='" + getDescription() + "'" +
            ", resourceUrl='" + getResourceUrl() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
