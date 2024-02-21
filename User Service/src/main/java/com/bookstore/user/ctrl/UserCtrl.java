package com.bookstore.user.ctrl;

import com.bookstore.commons.ctrl.GenericCtrl;
import com.bookstore.commons.model.RegisterRequest;
import com.bookstore.commons.service.GenericService;
import com.bookstore.user.model.ExtraUserInfo;
import com.bookstore.user.records.UserRequest;
import com.bookstore.user.service.ExtraUserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserCtrl {
    private final ExtraUserInfoService extraUserInfoService;

    @PostMapping
    public ResponseEntity<?> saveCustomer(@RequestBody RegisterRequest registerRequest,
                                          @RequestParam(name = "user-id") String userId){
        extraUserInfoService.save(registerRequest ,userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/customer")
    public ResponseEntity<List<ExtraUserInfo>> findAllCustomers(){
        return ResponseEntity.ok(extraUserInfoService.findAllCustomers());
    }

    @GetMapping("/admin")
    public ResponseEntity<List<ExtraUserInfo>> findAllAdmins(){
        return ResponseEntity.ok(extraUserInfoService.findAllAdmins());
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest , @RequestParam(name = "user-id") String userId){
        extraUserInfoService.updateUser(userRequest , userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
