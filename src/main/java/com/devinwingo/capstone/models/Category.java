package com.devinwingo.capstone.models;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
//CATEGORY MODEL
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Entity
public class Category {

    @Id
    String name;

    @ToString.Exclude
    @ManyToMany(mappedBy = "categories", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    List<Post> posts = new ArrayList<>();

    //Helper Method
    public void addPostToCategory(Post post){
        posts.add(post);
        post.getCategories().add(this);
    }

    //Override Equals and Hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return name.equals(category.name) && posts.equals(category.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, posts);
    }
}
