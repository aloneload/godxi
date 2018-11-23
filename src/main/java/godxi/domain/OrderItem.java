package godxi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 订单详情
 */
@ApiModel(description = "订单详情")
@Entity
@Table(name = "order_item")
public class OrderItem implements Serializable {

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
     * 产品原价
     */
    @NotNull
    @ApiModelProperty(value = "产品原价", required = true)
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

    /**
     * 设备ID
     */
    @NotNull
    @ApiModelProperty(value = "设备ID", required = true)
    @Column(name = "device_id", nullable = false)
    private String deviceId;

    /**
     * 经度
     */
    @NotNull
    @ApiModelProperty(value = "经度", required = true)
    @Column(name = "lon", nullable = false)
    private Double lon;

    /**
     * 纬度
     */
    @NotNull
    @ApiModelProperty(value = "纬度", required = true)
    @Column(name = "lat", nullable = false)
    private Double lat;

    /**
     * 国家
     */
    @NotNull
    @ApiModelProperty(value = "国家", required = true)
    @Column(name = "country", nullable = false)
    private String country;

    /**
     * 省份
     */
    @NotNull
    @ApiModelProperty(value = "省份", required = true)
    @Column(name = "province", nullable = false)
    private String province;

    /**
     * 城市
     */
    @NotNull
    @ApiModelProperty(value = "城市", required = true)
    @Column(name = "city", nullable = false)
    private String city;

    /**
     * 街道
     */
    @NotNull
    @ApiModelProperty(value = "街道", required = true)
    @Column(name = "street", nullable = false)
    private String street;

    /**
     * 详细地址
     */
    @NotNull
    @ApiModelProperty(value = "详细地址", required = true)
    @Column(name = "address", nullable = false)
    private String address;

    /**
     * 产品成交价格 打折后者优惠
     */
    @NotNull
    @ApiModelProperty(value = "产品成交价格 打折后者优惠", required = true)
    @Column(name = "current_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal currentPrice;

    /**
     * 商品数量
     */
    @NotNull
    @ApiModelProperty(value = "商品数量", required = true)
    @Column(name = "product_num", nullable = false)
    private Integer productNum;

    @OneToOne
    @JoinColumn(unique = true)
    private Product product;

    @OneToOne
    @JoinColumn(unique = true)
    private ProductOrder orders;

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

    public OrderItem type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaterialId() {
        return materialId;
    }

    public OrderItem materialId(String materialId) {
        this.materialId = materialId;
        return this;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getName() {
        return name;
    }

    public OrderItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public OrderItem originalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
        return this;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getDescription() {
        return description;
    }

    public OrderItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public OrderItem resourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
        return this;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public OrderItem deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Double getLon() {
        return lon;
    }

    public OrderItem lon(Double lon) {
        this.lon = lon;
        return this;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public OrderItem lat(Double lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getCountry() {
        return country;
    }

    public OrderItem country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public OrderItem province(String province) {
        this.province = province;
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public OrderItem city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public OrderItem street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public OrderItem address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public OrderItem currentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
        return this;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public OrderItem productNum(Integer productNum) {
        this.productNum = productNum;
        return this;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public Product getProduct() {
        return product;
    }

    public OrderItem product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductOrder getOrders() {
        return orders;
    }

    public OrderItem orders(ProductOrder productOrder) {
        this.orders = productOrder;
        return this;
    }

    public void setOrders(ProductOrder productOrder) {
        this.orders = productOrder;
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
        OrderItem orderItem = (OrderItem) o;
        if (orderItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderItem{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", materialId='" + getMaterialId() + "'" +
            ", name='" + getName() + "'" +
            ", originalPrice=" + getOriginalPrice() +
            ", description='" + getDescription() + "'" +
            ", resourceUrl='" + getResourceUrl() + "'" +
            ", deviceId='" + getDeviceId() + "'" +
            ", lon=" + getLon() +
            ", lat=" + getLat() +
            ", country='" + getCountry() + "'" +
            ", province='" + getProvince() + "'" +
            ", city='" + getCity() + "'" +
            ", street='" + getStreet() + "'" +
            ", address='" + getAddress() + "'" +
            ", currentPrice=" + getCurrentPrice() +
            ", productNum=" + getProductNum() +
            "}";
    }
}
