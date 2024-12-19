package interfaceLab;


import java.util.Optional;

public interface ILibrary {
     public int getSize();
     public int getCountBook();
     public double getCost();
     public IHall[] getHalls();
     public IHall getHall(int num);
     public Optional<IBook> getBook(int num);
     public IBook[] getSorted();
     public void showBooks();
     public void replaceHall(int num, IHall hall);
     public void replaceBook(int num, IBook book);
     public void addBook(int num, IBook book);
     public void removeBook(int num);
     public IBook getBestBook();
     public int hashCode();
     public Object clone();
}
