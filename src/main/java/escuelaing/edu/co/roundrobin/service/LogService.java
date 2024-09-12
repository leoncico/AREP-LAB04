package escuelaing.edu.co.roundrobin.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import escuelaing.edu.co.roundrobin.model.Log;
import escuelaing.edu.co.roundrobin.repository.LogRepository;

@Service
public class LogService {
    private final LogRepository logRepository;

    /**
     * Constructor
     * @param logRepository
     */
    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }
    /**
     * Save a log
     * @param log
     */
    public void saveLog(Log log) {
        logRepository.save(log);
    }

    /**
     * Get last records
     * @param limit
     * @return
     */
    public List<Log> getLastRecords(int limit) {
        List<Log> lastRecords = new ArrayList<>();
        List<Log> logs = logRepository.findAll();
        Collections.reverse(logs);
        for (int i = 0; i < limit &&  i < logs.size(); i++) {
            lastRecords.add(logs.get(i));
        }
        return lastRecords;
    }

}
