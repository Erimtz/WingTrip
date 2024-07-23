package com.wingtrip.user.client;

import com.wingtrip.user.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-user", url = "http://localhost:8087")
public interface UserFeignClient {

    @GetMapping("/api/v1/user/id/{id}")
    UserDTO getUserById(@PathVariable Long id);

    @GetMapping("/api/v1/user/username/{username}")
    UserDTO getUserByUsername(@PathVariable String username);

    @GetMapping("/api/v1/user/booking/{userId}")
    Long getBookingId(@PathVariable Long userId);
}
