package User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import Book.*;
import Main.Main;
import Mgr.*;

public class User implements Manageable {

	private String id;
	private String name;
	private Date birthDay = new Date();
	private String level;
	private String myLicenseNumber = "0";
	private ArrayList<Book> myBookList = null;

	public String lastBookString() {
		if (myBookList.isEmpty()) {
			return getId();
		}
		Book b = myBookList.get(myBookList.size() - 1);
		String lastBookString = Main.cmgr.findMap(b.getCarNumber()).getName() + " " + b.getDateText() + " "
				+ b.getStartTime() + "시부터 " + b.getEndTime() + "시까지";
		return lastBookString;
	}

	public String getMyLicenseNumber() {
		return this.myLicenseNumber;
	}

	public String[] getRowString() {
		String[] s = { id, name, getBirthDay(), level };
		return s;
	}

	public void initMyBook() {
		this.myBookList = new ArrayList<>();
	}

	public void addBook(Book b) {
		this.myBookList.add(b);
	}

	public ArrayList<Book> getBook() {
		if(myBookList ==null)
			return null;
		return myBookList;
	}

	public void setBirthDay(String kwd) {
		String[] ymd = kwd.split("-");
		this.birthDay.setYear(Integer.parseInt(ymd[0]));
		this.birthDay.setMonth(Integer.parseInt(ymd[1]));
		this.birthDay.setDate(Integer.parseInt(ymd[2]));

	}

	public void setUserBookList(ArrayList<Book> bList) {
		this.myBookList = bList;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getlevel() {
		return this.level;
	}

	public String getBirthDay() {
		return birthDay.getYear() + "-" + birthDay.getMonth() + "-" + birthDay.getDate();
	}

	public void setId(String kwd) {
		this.id = kwd;
	}

	public void setName(String kwd) {
		this.name = kwd;
	}

	public void setLevel(String kwd) {
		this.level = kwd;
	}

	public void setNewBook(Book b) {
		this.myBookList.add(b);
	}

	public void setLicenseNumber(String kwd) {
		this.myLicenseNumber = kwd;
	}

	@Override
	public void read(Scanner scan) {
		this.id = scan.next();
		this.name = scan.next();
		String birthDayText = scan.next();
		String[] ymd = birthDayText.split("-");
		this.birthDay.setYear(Integer.parseInt(ymd[0]));
		this.birthDay.setMonth(Integer.parseInt(ymd[1]));
		this.birthDay.setDate(Integer.parseInt(ymd[2]));
		this.myLicenseNumber = scan.next();
		this.level = scan.next();
	}

	@Override
	public void writer(FileWriter fw) {
		try {
			String text = "\n" + id + " " + name + " " + birthDay.getYear() + "-" + birthDay.getMonth() + "-"
					+ birthDay.getDate() + " " + myLicenseNumber + " " + level;
			fw.write(text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void print() {
		int sum = 0;
		System.out.println("ID : " + this.id);
		System.out.println("Name : " + this.name);
		System.out.println("Birthday : " + this.getBirthDay());
		System.out.println("Level : " + this.level);
		if (myBookList != null) {
			for (Book b : myBookList) {
				b.print();
				sum += b.getUsePrices();
			}
		} else
			System.out.println("예약 내역이 없습니다.");
		System.out.println("==============================================");
		System.out.println("비용 : " + sum);

	}

	@Override
	public boolean matches(String kwd) {
		return false;
	}
}
