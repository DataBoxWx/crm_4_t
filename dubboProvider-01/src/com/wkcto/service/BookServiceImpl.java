package com.wkcto.service;

import java.util.ArrayList;
import java.util.List;

import com.wkcto.beans.Books;

public class BookServiceImpl implements BookService {

	@Override
	public List<Books> getBooks() {
		System.out.println("**********进入getBooks*********");
		Books books1 = new Books();
		books1.setName("老人与海");
		books1.setMoney(12);
		books1.setPress("新华出版社");
		
		Books books2 = new Books();
		books2.setName("三个火枪手");
		books2.setMoney(22);
		books2.setPress("新华出版社");
		
		List<Books> list = new ArrayList<>();
		list.add(books1);
		list.add(books2);
		return list;
	}

}
