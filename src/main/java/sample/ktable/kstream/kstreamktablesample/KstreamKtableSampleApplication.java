package sample.ktable.kstream.kstreamktablesample;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;

@SpringBootApplication
public class KstreamKtableSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(KstreamKtableSampleApplication.class, args);
	}

	@EnableBinding(KStreamProcessorX.class)
	public static class KStreamKTableSample {


		@StreamListener
		public void process(@Input("kstreamInput") KStream<String, Long> fooStream,
							@Input("ktableInput") KTable<String, String> fooTable) {

			fooStream.foreach((key, value) -> {
				System.out.println("stream key received: " + key);
				System.out.println("stream value received: " + value);
			});

			fooTable.toStream().foreach((key, value) -> {
				System.out.println("table key received: " + key);
				System.out.println("table value received: " + value);
			});

		}
	}

	interface KStreamProcessorX  {

		@Input("kstreamInput")
		KStream<?, ?> kstreamInput();

		@Input("ktableInput")
		KTable<?, ?> ktableInput();
	}
}
