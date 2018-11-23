package godxi.service.mapper;

import godxi.domain.*;
import godxi.service.dto.PavilionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pavilion and its DTO PavilionDTO.
 */
@Mapper(componentModel = "spring", uses = {GodxiLicenseMapper.class})
public interface PavilionMapper extends EntityMapper<PavilionDTO, Pavilion> {

    @Mapping(source = "godxiLicense.id", target = "godxiLicenseId")
    PavilionDTO toDto(Pavilion pavilion);

    @Mapping(source = "godxiLicenseId", target = "godxiLicense")
    Pavilion toEntity(PavilionDTO pavilionDTO);

    default Pavilion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pavilion pavilion = new Pavilion();
        pavilion.setId(id);
        return pavilion;
    }
}
