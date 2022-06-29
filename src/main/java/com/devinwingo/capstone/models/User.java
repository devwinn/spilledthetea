package com.devinwingo.capstone.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user")
@Entity
public class User {
    @Id
    @NotEmpty(message = "Please Enter Your Email")
    @Email
    @Column(name = "email", unique = true)
    String email;

    @NotEmpty(message = "Please Enter Your User Name")
    @Length(message = "User Name Must Be 6 Or More Characters", min = 6)
    @Column(name = "user_name", unique = true)
    String userName;

    @NotEmpty
    String firstName;

    @NotEmpty
    String lastName;

    @NotEmpty(message = "Enter Password")
    @Length(message = "Password Must Be 8 Or More Characters", min = 8)
    @Setter(AccessLevel.NONE)
    String password;

    public User(String email, String userName, String firstName, String lastName, String password) {
        this.email = email;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = new BCryptPasswordEncoder(4).encode(password);
    }

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    List<Post> posts = new ArrayList<>();

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder(4).encode(password);
    }

    public void addPost(Post post) {
        posts.add(post);
        post.setUser(this);
    }

    public void deletePost(Post post) {
        posts.remove(post);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userName.equals(user.userName) && firstName.equals(user.firstName) && lastName.equals(user.lastName) && email.equals(user.email) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, firstName, lastName, email, password);
    }
}
