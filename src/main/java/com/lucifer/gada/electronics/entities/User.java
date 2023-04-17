package com.lucifer.gada.electronics.entities;

import com.lucifer.gada.electronics.helper.UserIdGenerator;
import com.lucifer.gada.electronics.utils.ImageNameValid;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name="TBL_USERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@ToString
public class User {

    //Testing//

//    @Id
//    @GeneratedValue(generator = "prod-generator")
//    @GenericGenerator(name = "prod-generator",
//            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "prod"),
//            strategy = "com.lucifer.gada.electronics.helper.UserIdGenerator")
    // End //




    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    @Size(min = 3, max=20, message = "Invalid Name")
    private String name;

    @Column(unique = true)
    @Email(message = "Invalid Email")
    @NotBlank(message = "Email should not be Null")
    private String email;

    @Size(min = 5, max = 20, message = "Invalid Password")
    @NotBlank(message = "Password cannot be Null")
    private String password;

    @Size(min = 4, max = 6, message = "Invalid Gender")
    private String gender;
    @NotBlank(message = "Write Something About You")
    private String about;
    private Date createdOn;

    @ImageNameValid
    @Column(name="profile_pic")
    private String imageName;

}
