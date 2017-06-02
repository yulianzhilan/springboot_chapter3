package cn.janescott.controller;

import cn.janescott.domain.Actor;
import cn.janescott.domain.Movie;
import cn.janescott.repository.ActorRepository;
import cn.janescott.repository.MovieRepository;
import cn.janescott.service.PagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by scott on 2017/6/2.
 */
@RestController
@RequestMapping("/movie")
public class MovieController {
    private static Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private PagesService<Movie> pagesService;

    @RequestMapping("/{id}")
    public ModelAndView show(ModelMap model, @PathVariable Long id){
        Movie movie = movieRepository.findOne(id);
        model.addAttribute("movie",movie);
        return new ModelAndView("movie/show");
    }

    @RequestMapping("/new")
    public ModelAndView create(ModelMap model){
        String[] files = {"/images/movie/西游记.jpg","/images/movie/西游记续集.jpg"};
        model.addAttribute("files", files);
        return new ModelAndView("movie/new");
    }

    /**
     * 修改数据时，由于从界面传回的电影对象中，
     * 丢失了其角色关系的数据（这是OGM的缺点），所以再次查询一次数据库，
     * 以取得一个电影的完整数据，然后再执行修改的操作。
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/edit/{id}")
    public ModelAndView update(ModelMap model, @PathVariable Long id){
        Movie movie = movieRepository.findOne(id);
        String[] files = {"/images/movie/西游记.jpg","/images/movie/西游记续集.jpg"};
        String[] rolelist = new String[]{"唐僧","孙悟空","猪八戒","沙僧"};
        Iterable<Actor> actors = actorRepository.findAll();

        model.addAttribute("files", files);
        model.addAttribute("rolelist",rolelist);
        model.addAttribute("movie",movie);
        model.addAttribute("actors",actors);

        return new ModelAndView("movie/edit");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Movie movie, HttpServletRequest request) throws Exception{
        String rolename= request.getParameter("rolename");
        String actorid = request.getParameter("actorid");
        Movie old = movieRepository.findOne(movie.getId());
        old.setName(movie.getName());
        old.setPhoto(movie.getPhoto());
        old.setCreateDate(movie.getCreateDate());
        if(!StringUtils.isEmpty(rolename) && !StringUtils.isEmpty(actorid)){
            Actor actor = actorRepository.findOne(Long.parseLong(actorid));
            old.addRole(actor, rolename);
        }
        movieRepository.save(old);
        logger.info("修改->ID:{}",old.getId());
        return "1";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Movie movie){
        movieRepository.save(movie);
        logger.info("新增->ID={}",movie.getId());
        return "1";
    }

    @RequestMapping("/list")
    public Page<Movie> list(HttpServletRequest request) throws Exception{
        return null;
    }
}
