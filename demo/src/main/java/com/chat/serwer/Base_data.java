package com.chat.serwer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Base_data {

        @GetMapping("/api/test_api")
        public String getMessage() {
            return "Hello, this is your message!";
        }

        @GetMapping("/api/get_info")
        public String getInfo() {
            return "This is opensource chat information.";
        }

        



}
