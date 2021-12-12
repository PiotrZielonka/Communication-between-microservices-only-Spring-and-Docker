package com.task.credit.repository;

import com.task.credit.domain.Credit;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends CrudRepository<Credit, Long> {

  List<Credit> findAll();

}
