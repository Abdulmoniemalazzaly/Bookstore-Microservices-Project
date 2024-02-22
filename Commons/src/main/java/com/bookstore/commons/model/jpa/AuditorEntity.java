package com.bookstore.commons.model.jpa;

import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditorEntity {


    @CreatedDate
    @Column(name = "CreatedOn")
    private LocalDateTime createdOn;

    @CreatedBy
    @Column(name = "CreatedBy")
    private String createdBy;


    @LastModifiedDate
    @Column(name = "UpdatedOn")
    private LocalDateTime updatedOn;

    @LastModifiedBy
    @Column(name = "UpdatedBy")
    private String updatedBy;

}
