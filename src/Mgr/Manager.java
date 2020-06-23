package Mgr;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import User.*;
import Account.*;
import Book.*;
import Lend.*;

public class Manager<T extends Manageable> {

	public void readFile(String filename, Factory<T> fac) {
	}

	public void writeFile(String filename) {
		try {
			FileWriter init = new FileWriter(filename);
			init.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printAll() {
	}

	public FileWriter makeWriter(String filename) {

		FileWriter fw = null;

		try {
			fw = new FileWriter(filename, true);
			return fw;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void search(String kwd) {
	}

	public T findMap(String kwd) {
		return null;
	}

	public Scanner openFile(String filename) {
		Scanner scan = null;
		try {
			scan = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		scan.nextLine();
		return scan;
	}

}
