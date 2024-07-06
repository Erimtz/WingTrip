package com.wingtrip.account.controller.errorhandling;

import com.wingtrip.account.util.DateTimeUtil;
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

import static com.wingtrip.account.constant.Constant.*;

@ControllerAdvice
@RestController
@Log4j2
public class GlobalHandlerException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> errorHandler(HttpServletRequest req, Exception ex) {
        Map<String, Object> result = new HashMap<>();
        result.put(TIMESTAMP, DateTimeUtil.now().toEpochDay());
        result.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.put(ERROR, ex.getMessage());
        result.put(PATH, new UrlPathHelper().getPathWithinApplication(req));
        log.error(ERROR + ex.getMessage());

        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
