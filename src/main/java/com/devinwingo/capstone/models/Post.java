package com.devinwingo.capstone.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
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
    Date createdOn;

    @NonNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(nullable = false)
    User user;


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
