package com.example.study.entity;

import com.example.study.dto.MemberInfoDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "member_info")
public class MemberInfoEntity {

    @Id
    private String id;
    private String name;
    private String birth;
    private String height;
    private String weight;
    private String health;
    private int gender;

    @Builder
    public MemberInfoEntity(String id, String name, String birth, String height, String weight, String health, int gender){
        super();
        this.id=id;
        this.name=name;
        this.birth=birth;
        this.height=height;
        this.weight=weight;
        this.health=health;
        this.gender=gender;
    }
    public MemberInfoDTO toMemberInfoDTO(){
        return MemberInfoDTO.builder()
                .id(this.id)
                .name(this.name)
                .birth(this.birth)
                .height(this.height)
                .weight(this.weight)
                .health(this.health)
                .gender(this.gender)
                .build();

    }

}
