package geo.wealth.riddle.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    SERVER("1", "Server arror"),
    INVALID_WORD_CHARACTERS("2", "The word {0} does not contain only letters."),
    EMPTY_INPUT("3", "Word must contain at least one letter.");

    private final String code;
    private final String message;

    public String toMessage(Object... args) {
        return MessageFormat.format(message, args);
    }
}
