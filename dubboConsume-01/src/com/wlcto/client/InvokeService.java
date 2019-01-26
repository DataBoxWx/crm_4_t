package com.wlcto.client;

import java.util.List;

import com.wkcto.beans.Books;
import com.wkcto.beans.Movie;
import com.wkcto.service.BookService;
import com.wkcto.service.MovieService;

public class InvokeService {
	private MovieService ms;
	
	private BookService bs;

	public void setMs(MovieService ms) {
		this.ms = ms;
	}

	public void setBs(BookService bs) {
		this.bs = bs;
	}
	
	public void printService(){
		List<Movie> movies = ms.getMovies();
		System.out.println("远程对象：" + movies.toString());
		
		List<Books> books = bs.getBooks();
		System.out.println("远程对象：" + books.toString());
	}
}
