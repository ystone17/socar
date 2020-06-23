package Account;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import Mgr.*;
import User.*;

public class AccountMgr<T extends Account> extends Manager<T> {

	private ArrayList<T> accountList = new ArrayList<>();
	private HashMap<String, T> accountMap = new HashMap<>();

	public ArrayList<T> getAcList() {
		return accountList;
	}
	
	public void putAcMap(T a) {
		accountMap.put(a.getId(), a);
	}

	@Override
	public void readFile(String filename, Factory<T> fac) {

		T t = null;
		Scanner fs = openFile(filename);

		while (fs.hasNext()) {

			t = fac.create();
			t.read(fs);

			if (accountList.contains(t))
				continue;
			accountList.add(t);
			accountMap.put(t.getId(), t);
		}

		fs.close();
	}

	@Override
	public void writeFile(String filename) {
		super.writeFile(filename);

		try {
			FileWriter fw = makeWriter(filename);
			for (T t : accountList) {
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
		for (T t : accountList) {
			t.print();
		}
	}

	public User signUp(Scanner scan) {

		String id, pw = "1", pwcheck = "2", name, birthDay;

		T t = null;
		t = (T) new Account();
		
		System.out.println("ID : ");
		id = scan.next();
		while (!pw.equals(pwcheck)) {
			System.out.println("PW : ");
			pw = scan.next();
			System.out.println("PW Check : ");
			pwcheck = scan.next();
		}
		t.setId(id);
		t.setPwd(pw);
		accountList.add(t);
		accountMap.put(t.getId(), t);

		User u = new User();
		u.setId(id);
		System.out.println("Name : ");
		name = scan.next();
		u.setName(name);
		System.out.println("BirthDay(ex. 1995-03-12) : ");
		birthDay = scan.next();
		u.setBirthDay(birthDay);
		u.setLevel("Normal");
		u.initMyBook();
		return u;

	}

	public void changePwd(Scanner scan) {

		T t = checkUser(scan);

		if (t == null) {
			System.out.println("아이디 또는 패스워드가 올바르지 않습니다.");
		} else {
			while (true) {
				String kwd;
				System.out.print("변경할 패스워드 : ");
				kwd = scan.next();
				System.out.print("패스워드 확인 : ");
				if (kwd.equals(scan.next())) {
					t.setPwd(kwd);
					System.out.println("패스워드 변경 완료.");
					break;
				} else
					continue;
			}
		}
	}

	public T checkUser(Scanner scan) {

		String id;
		String pwd;

		System.out.print("ID : ");
		id = scan.next();
		System.out.print("Password : ");
		pwd = scan.next();

		for (T t : accountList) {
			if (t.getId().equals(id) && t.getPwd().equals(pwd))
				return t;
		}

		return null;
	}

	@Override
	public T findMap(String kwd) {
		return accountMap.get(kwd);
	}
	
	public void removeHashMap(T t){
		accountMap.remove(t.getId());
	}
}
