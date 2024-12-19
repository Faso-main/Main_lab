package ChildrenLibrary;
import Error.InvalidBookPriceException;
import interfaceLab.IBook;
import java.io.Serializable;

public class Book implements IBook, Serializable{
    private String author;
    private String name;
    private double cost;
    private int year;

    public Book(){
        this("Не определено", "Не определено", 0.0, 0);
    }
    public Book(String author, String name, double cost, int year){
        if(cost<0){
            throw new InvalidBookPriceException("Цена должна быть больше 0");
        }
        this.author=author;
        this.name=name;
        this.cost=cost;
        this.year= year;
    }

    public Book(String name, String author){
        this(name, author, 0.0, 0);
    }
    public void setAuthor(String author){
        this.author=author;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setCost(double cost){
        this.cost=cost;
    }
    public void setYear(int year){
        this.year=year;
    }

    public String getAuthor(){
        return this.author;
    }
    public String getName(){
        return this.name;
    }
    public double getCost(){
        return this.cost;
    }
    public int getYear(){
        return this.year;
    }

    public String toString() {
        return "Автор: " + this.author + ", Название: " + this.name + ", Год издания: " + this.year;
    }

    public boolean equals(Object object) {
        if(this==object){
            return true;
        }
        if (object instanceof Book book) {
            if (book.author.equals(this.author) &&
                    book.year == this.year &&
                    book.cost == this.cost &&
                    book.name.equals(this.name)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode(){
        return (this.name.hashCode()) ^ (this.author.hashCode());
    }

    public Object clone() {
        return new Book(this.author, this.name, this.cost, this.year);
    }
}
