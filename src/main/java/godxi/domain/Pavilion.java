package godxi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 亭子
 */
@ApiModel(description = "亭子")
@Entity
@Table(name = "pavilion")
public class Pavilion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @NotNull
    @Column(name = "create_date", nullable = false)
    private Instant createDate;

    @NotNull
    @Column(name = "update_date", nullable = false)
    private Instant updateDate;

    @OneToOne
    @JoinColumn(unique = true)
    private GodxiLicense godxiLicense;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Pavilion deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Double getLon() {
        return lon;
    }

    public Pavilion lon(Double lon) {
        this.lon = lon;
        return this;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public Pavilion lat(Double lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getCountry() {
        return country;
    }

    public Pavilion country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public Pavilion province(String province) {
        this.province = province;
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public Pavilion city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public Pavilion street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public Pavilion address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Pavilion createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public Pavilion updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public GodxiLicense getGodxiLicense() {
        return godxiLicense;
    }

    public Pavilion godxiLicense(GodxiLicense godxiLicense) {
        this.godxiLicense = godxiLicense;
        return this;
    }

    public void setGodxiLicense(GodxiLicense godxiLicense) {
        this.godxiLicense = godxiLicense;
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
        Pavilion pavilion = (Pavilion) o;
        if (pavilion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pavilion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pavilion{" +
            "id=" + getId() +
            ", deviceId='" + getDeviceId() + "'" +
            ", lon=" + getLon() +
            ", lat=" + getLat() +
            ", country='" + getCountry() + "'" +
            ", province='" + getProvince() + "'" +
            ", city='" + getCity() + "'" +
            ", street='" + getStreet() + "'" +
            ", address='" + getAddress() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
