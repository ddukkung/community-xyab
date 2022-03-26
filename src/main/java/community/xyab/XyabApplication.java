package community.xyab;

import community.xyab.domain.board.Board;
import community.xyab.domain.board.BoardRepository;
import community.xyab.domain.user.Role;
import community.xyab.domain.user.User;
import community.xyab.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.IntStream;

@SpringBootApplication
public class XyabApplication {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(XyabApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(UserRepository userRepository, BoardRepository boardRepository) {
		return (args) -> {
			User user = userRepository.save(User.builder()
					.username("test")
					.password(bCryptPasswordEncoder.encode("test1234"))
					.nickname("test")
					.email("test@test.com")
					.role(Role.USER)
					.build());

			IntStream.rangeClosed(1, 50).forEach(index -> boardRepository.save(Board.builder()
					.title("게시글" + index)
					.content("컨텐츠")
					.user(user)
					.build())
			);
		};
	}

}
