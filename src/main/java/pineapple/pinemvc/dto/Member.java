package pineapple.pinemvc.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Member {
    private String memberId;
    private String password;
    private String name;
    private Integer age;
    private String gender;
    private String profile;
    private Integer country;
    private Phone phone;
    private String email;

    @Autowired
    private ObjectMapper objectMapper;

    @Builder
    public Member(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }

    public String getMemberLoginJson() throws JsonProcessingException {

        // Object Mapper를 통한 JSON 바인딩
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", this.memberId);
        map.put("password", this.password);

        return objectMapper.writeValueAsString(map);
    }
}
