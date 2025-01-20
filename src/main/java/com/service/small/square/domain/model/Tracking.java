package com.service.small.square.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class Tracking {
    private String id;
    private Long clientId;
    private Long orderId;
    private String currentState;
    private List<StateHistory> stateHistory;

    public Tracking(String id, Long clientId, Long orderId, String currentState, List<StateHistory> stateHistory) {
        this.id = id;
        this.clientId = clientId;
        this.orderId = orderId;
        this.currentState = currentState;
        this.stateHistory = stateHistory;
    }

    public Tracking() {}

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public Long getClientId() {return clientId;}
    public void setClientId(Long clientId) {this.clientId = clientId;}

    public Long getOrderId() {return orderId;}
    public void setOrderId(Long orderId) {this.orderId = orderId;}

    public String getCurrentState() {return currentState;}
    public void setCurrentState(String currentState) {this.currentState = currentState;}

    public List<StateHistory> getStateHistory() {return stateHistory;}
    public void setStateHistory(List<StateHistory> stateHistory) {this.stateHistory = stateHistory;}

    public static class StateHistory {
    private LocalDateTime timestamp;
    private String state;
    private String description;

    public StateHistory(LocalDateTime timestamp, String state, String description) {
        this.timestamp = timestamp;
        this.state = state;
        this.description = description;}

    public LocalDateTime getTimestamp() {return timestamp;}
    public void setTimestamp(LocalDateTime timestamp) {this.timestamp = timestamp;}

    public String getState() {return state;}
    public void setState(String state) {this.state = state;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    
}

}