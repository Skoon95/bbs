package dev.shyoon.studywebbbs.mappers;

import dev.shyoon.studywebbbs.entities.ArticleEntity;
import dev.shyoon.studywebbbs.entities.AttachmentEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper {
    int insertArticle(ArticleEntity article);
    int insertAttachment(AttachmentEntity attachment);

}
