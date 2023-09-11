package com.enoca.challenge.controller;

import com.enoca.challenge.model.Customer;
import com.enoca.challenge.model.Order;
import com.enoca.challenge.request.OrderRequest;
import com.enoca.challenge.request.UpdateOrderRequest;
import com.enoca.challenge.service.CustomerService;
import com.enoca.challenge.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    @Operation(summary = "Belirtilen tarihten sonraki siparişleri getirir.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Siparişler başarıyla getirildi", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Sipariş bulunamadı.", content = @Content)
    })
    @GetMapping("/afterDate")

    public ResponseEntity<?> getOrdersAfterDate(    @Parameter(description = "Aranacak tarih", example = "11.09.2023") @RequestParam String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate localDate = LocalDate.parse(date, formatter);
            List<Order> orders = orderService.getOrdersAfterDate(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            if (orders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Belirtilen tarihten sonraki siparişler bulunamadı.");
            }
            return ResponseEntity.ok(orders);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Geçersiz tarih formatı. Lütfen 'dd.MM.yyyy' formatında bir tarih girin.");
        }
    }
    @Operation(summary = "Tüm siparişleri getirir.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tüm siparişler başarıyla getirildi", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Sipariş bulunamadı.", content = @Content)
    })

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hiçbir sipariş bulunamadı.");
        }
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Belirtilen ID'ye sahip siparişi getirir.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sipariş başarıyla getirildi", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Sipariş bulunamadı.", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById( @Parameter(description = "Aranacak Order Id", example = "1")@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID " + id + " olan sipariş bulunamadı.");
        }
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "Yeni bir sipariş oluşturur.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sipariş başarıyla oluşturuldu", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Geçersiz istek.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Müşteri bulunamadı.", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {

        Order order = new Order();
        order.setTotalPrice(request.getTotalPrice());

        Customer customer = customerService.getCustomerById(request.getCustomer().getId());
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        order.setCustomer(customer);
        Order savedOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @Operation(summary = "Belirtilen siparişi günceller.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sipariş başarıyla güncellendi", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Sipariş bulunamadı.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Geçersiz istek.", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder( @Parameter(description = "Guncellenecek Order Id", example = "1")@PathVariable Long id, @RequestBody UpdateOrderRequest request) {
        try {
            Order existingOrder = orderService.getOrderById(id);
            if (existingOrder == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID " + id + " olan sipariş bulunamadı.");
            }
            existingOrder.setTotalPrice(request.getTotalPrice());
            Order updatedOrder = orderService.updateOrder(existingOrder);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sipariş güncellenemedi: " + e.getMessage());
        }
    }

    @Operation(summary = "Belirtilen siparişi siler.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sipariş başarıyla silindi"),
            @ApiResponse(responseCode = "404", description = "Sipariş bulunamadı.", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder( @Parameter(description = "Silinecek Order Id", example = "1")@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID " + id + " olan sipariş bulunamadı.");
        }
    }

}
