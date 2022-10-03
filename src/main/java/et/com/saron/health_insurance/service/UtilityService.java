package et.com.saron.health_insurance.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.data.domain.PageRequest.of;

@Service
public class UtilityService {

    public Pageable pageable(int page, int size, String direction, String param) {
        if (direction.equals("asc")) {
            return of(page, size, Sort.Direction.ASC, param);
        } else if (direction.equals("desc")) {
            return of(page, size, Sort.Direction.DESC, param);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "request field sorting order unknown");
        }

    }
}
