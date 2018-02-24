package com.s3k3l3v.bookstore.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BookItemList {
    private List<Book> books;

    public BookItemList() {
        this.books = new ArrayList<>();
    }

    public void add(int id, String title, String author, String editon, Date dateEditon, Integer numberCopies){
        Iterator<Book> iter = books.iterator();
        while (iter.hasNext()){
            Book book = iter.next();
            if (book.getId() == id){
                book.setNumberCopies(book.getNumberCopies() + numberCopies);
                return;
            }
        }
        books.add(new Book(id,title,author,editon,dateEditon,numberCopies));
    }

    public boolean update(int id, int numberCopies) {
        Iterator<Book> iter = books.iterator();
        while (iter.hasNext()) {
            Book item = iter.next();
            if (item.getId() == id) {
                // id found, increase qtyOrdered
                item.setNumberCopies(numberCopies);
                return true;
            }
        }
        return false;
    }

    public void remove(int id) {
        Iterator<Book> iter = books.iterator();
        while (iter.hasNext()) {
            Book item = iter.next();
            if (item.getId() == id) {
                books.remove(item);
                return;
            }
        }
    }

    public int size() {
        return books.size();
    }

    // Check if this Cart is empty
    public boolean isEmpty() {
        return size() == 0;
    }

    // Return all the CartItems in a List<CartItem>
    public List<Book> getBooks() {
        return books;
    }

    // Remove all the items in this Cart
    public void clear() {
        books.clear();
    }


}

