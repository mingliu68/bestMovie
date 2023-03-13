package com.javaunit3.springmvc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javaunit3.springmvc.model.MovieEntity;
import com.javaunit3.springmvc.model.VoteEntity;

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
        // model.addAttribute("BestMovie", bestMovieService.getBestMovie().getTitle());

        Session session = factory.getCurrentSession();

        session.beginTransaction();

        List<MovieEntity> movieEntityList = session.createQuery("from MovieEntity").list();
        
        // example snippet users.sort(Comparator.comparing(User::getCreatedOn));

        movieEntityList.sort(Comparator.comparing(movieEntity -> movieEntity.getVotes().size()));
        MovieEntity movieWithMostVotes = movieEntityList.get(movieEntityList.size() - 1);
        List<String> voterNames = new ArrayList<>();

        for(VoteEntity vote: movieWithMostVotes.getVotes()) {
            voterNames.add(vote.getVoterName());
        }
        
        String voterNameList = String.join(",", voterNames);

        model.addAttribute("bestMovie", movieWithMostVotes.getTitle());
        model.addAttribute("bestMovieVoters", voterNameList);

        session.getTransaction().commit();

        return "bestMovie";
    }


    @RequestMapping("/voteForBestMovieForm")
    public String voteForBestMovieFormPage(Model model) {

        Session session = factory.getCurrentSession();

        session.beginTransaction();

        List<MovieEntity> movieEntityList = session.createQuery("from MovieEntity").list();
        
        
        model.addAttribute("movies", movieEntityList);
        

        session.getTransaction().commit();


        return "voteForTheBestMovie";
    }

    @RequestMapping("/voteForBestMovie")
    public String voteForBestMovie(HttpServletRequest request, Model model) {

        // String title = request.getParameter("movieTitle");
        // model.addAttribute("BestMovieVote", title);

        String movieId = request.getParameter("movie_id");
        String voterName = request.getParameter("name");

        Session session = factory.getCurrentSession();

        session.beginTransaction();

        MovieEntity movieEntity = (MovieEntity) session.get(MovieEntity.class, Integer.parseInt(movieId));
        VoteEntity newVote = new VoteEntity();

        newVote.setVoterName(voterName);
        movieEntity.addVote(newVote);   
        
        session.update(movieEntity);

        session.getTransaction().commit();

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
