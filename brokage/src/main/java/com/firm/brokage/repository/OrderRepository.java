package com.firm.brokage.repository;

import com.firm.brokage.enums.Side;
import com.firm.brokage.enums.Status;
import com.firm.brokage.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("select t from Order t where t.customerId = :customerId")
    List<Order> findOrderByCustomerId(@Param("customerId") Long customerId);

    @Query("select t from Order t where t.customerId = :customerId and t.status = :status")
    List<Order> findOrderByCustIdAndStatus(@Param("customerId") Long customerId, @Param("status") Status status);

    @Query("select t from Order t where t.customerId = :customerId and t.side = :side")
    List<Order> findOrderByCustIdAndSide(@Param("customerId") Long customerId, @Param("side") Side side);

    @Query("select t from Order t where t.customerId = :customerId and t.createDate >= :startDate and  t.createDate <= :endDate")
    List<Order> findOrderByCustIdAndStartDtAndEndDt(@Param("customerId") Long customerId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
