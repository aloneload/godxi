package godxi.service.mapper;

import godxi.domain.*;
import godxi.service.dto.MaterialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Material and its DTO MaterialDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MaterialMapper extends EntityMapper<MaterialDTO, Material> {



    default Material fromId(Long id) {
        if (id == null) {
            return null;
        }
        Material material = new Material();
        material.setId(id);
        return material;
    }
}
