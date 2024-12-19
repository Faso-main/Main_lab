package main;
import ChildrenLibrary.*;
import ScientificLibrary.*;
import Library.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        ScientificBook[] book1 = new ScientificBook[]{new ScientificBook("Автор1", "Книга1", 200, 2000, 10),
                new ScientificBook("Автор2", "Книга2", 201, 2001, 11)};
        ScientificBook[] book2 = new ScientificBook[]{(ScientificBook) book1[0].clone(), (ScientificBook) book1[1].clone()};
        System.out.println(book1[0].equals(book2[1]));
        ScientificLibraryHall[] hall1 = new ScientificLibraryHall[]{new ScientificLibraryHall("Первый зал", book1)};
        ScientificLibraryHall[] hall2 = new ScientificLibraryHall[]{(ScientificLibraryHall) hall1[0].clone()};
        ScientificLibrary first = new ScientificLibrary(hall1);
        ScientificLibrary second = (ScientificLibrary) first.clone();
        System.out.println(hall1[0].equals(hall2[0]));
        first.showBooks();
        second.showBooks();
        System.out.println(first.equals(second));
        System.out.println("========================");
        System.out.println(first.hashCode());
        System.out.println(second.hashCode());
        second.removeBook(1);
        System.out.println(second.hashCode());
        System.out.println("========================");
        ScientificLibrary library = (ScientificLibrary) first.clone();
        library.showBooks();
        System.out.println("=");
        first.showBooks();
        System.out.println("========================");
        first.removeBook(0);
        library.showBooks();
        System.out.println("=");
        first.showBooks();
        System.out.println("========================");
        ChildenBook[] books = new ChildenBook[]{new ChildenBook("ДетАвтор", "ДетКнига", 200, 2001, 5), new ChildenBook("ДетАвторы", "ДетКниги", 210, 2021, 6)};
        ChildenLibraryHall[] childhall = new ChildenLibraryHall[]{(new ChildenLibraryHall("Первый зал", books))};
        ChildenLibraryHall[] childHALL = new ChildenLibraryHall[]{(ChildenLibraryHall) childhall[0].clone()};
        System.out.println(childHALL[0].equals(childhall[0]));
        ChildrenLibrary library3 = new ChildrenLibrary(childhall);
        ChildrenLibrary library4 = (ChildrenLibrary) library3.clone();
        library3.showBooks();
        System.out.println("=");
        library4.showBooks();
        System.out.println("===");
        library3.removeBook(1);
        library3.showBooks();
        System.out.println("=");
        library4.showBooks();
        System.out.println("=====================================================");
        try {
            FileOutputStream fileOutputStream1 = new FileOutputStream("library_data1.txt");
            FileOutputStream fileOutputStream2 = new FileOutputStream("library_data2.txt");
            Library.outputLibrary(library3, fileOutputStream1);
            Library.outputLibrary(first, fileOutputStream2);
            FileInputStream fileInputStream1 = new FileInputStream("library_data1.txt");
            FileInputStream fileInputStream2 = new FileInputStream("library_data2.txt");
            ChildrenLibrary restoredChildrenLibrary = (ChildrenLibrary) Library.inputLibrary(fileInputStream1);
            ScientificLibrary restoredScientificLibrary = (ScientificLibrary) Library.inputLibrary(fileInputStream2);
            if (library3.equals(restoredChildrenLibrary) && first.equals(restoredScientificLibrary)) {
                System.out.println("Исходные и восстановленные объекты совпадают");
            } else {
                System.out.println("Исходные и восстановленные объекты не совпадают");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("=====================================================");
        try {
            Writer fileWriter1 = new FileWriter("library_data3.txt");
            Writer fileWriter2 = new FileWriter("library_data4.txt");
            Library.writeLibrary(library3, fileWriter1);
            Library.writeLibrary(first, fileWriter2);
            fileWriter1.close();
            fileWriter2.close();
            Reader fileReader1 = new FileReader("library_data3.txt");
            Reader fileReader2 = new FileReader("library_data4.txt");
            ChildrenLibrary restoredChildrenLibrary = (ChildrenLibrary) Library.readLibrary(fileReader1);
            ScientificLibrary restoredScientificLibrary = (ScientificLibrary) Library.readLibrary(fileReader2);
            fileReader1.close();
            fileReader2.close();
            if (library3.equals(restoredChildrenLibrary) && first.equals(restoredScientificLibrary)) {
                System.out.println("Исходные и восстановленные объекты совпадают");
            } else {
                System.out.println("Исходные и восстановленные объекты не совпадают");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("=====================================================");
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
        if (first.equals(deserializedLibrary)) {
            System.out.println("Исходные и восстановленные объекты совпадают");
        } else {
            System.out.println("Исходные и восстановленные объекты не совпадают");
        }
    }
}


