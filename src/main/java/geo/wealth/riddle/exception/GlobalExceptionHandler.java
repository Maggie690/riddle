package geo.wealth.riddle.exception;

import geo.wealth.riddle.dto.ResponseDto;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Component
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CodedException.class)
    public ResponseEntity<ResponseDto<Void>> handlerCodedException(CodedException exception) {
        return getResponse(HttpStatus.BAD_REQUEST, ResponseDto.fail(exception.getCode(), exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Void>> handlerGeneralException(Exception exception) {
        return getResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseDto.fail(ErrorCode.SERVER.getCode(), exception.getMessage()));
    }

    private ResponseEntity<ResponseDto<Void>> getResponse(HttpStatus httpStatus, ResponseDto<Void> content) {
        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(content);
    }


}
