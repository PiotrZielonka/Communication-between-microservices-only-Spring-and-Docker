package com.task.product.service.mapper;

import com.task.product.domain.Product;
import com.task.product.service.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper extends EntityMapper<ProductDto, Product> {

  ProductDto toDto(Product s);

}
