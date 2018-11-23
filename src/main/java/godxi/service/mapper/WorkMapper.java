package godxi.service.mapper;

import godxi.domain.*;
import godxi.service.dto.WorkDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Work and its DTO WorkDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkMapper extends EntityMapper<WorkDTO, Work> {



    default Work fromId(Long id) {
        if (id == null) {
            return null;
        }
        Work work = new Work();
        work.setId(id);
        return work;
    }
}
