package com.lucifer.gada.electronics.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="TBL_USERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@ToString
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String gender;
    private String about;

    @Column(name="profile_pic")
    private String imageName;

}
