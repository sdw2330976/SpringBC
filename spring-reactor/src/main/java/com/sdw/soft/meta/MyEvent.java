package com.sdw.soft.meta;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by shangyd on 2018/4/26.
 */
@Document(collection = "event")
public class MyEvent {

    @Id
    private Long id;
    private String message;

    public MyEvent() {
    }

    public MyEvent(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
