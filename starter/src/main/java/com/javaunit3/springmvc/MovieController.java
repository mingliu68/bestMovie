package com.javaunit3.springmvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
