package com.tobias.recentlyviewedservice;

import com.tobias.recentlyviewedservice.RecentlyviewedServiceApp;
import com.tobias.recentlyviewedservice.config.AsyncSyncConfiguration;
import com.tobias.recentlyviewedservice.config.EmbeddedCassandra;
import com.tobias.recentlyviewedservice.config.EmbeddedKafka;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { RecentlyviewedServiceApp.class, AsyncSyncConfiguration.class })
@EmbeddedCassandra
@EmbeddedKafka
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public @interface IntegrationTest {
}
