package com.example.Catalog.model;

import com.example.Catalog.enums.GenreEnum;

public class Book {

    //Attribute
  String titel;
  String author;
  int ISBN;
  int pages;
  GenreEnum genre;


  //Constructor
  public Book(String titel, String author, int ISBN, int pages, GenreEnum genre) {
      this.titel = titel;
      this.author = author;
      this.ISBN = ISBN;
      this.pages = pages;
      this.genre = genre;
  }


  //Getter&Setter
  public String getTitel() {
      return titel;
  }

  public void setTitel(String titel) {
      this.titel = titel;
  }

  public String getAuthor() {
      return author;
  }

  public void setAuthor(String author) {
      this.author = author;
  }

  public int getISBN() {
      return ISBN;
  }

  public void setISBN(int ISBN) {
      this.ISBN = ISBN;
  }

  public int getPages() {
      return pages;
  }

  public void setPages(int pages) {
      this.pages = pages;
  }

  public GenreEnum getGenre() {
      return genre;
  }

  public void setGenre(GenreEnum genre) {
      this.genre = genre;
  }
}
