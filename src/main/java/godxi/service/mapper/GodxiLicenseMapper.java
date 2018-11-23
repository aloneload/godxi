package godxi.service.mapper;

import godxi.domain.*;
import godxi.service.dto.GodxiLicenseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GodxiLicense and its DTO GodxiLicenseDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GodxiLicenseMapper extends EntityMapper<GodxiLicenseDTO, GodxiLicense> {



    default GodxiLicense fromId(Long id) {
        if (id == null) {
            return null;
        }
        GodxiLicense godxiLicense = new GodxiLicense();
        godxiLicense.setId(id);
        return godxiLicense;
    }
}
