package Lend;

import java.util.ArrayList;

import Book.Book;
import Mgr.*;

public interface Lendable extends Manageable {
	
	String getSigniture();
	int getPrice();
	String getName();
	void setLendBookLIst(ArrayList<Book> bList);
	boolean removeCheck(String kwd);
	
}
