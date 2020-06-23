package Account;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Mgr.*;
import User.*;

public class Account implements Manageable {

	private String id;
	private String pwd;

	public void setId(String kwd) {
		this.id = kwd;
	}
	
	public void setPwd(String kwd) {
		this.pwd = kwd;
	}

	public String getId() {
		return this.id;
	}

	public String getPwd() {
		return this.pwd;
	}

	@Override
	public void read(Scanner scan) {
		this.id = scan.next();
		this.pwd = scan.next();
	}

	@Override
	public void print() {
		System.out.println("ID : " + this.id);
		System.out.println("Pwd : " + this.pwd);
	}
	
	@Override
	public void writer(FileWriter fw) {
		try {
			String text = "\n"+id+" "+pwd;
			fw.write(text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean matches(String kwd) {
		return false;
	}

	public boolean matches(String id, String pwd) {
		return (this.id.equals(id) && this.pwd.equals(pwd));
	}
}
