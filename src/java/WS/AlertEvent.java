/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WS;

/**
 *
 * @author Neha Meena
 */
public class AlertEvent {
    private String type;
    private String message;
    private long timestamp;

    public AlertEvent(String type, String message) {
        this.type = type;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters
    public String getType() { return type; }
    public String getMessage() { return message; }
    public long getTimestamp() { return timestamp; }


// Interface for alert listeners
public interface AlertListener {
    void onAlert(AlertEvent event);
}
}

