package geo.wealth.riddle.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseDto<T> {

    private final T response;

    public static <T> ResponseDto<T> response(T content) {
        return new ResponseDto<>(content);

    }
}
