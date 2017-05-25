package de.craplezz.marble.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author Overload
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public class ChatMessage implements Serializable {

    private String text;

}
