package com.wingtrip.user.client;

import com.wingtrip.user.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-user", url = "http://localhost:8088")
public interface UserFeignClient {

    @GetMapping("/users/username/{username}")
    UserDTO getUserByUsername(@PathVariable String username);

    @GetMapping("/users/id/{userId}")
    UserDTO getUserById(@PathVariable Long userId);
    @GetMapping("/users/booking/{userId}")
    Long getBookingId(Long userId);
}
