package godxi.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the GodxiLicense entity.
 */
public class GodxiLicenseDTO implements Serializable {

    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String deviceId;

    @NotNull
    private Instant issuedDate;

    @NotNull
    private Instant expiredDate;

    @NotNull
    private String version;

    @NotNull
    private Boolean valid;

    @NotNull
    @Size(max = 10000)
    private String licenseKey;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Instant getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Instant issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Instant getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Instant expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean isValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public void setLicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
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

        GodxiLicenseDTO godxiLicenseDTO = (GodxiLicenseDTO) o;
        if (godxiLicenseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), godxiLicenseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GodxiLicenseDTO{" +
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
