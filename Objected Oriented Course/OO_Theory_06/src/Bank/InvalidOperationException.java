package Bank;

import java.io.*;

public class InvalidOperationException extends Exception{
   
    private String message;//a detailed message

    public InvalidOperationException(String message) { 
 
         this.message=message;
 
    }
}