package Gemeinsam;

import java.io.Serializable;
import java.lang.reflect.Type;

public abstract class Nachricht implements Serializable
{
   public abstract Type getType();

}