package escuelaing.edu.co.roundrobin.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import escuelaing.edu.co.roundrobin.model.Log;
import escuelaing.edu.co.roundrobin.service.LogService;

@RestController
@RequestMapping("/log")
public class LogController {
    private final LogService logService;

    /**
     * Constructor
     * @param logService
     */
    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    /**
     * Update log
     * @param value
     * @return
     */
    @GetMapping("/update")
    public ResponseEntity<?> updateLog(@RequestParam String value) {
        Log log = new Log(value);
        logService.saveLog(log);
        try{
            return new ResponseEntity<>(logService.getLastRecords(10), HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>("{\"error\":\"Error al obtener los últimos 10 mensajes\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }
}
