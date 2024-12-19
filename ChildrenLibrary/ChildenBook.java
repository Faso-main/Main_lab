package ChildrenLibrary;
import java.io.Serializable;

public class ChildenBook extends Book implements interfaceLab.IBook,Serializable   {
    private int minOld;
    public ChildenBook(String author, String name, double cost, int year, int minOld){
        super(author, name, cost, year);
        this.minOld=minOld;
    }
    public ChildenBook(){
        super();
        this.minOld=0;
    }
    public ChildenBook(String author, String name){
        super(author,name);
        this.minOld=0;
    }

    public void setMinOld(int minOld){
        this.minOld=minOld;
    }
    public int getMinOld(){
        return this.minOld;
    }

    public String toString(){
        return "Автор: " + this.getAuthor() + ", Название: " + this.getName() + ", Год издания: " + this.getYear() + " Минимальный возраст: " + this.minOld;
    }

    public boolean equals(Object object){
        if(this==object){
            return true;
        }
        if(object instanceof ChildenBook book) {
            if (book.getAuthor().equals(this.getAuthor()) &&
                    book.getYear() == this.getYear() &&
                    book.getCost() == this.getCost() &&
                    book.getName().equals(this.getName()) &&
                    book.getMinOld() == this.getMinOld()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode(){
        return super.getName().hashCode() ^ super.getAuthor().hashCode() ^ Integer.hashCode(this.getMinOld());
    }

    public Object clone() {
        return new ChildenBook(this.getAuthor(), this.getName(), this.getCost(), this.getYear(), this.minOld);
    }
}
