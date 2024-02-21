package com.bookstore.auth.model.jpa;

import com.bookstore.auth.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "User_Token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private String ipAddress;
    private String remoteIpAddress;
    private boolean expired;
    private boolean revoked;
    private String userAgnet;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Token(Boolean revoked){
        this.revoked = revoked;
    }
}
