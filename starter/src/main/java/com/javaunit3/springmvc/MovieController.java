package com.javaunit3.springmvc;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javaunit3.springmvc.model.MovieEntity;

@Controller
public class MovieController {

    @Autowired
    BestMovieService bestMovieService;
    
    @RequestMapping("/")
    public String getIndexPage() {
        return "index";
    }

    @RequestMapping("/bestMovie")
    public String getBestMoviePage(Model model) {
        model.addAttribute("BestMovie", bestMovieService.getBestMovie().getTitle());
        return "bestMovie";
    }


    @RequestMapping("/voteForBestMovieForm")
    public String voteForBestMovieFormPage() {

        return "voteForTheBestMovie";
    }

    @RequestMapping("/voteForBestMovie")
    public String voteForBestMovie(HttpServletRequest request, Model model) {

        String title = request.getParameter("movieTitle");
        model.addAttribute("BestMovieVote", title);

        return "voteForTheBestMovie";
    }

    @Autowired
    private SessionFactory factory; 
    
    @RequestMapping("/addMovieForm")
    public String addMovieForm() {

        return "addMovie";
    }

    @RequestMapping("/addMovie")
    public String addMovie(HttpServletRequest request) {
        
        String movieTitle = request.getParameter("movieTitle");
        String maturityRating = request.getParameter("maturityRating");
        String genre = request.getParameter("genre");

        MovieEntity movie = new MovieEntity();
        movie.setTitle(movieTitle);
        movie.setMaturityRating(maturityRating);
        movie.setGenre(genre);

        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(movie);
        session.getTransaction().commit();


        return "addMovie";
    }


}
