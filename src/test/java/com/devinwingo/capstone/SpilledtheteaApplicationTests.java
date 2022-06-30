package com.devinwingo.capstone;

import com.devinwingo.capstone.dao.PostRepository;
import com.devinwingo.capstone.dao.UserRepository;
import com.devinwingo.capstone.models.Post;
import com.devinwingo.capstone.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class SpilledtheteaApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;
	@Test
	public void findByUserName() {
		User user = User.builder()
				.firstName("carl")
				.lastName("weathers")
				.userName("cweathers")
				.password("password")
				.email("cweathers@gmail.com")
				.build();
		userRepository.save(user);
		assertEquals(userRepository.findByUserName("cweathers"), userRepository.findByUserName(user.getUserName()));
	}

	@Test
	void findAllByUser() {
		User testUser = User.builder()
				.firstName("Dave")
				.lastName("Williams")
				.userName("dWilly")
				.password("password")
				.email("dwilly@gmail.com")
				.build();

		Post testPost1 = Post.builder()
				.user(testUser)
				.heading("test heading 1")
				.content("test content")
				.createdOn(LocalDateTime.now())
				.build();

		Post testPost2 = Post.builder()
				.user(testUser)
				.heading("test heading 2")
				.content("test content 2")
				.createdOn(LocalDateTime.now())
				.build();


		//userRepository.save(testUser);
		postRepository.save(testPost1);
		postRepository.save(testPost2);

		assertEquals(postRepository.findAllByUser("dwilly@gmail.com"), postRepository.findAllByUser(testPost1.getUser().getEmail()));
		assertEquals(postRepository.findAllByUser("dwilly@gmail.com"), postRepository.findAllByUser(testPost2.getUser().getEmail()));
	}

}
