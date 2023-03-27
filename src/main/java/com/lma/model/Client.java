package com.lma.model;

import java.util.HashSet;
import java.util.Objects;

public class Client {
    private String firstName;
    private String lastName;
    private HashSet<Book> books;
    private HashSet<Order> orders;

    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = new HashSet<>();
        this.orders = new HashSet<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public HashSet<Book> getBooks() {
        return books;
    }

    public HashSet<Order> getOrders() {
        return orders;
    }

    public void addBook(Book book){
        books.add(book);
    }

    public void addOrder(Order order){
        orders.add(order);
    }

    public String getFullName(){
        return String.format("%s %s",this.firstName,this.lastName);
    }

    @Override
    public String toString() {
        return String.format("Client name: %s %s\nBook count: %d\nOrder count: %d\n",this.firstName,this.lastName,this.books.size(),this.orders.size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return getFirstName().equals(client.getFirstName()) && getLastName().equals(client.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }


}
