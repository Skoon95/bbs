package dev.shyoon.studywebbbs.mappers;

import dev.shyoon.studywebbbs.entities.ArticleEntity;
import dev.shyoon.studywebbbs.entities.AttachmentEntity;
import dev.shyoon.studywebbbs.entities.CommentEntity;
import dev.shyoon.studywebbbs.entities.ImageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleMapper {
    int insertArticle(ArticleEntity article);
    int insertAttachment(AttachmentEntity attachment);
    int insertComment(CommentEntity comment);

    int insertImage(ImageEntity image);

    ArticleEntity selectArticleByIndex(@Param(value = "index")int index);
    AttachmentEntity[] selectAttachmentsByArticleIndexNoData(@Param(value = "articleIndex")int articleIndex);
    AttachmentEntity selectAttachment(@Param(value = "index")int index);

    CommentEntity selectComment(@Param(value = "index") int index);
    CommentEntity[] selectCommentByArticleIndex(@Param(value = "articleIndex")int articleIndex);

    ImageEntity selectImageByIndex(@Param(value = "index")int index);

    int updateArticle(ArticleEntity article);

    int updateComment(CommentEntity comment);

}
