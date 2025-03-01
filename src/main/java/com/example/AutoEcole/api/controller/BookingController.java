package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.bll.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")

public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/create")
public ResponseEntity<CreateBookingResponseBody> createBooking(@RequestBody createBookingRequestBody request){
    return ResponseEntity.ok(bookingService.)
}
}


