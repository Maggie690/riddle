package geo.wealth.riddle.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodedException extends Exception {

    private final String code;

    public CodedException(ErrorCode code, Object... args) {
        super(code.toMessage(args));
        this.code = code.getCode();
    }
}
