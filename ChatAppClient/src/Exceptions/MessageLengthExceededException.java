package Exceptions;

public class MessageLengthExceededException extends Exception {
    int length;
    public MessageLengthExceededException(int length) {
        this.length = length;
    }
    public String toString()
    {
        return length + " characters entered. Please enter less than or equal to 255";
    }
}