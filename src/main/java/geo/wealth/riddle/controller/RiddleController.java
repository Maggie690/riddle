package geo.wealth.riddle.controller;

import geo.wealth.riddle.dto.ResponseDto;
import geo.wealth.riddle.dto.RiddleDto;
import geo.wealth.riddle.services.Riddle;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static geo.wealth.riddle.controller.RiddleController.BASE;

@RestController
@RequestMapping(BASE)
@RequiredArgsConstructor
public class RiddleController {
    public static final String BASE = "/api/riddle";

    private final Riddle riddle;

    @GetMapping("/{word}")
    public ResponseDto<RiddleDto> getWords(@PathVariable("word") String word) throws IOException {
        return ResponseDto.response(riddle.findWordsByRecursion(word.toUpperCase()));
    }
}
