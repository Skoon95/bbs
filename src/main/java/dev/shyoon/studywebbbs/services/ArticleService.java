package dev.shyoon.studywebbbs.services;

import dev.shyoon.studywebbbs.entities.ArticleEntity;
import dev.shyoon.studywebbbs.entities.AttachmentEntity;
import dev.shyoon.studywebbbs.entities.ImageEntity;
import dev.shyoon.studywebbbs.mappers.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@Service
public class ArticleService {
    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    public ArticleEntity readArticle(int index){
        ArticleEntity article = this.articleMapper.selectArticleByIndex(index);
        if (article != null && !article.isDeleted()){
            article.setView(article.getView()+1);
            this.articleMapper.updateArticle(article);
        }
        return article;
    }

    public AttachmentEntity[] getAttachmentsOf(ArticleEntity article){
        return this.articleMapper.selectAttachmentsByArticleIndexNoData(article.getIndex());
    }

    @Transactional
    public boolean putArticle(HttpServletRequest request, ArticleEntity article, MultipartFile[] files) throws IOException {
        article.setView(0)
                .setCreatedAt(new Date())
                .setClientIp(request.getRemoteAddr())
                .setClientUa(request.getHeader("User-Agent"))
                .setDeleted(false);
        if (this.articleMapper.insertArticle(article)<1){
            return false;
        }
        int inserted = 0;
        for (MultipartFile file : files){
            AttachmentEntity attachment = new AttachmentEntity()
                    .setArticleIndex(article.getIndex())
                    .setFileData(file.getBytes())
                    .setFileContentType(file.getContentType())
                    .setFileSize((int)file.getSize())
                    .setFileName(file.getOriginalFilename());
            inserted += this.articleMapper.insertAttachment(attachment);
        }
        return inserted == files.length;
    }

    public ImageEntity putImage(HttpServletRequest request, MultipartFile file) throws IOException {
        ImageEntity image = new ImageEntity()
                .setName(file.getOriginalFilename())
                .setSize(file.getSize())
                .setContentType(file.getContentType())
                .setData(file.getBytes())
                .setCreatedAt(new Date())
                .setClientIp(request.getRemoteAddr())
                .setClientUa(request.getHeader("User-Agent"));
        this.articleMapper.insertImage(image);
        return image;
    }

    public ImageEntity getIndex(int index){
        return this.articleMapper.selectImageByIndex(index);
    }

    public AttachmentEntity getAttachment(int index){
        return this.articleMapper.selectAttachment(index);
    }
}
