package driver;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Soby Chacko
 */
@SpringBootApplication
public class SendToStreamTopic {

	public static void main(String... args) {
		SpringApplication.run(SendToStreamTopic.class, args);
	}

	@Bean
	public ApplicationRunner runner(KafkaTemplate<String, Long> template) {
		return args -> {

			kafkaTemplate().send("streamIn", "foo-1", 1L);
			Thread.sleep(1_000);
		};
	}

	@Bean
	public ProducerFactory<String, Long> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
		return props;
	}

	@Bean
	public KafkaTemplate<String, Long> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
}
