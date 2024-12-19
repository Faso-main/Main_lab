package ChildrenLibrary;
import java.io.Serializable;
import java.util.Optional;
import Error.HallIndexOutOfBoundsException;
import Error.InvalidBookCountException;
import interfaceLab.*;
import java.lang.StringBuilder;

public class ChildrenLibrary implements ILibrary  {
    private IHall[] halls;

    public ChildrenLibrary(int count, int[] bookCount){
        if(count<0){
            throw new HallIndexOutOfBoundsException("");
        }
        this.halls = new ChildenLibraryHall[count];
        for(int i = 0; i<count; i++){
            if(bookCount[i]<0){
                throw new InvalidBookCountException("");
            }
            this.halls[i]= new ChildenLibraryHall(bookCount[i]);
        }
    }

    public ChildrenLibrary(interfaceLab.IHall[] hall){
        this.halls = hall;
    }

    public int getCountHalls(){
        return this.halls.length;
    }

    public void setName(int index, String name){
        this.halls[index].setName(name);
    }

    public int getCountBooks(){
        int count = 0;
        for (IHall hall : this.halls) {
            count += hall.getCount();
        }
        return count;
    }

    public int getSize() {
        return this.halls.length;
    }

    public int getCountBook() {
        return 0;
    }

    public double getCost(){
        double cost=0;
        for(IHall hall: this.halls){
            cost+= hall.totalCosts();
        }
        return cost;
    }

    public IHall[] getHalls() {
        IHall[] haller = new IHall[this.halls.length];
        for(int i = 0; i < this.halls.length; i++){
            haller[i]= (IHall) this.halls[i].clone();
        }
        return haller;
    }


    public IHall getHall(int index) {
        if (index >= 0 && index < halls.length) {
            return halls[index];
        }
        return null;
    }

    public Optional<IBook> getBook(int index) {
        int cindex = 0;
        for (IHall hall : halls) {
            if (index < cindex + hall.getCount()) {
                return hall.getBook(index -cindex);
            }
            cindex += hall.getCount();
        }
        return Optional.empty();
    }

    public IBook[] getSorted() {
        int totalBooks = 0;
        for (IHall hall : this.halls) {
            totalBooks += hall.getLongBooks();
        }
        IBook[] tempBooks = new IBook[totalBooks];
        int index = 0;
        for (IHall hall : this.halls) {
            for (IBook book: hall.getBooks()) {
                if (book != null) {
                    tempBooks[index++] = book;
                }
            }
        }
        IBook[] allBooks = new IBook[index];
        System.arraycopy(tempBooks, 0, allBooks, 0, index);
        for (int i = 0; i < allBooks.length - 1; i++) {
            for (int j = 0; j < allBooks.length - 1 - i; j++) {
                if (allBooks[j].getCost() < allBooks[j + 1].getCost()) {
                    IBook temp = allBooks[j];
                    allBooks[j] = allBooks[j + 1];
                    allBooks[j + 1] = temp;
                }
            }
        }
        return allBooks;
    }

    public void showHalls(){
        for(IHall hall: this.halls){
            System.out.println(hall.getName()+' '+hall.getCount());
        }
    }

    public void replaceHall(int index, interfaceLab.IHall hall) {
        if (index >= 0 && index < halls.length) {
            this.halls[index] = hall;
        }
        else{
            throw new HallIndexOutOfBoundsException("");
        }
    }

    public void replaceBook(int index, interfaceLab.IBook book) {
        int cindex = 0;
        if(index<0){
            throw new IndexOutOfBoundsException("");
        }
        for (IHall hall : halls) {
            if (index < cindex + hall.getLongBooks()) {
                hall.replaceBook(index - cindex, book);
                return;
            }
            cindex += hall.getCount();
        }
    }

    public void addBook(int num, interfaceLab.IBook book) {
        int cindex = 0;
        if(num<0){
            throw new IndexOutOfBoundsException("");
        }
        for (IHall hall : halls) {
            if (num < cindex + hall.getLongBooks()) {
                hall.addBook(num - cindex, book);
                return;
            }
            cindex += hall.getCount();
        }
    }


    public void addBook(interfaceLab.IBook book) {
        for (IHall hall : this.halls) {
            if (hall.getCount() < hall.getLongBooks()) {
                hall.addBook(hall.getLongBooks(), book);
                return;
            }
        }
    }

    public void removeBook(int index) {
        if(index<0){
            throw new IndexOutOfBoundsException("");
        }
        int cindex = 0;
        for (IHall hall : halls) {
            if (index < cindex + hall.getLongBooks()) {
                hall.removeBook(index - cindex);
                return;
            }
            cindex += hall.getCount();
        }
    }

    public IBook getBestBook(){
        IBook best = new ChildenBook();
        for(IHall hall: this.halls){
            if(hall.getBest().getCost()>best.getCost()){
                best = hall.getBest();
            }
        }
        return best;
    }

    public void showBooks(){
        for (IHall hall : halls) {
            if (hall.getBooks() != null) {
                System.out.print(hall.getName() + ':');
                for (IBook book : hall.getBooks()) {
                    if (book != null) { // Проверка на null перед вызовом getName()
                        System.out.print(book.getName() + '('+book.getAuthor()+')'+' ');
                    }
                }
                System.out.println(); // Для перехода на новую строку после каждого зала
            }
        }
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for(int i=0;i<this.halls.length;i++){
            str.append(this.halls[i]);
            str.append("\n");
        }
        return str.toString();
    }

    public boolean equals(Object object) {
        if(this==object){
            return true;
        }
        if (object instanceof ChildrenLibrary library) {
            if (this.getHalls().length == library.getHalls().length) {
                for (int i = 0; i < this.getHalls().length; i++) {
                    if (!this.getHall(i).equals(library.getHall(i))) {
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
        for(IHall book: halls){
            hash^=book.hashCode();
        }
        return this.getSize() ^ hash;
    }

    public Object clone(){
        return new ChildrenLibrary(this.getHalls());
    }
}
