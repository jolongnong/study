package com.example.study.entity;


import com.example.study.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "member")
public class MemberEntity {
    @Id
    private String id;
    private String pw;
    private String address;
    private String phone;
    private String email;
    private String profile;
    private String auth;
    @CreationTimestamp
    private Date reg;


    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id",insertable = false,updatable = false)
    private MemberInfoEntity memberInfo;


    @Builder
    public MemberEntity(String id, String pw, String address, String phone, String email, String profile, String auth, Date reg){
        super();
        this.id=id;
        this.pw=pw;
        this.address=address;
        this.phone=phone;
        this.email=email;
        this.profile=profile;
        this.auth=auth;
        this.reg=reg;
    }

    public MemberDTO toMemberDTO(){
        return MemberDTO.builder()
                .id(this.id)
                .pw(this.pw)
                .address(this.address)
                .phone(this.phone)
                .email(this.email)
                .profile(this.profile)
                .auth(this.auth)
                .reg(this.reg)
                .build();
    }

}
