package snmaddula.redis.poc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Employee implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("ipAddress")
    private String ipAddress;

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("jobTitle")
    private String jobTitle;

}
