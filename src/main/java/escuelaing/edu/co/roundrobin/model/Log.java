package escuelaing.edu.co.roundrobin.model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "log")
public class Log {
    
    @Id
    private String id;
    private String value;
    private String date;

    /**
     * Constructor
     * @param value
     */
    public Log(String value) {
        id = UUID.randomUUID().toString();
        this.value = value;
        this.date = formatDate(date);
    }

    /**
     * Get id
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Set id
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get value
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     * Set value
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Get date
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     * Set date
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    public String formatDate(String date2){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = now.format(formatter);
        return formattedDateTime;
    }
}
