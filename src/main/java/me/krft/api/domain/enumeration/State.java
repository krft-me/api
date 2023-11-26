package me.krft.api.domain.enumeration;

/**
 * State enum\nRepresents the state of an order
 */
public enum State {
    WAITING,
    UNSIGNED,
    SIGNED,
    IN_PROGRESS,
    CANCELLED,
    DONE,
}
