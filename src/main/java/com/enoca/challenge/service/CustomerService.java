package com.enoca.challenge.service;

import com.enoca.challenge.model.Customer;
import com.enoca.challenge.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {


    private final CustomerRepository customerRepository;

    public List<Customer> getCustomersByNameContains(String name) {
        return customerRepository.findByNameContaining(name);
    }

    public List<Customer> getCustomersWithoutOrders() {
        return customerRepository.findCustomersWithoutOrders();
    }
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        if (customerRepository.existsById(customer.getId())) {
            return customerRepository.save(customer);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Müşteri bulunamadı.");
        }
    }
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("ID " + id + " olan müşteri bulunamadı.");
        }
        customerRepository.deleteById(id);
    }

}
