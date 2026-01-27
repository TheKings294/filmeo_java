package com.filmeo.webapp.utils;

import com.filmeo.webapp.model.entity.*;
import com.filmeo.webapp.model.repository.*;
import com.filmeo.webapp.type.GenderEnum;
import com.filmeo.webapp.type.SeriStatusEnum;
import com.github.javafaker.Country;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RefillDb {
    private final Faker faker = new Faker(new Locale("fr"));

    private final GenreRepository genreRepository;
    private final HumanRepository humanRepository;
    private final MovieRepository movieRepository;
    private final NationalityRepository nationalityRepository;
    private final StreamingPlatformRepository streamingPlatformRepository;
    private final ReviewRepository reviewRepository;
    private final SeriRepository seriRepository;
    private final UserRepository userRepository;
    private final PlatformSeriRepository platformSeriRepository;
    private final PlatformMovieRepository platformMovieRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void init() {
        createUser(100);
        createNationality();
        createHuman(100);
        createStreamingPlatform();
        createGenre();
        createMovies();
        createSeries();
        createReview();
    }

    public void createUser(int quantity) {
        String password = passwordEncoder.encode("password");
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        Set<User> users = new HashSet<>();
        Set<String> usedEmails = new HashSet<>();
        Set<String> usedPseudos = new HashSet<>();

        for (int i = 0; i < quantity; i++) {
            User user = new User();
            user.setRoles(roles);

            String pseudo;
            do {
                pseudo = generateUniquePseudo();
            } while (usedPseudos.contains(pseudo));
            usedPseudos.add(pseudo);
            user.setPseudo(pseudo);

            String email;
            do {
                email = faker.internet().safeEmailAddress();
            } while (usedEmails.contains(email));
            usedEmails.add(email);
            user.setEmail(email);

            user.setPassword(password);
            users.add(user);
        }

        User user2 = new User();
        user2.setRoles(roles);
        user2.setPseudo(faker.name().username());
        user2.setEmail("user@test.com");
        user2.setPassword(password);
        users.add(user2);

        userRepository.saveAll(users);

        roles.add("ADMIN");
        User user = new User();
        user.setRoles(roles);
        user.setPseudo(faker.name().username());
        user.setEmail("admin@test.com");
        user.setPassword(password);

        userRepository.save(user);
    }

    public void createHuman(int quantity) {
        Set<Human> humans = new HashSet<>();

        for (int i = 0; i < quantity; i++) {
            Human human = new Human();
            human.setFirstName(faker.name().firstName());
            human.setLastName(faker.name().lastName());
            human.setProfilePicture(faker.internet().avatar());
            human.setGender(i % 2 == 0 ? GenderEnum.MALE : GenderEnum.FEMALE);
            Date birthDate = faker.date().birthday(20, 100);
            human.setBirthDate(convertToLocalDate(birthDate));

            if (faker.random().nextDouble() < 0.3) {
                LocalDate birth = human.getBirthDate();
                Date deathDate = faker.date().between(
                        convertToDate(birth),
                        new Date()
                );
                human.setDeathDate(convertToLocalDate(deathDate));
            }
            human.setNationality(nationalityRepository.findAll().get(getRandom(0, 98)));

            humans.add(human);
        }

        humanRepository.saveAll(humans);
    }

    public void createMovies() {
        Set<Movie> movies = new HashSet<>();

        for (int i = 0; i < 100; i++) {
            Movie movie = new Movie();
            movie.setTitle(faker.book().title());
            movie.setResume(faker.lorem().sentence(80));
            movie.setPosterURL(faker.internet().avatar());
            movie.setRealisator(humanRepository.findAll().get(getRandom(0, humanRepository.findAll().size() - 1)));

            for (int j = 0; j < getRandom(1, 10); j++) {
                List<Human> casting = movie.getCasting();
                casting.add(humanRepository.findAll().get(getRandom(0, humanRepository.findAll().size() - 1)));
                movie.setCasting(casting);
            }

            for (int j = 0; j < getRandom(1, 3); j++) {
                List<Nationality> nationalities = movie.getNationalities();
                nationalities.add(nationalityRepository.findAll().get(getRandom(0, nationalityRepository.findAll().size() - 1)));
                movie.setNationalities(nationalities);
            }

            for (int j = 0; j < getRandom(1, 3); j++) {
                List<Genre> genres = movie.getGenres();
                genres.add(genreRepository.findAll().get(getRandom(0, genreRepository.findAll().size() - 1)));
                movie.setGenres(genres);
            }

            movies.add(movie);
        }

        movieRepository.saveAll(movies);

        movies.forEach((movie) -> movie.setPlatformMovies(setPlatform(movie)));
        movieRepository.saveAll(movies);
    }

    public void createSeries() {
        Set<Seri> series = new HashSet<>();

        for (int i = 0; i < 100; i++) {
            Seri seri = new Seri();
            seri.setTitle(faker.book().title());
            seri.setResume(faker.lorem().sentence(80));
            seri.setPosterURL(faker.internet().avatar());
            seri.setStaus(i % 2 == 0 ? SeriStatusEnum.FINISH : SeriStatusEnum.IN_PROGRESS);
            seri.setSeasons(getRandom(1, 8));
            seri.setEpisode(seri.getSeasons() * 20);

            for (int j = 0; j < getRandom(1, 10); j++) {
                List<Human> casting = seri.getCasting();
                casting.add(humanRepository.findAll().get(getRandom(0, humanRepository.findAll().size() - 1)));
                seri.setCasting(casting);
            }

            for (int j = 0; j < getRandom(1, 3); j++) {
                List<Nationality> nationalities = seri.getNationalities();
                nationalities.add(nationalityRepository.findAll().get(getRandom(0, nationalityRepository.findAll().size() - 1)));
                seri.setNationalities(nationalities);
            }

            for (int j = 0; j < getRandom(1, 3); j++) {
                List<Genre> genres = seri.getGenres();
                genres.add(genreRepository.findAll().get(getRandom(0, genreRepository.findAll().size() - 1)));
                seri.setGenres(genres);
            }

            series.add(seri);
        }

        seriRepository.saveAll(series);
        series.forEach((seri) -> seri.setPlatformSeris(setPlatform(seri)));
        seriRepository.saveAll(series);
    }

    public void createStreamingPlatform() {
        Set<StreamingPlatform> platforms = new HashSet<>();

        for (int i = 0; i < 20; i++) {
            StreamingPlatform platform = new StreamingPlatform();
            platform.setName(generatePlatformName());
            platform.setLink(faker.internet().url());
            platform.setLogoUrl(faker.internet().avatar());
            platforms.add(platform);
        }

        streamingPlatformRepository.saveAll(platforms);
    }

    public void createGenre() {
        Set<Genre> genres = new HashSet<>();

        List<String> genreNames = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            String genreName = faker.book().genre();
            // Avoid duplicates
            if (!genreNames.contains(genreName)) {
                genreNames.add(genreName);
            }
        }

        String[] additionalGenres = {
                "Action", "Adventure", "Comedy", "Drama", "Horror",
                "Science Fiction", "Fantasy", "Thriller", "Romance", "Mystery"
        };

        for (String additional : additionalGenres) {
            if (!genreNames.contains(additional) && genreNames.size() < 30) {
                genreNames.add(additional);
            }
        }

        for (String genreName : genreNames) {
            Genre genre = new Genre();
            genre.setName(genreName);
            String description = faker.lorem().sentence(15) + " " +
                    faker.lorem().sentence(12) + " " +
                    faker.lorem().sentence(10);

            genre.setDescription(description);

            genres.add(genre);
        }

        genreRepository.saveAll(genres);
    }

    public void createNationality() {
        Set<Nationality> nationalities = new HashSet<>();

        for (int i = 0; i < 100; i++) {
            Country c = faker.country();
            Nationality nationality = new Nationality();
            nationality.setName(c.name());
            nationality.setFlagUrl("https://flagcdn.com/w320/" + c.countryCode2() + ".png");
            nationalities.add(nationality);
        }

        nationalityRepository.saveAll(nationalities);
    }

    public void createReview() {
        Set<Review> reviews = new HashSet<>();

        for (int i = 0; i < 100; i++) {
            Review review = new Review();
            review.setUser(userRepository.findAll().get(getRandom(0, userRepository.findAll().size() - 1)));
            review.setComment(faker.lorem().sentence(50));
            review.setRate(getRandom(1, 10));
            switch (getRandom(1, 3)) {
                case 1:
                    review.setMovie(movieRepository.findAll().get(getRandom(0, movieRepository.findAll().size() -1 )));
                    break;
                case 2:
                    review.setSeri(seriRepository.findAll().get(getRandom(0, seriRepository.findAll().size() -1 )));
                    break;
                case 3:
                    review.setActor(humanRepository.findAll().get(getRandom(0, humanRepository.findAll().size() -1 )));
                    break;
                default:
                    break;
            }

            reviews.add(review);
        }

        reviewRepository.saveAll(reviews);
    }

    private String generatePlatformName() {
        // Generate creative platform names using Faker
        String[] prefixes = {
                faker.company().name(),
                faker.app().name(),
                faker.superhero().name(),
                faker.color().name(),
                faker.ancient().god(),
                faker.space().galaxy()
        };

        String[] suffixes = {
                "Stream", "Play", "Flix", "Plus", "TV", "Video",
                "Watch", "Hub", "Now", "Prime", "Max", "Go"
        };

        String prefix = prefixes[faker.random().nextInt(prefixes.length)];
        String suffix = suffixes[faker.random().nextInt(suffixes.length)];

        // Clean up the name (remove spaces, special characters)
        prefix = prefix.replaceAll("[^a-zA-Z0-9]", "");

        // Limit length and combine
        if (prefix.length() > 15) {
            prefix = prefix.substring(0, 15);
        }

        return prefix + suffix;
    }

    private int getRandom(int min, int max) {

        int range = (max - min) + 1;
        int random = (int) ((range * Math.random()) + min);
        return random;
    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    // Helper method to convert LocalDate to Date
    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private List<PlatformMovie> setPlatform(Movie movie) {
        List<PlatformMovie> platformMovieList = new ArrayList<>();

        for (int i = 0; i < getRandom(1, 3); i++) {
            PlatformMovie platformMovie = new PlatformMovie();
            platformMovie.setPlatform(streamingPlatformRepository.findAll().get(getRandom(0,
                    streamingPlatformRepository.findAll().size() - 1)));
            platformMovie.setMovie(movie);
            platformMovie.setEndDate(convertToLocalDate(faker.date().future(getRandom(1, 100), TimeUnit.DAYS)));

            platformMovieList.add(platformMovie);
        }

        platformMovieRepository.saveAll(platformMovieList);
        return platformMovieList;
    }

    private List<PlatformSeri> setPlatform(Seri seri) {
        List<PlatformSeri> platformSerisList = new ArrayList<>();

        for (int i = 0; i < getRandom(1, 3); i++) {
            PlatformSeri platformSeri = new PlatformSeri();
            platformSeri.setPlatform(streamingPlatformRepository.findAll().get(getRandom(0,
                    streamingPlatformRepository.findAll().size() - 1)));
            platformSeri.setSeri(seri);
            platformSeri.setEndDate(convertToLocalDate(faker.date().future(getRandom(1, 100), TimeUnit.DAYS)));

            platformSerisList.add(platformSeri);
        }

        platformSeriRepository.saveAll(platformSerisList);
        return platformSerisList;
    }

    private String generateUniquePseudo() {
        // Mix different Faker methods to create more unique pseudos
        int method = faker.random().nextInt(6);
        String pseudo;

        switch (method) {
            case 0:
                pseudo = faker.name().username();
                break;
            case 1:
                pseudo = faker.internet().slug();
                break;
            case 2:
                pseudo = faker.superhero().name().replaceAll(" ", "").toLowerCase();
                break;
            case 3:
                pseudo = faker.color().name() + faker.number().digits(3);
                break;
            case 4:
                pseudo = faker.animal().name().replaceAll(" ", "") + faker.number().digits(2);
                break;
            default:
                pseudo = faker.name().firstName().toLowerCase() + faker.number().digits(4);
        }

        // Ensure length constraint (max 50 characters)
        if (pseudo.length() > 50) {
            pseudo = pseudo.substring(0, 50);
        }

        return pseudo;
    }
}
