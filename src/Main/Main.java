package Main;

import java.util.Date;
import java.util.Scanner;

import Account.Account;
import Account.AccountMgr;
import Book.Book;
import Book.BookMgr;
import Lend.Car;
import Lend.LendMgr;
import License.License;
import License.LicenseMgr;
import Mgr.Factory;
import User.User;
import User.UserMgr;

public class Main {
	
	public static Date today = new Date();
	public static User me;

	public static void main(String[] args) {
		Main main = new Main();
		main.doit();
	}

	public static BookMgr<Book> bmgr = new BookMgr();
	public static AccountMgr<Account> acmgr = new AccountMgr();
	public static UserMgr<User> umgr = new UserMgr();
	public static LicenseMgr<License> lmgr = new LicenseMgr();
	public static LendMgr<Car> cmgr = new LendMgr<>();
	Scanner scan = new Scanner(System.in);
	
	public static void setUser(User u) {
		me = u;
	}
	
	void doit() {
		readAll();
		showGui();
	}

	void readAll() {

		bmgr.readFile("Book.txt", new BookFactory());
		acmgr.readFile("Account.txt", new AccountFactory());
		umgr.readFile("User.txt", new UserFactory());
		cmgr.readFile("Car.txt", new CarFactory());
		lmgr.readFile("License.txt", new LicenseFactory());

	}

	public static void writeAll() {

		acmgr.writeFile("Account.txt");
		umgr.writeFile("User.txt");  
		cmgr.writeFile("Car.txt");   
		bmgr.writeFile("Book.txt");
		lmgr.writeFile("License.txt");

	}

	void Menu() {
		int num = 99;
		while (num != 0) {
			System.out.println("==============================================");
			System.out.println("1. 로그인\\t\\t2. 회원가입\\t0. End");
			System.out.println("==============================================");
			num = scan.nextInt();
			switch (num) {
			case 1:
				signOn(); // 로그인
				whoami(); // 권한에 맞는 메뉴 실행
				break;
			case 2:
				signUp(); // 회원가입
				break;
			case 0:
				System.out.println("프로그램을 종료합니다...");
				break;
			}
		}
	}

	// 회원가입
	void signUp() {
		umgr.signUp(acmgr.signUp(scan));
	}

	// 로그인 작업
	void signOn() {
		while (me == null) {
			me = umgr.login(scan, acmgr.getAcList());
		}
	}

	// 권한 확인 후 해당 메뉴 실행
	void whoami() {
		switch (me.getlevel()) {
		case "Normal":
			runUserMenu();
			break;
		case "Admin":
			runAdminMenu();
			break;
		}
	}

	// 사용자 메뉴 실행
	void runUserMenu() {

		int num = 99;

		while (num != 0) {

			if (me == null)
				break;

			System.out.println("==============================================");
			System.out.printf("%s회원님\\n", me.getName());
			System.out.println("====================메뉴 선택====================");
			System.out.println("1. 비밀번호 변경\t 2. 차량 예약\t 3. 예약정보 조회\t 4.면허정보 등록\t 0.종료");
			num = scan.nextInt();

			switch (num) {
			case 1:
				acmgr.changePwd(scan);
				break;
			case 2:
				//bmgr.makeBook(me, scan);
				break;
			case 3:
				me.print();
				break;
			case 0:
				me = null;
				break;
			}
		}

	}

	// 관리자 메뉴 실행
	void runAdminMenu() {

		int num = 99;

		while (num != 0) {

			if (me == null)
				break;

			System.out.println("==============================================");
			System.out.printf("[%s] %s님\n", me.getlevel(), me.getName());
			System.out.println("====================메뉴 선택====================");
			System.out.println("1. 차량추가\t 2. 차량삭제\t 3.예약정보 조회\t 0. 종료");
			num = scan.nextInt();

			switch (num) {
			case 1:
				cmgr.newThings(scan, new CarFactory(), "차량정보를 입력 해 주세요.(차량번호, 차량명, 등급, 시간당요금)");
				cmgr.printAll();
				break;
			case 2:
				cmgr.removeThings(scan);
				cmgr.printAll();
				break;
			case 3:
				bmgr.printAll();
				break;
			case 0:
				me = null;
				System.out.println("==============================================");
				System.out.println("관리자 메뉴를 종료합니다.");
				break;
			}
		}

	}

	void showGui() {
		new swing.SwingMain();
	}

	class CarFactory implements Factory<Car> {
		@Override
		public Car create() {
			return new Car();
		}
	}

	class UserFactory implements Factory<User> {
		@Override
		public User create() {
			return new User();
		}
	}

	class AccountFactory implements Factory<Account> {
		@Override
		public Account create() {
			return new Account();
		}
	}

	class BookFactory implements Factory<Book> {
		@Override
		public Book create() {
			return new Book();
		}
	}
	
	class LicenseFactory implements Factory<License> {
		@Override
		public License create() {
			return new License();
		}
	}

}
