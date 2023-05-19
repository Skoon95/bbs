package dev.shyoon.studywebbbs.entities;

import java.util.Objects;

public class AttachmentEntity {
    private int index;
    private int articleIndex;
    private String fileName;
    private int fileSize;
    private String fileContentType;
    private byte[] fileData;

    public int getIndex() {
        return index;
    }

    public AttachmentEntity setIndex(int index) {
        this.index = index;
        return this;
    }

    public int getArticleIndex() {
        return articleIndex;
    }

    public AttachmentEntity setArticleIndex(int articleIndex) {
        this.articleIndex = articleIndex;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public AttachmentEntity setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public int getFileSize() {
        return fileSize;
    }

    public AttachmentEntity setFileSize(int fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public AttachmentEntity setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public AttachmentEntity setFileData(byte[] fileData) {
        this.fileData = fileData;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttachmentEntity that = (AttachmentEntity) o;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
