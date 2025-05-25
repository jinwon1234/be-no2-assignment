package kakao.beno2homework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        String sql = new String(Files.readAllBytes(Paths.get("schedule.sql")));
        for (String query : sql.split(";")) {
            if (!query.trim().isEmpty()) {
                jdbcTemplate.execute(query);
            }
        }
    }
}
