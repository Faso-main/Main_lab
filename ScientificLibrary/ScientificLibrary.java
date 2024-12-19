package ScientificLibrary;
import ChildrenLibrary.ScientificBook;
import java.util.Optional;
import Error.*;
import interfaceLab.*;
import java.lang.StringBuilder;
import java.io.Serializable;

public class ScientificLibrary implements ILibrary,Serializable{
    private Item2 head;
    private int size;

    private ScientificLibrary(){
        this.head = new Item2(null);
        this.head.next = head;
        this.head.prev = head;
        this.size = 0;
    }

    public ScientificLibrary(int count, int[] booksPerHall){
        this();
        if(count<0){
            throw new HallIndexOutOfBoundsException("");
        }
        if(booksPerHall.length==0){
            throw new IndexOutOfBoundsException("");
        }
        for(int i=0; i<count;i++){
            ScientificLibraryHall hall = new ScientificLibraryHall("Зал "+i, booksPerHall[i]);
            this.addHall(hall);
        }
    }

    public ScientificLibrary(interfaceLab.IHall[] halls){
        this();
        if(halls.length==0){
            throw new IndexOutOfBoundsException("");
        }
        for(IHall hall: halls){
            IHall hall1 = (IHall)hall.clone();
            this.addHall(hall1);
        }
    }

    public void addHall(interfaceLab.IHall hall) {
        Item2 newItem = new Item2(hall);
        if (size == 0) {
            head.next = newItem;
            newItem.prev = head;
            newItem.next = head;
            head.prev = newItem;
        } else {
            Item2 tail = head.prev;
            tail.next = newItem;
            newItem.prev = tail;
            newItem.next = head;
            head.prev = newItem;
        }
        size++;
    }

    public boolean isEmpty(){
        return head.prev == head;
    }

    public int getSize(){
        return this.size;
    }

    public IHall getHall(int index){
        if(index<0 || index>=size){
            return null;
        }
        Item2 current = head.next;
        for(int i=0; i<index;i++){
            current = current.next;
        }
        return current.getData();
    }

    public boolean addHall(int index, interfaceLab.IHall hall) {
        if (index < 0 || index > size) {
            throw new HallIndexOutOfBoundsException("");
        }
        Item2 newItem = new Item2(hall);
        if (isEmpty()) {
            head.next = newItem;
            newItem.prev = head;
            newItem.next = head;
            head.prev = newItem;
        } else {
            if (index == 0) {
                Item2 currentFirst = head.next;
                newItem.next = currentFirst;
                newItem.prev = head;
                currentFirst.prev = newItem;
                head.next = newItem;
            } else {
                Item2 current = head.next;
                for (int i = 0; i < index; i++) {
                    current = current.next;
                }
                newItem.prev = current.prev;
                newItem.next = current;
                current.prev.next = newItem;
                current.prev = newItem;
            }
        }
        size++;
        return true;
    }

    public void addBook(int index, interfaceLab.IBook book){
        if(isEmpty() || index < 0){
            throw new HallIndexOutOfBoundsException("");
        }
        int cindex = 0;
        Item2 current = head.next;
        do{
            if(index<=cindex+current.getData().getCount()){
                current.getData().addBook(cindex-index, book);
                return;
            }
            cindex+=current.getData().getSize();
            current = current.next;
        } while(current!=head);
    }

    public boolean addToBook(int index, interfaceLab.IBook book){
        if(isEmpty() || index < 0 || index >= size){
            throw new HallIndexOutOfBoundsException("");
        }
        Item2 current = head.next;
        for(int i=0; i<index;i++){
            current=current.next;
        }
        int cindex = current.getData().getSize();
        current.getData().addBook(cindex,book);
        return true;
    }

    public boolean removeHall(int index) {
        if (isEmpty() || index < 0 || index >= size) {
            throw new HallIndexOutOfBoundsException("");
        }
        Item2 current = head.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.prev.next=current.next;
        current.next.prev=current.prev;
        this.size--;
        return true;
    }

    public boolean removeBook(int hall, int index){
        if(isEmpty() || index<0 || index>size){
            throw new HallIndexOutOfBoundsException("");
        }
        Item2 current = head.next;
        for(int i=0;i<hall;i++){
            current=current.next;
        }
        current.getData().removeBook(index);
        return true;
    }

    public void removeBook(int index){
        if(isEmpty()&&this.CountBook()<index || index<0){
            throw new IndexOutOfBoundsException("");
        }
        Item2 current = head.next;
        int cindex=0;
        for(int i = 0;i<size;i++){
            if(index<cindex+current.getData().getSize()){
                current.getData().removeBook(index-cindex);
                return;
            }
            cindex+=current.getData().getSize();
            current=current.next;
        }
    }

