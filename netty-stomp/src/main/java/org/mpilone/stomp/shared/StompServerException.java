package org.mpilone.stomp.shared;

/**
 * An exception related to STOMP frame processing.
 * 
 * @author mpilone
 */
public class StompServerException extends RuntimeException {

  /**
   * Serialization ID.
   */
  private static final long serialVersionUID = 1L;

  private Frame frame;
  private String details;

  /**
   * Constructs the exception with a related frame that was being processed when
   * the error occurred.
   *
   * @param message the short error message
   * @param details the detailed error message to include in a response body
   * @param frame the frame that was being processed when the error occurred
   */
  public StompServerException(String message, String details, Frame frame) {
    this(message);

    this.frame = frame;
    this.details = details;
  }

  /**
   * Returns the frame that was being processed when the error occurred.
   *
   * @return the frame or null
   */
  public Frame getFrame() {
    return frame;
  }

  /**
   * Returns the detailed error description to return to the client as the body
   * of the ERROR frame if possible.
   *
   * @return the detailed error message or null
   */
  public String getDetails() {
    return details;
  }

  /**
   * Constructs the exception with the given message.
   * 
   * @param message
   *          the human readable message
   */
  public StompServerException(String message) {
    super(message);
  }

  /**
   * Constructs the exception with the given cause.
   * 
   * @param cause
   *          the root cause exception
   */
  public StompServerException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs the exception with the given message and cause.
   * 
   * @param message
   *          the human readable message
   * @param cause
   *          the root cause exception
   */
  public StompServerException(String message, Throwable cause) {
    super(message, cause);
  }

}
