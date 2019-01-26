package com.wkcto.service;
import java.util.ArrayList;
import java.util.List;

import com.wkcto.beans.Movie;
import com.wkcto.service.MovieService;

public class MoviesServiceImpl implements MovieService {

	@Override
	public List<Movie> getMovies() {
		System.out.println("=============进入gteMovies========");
		
		Movie movie1 = new Movie();
		movie1.setName("变形金刚");
		movie1.setTime("120");
		
		Movie movie2 = new Movie();
		movie2.setName("时空玩家");
		movie2.setTime("130");
		
		List<Movie> list = new ArrayList<>();
		list.add(movie1);
		list.add(movie2);
		return list;
	}

}
