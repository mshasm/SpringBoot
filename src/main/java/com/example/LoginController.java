import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://gitlab.example.com") // Allow frontend access
@RequestMapping("/api") // Prefix all routes with /api
public class LoginController {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    private Map<String, String> userDatabase = new HashMap<>(); // Simulated DB

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> user) {
        String username = user.get("username");
        String password = user.get("password");

        if (userDatabase.containsKey(username)) {
            logger.warn("User {} already exists", username);
            return Collections.singletonMap("message", "User already exists!");
        }

        userDatabase.put(username, password); // Store user in memory (replace with DB)
        logger.info("User Registered: {}", username);
        return Collections.singletonMap("message", "User registered successfully!");
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> user) {
        String username = user.get("username");
        String password = user.get("password");

        logger.info("Login Attempt: {}", username);

        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
            logger.info("Login Successful: {}", username);
            return Collections.singletonMap("message", "Login Successful!");
        }

        logger.warn("Invalid Login Attempt: {}", username);
        return Collections.singletonMap("message", "Invalid Credentials");
    }
}

