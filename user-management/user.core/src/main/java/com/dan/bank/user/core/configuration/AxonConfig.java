package com.dan.bank.user.core.configuration;

import com.mongodb.client.MongoClient;
import com.thoughtworks.xstream.XStream;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.metrics.GlobalMetricRegistry;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class AxonConfig {

    @Value("${spring.data.mongodb.database:user}")
    private String mongoDatabase;

    @Bean
    public MongoTemplate axonMongoTemplate(final MongoClient mongoClient){
        return DefaultMongoTemplate.builder()
                .mongoDatabase(mongoClient, mongoDatabase)
                .build();
    }

    @Bean
    public TokenStore tokenStore(final Serializer serializer, final MongoTemplate axonMongoTemplate){
        return MongoTokenStore.builder()
                .mongoTemplate(axonMongoTemplate)
                .serializer(serializer)
                .build();
    }

    @Bean
    public EventStorageEngine eventStorageEngine(final MongoTemplate axonMongoTemplate,
                                                 final Serializer defaultSerializer){
        return MongoEventStorageEngine.builder()
                .mongoTemplate(axonMongoTemplate)
                .eventSerializer(defaultSerializer)
                .build();
    }

    @Bean
    public EventStore embeddedEventStore(final EventStorageEngine eventStorageEngine,
                                         final GlobalMetricRegistry metricRegistry){
        return EmbeddedEventStore.builder()
                .storageEngine(eventStorageEngine)
                .messageMonitor(metricRegistry.registerEventBus("eventStore"))
                .build();
    }
    @Bean
    @Primary
    public Serializer defaultSerializer() {
        final XStream xStream = new XStream();
        xStream.allowTypesByWildcard(new String[] { "com.dan.bank.**" });
        return XStreamSerializer.builder()
                .xStream(xStream)
                .build();
    }

}
