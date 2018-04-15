package assets;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner init(AssetRepository assetRepository,
			NoteRepository noteRepository) {
		return (evt) -> Arrays.asList(
				"jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
				.forEach(
						a -> {
							Asset asset = assetRepository.save(new Asset(a,
									"password"));
							noteRepository.save(new Note(asset,
									"http://bookmark.com/1/" + a));
							noteRepository.save(new Note(asset,
									"http://bookmark.com/2/" + a));
						});
	}

}