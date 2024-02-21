package com.bookstore.commons.ctrl;

import com.bookstore.commons.model.jpa.AuditorEntity;
import com.bookstore.commons.model.jpa.Authority;
import com.bookstore.commons.model.jpa.Role;
import com.bookstore.commons.service.GenericService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

public class GenericCtrl<T extends AuditorEntity> {

    private final GenericService<T> genericService;

    public GenericCtrl(GenericService<T> genericService) {
        this.genericService = genericService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole(#this.this.retrieveRoles) and hasAnyAuthority(#this.this.retrieveAuthorities)")
    public ResponseEntity<List<T>> findAll(){
        return ResponseEntity.ok(genericService.findAll());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole(#this.this.createRoles) and hasAnyAuthority(#this.this.createAuthorities)")
    public ResponseEntity<?> saveEntity(@RequestBody T entity){
        genericService.saveEntity(entity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    @PreAuthorize("hasAnyRole(#this.this.updateRoles) and hasAnyAuthority(#this.this.updateAuthorities)")
    public ResponseEntity<?> updateEntity(@RequestBody T entity){
        genericService.updateEntity(entity);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole(#this.this.deleteRoles) and hasAnyAuthority(#this.this.deleteAuthorities)")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") Long id){
        genericService.deleteEntityById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public List<String> getRetrieveRoles() {
        return Collections.singletonList(Role.ROLES_NAMES.ROLE_USER.getValue());
    }

    public List<String> getRetrieveAuthorities() {
        return Collections.singletonList(Authority.AUTHORITY_NAMES.READ_AUTHORITY.getValue());
    }

    public List<String> getCreateRoles() {
        return Collections.singletonList(Role.ROLES_NAMES.ROLE_ADMIN.getValue());
    }

    public List<String> getCreateAuthorities() {
        return Collections.singletonList(Authority.AUTHORITY_NAMES.CREATE_AUTHORITY.getValue());
    }

    public List<String> getUpdateRoles() {
        return Collections.singletonList(Role.ROLES_NAMES.ROLE_ADMIN.getValue());
    }

    public List<String> getUpdateAuthorities() {
        return Collections.singletonList(Authority.AUTHORITY_NAMES.EDIT_AUTHORITY.getValue());
    }

    public List<String> getDeleteRoles() {
        return Collections.singletonList(Role.ROLES_NAMES.ROLE_ADMIN.getValue());
    }

    public List<String> getDeleteAuthorities() {
        return Collections.singletonList(Authority.AUTHORITY_NAMES.DELETE_AUTHORITY.getValue());
    }
}
