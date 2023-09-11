package com.enoca.challenge.controller;

import com.enoca.challenge.model.Customer;
import com.enoca.challenge.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {


    private final CustomerService customerService;

    @Operation(summary = "İsminde belirtilen kelime veya harf geçen müşterileri getirir.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteriler getirildi", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Müşteri bulunamadı.", content = @Content)
    })
    @GetMapping("/search")
    public ResponseEntity<?> getCustomersByNameContains(@RequestParam String name) {
        List<Customer> customers = customerService.getCustomersByNameContains(name);
        if (customers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("İsminde '" + name + "' geçen müşteri bulunamadı.");
        }
        return ResponseEntity.ok(customers);
    }


    @Operation(summary = "Siparişi olmayan müşterileri getirir.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteriler getirildi", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Müşteri bulunamadı.", content = @Content)
    })

    @GetMapping("/withoutOrders")
    public ResponseEntity<?> getCustomersWithoutOrders() {
        List<Customer> customers = customerService.getCustomersWithoutOrders();
        if (customers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Siparişi olmayan müşteri bulunamadı.");
        }
        return ResponseEntity.ok(customers);
    }

    @Operation(summary = "Tüm müşterileri getirir.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tüm müşteriler getirildi", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Müşteri bulunamadı.", content = @Content)
    })


    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Müşteri bulunamadı.");
        }
        return ResponseEntity.ok(customers);
    }

    @Operation(summary = "Belirtilen ID'ye sahip müşteriyi getirir.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteri getirildi", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Müşteri bulunamadı.", content = @Content)
    })

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(  @Parameter(description = "Aranacak Musteri Id", example = "1") @PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID " + id + " olan müşteri bulunamadı.");
        }
        return ResponseEntity.ok(customer);
    }

    @Operation(summary = "Yeni bir müşteri oluşturur.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Müşteri başarıyla oluşturuldu", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Geçersiz istek.", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customer));
    }

    @Operation(summary = "Belirtilen ID'ye sahip müşteriyi günceller.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteri başarıyla güncellendi", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Müşteri bulunamadı.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Geçersiz istek.", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer( @Parameter(description = "Guncellenecek Musteri Id", example = "1")@PathVariable Long id, @RequestBody Customer customer) {
        customer.setId(id);
        Customer updatedCustomer = customerService.updateCustomer(customer);
        if (updatedCustomer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID " + id + " olan müşteri bulunamadı.");
        }
        return ResponseEntity.ok(updatedCustomer);
    }

    @Operation(summary = "Belirtilen ID'ye sahip müşteriyi siler.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Müşteri başarıyla silindi"),
            @ApiResponse(responseCode = "404", description = "Müşteri bulunamadı.", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer( @Parameter(description = "Silinecek Musteri Id", example = "1")@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}