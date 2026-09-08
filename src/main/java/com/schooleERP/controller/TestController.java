package com.schooleERP.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "ADMIN access successful";
    }

    @GetMapping("/principal")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public String principal() {
        return "PRINCIPAL access successful";
    }

    @GetMapping("/teacher")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public String teacher() {
        return "TEACHER access successful";
    }
}

