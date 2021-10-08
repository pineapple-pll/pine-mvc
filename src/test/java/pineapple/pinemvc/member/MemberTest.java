package pineapple.pinemvc.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class MemberTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void objectMapperTest() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", "testId");
        map.put("password", "12341234");

        String params1 = objectMapper.writeValueAsString(map);
        System.out.println(params1);
    }
}
