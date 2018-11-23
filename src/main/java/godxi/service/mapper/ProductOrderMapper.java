package godxi.service.mapper;

import godxi.domain.*;
import godxi.service.dto.ProductOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductOrder and its DTO ProductOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductOrderMapper extends EntityMapper<ProductOrderDTO, ProductOrder> {



    default ProductOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductOrder productOrder = new ProductOrder();
        productOrder.setId(id);
        return productOrder;
    }
}
