package me.finnthorben.thingpeoplelist.people;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.finnthorben.thingpeoplelist.genericapi.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class PeopleControllerAdvice {
    @ExceptionHandler(PeopleService.NoSuchPersonException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Problem noSuchPersonHandler(PeopleService.NoSuchPersonException exc) {
        return new Problem("No such person", exc.getLocalizedMessage());
    }
}
