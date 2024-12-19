package Library;
import ChildrenLibrary.*;
import ScientificLibrary.*;
import interfaceLab.*;
import java.io.*;

public class Library {
    public static void outputLibrary(ILibrary library, OutputStream out) throws IOException {
        try(DataOutputStream dataOut = new DataOutputStream(out)) {
            dataOut.writeUTF(library.getClass().getName()); //1
            dataOut.writeInt(library.getHalls().length);//2
            for (IHall hall : library.getHalls()) {
                dataOut.writeUTF(hall.getClass().getName());//3
                dataOut.writeUTF(hall.getName());//4
                dataOut.writeInt(hall.getBooks().length);//5
                for (IBook book : hall.getBooks()) {
                    dataOut.writeUTF(book.getClass().getName()); //6
                    dataOut.writeUTF(book.getName()); //7
                    dataOut.writeUTF(book.getAuthor());//8
                    dataOut.writeDouble(book.getCost());//9
                    dataOut.writeInt(book.getYear());//10
                    if(book.getClass().getName().equals("ChildrenLibrary.ChildenBook")){
                        ChildenBook chBook = (ChildenBook) book;
                        dataOut.writeInt(chBook.getMinOld()); //11
                    } else if (book.getClass().getName().equals("ChildrenLibrary.ScientificBook")) {
                        ScientificBook scBook = (ScientificBook) book;
                        dataOut.writeDouble(scBook.getIndex());//11
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ILibrary inputLibrary (InputStream in){
        try (DataInputStream dataIn = new DataInputStream(in)) {
            ILibrary library = null;
            String libraryType = dataIn.readUTF();//1
            int numHalls = dataIn.readInt();//2
            IHall[] halls = new IHall[numHalls];
            for (int i = 0; i < numHalls; i++) {
                String hallType = dataIn.readUTF();//3
                String hallName = dataIn.readUTF();//4
                int numBooks = dataIn.readInt();//5
                IHall hall = null;
                if(hallType.equals("ScientificLibrary.ScientificLibraryHall")){
                    hall = new ScientificLibraryHall(hallName, numBooks);
                }
                if(hallType.equals("ChildrenLibrary.ChildenLibraryHall")){
                    hall = new ChildenLibraryHall(hallName, numBooks);
                }
                IBook[] books = new IBook[numBooks];
                for (int j = 0; j < numBooks; j++) {
                    String bookType = dataIn.readUTF();//6
                    String name = dataIn.readUTF();//7
                    String author = dataIn.readUTF();//8
                    double cost = dataIn.readDouble();//9
                    int year = dataIn.readInt();//10
                    double index = 0;
                    if(bookType.equals("ChildrenLibrary.ScientificBook")) {
                        index = dataIn.readDouble();//11
                    } else if (bookType.equals("ChildrenLibrary.ChildenBook")) {
                        index = dataIn.readInt();
                    }
                    IBook book = null;
                    if(bookType.equals("ChildrenLibrary.ScientificBook")){
                        book = new ScientificBook(author,name,cost,year,index);
                    }
                    else if(bookType.equals("ChildrenLibrary.ChildenBook")){
                        book = new ChildenBook(author,name,cost,year, (int)index);
                    }
                    else{
                        book = new Book(name,author,cost,year);
                    }
                    hall.replaceBook(j, book);
                }
                halls[i] = hall;
            }
            if(libraryType.equals("ScientificLibrary.ScientificLibrary")){
                library = new ScientificLibrary(halls);
            }
            if(libraryType.equals("ChildrenLibrary.ChildrenLibrary")){
                library = new ChildrenLibrary(halls);
            }
            return library;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeLibrary (ILibrary lib, Writer out){
        try (BufferedWriter writer = new BufferedWriter(out)) {
            writer.write('"' + lib.getClass().getName() + '"' + " ");
            writer.write(lib.getHalls().length + " ");
            for (int i=0; i<lib.getHalls().length; i++) {
                IHall hall = lib.getHall(i);
                writer.write('"' + hall.getClass().getName() + '"' + " ");
                writer.write('"' + hall.getName() + '"' + " ");
                writer.write(hall.getBooks().length + " ");
                for (int j=0; j < hall.getBooks().length; j++) {
                    IBook book = hall.getBook(j).get();
                    writer.write('"' + book.getClass().getName() + '"' + " ");
                    writer.write('"' + book.getName() + '"' + " ");
                    writer.write('"' + book.getAuthor() + '"' + " ");
                    writer.write(book.getCost() + " ");
                    writer.write(book.getYear() + " ");
                    if(book.getClass().getName().equals("ChildrenLibrary.ChildenBook")) {
                        ChildenBook chBook = (ChildenBook) book;
                        writer.write(chBook.getMinOld() + " ");
                    }
                    else if(book.getClass().getName().equals("ChildrenLibrary.ScientificBook")) {
                        ScientificBook scBook = (ScientificBook) book;
                        writer.write(scBook.getIndex() + " ");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ILibrary readLibrary (Reader in){
        ILibrary library = null;
        IHall hall = null;
        IBook book = null;
        try (BufferedReader reader = new BufferedReader(in)) {
            StreamTokenizer tokenizer = new StreamTokenizer(in);
            tokenizer.quoteChar('"');
            tokenizer.nextToken();
            String libraryType = tokenizer.sval;
            tokenizer.nextToken();
            int numHalls = (int) tokenizer.nval;
            IHall[] halls = new IHall[numHalls];
            for (int i = 0; i < numHalls; i++) {
                tokenizer.nextToken();
                String hallType = tokenizer.sval;
                tokenizer.nextToken();
                String nameHall = tokenizer.sval;
                tokenizer.nextToken();
                int numBooks = (int) tokenizer.nval;
                IBook[] books = new IBook[numBooks];
                for (int j = 0; j < numBooks; j++) {
                    tokenizer.nextToken();
                    String bookType = tokenizer.sval;
                    tokenizer.nextToken();
                    String name = tokenizer.sval;
                    tokenizer.nextToken();
                    String author = tokenizer.sval;
                    tokenizer.nextToken();
                    double cost = tokenizer.nval;
                    tokenizer.nextToken();
                    int year = (int) tokenizer.nval;
                    if(bookType.equals("ChildrenLibrary.ScientificBook")){
                        tokenizer.nextToken();
                        int index = (int) tokenizer.nval;
                        book = new ScientificBook(author, name, cost, year, index);
                    } else if (bookType.equals("ChildrenLibrary.ChildenBook")) {
                        tokenizer.nextToken();
                        int index = (int) tokenizer.nval;
                        book = new ChildenBook(author, name, cost, year, index);
                    }
                    else{
                        book = new Book(author, name, cost, year);
                    }
                    books[j] = book;
                }
                if(hallType.equals("ScientificLibrary.ScientificLibraryHall")){
                    halls[i] = new ScientificLibraryHall(nameHall,books);
                }
                else if(hallType.equals("ChildrenLibrary.ChildenLibraryHall")){
                    halls[i] = new ChildenLibraryHall(nameHall, books);
                }
            }
            if(libraryType.equals("ScientificLibrary.ScientificLibrary")){
                library = new ScientificLibrary(halls);
            }
            else if(libraryType.equals("ChildrenLibrary.ChildrenLibrary")){
                library = new ChildrenLibrary(halls);
            }
            return library;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
