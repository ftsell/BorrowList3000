package me.finnthorben.thingpeoplelist.lists;

import lombok.RequiredArgsConstructor;
import me.finnthorben.thingpeoplelist.genericapi.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ThingListControllerAdvice {
    @ExceptionHandler(ThingListService.NoSuchThingListException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Problem noSuchListHandler(ThingListService.NoSuchThingListException exc) {
        return new Problem("No such List", exc.getLocalizedMessage());
    }

    @ExceptionHandler(ThingListService.ThingListAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    Problem listAlreadyExistsHandler(ThingListService.ThingListAlreadyExistsException exc) {
        return new Problem("List already exists", exc.getLocalizedMessage());
    }
}
