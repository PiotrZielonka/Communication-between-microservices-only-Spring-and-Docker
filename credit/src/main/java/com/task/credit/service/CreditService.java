package com.task.credit.service;

import com.task.credit.service.dto.CreditCustomerProductDto;
import com.task.credit.service.dto.CreditDto;
import java.util.List;

public interface CreditService {

  CreditDto save(CreditCustomerProductDto creditCustomerProductDto);

  List<CreditCustomerProductDto> findAll();

  List<CreditDto> findAllOnlyCredits();
}
