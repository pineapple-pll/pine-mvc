package pineapple.pinemvc.global.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pineapple.pinemvc.global.error.exception.CustomException;
import pineapple.pinemvc.global.error.exception.ErrorCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestDto {

    private int id;
    private String method;
    private String url;
    private Map<String, Object> body = new HashMap<>();

    /**
     * method 검증
     *
     * @param requestDto
     */
    public void validDto(RequestDto requestDto) {
        String[] allowMethodList = {"GET", "POST", "PATCH", "DELETE"};
        if (!Arrays.stream(allowMethodList).anyMatch(requestDto.getMethod().toUpperCase(Locale.ROOT)::equals)) {
//            throw new CustomException(ErrorCode.NOT_ALLOWED_METHOD);
        }
    }
}
