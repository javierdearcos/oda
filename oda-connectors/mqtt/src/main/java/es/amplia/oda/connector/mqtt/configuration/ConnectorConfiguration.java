package es.amplia.oda.connector.mqtt.configuration;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/**
 * Configuration of the MQTT connector.
 */
public class ConnectorConfiguration {

    /**
     * MQTT property names
     */
    static final String MQTT_CONNECTOR_HOST_PROPERTY = "connector.host";
    static final String MQTT_CONNECTOR_PORT_PROPERTY = "connector.port";
    static final String MQTT_CONNECTOR_SECURE_PORT_PROPERTY = "connector.secure.port";
    static final String MQTT_CONNECTOR_SECURE_CONNECTION_PROPERTY = "connector.secure";

    static final String MQTT_CONNECTOR_MQTT_VERSION_PROPERTY = "connection.mqtt.version";
    static final String MQTT_CONNECTOR_AUTOMATIC_RECONNECT_PROPERTY = "connection.automatic.reconnect";
    static final String MQTT_CONNECTOR_CONNECTION_TIMEOUT_PROPERTY = "connection.timeout";
    static final String MQTT_CONNECTOR_KEEP_ALIVE_INTERVAL_PROPERTY = "connection.keepalive.interval";
    static final String MQTT_CONNECTOR_MAX_IN_FLIGHT_PROPERTY = "connection.max.inflight";
    static final String MQTT_CONNECTOR_CLEAN_SESSION_PROPERTY = "connection.clean.session";

    static final String MQTT_CONNECTOR_LWT_TOPIC_PROPERTY = "lwt.topic";
    static final String MQTT_CONNECTOR_LWT_PAYLOAD_PROPERTY = "lwt.payload";
    static final String MQTT_CONNECTOR_LWT_QOS_PROPERTY = "lwt.qos";
    static final String MQTT_CONNECTOR_LWT_RETAINED_PROPERTY = "lwt.retained";

    static final String MQTT_CONNECTOR_KEYSTORE_PATH_PROPERTY = "keystore.path";
    static final String MQTT_CONNECTOR_KEYSTORE_TYPE_PROPERTY = "keystore.type";
    static final String MQTT_CONNECTOR_KEYSTORE_PASSWORD_PROPERTY = "keystore.password";

    static final String MQTT_CONNECTOR_REQUEST_QUEUE_PROPERTY = "queue.request";
    static final String MQTT_CONNECTOR_RESPONSE_QUEUE_PROPERTY = "queue.response";
    static final String MQTT_CONNECTOR_IOT_QUEUE_PROPERTY = "queue.iot";
    static final String MQTT_CONNECTOR_QOS_PROPERTY = "message.qos";
    static final String MQTT_CONNECTOR_RETAINED_PROPERTY = "message.retained";

    /**
     * Supported MQTT versions.
     */
    static final String MQTT_VERSION_3_1 = "3.1";
    static final String MQTT_VERSION_3_1_1 = "3.1.1";

    /**
     * Supported URL protocol headers.
     */
    private static final String TCP_URL_PROTOCOL_HEADER = "tcp://";
    private static final String SSL_URL_PROTOCOL_HEADER = "ssl://";

    /**
     * Connection host.
     */
    private final String host;

    /**
     * Connection port.
     */
    private final int port;

    /**
     * Connection secure port.
     */
    private final int securePort;

    /**
     * Connection client identifier.
     */
    private final String clientId;
    /**
     * Connection configuration.
     */
    private final MqttConfiguration connectionConfiguration;
    /**
     * LWT configuration.
     */
    private final MqttConfiguration lwtConfiguration;
    /**
     * SSL configuration.
     */
    private final MqttConfiguration sslConfiguration;
    /**
     * Queues configuration.
     */
    private final QueuesConfiguration queuesConfiguration;
    /**
     * Secure connection
     */
    private boolean secureConnection;

    /**
     * Constructor.
     *
     * @param host                    Connection host.
     * @param port                    Connection port.
     * @param securePort              Connection secure port.
     * @param clientId                Connection client identifier.
     * @param secureConnection        Is a secure connection.
     * @param connectionConfiguration Connection configuration.
     * @param lwtConfiguration        LWT configuration.
     * @param sslConfiguration        SSL configuration.
     * @param queueConfiguration      Queue configuration.
     */
    ConnectorConfiguration(String host, int port, int securePort, String clientId, boolean secureConnection,
                           MqttConfiguration connectionConfiguration, MqttConfiguration lwtConfiguration,
                           MqttConfiguration sslConfiguration, QueuesConfiguration queueConfiguration) {
        this.host = host;
        this.port = port;
        this.securePort = securePort;
        this.clientId = clientId;
        this.secureConnection = secureConnection;
        this.connectionConfiguration = connectionConfiguration;
        this.lwtConfiguration = lwtConfiguration;
        this.sslConfiguration = sslConfiguration;
        this.queuesConfiguration = queueConfiguration;
    }

    /**
     * Get the MQTT broker URL.
     *
     * @return MQTT broker URL.
     */
    public String getBrokerUrl() {
        String brokerUrl = secureConnection ? SSL_URL_PROTOCOL_HEADER : TCP_URL_PROTOCOL_HEADER;
        brokerUrl += host;
        brokerUrl += ":";
        brokerUrl += secureConnection ? securePort : port;
        return brokerUrl;
    }

    /**
     * Get the client identifier.
     *
     * @return Client identifier.
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Create MQTT connect options with current configuration.
     *
     * @return MQTT connect options with current configuration.
     */
    public MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        connectionConfiguration.configure(options);
        lwtConfiguration.configure(options);
        sslConfiguration.configure(options);
        return options;
    }

    /**
     * Get the queues configuration.
     *
     * @return Queues configuration.
     */
    public QueuesConfiguration getQueuesConfiguration() {
        return queuesConfiguration;
    }
}
