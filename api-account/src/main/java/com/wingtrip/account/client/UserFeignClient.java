package com.wingtrip.account.client;

import com.wingtrip.user.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "api-user", url = "http://localhost:8087")
public interface UserFeignClient {

    @GetMapping("/api/v1/user/feign-user-id/{id}")
    UserDTO getUserById(@PathVariable Long id);

    @GetMapping("/api/v1/user/feign-username/{username}")
    UserDTO getUserByUsername(@PathVariable String username);

    @GetMapping("/api/v1/user/feign-booking/{id}")
    UserDTO getBookingId(@PathVariable Long id);

    @PutMapping("/api/v1/user/feign-update-user/{id}")
    UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO);
}
