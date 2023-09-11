package com.enoca.challenge.request;


public class OrderRequest {

    private Double totalPrice;
    private CustomerReference customer;

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public CustomerReference getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerReference customer) {
        this.customer = customer;
    }

    public static class CustomerReference {
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
