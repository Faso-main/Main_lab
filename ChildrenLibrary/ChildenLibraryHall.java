package ChildrenLibrary;
import java.util.Optional;
import Error.BookIndexOutOfBoundsException;
import Error.InvalidBookCountException;
import interfaceLab.*;
import java.lang.StringBuilder;
import java.io.Serializable;

public class ChildenLibraryHall implements IHall,Serializable{
    private String name;
    private IBook[] books;

    public ChildenLibraryHall(String name, int N) {
        this(N);
        this.name = name;
    }

    public ChildenLibraryHall(String name, interfaceLab.IBook[] books) {
        this(books);
        this.name = name;
    }

    public ChildenLibraryHall(int N) {
        if(N<0){
            throw new InvalidBookCountException("Количество книг не может быть меньше 0");
        }
        this.books = new IBook[N];
        this.name = "Неизвестный";
    }

    public ChildenLibraryHall(interfaceLab.IBook[] books) {
        if(books.length==0){
            throw new InvalidBookCountException("Количество книг не может ,snm 0");
        }
        this.books = books;
    }

    public ChildenLibraryHall() {
        this.name = "не определено";
    }

    public IBook[] getBooks() {
        IBook[] booker = new IBook[this.books.length];
        for(int i=0; i<this.books.length; i++){
            booker[i] = this.books[i];
        }
        return booker;
    }

    public void addBook(int index, interfaceLab.IBook book) {
        if(this.books.length==0){
            this.books=new IBook[1];
            this.books[0]=book;
        }
        if(index<0 || index>books.length){
            throw new BookIndexOutOfBoundsException("");
        }
        for (int i = 0; i < index; i++) {
            if (this.books[i] == null) {
                this.books[i] = book;
                return;
            }
        }
        this.books[index] = book;
    }

    public int getSize(){
        return this.books.length;
    }

    public int getCount() {
        int kol = 0;
        for (int i = 0; i < this.books.length; i++) {
            if (this.books[i] != null) {
                kol++;
            }
        }
        return kol;
    }

    public double getCost(){
        double cost = 0;
        for(IBook book: this.books){
            cost+=book.getCost();
        }
        return cost;
    }

    public int getLongBooks() {
        return this.books.length;
    }

    public String booksName() {
        String names = "";
        for (IBook childerBook : this.books) {
            if (childerBook != null && !"Не определено".equals(childerBook.getName())) {
                names = childerBook.getName() + ' ';
            }
        }
        return names;
    }

    public double totalCosts() {
        double costs = 0;
        for (IBook childerBook : this.books) {
            if (childerBook != null && !"Не определено".equals(childerBook.getName())) {
                costs += childerBook.getCost();
            }
        }
        return costs;
    }

    public Optional<IBook> getBook(int index) {
        if (index >= 0 && index < books.length) {
            return Optional.ofNullable(books[index]);
        }
        return Optional.empty();
    }

    public void updatetBook(int index, interfaceLab.IBook book) {
        if (index >= 0 && index < books.length) {
            this.books[index] = book;
        } else {
            throw new BookIndexOutOfBoundsException("Нет такого индекса");
        }
    }

    public void addBook(interfaceLab.IBook book) {
        int length = this.books.length;
        for (int i = 0; i < length; i++) {
            if (this.books[i] == null) {
                this.books[i] = book;
                return;
            }
        }
        IBook[] newBook = new IBook[length * 2];
        for (int i = 0; i < length; i++) {
            newBook[i] = this.books[i];
        }
        newBook[length] = book;
        this.books = newBook;
    }

    public void replaceBook(int index, IBook book) {
        int length = this.books.length;
        if (index < 0) {
            return;
        }
        if (index < length) {
            this.books[index] = (IBook)book;
            return;
        }
        throw new BookIndexOutOfBoundsException("Нет такого индекса");
    }

    public void removeBook(int index) {
        if (index < 0 || index >= this.books.length) {
            throw new BookIndexOutOfBoundsException("Нет такого индекса");
        }
        IBook[] books1 = new IBook[this.books.length-1];
        for (int i = index; i < this.books.length - 1; i++) {
            this.books[i] = this.books[i + 1];
        }
        System.arraycopy(this.books, 0, books1, 0, books1.length);
        this.books=books1;
    }

    public IBook getBest(){
        IBook bestBook = new ChildenBook();
        for(IBook book: this.books){
            if(book.getCost()>bestBook.getCost()) {
                bestBook=book;
            }
        }
        return bestBook;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name=name;
    }

    public void showBooks() {
        for (IBook book : this.books) {
            System.out.print(book.getName());
        }
    }

    public IBook[] getSorted() {
        IBook[] books = new ChildenBook[this.getCount()];
        for (int i = 0; i < books.length - 1; i++) {
            for (int j = i + 1; j < books.length; j++) {
                if (books[i].getCost() > books[j].getCost()) {
                    IBook temp = books[i];
                    books[i] = books[j];
                    books[j] = temp;
                }
            }
        }
        return books;
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i<this.books.length;i++){
            str.append(this.books[i]);
            str.append(" ");
        }
        return str.toString();
    }

    public boolean equals(Object object){
        if(this==object){
            return true;
        }
        if(object instanceof ChildenLibraryHall hall) {
            if (hall.getCount() == this.getCount()) {
                for (int i = 0; i < hall.getCount(); i++) {
                    if (!hall.getBook(i).equals(this.getBook(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public int hashCode(){
        int hash = 0;
        for(IBook book: books){
            hash^=book.hashCode();
        }
        return this.getSize() ^ hash;
    }

    public Object clone() {
        IBook[] clonedBooks = new IBook[this.books.length];
        for (int i = 0; i < this.books.length; i++) {
           clonedBooks[i] = (IBook)this.books[i].clone();
        }
        return new ChildenLibraryHall(new String(this.name), clonedBooks);
    }
}
