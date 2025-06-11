package com.mm.mimo.entity;

import com.mm.mimo.enums.OAuthProvider;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "oauth_accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OauthAccount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Enumerated(EnumType.STRING)
    private OAuthProvider provider;

    @Column(name = "provider_id")
    private String providerId;


} 