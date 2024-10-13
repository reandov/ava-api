package covildocafe.ava.utils;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ServiceUtils {

    public static <T> T findEntityOrThrow(Optional<T> optionalEntity, Long id, String entityName) {
        return optionalEntity.orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, entityName + " with ID " + id + " does not exist")
        );
    }
}