package Lend;

import Mgr.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;

import Book.Book;

public class Car implements Lendable {

	private String carNumber;
	private String carName;
	private String price;
	private ArrayList<Book> myCBookList;
	private String ImageFile;
	private ImageIcon image;

	public void addCBList(Book b) {
		myCBookList.add(b);
	}

	public void setMyCBookList(ArrayList<Book> bList) {
		this.myCBookList = bList;
	}

	public ArrayList<Book> getmyCBookList() {
		return this.myCBookList;
	}

	@Override
	public String getSigniture() {
		return carNumber;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return carName;
	}

	@Override
	public int getPrice() {
		return Integer.parseInt(this.price);
	}

	public ImageIcon getImage() {
		return image;
	}

	@Override
	public void setLendBookLIst(ArrayList<Book> bList) {
		this.myCBookList = bList;
	}

	@Override
	public void read(Scanner scan) {
		this.carNumber = scan.next();
		this.carName = scan.next();
		this.price = scan.next();
		ImageFile = scan.next();
		image = new ImageIcon(ImageFile);
	}

	@Override
	public void writer(FileWriter fw) {
		try {
			String text = "\n" + carNumber + " " + carName + " " + price + " " + ImageFile;
			fw.write(text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void print() {
		System.out.println("차량번호 : " + this.carNumber);
		System.out.println("차량명 : " + this.carName);
		System.out.println("시간당 요금 : " + this.price);
	}

	@Override
	public boolean matches(String kwd) {
		return (this.carNumber.equals(kwd) || this.carName.contains(kwd));
	}

	public boolean matches(String min, String max) {
		return (Integer.parseInt(this.price) >= Integer.parseInt(min)
				&& Integer.parseInt(this.price) <= Integer.parseInt(max));
	}

	@Override
	public boolean removeCheck(String kwd) {
		return this.carNumber.equals(kwd);
	}

}