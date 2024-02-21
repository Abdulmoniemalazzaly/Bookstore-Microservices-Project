package com.bookstore.user.service;

import com.bookstore.commons.model.RegisterRequest;
import com.bookstore.commons.service.GenericService;
import com.bookstore.user.model.ExtraUserInfo;
import com.bookstore.user.records.UserRequest;
import com.bookstore.user.repo.ExtraUserInfoRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExtraUserInfoService {

    private final ExtraUserInfoRepo extraUserInfoRepo;

    public void save(RegisterRequest registerRequest , String userId){
        ExtraUserInfo userInfo = ExtraUserInfo.builder()
                .address(registerRequest.address())
                .firstname(registerRequest.firstname())
                .lastname(registerRequest.lastname())
                .phone(registerRequest.phone())
                .userId(userId)
                .isCustomer(true)
                .build();
        extraUserInfoRepo.save(userInfo);
    }

    public List<ExtraUserInfo> findAllCustomers(){
        return extraUserInfoRepo.findAllCustomers();
    }

    public List<ExtraUserInfo> findAllAdmins(){
        return extraUserInfoRepo.findAllAdmins();
    }

    public void updateUser(UserRequest userRequest, String userId) {
        ExtraUserInfo userInfo = extraUserInfoRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with Id " + userId));
        userInfo.setFirstname(userRequest.firstname());
        userInfo.setLastname(userRequest.lastname());
        userInfo.setAddress(userRequest.address());
        userInfo.setPhone(userRequest.phone());
        extraUserInfoRepo.save(userInfo);
    }
}
