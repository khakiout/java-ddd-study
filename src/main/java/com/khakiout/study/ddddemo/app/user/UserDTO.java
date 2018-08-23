package com.khakiout.study.ddddemo.app.user;

import com.khakiout.study.ddddemo.app.BaseDTO;
import java.time.LocalDateTime;

public class UserDTO extends BaseDTO {

    String id;
    String firstName;
    String lastName;
    LocalDateTime birthday;
    int age;

}
