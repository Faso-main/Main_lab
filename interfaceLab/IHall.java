package interfaceLab;
import java.util.Optional;

public interface IHall {
    public int getLongBooks();
    public void showBooks();
    public Optional<IBook> getBook(int index);
    public IBook[] getBooks();
    public void replaceBook(int index, IBook book);
    public void addBook(int index, IBook book);
    public void addBook(IBook book);
    public void removeBook(int index);
    public int getCount();
    public double totalCosts();
    public String getName();
    public IBook getBest();
    public void setName(String name);
    public int getSize();
    public IBook[] getSorted();
    public int hashCode();
    public Object clone();
}
