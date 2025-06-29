package com.github.timebetov.transactions.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity @Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fromAccount;

    @Column(nullable = false)
    private String toAccount;

    @Column(nullable = false)
    private Long amount;

    @Column(length = 50)
    private String message;

    @Column(nullable = false, updatable = false)
    private LocalDateTime transferredAt;
}
