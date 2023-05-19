package dev.shyoon.studywebbbs.controllers;

import dev.shyoon.studywebbbs.entities.ArticleEntity;
import dev.shyoon.studywebbbs.entities.AttachmentEntity;
import dev.shyoon.studywebbbs.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping(value = "/article")
public class ArticleController {


    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @RequestMapping(value = "write",
    method = RequestMethod.GET,
    produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getWrite(){
        ModelAndView modelAndView = new ModelAndView("article/write");
        return modelAndView;

    }

    @RequestMapping(value = "write",
    method = RequestMethod.POST,
            produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public ModelAndView postWrite(HttpServletRequest request, ArticleEntity article,@RequestParam(value = "files",required = false)MultipartFile... files)throws IOException {
        if (files==null){
            files = new MultipartFile[0];
        }
        boolean result = this.articleService.write(request,article,files);
        ModelAndView modelAndView = new ModelAndView();
        if (result){
            modelAndView.setViewName("redirect:/article/read?index="+article.getIndex());
        }else {
            modelAndView.setViewName("article/write");
            modelAndView.addObject("result",result);
        }
        return modelAndView;
    }

}
