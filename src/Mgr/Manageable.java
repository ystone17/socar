package Mgr;
import java.io.FileWriter;
import java.util.Scanner;

public interface Manageable {
	void read(Scanner scan);
	void print();
	void writer(FileWriter fw);
	boolean matches(String kwd);
}
