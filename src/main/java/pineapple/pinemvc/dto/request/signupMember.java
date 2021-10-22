package pineapple.pinemvc.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pineapple.pinemvc.dto.Phone;

@Getter
@AllArgsConstructor
public class signupMember {
    private String memberId;
    private String password;
    private String name;
    private Integer age;
    private String gender;
    private String profile;
    private String country;
    private String phone;
    private String email;
    private String active;
    private String address;
}
