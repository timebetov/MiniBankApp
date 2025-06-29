package com.github.timebetov.accounts.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String ownerFirstName;

    @Column(nullable = false)
    private String ownerLastName;
    private Long balance;

    @Column(nullable = false)
    private String keycloakUserId;
}
