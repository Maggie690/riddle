package geo.wealth.riddle.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseDto<T> {

    private final T response;
    private final Error error;

    public static <T> ResponseDto<T> response(T content) {
        return new ResponseDto<>(content, null);
    }

    public static <Void> ResponseDto<Void> response() {
        return new ResponseDto<>(null, null);
    }

    public static <Void> ResponseDto<Void> fail(String code, String message) {
        return new ResponseDto<>(null, new Error(code, message));
    }

    @Getter
    @RequiredArgsConstructor
    static class Error {
        private final String code;
        private final String message;
    }
}
