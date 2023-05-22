package dev.shyoon.studywebbbs.mappers;

import dev.shyoon.studywebbbs.entities.ArticleEntity;
import dev.shyoon.studywebbbs.entities.AttachmentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleMapper {
    int insertArticle(ArticleEntity article);
    int insertAttachment(AttachmentEntity attachment);

    ArticleEntity selectArticleByIndex(@Param(value = "index")int index);
    AttachmentEntity[] selectAttachmentsByArticleIndexNoData(@Param(value = "articleIndex")int articleIndex);

    int updateArticle(ArticleEntity article);

}
