package License;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import Mgr.Manageable;

public class License implements Manageable {
	
	String licenseNumber;
	String licenseType;
	Date licenseStart;
	Date licenseEnd;
	
	public String getLicenseNumber() {
		return this.licenseNumber;
	}
	
	public String getLicenseType() {
		return this.licenseType;
		
	}
	
	public String getStartDayString() {
		return licenseStart.getYear()+"-"+(licenseStart.getMonth()+1)+"-"+licenseStart.getDate();
	}
	
	public String getEndDayString() {
		return licenseEnd.getYear()+"-"+(licenseEnd.getMonth()+1)+"-"+licenseEnd.getDate();
	}
	
	void setLicenseNumber(String LicenseNumber) {
		this.licenseNumber = LicenseNumber;
	}
		
	void setLicenseType(String LicenseType) {
		this.licenseType = LicenseType;
	}
	
	void setLicenseStart(String LicenseStart) {
		String[] ymd = LicenseStart.split("-");
		this.licenseStart = new Date();
		this.licenseStart.setYear(Integer.parseInt(ymd[0]));
		this.licenseStart.setMonth(Integer.parseInt(ymd[1])-1);
		this.licenseStart.setDate(Integer.parseInt(ymd[2]));
	}
	
	void setLicenseEnd(String LicenseEnd) {
		String[] ymd = LicenseEnd.split("-");
		this.licenseEnd = new Date();
		this.licenseEnd.setYear(Integer.parseInt(ymd[0]));
		this.licenseEnd.setMonth(Integer.parseInt(ymd[1])-1);
		this.licenseEnd.setDate(Integer.parseInt(ymd[2]));
	}
	
	@Override
	public void read(Scanner scan) {
		this.licenseNumber = scan.next();
		this.licenseType = scan.next();
		String startDay = scan.next();
		String endDay = scan.next();
		setLicenseStart(startDay);
		setLicenseEnd(endDay);
	}

	@Override
	public void print() {

		System.out.println("면허번호 : " + this.licenseNumber);
		System.out.println("면허종류 : " + this.licenseType);
		System.out.println("발급일자 : " + licenseStart.getYear()+"-"+licenseStart.getMonth()+"-"+licenseStart.getDate());
		System.out.println("종료일자 : " + licenseEnd.getYear()+"-"+licenseEnd.getMonth()+"-"+licenseEnd.getDate());
		
	}

	@Override
	public void writer(FileWriter fw) {
		
		String text = "\n"+licenseNumber+" "+licenseType+" "+getStartDayString()+" "+getEndDayString();
		try {
			fw.write(text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean matches(String kwd) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
}
