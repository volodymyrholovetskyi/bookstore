package com.volodymyr.bookaro.order.db;

import com.volodymyr.bookaro.order.domain.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipientJpaRepository extends JpaRepository<Recipient, Long> {

    Optional<Recipient> findByEmailIgnoreCase(String email);
}
