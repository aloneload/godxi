package godxi.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the OrderItem entity.
 */
public class OrderItemDTO implements Serializable {

    private Long id;

    @NotNull
    private String type;

    @NotNull
    private String materialId;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal originalPrice;

    @NotNull
    private String description;

    @NotNull
    private String resourceUrl;

    @NotNull
    private String deviceId;

    @NotNull
    private Double lon;

    @NotNull
    private Double lat;

    @NotNull
    private String country;

    @NotNull
    private String province;

    @NotNull
    private String city;

    @NotNull
    private String street;

    @NotNull
    private String address;

    @NotNull
    private BigDecimal currentPrice;

    @NotNull
    private Integer productNum;

    private Long productId;

    private Long ordersId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Long productOrderId) {
        this.ordersId = productOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderItemDTO orderItemDTO = (OrderItemDTO) o;
        if (orderItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
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
            ", product=" + getProductId() +
            ", orders=" + getOrdersId() +
            "}";
    }
}
