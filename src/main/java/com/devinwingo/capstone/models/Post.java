package com.devinwingo.capstone.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    @NonNull
    @Column(length = 75)
    String heading;

    @NonNull
    @Column(length = 350)
    String content;

    @NonNull
    @Column(name = "date")
    @CreationTimestamp
    LocalDateTime createdOn;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    User user;

    @ToString.Exclude
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    List<Comment> comments = new ArrayList<>();


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinTable(name = "post_categories",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_name"))
    List<Category> categories = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void addCategory(String catName) {
        Category newCategory = new Category();
        newCategory.setName(catName);
        this.categories.add(newCategory);
        newCategory.getPosts().add(this);
    }

    public void deleteCategories() {
        this.getCategories().removeAll(this.getCategories());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id && heading.equals(post.heading) && content.equals(post.content) && user.equals(post.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, heading, content, user);
    }
}
