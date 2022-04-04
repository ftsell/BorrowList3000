package me.finnthorben.thingpeoplelist.people;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class PeopleControllerAdvice {
    @ExceptionHandler(IPeopleService.NoSuchPersonException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Problem noSuchPersonHandler(IPeopleService.NoSuchPersonException exc) {
        return Problem.create()
                .withTitle("No such person")
                .withDetail(exc.getLocalizedMessage());
    }

    @ExceptionHandler(IPeopleService.PersonAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    Problem personAlreadyExistsHandler(IPeopleService.PersonAlreadyExistsException exc) {
        return Problem.create()
                .withTitle("Person already exists")
                .withDetail(exc.getLocalizedMessage());
    }
}
