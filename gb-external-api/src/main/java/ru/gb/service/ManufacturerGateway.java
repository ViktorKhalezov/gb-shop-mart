package ru.gb.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.controller.ManufacturerApi;
import ru.gb.dto.ManufacturerDto;

import java.util.List;

@FeignClient(url = "localhost:8080/manufacturer", value = "manufacturerGateway")
public interface ManufacturerGateway extends ManufacturerApi {

    @GetMapping(produces = "application/json;charset=UTF-8")
    List<ManufacturerDto> getManufacturerList();

    @GetMapping(value = "/{manufacturerId}", produces = "application/json;charset=UTF-8")
    ResponseEntity<?> getManufacturer(@PathVariable("manufacturerId") Long id);


}
