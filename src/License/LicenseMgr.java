package License;

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

public class LicenseMgr<T extends License> extends Manager<T> {

	private ArrayList<T> licenseList = new ArrayList<>();
	private HashMap<String, T> licenseMap = new HashMap<>();
	
	public ArrayList<T> getLicenseList() {
		return this.licenseList;
	}
	public HashMap<String, T> getLicenseMap() {
		return this.licenseMap;
	}
	
	@Override
	public void readFile(String filename, Factory<T> fac) {

		T t = null;
		Scanner fs = openFile(filename);

		while (fs.hasNext()) {

			t = fac.create();
			t.read(fs);

			if (licenseList.contains(t))
				continue;
			licenseList.add(t);
			licenseMap.put(t.getLicenseNumber(), t);

		}

		fs.close();
	}
	
	@Override
	public void writeFile(String filename) {
		super.writeFile(filename);

		try {
			FileWriter fw = makeWriter(filename);
			for (T t : licenseList) {
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
		for (T t : licenseList) {
			System.out.println("==============================================");
			t.print();
		}
	}
	
	public boolean registeLicense(String Number, String Type, String get, String end) {
		
		T l = (T) new License();
		l.setLicenseNumber(Number);
		l.setLicenseType(Type);
		l.setLicenseStart(get);
		l.setLicenseEnd(end);
		
		for(License li : licenseList) {
			if(li.getLicenseNumber().equals(l.getLicenseNumber()))
				return true;
		}
		
		licenseList.add(l);
		licenseMap.put(l.getLicenseNumber(), l);
		
		return false;
		
	}
	
	@Override
	public T findMap(String kwd) {
		return licenseMap.get(kwd);
	}

}