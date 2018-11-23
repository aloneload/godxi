package godxi.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Pavilion entity.
 */
public class PavilionDTO implements Serializable {

    private Long id;

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
    private Instant createDate;

    @NotNull
    private Instant updateDate;

    private Long godxiLicenseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getGodxiLicenseId() {
        return godxiLicenseId;
    }

    public void setGodxiLicenseId(Long godxiLicenseId) {
        this.godxiLicenseId = godxiLicenseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PavilionDTO pavilionDTO = (PavilionDTO) o;
        if (pavilionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pavilionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PavilionDTO{" +
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
            ", godxiLicense=" + getGodxiLicenseId() +
            "}";
    }
}
