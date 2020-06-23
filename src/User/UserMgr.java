package User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import Book.*;
import Account.*;
import Lend.*;
import Main.Main;
import Mgr.*;

public class UserMgr<T extends User> extends Manager<T> {
	
	private ArrayList<T> userList = new ArrayList<>();	
	private HashMap<String, T> userMap = new HashMap<>();
	
	public ArrayList<T> getUserList() {
		return userList;
	}
	
	public void putUserMap(T u) {
		u.setLevel("Normal");
		userMap.put(u.getId(), u);
	}
	
	public ArrayList<Book> bookMatches(String kwd) {
		
		ArrayList<Book> myList = new ArrayList<>();
		for(Book b : Main.bmgr.getBookList()) {
			if(b.getCarNumber().equals(kwd) || b.getUserId().equals(kwd))
					myList.add(b);
		}
		return myList;
		
	}
	
	@Override
	public void readFile(String filename, Factory<T> fac) {

		T t = null;
		Scanner fs = openFile(filename);

		while (fs.hasNext()) {

			t = fac.create();
			t.read(fs);
			t.setUserBookList(bookMatches(t.getId()));
			if (userList.contains(t))
				continue;
			userList.add(t);
			userMap.put(t.getId(), t);
			
		}

		fs.close();
	}
	
	@Override
	public void writeFile(String filename) {
		super.writeFile(filename);

		try {
			FileWriter fw = makeWriter(filename);
			for (T t : userList) {
//				t.print();
				t.writer(fw);
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void printAll() {
		for(T t : userList) {
			t.print();
		}
	}
	
	public void signUp(T t) {
		userList.add(t);
		userMap.put(t.getId(), t);
	}
	
	public User login(Scanner scan, ArrayList<Account> arrayList) {
		
		String id, pwd;
		
		System.out.print("ID : ");
		id = scan.next();
		System.out.print("Pwd : ");
		pwd = scan.next();
		
		Account a = null;
		
		for(T t : userList) {
			if(t.getId().equals(id)) {
				for(Account m2 : arrayList) {
					a = m2;
					if(a.getId().equals(id) && a.getPwd().equals(pwd))
						return t;
				}
			}
		}
		
		System.out.println("아이디 또는 비밀번호가 올바르지 않습니다.");
		return null;
	}
	
	@Override
	public T findMap(String kwd) {
		return userMap.get(kwd);
	}
	
	public void removeHashMap(T t){
		userMap.remove(t.getId());
	}
}
