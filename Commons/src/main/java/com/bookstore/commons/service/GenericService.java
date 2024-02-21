package com.bookstore.commons.service;

import com.bookstore.commons.model.jpa.AuditorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public class GenericService<T extends AuditorEntity> {

    private final JpaRepository jpaRepository;

    public GenericService(JpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public List<T> findAll(){
        return jpaRepository.findAll();
    }

    public void saveEntity(T entity){
        jpaRepository.save(entity);
    }

    public void updateEntity(T entity){
        jpaRepository.save(entity);
    }

    public void deleteEntityById(Long id){
        jpaRepository.deleteById(id);
    }

}