    public int CountBook(){
        int count=0;
        Item2 current = head.next;
        if(!this.isEmpty()){
            for(int i = 0; i<size; i++){
                count+=current.getData().getBooks().length;
                current = current.next;
            }
            return count;
        }
        return 0;
    }

    public double getCost(){
        double cost=0;
        Item2 current = head.next;
        if(!this.isEmpty()){
            for(int i = 0; i<size; i++){
                for(IBook book: current.getData().getBooks()){
                        cost+=book.getCost();
                }

                current = current.next;
            }
            return cost;
        }
        return 0;
    }

    public IHall[] getHalls(){
        if(!isEmpty()) {
            IHall[] halls = new ScientificLibraryHall[size];
            Item2 current = head.next;
            for (int i = 0; i < size; i++) {
                halls[i] = (IHall) current.getData().clone();
                current = current.next;
            }
            return halls;
        }
        return null;
    }

    public Optional<IBook> getBook(int index){
        if(!isEmpty()){
            Item2 current = head.next;
            int cindex=0;
            for (int i=0;i<size;i++) {
                if (index < cindex + current.getData().getCount()) {
                    return current.getData().getBook(index - cindex);
                }
                cindex += current.getData().getSize();
                current = current.next;
            }
        }
        return Optional.empty();
    }

    public IBook[] getSorted() {
        if (isEmpty()) {
            return new ScientificBook[0];
        }
        Item2 current = head.next;
        int totalBooks = CountBook();
        IBook[] books = new IBook[totalBooks];
        int index = 0;
        for (int i = 0; i < size; i++) {
            IHall hall = current.getData();
            IBook[] hallBooks = hall.getSorted();
            for (IBook book : hallBooks) {
                books[index++] = book;
            }
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

    public void showHalls(){
        Item2 current = head.next;
        do{
            System.out.print(current.getData().getName()+": "+current.getData().getBooks().length);
            current=current.next;
        }while(current!=head);
    }

    public void showBooks(){
        if(isEmpty()){
            return;
        }
        Item2 current = head.next;
        do{
            System.out.print(current.getData().getName()+": ");
            current.getData().showBooks();
            System.out.println();
            current=current.next;
        }while(current!=head);
    }

    public void replaceHall(int index, interfaceLab.IHall hall){
        if (index < 0 || index > size || isEmpty()) {
            return;
        }
        Item2 newItem = new Item2(hall);
        Item2 current = head.next;
        for(int i=0;i<index;i++){
            current = current.next;
        }
        newItem.next=current.next;
        newItem.prev=current.prev;
        current.prev.next = newItem;
        current.next.prev = newItem;
    }

    public void replaceBook(int index, interfaceLab.IBook book){
        if(isEmpty()&&this.CountBook()<index || index<0){
            throw new IndexOutOfBoundsException("");
        }
        Item2 current = head.next;
        int cindex=0;
        for(int i = 0;i<size;i++){
            if(index<cindex+current.getData().getSize()){
                current.getData().replaceBook(index-cindex, book);
                return;
            }
            cindex+=current.getData().getSize();
            current=current.next;
        }
    }

    public IBook getBestBook(){
        if(isEmpty()){
            return new ScientificBook();
        }
        Item2 current = head.next;
        IBook bestBook = current.getData().getBest();
        while(current!=head){
            if(current.next!=head && bestBook.getCost()<current.next.getData().getBest().getCost()){
                bestBook=current.next.getData().getBest();
            }
            current=current.next;
        }
        return bestBook;
    }

    public int getCountBook(){
        return this.getSorted().length;
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        Item2 current = head.next;
        do{
            str.append(current.getData()).append("\n");
            current=current.next;
        }while(current!=head);
        return str.toString();
    }

    public boolean equals(Object object) {
        if(this==object){
            return true;
        }
        if (object instanceof ScientificLibrary library) {
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
        Item2 current = head.next;
        do{
            hash ^= current.getData().hashCode();
            current=current.next;
        }while(current!=head);
        return this.size ^ hash;
    }

    public Object clone(){
        return new ScientificLibrary(this.getHalls());
    }
}

class Item2 implements Serializable{
    public IHall data;
    public Item2 prev;
    public Item2 next;
    public Item2(IHall data) {
        this.data = data;
        this.next = null;
    }
    public IHall getData(){
        return this.data;
    }
}

