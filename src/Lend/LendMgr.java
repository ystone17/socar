package Lend;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import Book.Book;
import Main.Main;
import Mgr.*;

public class LendMgr<T extends Lendable> extends Manager<T> {

	private ArrayList<T> List = new ArrayList<>();
	private HashMap<String, T> lendMap = new HashMap<>();
		
	public ArrayList<T> getLendList() {
		return this.List;
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
			t.setLendBookLIst(bookMatches(t.getSigniture()));
			
			if (List.contains(t))
				continue;
			List.add(t);
			lendMap.put(t.getSigniture(), t);

		}

		fs.close();
	}

	@Override
	public void writeFile(String filename) {
		super.writeFile(filename);
		try {
			FileWriter fw = makeWriter(filename);
			for (T t : List) {
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
		if (List == null) {
			System.out.println("차량이 없습니다.");
		} else {
			for (T t : List) {
				t.print();
			}
		}
	}

	// 새로운 물품 추가
	public void newThings(Scanner scan, Factory<T> fac, String Msg) {
		T t = null;
		t = fac.create();
		System.out.print(Msg);
		t.read(scan);
		lendMap.put(t.getSigniture(), t);
		List.add(t);
	}

	public String newBookItem(Scanner scan) {

		int num;
		System.out.println("대여할 차량을 선택 해 주세요");

		for (int i = 0; i < List.size(); i++) {
			System.out.printf("%d. ", i + 1);
			List.get(i).print();
		}

		num = scan.nextInt() - 1;

		return List.get(num).getSigniture();
	}

	// 기존 물품 삭제
	public void removeThings(Scanner scan) {

		System.out.println("제거할 대상의 번호를 입력 해 주세요.");
		String kwd = scan.next();
		T t = null;

		for (int i = 0; i < List.size(); i++) {
			t = List.get(i);

			if (t.removeCheck(kwd))
				List.remove(i);
		}
	}
	
	@Override
	public T findMap(String kwd) {
		return lendMap.get(kwd);
	}
}
