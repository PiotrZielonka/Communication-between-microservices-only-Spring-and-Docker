package com.task.credit.service.mapper;

import com.task.credit.domain.Credit;
import com.task.credit.service.dto.CreditCustomerProductDto;
import com.task.credit.service.dto.CreditDto;
import com.task.customer.service.mapper.CustomerMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface CreditMapper extends EntityMapper<CreditDto, Credit> {

  CreditDto toDto(Credit s);

  CreditCustomerProductDto toDtoCreditCustomerProductDto(Credit s);
}
