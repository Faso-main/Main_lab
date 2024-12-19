package ScientificLibrary;
import ChildrenLibrary.ScientificBook;
import java.util.Objects;
import java.util.Optional;
import Error.*;
import interfaceLab.*;
import java.lang.StringBuilder;
import java.io.Serializable;

public class ScientificLibraryHall implements IHall,Serializable{
    private Item head;
    private int size;
    private String name;

    private ScientificLibraryHall(){
        this.head = new Item(null);
        this.head.next = head;
        this.size = 0;
    }

    public ScientificLibraryHall(String name, int numBooks){
        this();
        if(numBooks<0){
            throw new InvalidBookCountException("");
        }
        this.name = name;
        for(int i=0; i<numBooks; i++){
            addBook(i,new ScientificBook());
        }
    }

    public ScientificLibraryHall(String name, interfaceLab.IBook[] books){
        this();
        if(books.length==0){
            throw new IndexOutOfBoundsException("");
        }
        this.name = name;
        for(int i=0;i<books.length;i++){
            addBook(i,books[i]);
        }
    }

    public boolean isEmpty(){
        return this.size==0;
    }

    public int getSize(){
        return this.size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public Optional<interfaceLab.IBook> getBook(int index) {
        if (isEmpty() || index < 0 || index >= size) {
            return Optional.empty();
        }
        Item current = head.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return Optional.of(current.data);
    }

    public void addBook(int index, interfaceLab.IBook book) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("");
        }
        Item newItem = new Item(book);
        if (isEmpty() || index==0) {
            head.next = newItem;
            newItem.next = head;
            size++;
            return;
        } else {
            Item current = head.next;
            for (int i = 0; i < index; i++) {
                if(current.next==head) {
                    break;
                }
                current = current.next;
            }
            newItem.next = current.next;
            current.next = newItem;
        }
        size++;
    }

    public void addBook(interfaceLab.IBook book){
        Item newItem = new Item(book);
        if (isEmpty()) {
            head.next = newItem;
            newItem.next = head;
            size++;
            return;
        } else {
            Item current = head.next;
            do{
                current = current.next;
            }while(current.next!=head);
            newItem.next = current.next;
            current.next = newItem;
        }
        size++;
    }

    public void replaceBook(int index, interfaceLab.IBook book){
        if (isEmpty() || index < 0 || index > size) {
            throw new IndexOutOfBoundsException("");
        }
        Item newItem = new Item(book);
        Item current = head.next;
        if(index==0){
            head.next=newItem;
            newItem.next=current.next;
            return;
        }
        for(int i=0;i<index-1;i++){
            current=current.next;
        }
        Item time = current.next;
        newItem.next=current.next.next;
        current.next=newItem;
    }

    public void removeBook(int index){
        if(isEmpty() || index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("");
        }
        Item current = head.next;
        if(index==0){
            head.next=current.next;
        }
        for(int i = 0; i < index-1; i++){
            current = current.next;
        }
        current.next = current.next.next;
        this.size--;
    }

    public int getCount() {
        return this.CountBooks();
    }

    public double totalCosts() {
        return 0;
    }

    public void traverse() {
        if (isEmpty()) {
            return;
        }
        Item current = head.next;
        do {
            System.out.println(current.data);
            current = current.next;
        } while (current != this.head);
    }

    public interfaceLab.IBook[] getBooks(){
        if(isEmpty()){
            return new IBook[0];
        }
        IBook[] books = new IBook[this.size];
        Item current = head.next;
        for(int i=0; i<size; i++){
            books[i] = current.getData();
            current = current.next;
        }
        return books;
    }

    public int CountBooks(){
        if(isEmpty()){
            return 0;
        }
        int count = 0;
        Item current = head.next;
        do{
            boolean b = !Objects.equals(current.getData().getName(), "Не определено")
                    && !Objects.equals(current.getData().getAuthor(), "Не определено")
                    && current.getData().getCost() != 0.0
                    && current.getData().getYear() != 0;
            if(b){
                count++;
            }
            current=current.next;
        }while(current!=head);
        return count;
    }

    public IBook[] getSorted() {
        if (isEmpty()) {
            return new IBook[0];
        }
        IBook[] books = new IBook[this.size];
        Item current = head.next;
        for (int i = 0; i < size; i++) {
            books[i] = current.data;
            current = current.next;
        }
        for (int i = 0; i < books.length - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < books.length; j++) {
                if (books[j].getCost() > books[maxIndex].getCost()) {
                    maxIndex = j;
                }
            }
            if (maxIndex != i) {
                IBook temp = books[i];
                books[i] = books[maxIndex];
                books[maxIndex] = temp;
            }
        }
        return books;
    }

    public IBook getBest(){
        if(isEmpty()){
            return new ScientificBook();
        }
        Item current = head.next;
        IBook bestBook = current.getData();
        while(current!=head){
            if(current.next!=head && bestBook.getCost()<current.next.getData().getCost()){
                bestBook = current.getData();
            }
            current = current.next;
        }
        return bestBook;
    }

    public int getLongBooks() {
        return size;
    }

    public void showBooks(){
        if(isEmpty()){
            return;
        }
        Item current = head.next;
        do{
            System.out.print(current.getData().getName()+"("+current.getData().getAuthor()+") ");
            current=current.next;
        }while(current!=head);
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        Item current = head.next;
        do{
            str.append(current.getData()).append("\n");
            current = current.next;
        }while(current!=head);
        return str.toString();
    }

    public boolean equals(Object object){
        if(this==object){
            return true;
        }
        if(object instanceof ScientificLibraryHall hall) {
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
        Item current = head.next;
        do{
            hash^=current.getData().hashCode();
            current=current.next;
        }while(current!=head);
        return this.size ^ hash;
    }

    public Object clone(){
        return new ScientificLibraryHall(new String(this.name), this.getBooks());
    }
}


class Item implements Serializable{
    public IBook data;
    public Item next;

    public Item(interfaceLab.IBook data) {
        this.data = (IBook)data;
        this.next = null;
    }

    public IBook getData(){
        return this.data;
    }
}
