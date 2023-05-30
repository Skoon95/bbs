package dev.shyoon.studywebbbs.controllers;

import dev.shyoon.studywebbbs.entities.ArticleEntity;
import dev.shyoon.studywebbbs.entities.AttachmentEntity;
import dev.shyoon.studywebbbs.entities.CommentEntity;
import dev.shyoon.studywebbbs.entities.ImageEntity;
import dev.shyoon.studywebbbs.services.ArticleService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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


    @RequestMapping(value = "read",
    method = RequestMethod.GET,
    produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getRead(@RequestParam(value = "index") int index){
        ModelAndView modelAndView = new ModelAndView("article/read");
        ArticleEntity article = this.articleService.readArticle(index);
        modelAndView.addObject("article",article);
        if (article != null){
            modelAndView.addObject("attachments",this.articleService.getAttachmentsOf(article));
        }
        return modelAndView;
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
        boolean result = this.articleService.putArticle(request,article,files);
        ModelAndView modelAndView = new ModelAndView();
        if (result){
            modelAndView.setViewName("redirect:/article/read?index="+article.getIndex());
        }else {
            modelAndView.setViewName("article/write");
            modelAndView.addObject("result",result);
        }
        return modelAndView;
    }


    @RequestMapping(value = "uploadImage",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postUploadImage(HttpServletRequest request,
            @RequestParam(value = "upload") MultipartFile file) throws IOException {
//        ckeditor 쪽에서 upload 로 보냄
        ImageEntity image = this.articleService.putImage(request,file);
        JSONObject responseObject = new JSONObject(){{
           put("url","/article/downloadImage?index="+image.getIndex());
        }};
        return responseObject.toString();
    }

    @RequestMapping(value = "downloadImage",method = RequestMethod.GET)
    public ResponseEntity<byte[]> getDownloadImage(@RequestParam(value = "index")int index){
        ImageEntity image = this.articleService.getIndex(index);
        ResponseEntity<byte[]> response;
        if (image ==null){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(image.getSize());
            headers.setContentType(MediaType.parseMediaType(image.getContentType()));
            response = new ResponseEntity<>(image.getData(),headers,HttpStatus.OK);
        }
        return response;
    }

//    첨부파일 다운로드
    @RequestMapping(value = "download",method = RequestMethod.GET)
    public ResponseEntity<byte[]> getDownload(@RequestParam(value = "index")int index){
        AttachmentEntity attachment = this.articleService.getAttachment(index);
        ResponseEntity<byte[]> response;
        if (attachment ==null){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(attachment.getFileSize());
            headers.setContentType(MediaType.parseMediaType(attachment.getFileContentType()));
            response = new ResponseEntity<>(attachment.getFileData(),headers,HttpStatus.OK);
        }
        return response;
    }

    @RequestMapping(value = "comment",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postComment(HttpServletRequest request,
                              CommentEntity comment){
        boolean result = this.articleService.putComment(request, comment);
        return String.valueOf(result);
    }

    @RequestMapping(value = "comment",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CommentEntity[] getComment(@RequestParam(value = "articleIndex")int articleIndex){
        return this.articleService.getCommentsOf(articleIndex);
    }

    @RequestMapping(value = "comment",
            method = RequestMethod.DELETE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String deleteComment(CommentEntity comment) {
        boolean result = this.articleService.deleteComment(comment);
        return String.valueOf(result);
    }


}
