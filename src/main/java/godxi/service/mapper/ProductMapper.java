package godxi.service.mapper;

import godxi.domain.*;
import godxi.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {PavilionMapper.class, MaterialMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "pavilion.id", target = "pavilionId")
    @Mapping(source = "material.id", target = "materialId")
    ProductDTO toDto(Product product);

    @Mapping(source = "pavilionId", target = "pavilion")
    @Mapping(source = "materialId", target = "material")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
