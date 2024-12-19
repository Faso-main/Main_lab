package ChildrenLibrary;
import interfaceLab.IBook;
import java.io.Serializable;

public class ScientificBook extends Book implements IBook, Serializable {
    private double index;

    public ScientificBook(String author, String name, double cost, int year, double index){
        super(author, name, cost, year);
        this.index = index;
    }

    public ScientificBook(String author, String name){
        super(author, name);
        this.index=0;
    }

    public ScientificBook(){
        super();
        this.index=0;
    }

    public void setIndex(double index){
        this.index=index;
    }

    public double getIndex(){
        return this.index;
    }

    public String toString(){
        return "Автор: " + this.getAuthor() + ", Название: " + this.getName() + ", Год издания: " + this.getYear() + " Индекс: " + this.index;
    }

    public boolean equals(Object object){
        if(this==object){
            return true;
        }
        if(object instanceof ScientificBook book) {
            if (book.getAuthor().equals(this.getAuthor()) &&
                    book.getYear() == this.getYear() &&
                    book.getCost() == this.getCost() &&
                    book.getName().equals(this.getName()) &&
                    book.getIndex() == this.getIndex()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        long bits = Double.doubleToLongBits(this.getIndex());
        int first = (int) (bits ^ (bits >>> 32));
        int second = (int) bits;
        return this.getName().hashCode() ^ this.getAuthor().hashCode() ^ first ^ second;
    }

    public Object clone() {
        return new ScientificBook(this.getAuthor(), this.getName(), this.getCost(), this.getYear(), this.index);
    }
}


