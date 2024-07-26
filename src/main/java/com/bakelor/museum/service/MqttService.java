import com.bakelor.museum.controller.MqttController;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MqttService implements MqttCallback {

    private final MqttClient client;
    private final MqttController mqttController;

    public MqttService(@Value("${mqtt.broker}") String broker, @Value("${mqtt.clientId}") String clientId, MqttController mqttController) throws MqttException {
        this.client = new MqttClient(broker, clientId);
        this.client.setCallback(this);
        this.client.connect();
        this.client.subscribe("your/topic");
        this.mqttController = mqttController;
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.err.println("Connection lost: " + cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String payload = new String(message.getPayload());
        System.out.println("Message arrived: " + payload);
        mqttController.sendToClients(payload);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Not needed for this example
    }
}