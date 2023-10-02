package com.crud.cinema.backend.controller;

import com.crud.cinema.backend.domain.freecurrency.FreecurrencyEurToPlnDto;
import com.crud.cinema.backend.freecurrency.client.facade.FreecurrencyFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/freecurrency")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FreecurrencyController {

    private final FreecurrencyFacade freecurrencyFacade;
    @GetMapping("eurtopln")
    public ResponseEntity<FreecurrencyEurToPlnDto> getEurToPlnRate() {
        return ResponseEntity.ok(freecurrencyFacade.getEutToPlnRate());
    }
}
