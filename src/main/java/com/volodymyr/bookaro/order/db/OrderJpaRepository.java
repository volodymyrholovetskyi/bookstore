package com.volodymyr.bookaro.order.db;

import com.volodymyr.bookaro.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {


}
