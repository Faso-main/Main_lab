package main;
import ChildrenLibrary.ScientificBook;
import ScientificLibrary.*;
import java.io.*;

public class Test {
    public static void main(String[] args) {
        ScientificBook[] book1 = new ScientificBook[]{new ScientificBook("Автор1", "Книга1", 200, 2000, 10),
                new ScientificBook("Автор2", "Книга2", 201, 2001, 11)};
        ScientificBook[] book2 = new ScientificBook[]{(ScientificBook)book1[0].clone(), (ScientificBook)book1[1].clone()};
        ScientificLibraryHall[] hall1 = new ScientificLibraryHall[]{new ScientificLibraryHall("Первый зал", book1)};
        ScientificLibrary first = new ScientificLibrary(hall1);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("book.ser"))) {
            out.writeObject(first);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ScientificLibrary deserializedLibrary = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("book.ser"))) {
            deserializedLibrary = (ScientificLibrary) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(deserializedLibrary);
    }
}

