package escuelaing.edu.co.roundrobin.repository;

import org.springframework.stereotype.Repository;
import escuelaing.edu.co.roundrobin.model.Log;
import org.springframework.data.mongodb.repository.MongoRepository;


@Repository
public interface LogRepository extends MongoRepository<Log, String> {
}
