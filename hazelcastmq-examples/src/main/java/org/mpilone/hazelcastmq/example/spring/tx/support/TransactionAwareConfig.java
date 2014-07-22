
package org.mpilone.hazelcastmq.example.spring.tx.support;

import org.mpilone.hazelcastmq.spring.tx.TransactionAwareHazelcastInstanceProxyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * Spring configuration for the
 * {@link TransactionAwareHazelcastInstanceProxyFactory} example.
 *
 * @author mpilone
 */
@Configuration
@EnableTransactionManagement
public class TransactionAwareConfig {

  @Bean(destroyMethod = "shutdown")
  public HazelcastInstance hazelcast() {
    return Hazelcast.newHazelcastInstance();
  }

  @Bean
  public HazelcastInstance transactionAwareHazelcast() {
    TransactionAwareHazelcastInstanceProxyFactory factory =
        new TransactionAwareHazelcastInstanceProxyFactory(hazelcast(), true);

    return factory.create();
  }

  @Bean
  public NoopTransactionManager transactionManager() {
    return new NoopTransactionManager();
  }

  @Bean
  public BusinessService businessService() {
    return new BusinessService(transactionAwareHazelcast());
  }

  @Bean(destroyMethod = "shutdown")
  public DemoQueueReader queueReader() {
    return new DemoQueueReader(transactionAwareHazelcast());
  }

}
