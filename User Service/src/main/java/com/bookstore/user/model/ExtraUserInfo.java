package com.bookstore.user.model;

import com.bookstore.commons.model.jpa.AuditorEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtraUserInfo extends AuditorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false , unique = true)
    private String userId;
    @Column(nullable = false)
    private String firstname;
    private String lastname;
    private String address;
    private String phone;
    private Boolean isCustomer;
}
