package Book;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import Lend.*;
import Main.Main;
import Mgr.*;
import User.User;

public class BookMgr<T extends Book> extends Manager<T> {

	private ArrayList<T> bookList = new ArrayList<>();
	private HashMap<String, T> bookMap = new HashMap<>();
	
	public ArrayList<T> getBookList() {
		return this.bookList;
	}
	
	public HashMap<String, T> getBookMap() {
		return this.bookMap;
	}
	
	public void addBookList(T t) {
		bookList.add(t);
		bookMap.put(t.getBookNumber(), t);
	}
	
	@Override
	public void readFile(String filename, Factory<T> fac) {

		T t = null;
		Scanner fs = openFile(filename);

		while (fs.hasNext()) {

			t = fac.create();
			t.read(fs);

			if (bookList.contains(t))
				continue;
			bookList.add(t);
			bookMap.put(t.getBookNumber(), t);

		}

		fs.close();
	}
	
	@Override
	public void writeFile(String filename) {
		super.writeFile(filename);

		try {
			FileWriter fw = makeWriter(filename);
			for (T t : bookList) {
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
		for (T t : bookList) {
			System.out.println("==============================================");
			t.print();
		}
	}

	public void makeBook(String userId, String carNumber,String date, String startTime, String endTime) {
		
		T b = (T) new Book();
		
		// 예약번호를 자동으로 입력
		String lastBookNum = bookList.get(bookList.size()-1).getBookNumber();
		lastBookNum = lastBookNum.replace("A2019-", "");
		b.SetBookNumber("A2019-"+(Integer.parseInt(lastBookNum)+1));
		
		b.setUserId(userId);
		b.setCarNumber(carNumber);
				
		System.out.println("예약 날짜을 원하시는 날짜를 입력 해 주세요. ( ex. 2019-12-5)");
		b.setDate(date);
		
		String[] bookTable = new String[24];
		bookTable = carBookTable(b.getCarNumber(), date);
		
		int[] time = newBookTime(bookTable, startTime, endTime);
		

		
		if(time[0]==0 && time[1]==0) {
			
			System.out.println("예약을 취소합니다.");
			
		} else {
			
			b.setStartTime(time[0]);
			b.setEndTime(time[1]);
			b.setUsePrice((b.getEndTime()-b.getStartTime())
					* Main.cmgr.findMap(b.getCarNumber()).getPrice());
			
			b.print();
			
			Main.cmgr.findMap(b.getCarNumber()).addCBList(b);
			this.bookList.add(b);
			Main.me.addBook(b);
			bookMap.put(b.getBookNumber(), b);
		}
		
	}
	
	
	String newBookCarNumber(Scanner scan) {
		int num;
		
		System.out.println("예약할 차량을 선택 해 주세요.");
	
		int count = 1;
		for(Car c : Main.cmgr.getLendList()) {
			System.out.printf("[%d]--------------\n", count++);
			c.print();
		}
		num = scan.nextInt();
		
		return Main.cmgr.getLendList().get(num-1).getSigniture();
	}
	
	String[] carBookTable(String carNumber, String dateText) {
		
		String[] time = new String[24];
		
		for(int i=0;i<24;i++) {
			time[i] = " ";
		}
		
		ArrayList<Book> thisDay = new ArrayList<>();
		Car c = Main.cmgr.findMap(carNumber);
		
		for(Book bb : c.getmyCBookList()) {
			if(bb.getDateText().equals(dateText)) {
				thisDay.add(bb);
			}
		}
		
		int tdCount = thisDay.size(); // 해당날짜에 예약이 몇개나 되어있는지
		int[][] useTime = new int[tdCount][2]; // [n번째 예약][0=시작시간 1=종료시간]
		
		int count=0;
		
		for(Book bb : thisDay) {
			useTime[count][0] = bb.getStartTime();
			useTime[count++][1] = bb.getEndTime();
		}
	
		for(int i=0;i<24;i++) {
			System.out.printf("[%2d]",i+1);
		}
		System.out.println();
		
		timeCompare(useTime, time);
		
		for(int i=0;i<24;i++) {
			System.out.printf("[%2s]",time[i]);
		}
		System.out.println();
		
		return time;
	}
	
	int[] newBookTime(String[] bookTable, String sT, String eT) {
		
		int[] times = new int[2];
		int startTime=0, endTime=0;

		while(true) {
			
			boolean result = true;
			
			
			System.out.println("예약 시작시간과 종료시간을 입력 해 주세요 ( ex. 05 11 / 종료시 0 0 입력)");
			
			startTime = Integer.parseInt(sT);
			endTime = Integer.parseInt(eT);;
			if(startTime==0 && endTime==0) {
				times[0]=0;
				times[1]=0;
				return times;
			}
			
			for(int i=startTime;i<endTime;i++) {
				
				if(bookTable[i].equals("■"))
					result = false;
				
			}
			
			if(result) {
				
				times[0] = startTime;
				times[1] = endTime;
				return times;
				
			} else {
				System.out.println("예약되지 않은 시간을 선택 해 주세요!");
			}
		}
		
	}
	
	void timeCompare(int[][] useTime, String[] time) {
		
		for(int[] a : useTime) {
			for(int i=a[0]-1;i<=a[1]-1;i++) {
				time[i] = "■";
			}
		}
		
	}
	// 새로 예약을 할 때 어느시간이 비어있는지 가르쳐 줌
	public void emptyTime(Book b, String dateText, Scanner scan) {
		
		// 시간 배열 값이 0이면 예약중 아니면 비어있음.
		String[] time = new String[24];
		
		for(int i=0;i<24;i++) {
			time[i] = " ";
		}
		
		ArrayList<Book> thisDay = new ArrayList<>();
		Car c = Main.cmgr.findMap(b.getCarNumber());
		
		for(Book bb : c.getmyCBookList()) {
			if(bb.getDateText().equals(dateText)) {
				thisDay.add(bb);
			}
		}
		
		int tdCount = thisDay.size();
		int[][] useTime = new int[tdCount][2];
		
		int count=0;
		
		for(Book bb : thisDay) {
			useTime[count][0] = bb.getStartTime();
			useTime[count++][1] = bb.getEndTime();
		}
	
		for(int i=0;i<24;i++) {
			System.out.printf("[%2d]",i+1);
		}
		System.out.println();
		
		timeCompare(useTime, time);
		
		for(int i=0;i<24;i++) {
			System.out.printf("[%2s]",time[i]);
		}
		System.out.println();
		
		int startTime=0, endTime=0;

		while(true) {
			
			boolean result = true;
			
			startTime = scan.nextInt();
			endTime = scan.nextInt();
			
			System.out.println("예약 시작시간과 종료시간을 입력 해 주세요 ( ex. 05 11 / 종료시 0 0 입력)");
			
			for(int i=startTime;i<endTime;i++) {
				if(time[i].equals("■"))
					result = false;
			}
			if(result) {
				b.setStartTime(startTime);
				b.setEndTime(endTime);
				break;
			} else {
				System.out.println("예약되지 않은 시간을 선택 해 주세요!");
				if(startTime==0 && endTime==0) {
					break;
				}
			}
		}
	}
	
}