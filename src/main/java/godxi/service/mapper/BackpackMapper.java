package godxi.service.mapper;

import godxi.domain.*;
import godxi.service.dto.BackpackDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Backpack and its DTO BackpackDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface BackpackMapper extends EntityMapper<BackpackDTO, Backpack> {

    @Mapping(source = "product.id", target = "productId")
    BackpackDTO toDto(Backpack backpack);

    @Mapping(source = "productId", target = "product")
    Backpack toEntity(BackpackDTO backpackDTO);

    default Backpack fromId(Long id) {
        if (id == null) {
            return null;
        }
        Backpack backpack = new Backpack();
        backpack.setId(id);
        return backpack;
    }
}
