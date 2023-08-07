package com.example.Module2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String,Object> template;

    @Autowired
    private AuthService authService;
    private String topic = "send";
    public void sendMessage( String message,String jwtToken){
        try {
            // Authenticate and authorize the user

            authService.authenticateAndAuthorize(jwtToken);
            template.send(topic, message);
        }
        catch (ResponseStatusException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"UNAUTHORIZED ACCESS");

        }
        catch(KafkaException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Exception While Sending Message");
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception while calling API");
        }

    }
}
