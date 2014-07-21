
package org.mpilone.hazelcastmq.example.spring.tx.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hazelcast.core.*;

/**
 * A simple business service to be used in the transaction examples.
 *
 * @author mpilone
 */
public class BusinessService {
  private HazelcastInstance hazelcastInstance;
  private final static Logger log = LoggerFactory.getLogger(
      BusinessService.class);

  public BusinessService() {
    this.hazelcastInstance = null;
  }

  public BusinessService(HazelcastInstance hazelcastInstance) {
    this();

    this.hazelcastInstance = hazelcastInstance;
  }

  public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
    this.hazelcastInstance = hazelcastInstance;
  }

  @Transactional
  public void processWithTransaction() {
      IQueue<String> demoQueue = getQueue("demo.queue",
        hazelcastInstance);

    log.info("Offering to queue in transaction.");
    demoQueue.offer(getClass().getName() + ": processWithTransaction");

    log.info("Sleeping.");

    try {
      Thread.sleep(5000);
    }
    catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    }

    log.info("Done.");
  }

  public void processWithoutTransaction() {
    IQueue<String> demoQueue = getQueue("demo.queue",
        hazelcastInstance);

    log.info("Offering to queue outside transaction.");
    demoQueue.offer(getClass().getName() + ": processWithoutTransaction");

    log.info("Sleeping.");

    try {
      Thread.sleep(5000);
    }
    catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    }

    log.info("Done.");
  }

  @Transactional
  public void processWithTransactionAndException() {
    IQueue<String> demoQueue = getQueue("demo.queue",
        hazelcastInstance);

    log.info("Offering to queue in transaction.");
    demoQueue.offer(getClass().getName()
        + ": processWithTransactionAndException");

    log.info("Sleeping.");

    try {
      Thread.sleep(5000);
    }
    catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    }

    log.info("Throwing exception in transaction.");
    throw new RuntimeException("Better roll back.");
  }

  /**
   * Gets the queue with the given name. The default implementation simply uses
   * {@link HazelcastInstance#getQueue(java.lang.String)} but subclasses can use
   * different strategies.
   *
   * @param name the name of the queue to get
   * @param hazelcastInstance the HazelcastInstance to get the queue from
   *
   * @return the queue instance
   */
  protected IQueue<String> getQueue(String name,
      HazelcastInstance hazelcastInstance) {
    return hazelcastInstance.getQueue(name);
  }

}
