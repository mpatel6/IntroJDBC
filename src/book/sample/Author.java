/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book.sample;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Ankita
 */
public class Author {

    private String authorName;
    private Integer authorId;
    private Date dateCreated;

    public Author() {
    }

    public Author(String authorName, Date dateCreated) {
        this.authorName = authorName;
        this.dateCreated = dateCreated;
    }

    
    
    public Author(Integer authorId, String authorName, Date dateCreated) {
        this.authorName = authorName;
        this.authorId = authorId;
        this.dateCreated = dateCreated;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.authorId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if (!Objects.equals(this.authorId, other.authorId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Author{" + "authorName=" + authorName + ", authorId=" + authorId + ", dateCreated=" + dateCreated + '}';
    }

}
