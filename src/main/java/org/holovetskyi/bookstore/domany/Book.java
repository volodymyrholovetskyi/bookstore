package org.holovetskyi.bookstore.domany;


import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public class Book {

    @NotNull
    @Range(min = 1, max = 4, message = "podałeś nieprawidłowy numer książki")
    private int idBook;

    private String bookTitle;

    private String author;

    private String edition;

    private int numberOfPages;

    public Book(String bookTitle, String author, String edition, int numberOfPages) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.edition = edition;
        this.numberOfPages = numberOfPages;
    }

    public Book(){

    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    @Override
    public String toString() {
        return "Book{" +
                "idBook=" + idBook +
                ", bookTitle='" + bookTitle + '\'' +
                ", author='" + author + '\'' +
                ", edition='" + edition + '\'' +
                ", numberOfPages=" + numberOfPages +
                '}';
    }
}
