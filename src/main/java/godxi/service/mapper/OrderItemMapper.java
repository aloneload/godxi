package godxi.service.mapper;

import godxi.domain.*;
import godxi.service.dto.OrderItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderItem and its DTO OrderItemDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class, ProductOrderMapper.class})
public interface OrderItemMapper extends EntityMapper<OrderItemDTO, OrderItem> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "orders.id", target = "ordersId")
    OrderItemDTO toDto(OrderItem orderItem);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "ordersId", target = "orders")
    OrderItem toEntity(OrderItemDTO orderItemDTO);

    default OrderItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        return orderItem;
    }
}
