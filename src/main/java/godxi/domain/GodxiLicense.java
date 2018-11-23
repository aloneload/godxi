package godxi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 许可证
 */
@ApiModel(description = "许可证")
@Entity
@Table(name = "godxi_license")
public class GodxiLicense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * license 注册邮箱
     */
    @NotNull
    @ApiModelProperty(value = "license 注册邮箱", required = true)
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * 设备ID
     */
    @NotNull
    @ApiModelProperty(value = "设备ID", required = true)
    @Column(name = "device_id", nullable = false)
    private String deviceId;

    /**
     * 签发日期
     */
    @NotNull
    @ApiModelProperty(value = "签发日期", required = true)
    @Column(name = "issued_date", nullable = false)
    private Instant issuedDate;

    /**
     * 到期日期
     */
    @NotNull
    @ApiModelProperty(value = "到期日期", required = true)
    @Column(name = "expired_date", nullable = false)
    private Instant expiredDate;

    /**
     * 版本
     */
    @NotNull
    @ApiModelProperty(value = "版本", required = true)
    @Column(name = "version", nullable = false)
    private String version;

    /**
     * 是否有效
     */
    @NotNull
    @ApiModelProperty(value = "是否有效", required = true)
    @Column(name = "valid", nullable = false)
    private Boolean valid;

    /**
     * licenseKey
     */
    @NotNull
    @Size(max = 10000)
    @ApiModelProperty(value = "licenseKey", required = true)
    @Column(name = "license_key", length = 10000, nullable = false)
    private String licenseKey;

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

    public String getEmail() {
        return email;
    }

    public GodxiLicense email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public GodxiLicense deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Instant getIssuedDate() {
        return issuedDate;
    }

    public GodxiLicense issuedDate(Instant issuedDate) {
        this.issuedDate = issuedDate;
        return this;
    }

    public void setIssuedDate(Instant issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Instant getExpiredDate() {
        return expiredDate;
    }

    public GodxiLicense expiredDate(Instant expiredDate) {
        this.expiredDate = expiredDate;
        return this;
    }

    public void setExpiredDate(Instant expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getVersion() {
        return version;
    }

    public GodxiLicense version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean isValid() {
        return valid;
    }

    public GodxiLicense valid(Boolean valid) {
        this.valid = valid;
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public GodxiLicense licenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
        return this;
    }

    public void setLicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public GodxiLicense createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public GodxiLicense updateDate(Instant updateDate) {
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
        GodxiLicense godxiLicense = (GodxiLicense) o;
        if (godxiLicense.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), godxiLicense.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GodxiLicense{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", deviceId='" + getDeviceId() + "'" +
            ", issuedDate='" + getIssuedDate() + "'" +
            ", expiredDate='" + getExpiredDate() + "'" +
            ", version='" + getVersion() + "'" +
            ", valid='" + isValid() + "'" +
            ", licenseKey='" + getLicenseKey() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
