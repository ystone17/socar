package Book;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import Lend.Car;
import Main.Main;
import Mgr.*;

public class Book implements Manageable {

	private String bookNumber; // 예약코드
	private String userId;
	private String carNumber;
	private Date date;
	private int startTime;
	private int endTime;
	private int usePrice;
	
	public String getDateText() {
		return date.getYear() +"-"+(date.getMonth()+1)+"-"+date.getDate();
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public int getStartTime() {
		return this.startTime;
	}
	
	public int getEndTime() {
		return this.endTime;
	}
	
	public int getUsePrices() {
		return this.usePrice;
	}

public String[] getRowString() {
		String[] s = {getBookNumber(),getUserId(),getCarNumber(),getDateText(),
				getStartTime()+"",getEndTime()+"",getUsePrices()+""};
		return s;
	}
	
	
	public void setUsePrice(int usePrice) {
		this.usePrice = usePrice;
	}
	
	public String getBookNumber() {
		return this.bookNumber;
	}
	
	public String getUserId() {
		return this.userId;
	}
	
	public String getCarNumber() {
		return this.carNumber;
	}
	
	public void SetBookNumber(String bookNumber) {
		this.bookNumber = bookNumber;
	}
	
	
	public void setDate(String str) {
		String[] ymd = str.split("-");
		this.date = new Date(Integer.parseInt(ymd[0]), Integer.parseInt(ymd[1])-1, Integer.parseInt(ymd[2]));
	}
	
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	
	@Override
	public void read(Scanner scan) {
		this.bookNumber = scan.next();
		this.userId = scan.next();
		this.carNumber = scan.next();
		setDate(scan.next());
		this.startTime = scan.nextInt();
		this.endTime = scan.nextInt();
		this.usePrice = scan.nextInt();
	}
	
	@Override
	public void writer(FileWriter fw) {
		try {
			String d = date.getYear() +"-"+ (date.getMonth()+1) +"-"+ date.getDate();
			String text = "\n"+bookNumber+" "+userId+" "+carNumber+" "+d+" "+startTime+" "+endTime+" "+usePrice;
			fw.write(text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void print() {
		System.out.println("==============================================");
		System.out.println("예약번호 : " + bookNumber);
		System.out.println("예약자ID : " + userId);
		System.out.println("예약차량번호 : " + carNumber);
		System.out.println("예약 날짜 : " + date.getYear() +"년 " + (date.getMonth()+1) +"월 " + date.getDate()+"일 / "+startTime +"시 ~ " + endTime+"시");
		System.out.println("이용요금 : " + usePrice);
	}

	@Override
	public boolean matches(String kwd) {
		return false;
	}

}
