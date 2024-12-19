package interfaceLab;

public interface IBook {
    public void setName(String name);
    public void setAuthor(String author);
    public void setYear(int year);
    public void setCost(double cost);
    public String getName();
    public String getAuthor();
    public int getYear();
    public double getCost();
    public int hashCode();
    public Object clone();
}
