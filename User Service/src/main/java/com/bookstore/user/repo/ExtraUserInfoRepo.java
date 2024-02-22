package com.bookstore.user.repo;

import com.bookstore.user.model.ExtraUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExtraUserInfoRepo extends JpaRepository<ExtraUserInfo , Long> {
   @Query("""
        SELECT E From ExtraUserInfo E WHERE E.isCustomer = true     
    """)
   List<ExtraUserInfo> findAllCustomers();

    @Query("""
        SELECT E From ExtraUserInfo E WHERE E.isCustomer = false     
    """)
    List<ExtraUserInfo> findAllAdmins();

    @Query("""
        SELECT E From ExtraUserInfo E WHERE E.userId = :userId 
    """)
    Optional<ExtraUserInfo> findByUserId(@Param("userId") String userId);
}
