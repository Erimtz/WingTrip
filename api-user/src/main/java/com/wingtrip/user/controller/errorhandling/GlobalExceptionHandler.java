package com.wingtrip.user.controller.errorhandling;

import com.wingtrip.user.util.DateTimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UrlPathHelper;

import java.util.HashMap;
import java.util.Map;

import static com.wingtrip.user.constant.Constant.*;

@ControllerAdvice
@RestController
@Log4j2
class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest req, Exception ex) {
        Map<String, Object> result = new HashMap<>();
        result.put(TIMESTAMP, DateTimeUtil.now().toEpochDay());
        result.put(STATUS, HttpStatus.NOT_FOUND.value());
        result.put(ERROR, ex.getMessage());
        result.put(PATH, new UrlPathHelper().getPathWithinApplication(req));
        log.error(ERROR + ex.getMessage());
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
}
