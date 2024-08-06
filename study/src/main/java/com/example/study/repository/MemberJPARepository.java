package com.example.study.repository;

import com.example.study.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJPARepository extends JpaRepository <MemberEntity,String> {


    Long countByAuth(String auth);

    Page<MemberEntity> findByAuth(String auth, PageRequest pageable);
}
