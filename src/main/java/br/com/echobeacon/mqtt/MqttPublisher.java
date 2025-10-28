package br.com.echobeacon.mqtt;

import br.com.echobeacon.model.Moto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MqttPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttPublisher.class);

    @Value("${mqtt.brokerUrl:tcp://broker.hivemq.com:1883}")
    private String brokerUrl;

    @Value("${mqtt.clientId:echobeacon-mvc}")
    private String clientId;

    @Value("${mqtt.topic:fiap/iot/echobeacon/comando}")
    private String topic;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private MqttClient client;

    @PostConstruct
    public void connect() {
        try {
            client = new MqttClient(brokerUrl, clientId + "-" + UUID.randomUUID(), new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            client.connect(options);
            LOGGER.info("Conectado ao broker MQTT em {}", brokerUrl);
        } catch (MqttException e) {
            LOGGER.error("Falha ao conectar ao broker MQTT: {}", e.getMessage(), e);
        }
    }

    @PreDestroy
    public void disconnect() {
        try {
            if (client != null && client.isConnected()) {
                client.disconnect();
                client.close();
                LOGGER.info("Conexão MQTT encerrada");
            }
        } catch (MqttException e) {
            LOGGER.warn("Erro ao encerrar conexão MQTT: {}", e.getMessage(), e);
        }
    }

    private void ensureConnected() throws MqttException {
        if (client == null) {
            connect();
        } else if (!client.isConnected()) {
            client.reconnect();
        }
    }

    public void publishAtivar(String echoBeaconId, Moto moto) {
        try {
            ensureConnected();

            Map<String, Object> payload = new HashMap<>();
            payload.put("comando", "ativar");
            payload.put("echoBeaconId", echoBeaconId);
            Map<String, Object> motoObj = new HashMap<>();
            motoObj.put("placa", moto.getPlaca());
            motoObj.put("modelo", moto.getModelo());
            motoObj.put("chassi", moto.getChassi());
            payload.put("moto", motoObj);

            String json = objectMapper.writeValueAsString(payload);
            MqttMessage message = new MqttMessage(json.getBytes(StandardCharsets.UTF_8));
            message.setQos(0);

            client.publish(this.topic, message);
            LOGGER.info("MQTT publicado em {}: {}", this.topic, json);
        } catch (Exception e) {
            LOGGER.error("Erro ao publicar MQTT: {}", e.getMessage(), e);
        }
    }
}
