package com.practicum.cash.repository;

import com.practicum.cash.model.OutboxEvent;
import com.practicum.cash.model.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutboxRepository extends JpaRepository<OutboxEvent, Long> {
    List<OutboxEvent> findByStatus(OutboxStatus status);
    default List<OutboxEvent> findPendingEvents() {
        return findByStatus(OutboxStatus.PENDING);
    }


}
