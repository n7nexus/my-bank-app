package com.practicum.accounts.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "account_id", length = 100)
    private String accountId;
    @Column(name = "full_name", length = 250)
    private String fullName;
    @Column(name = "birthdate")
    private LocalDate birthdate;
    @Builder.Default
    @Column(name = "money", nullable = false, precision = 19, scale = 2)
    private BigDecimal money = BigDecimal.ZERO;

}
