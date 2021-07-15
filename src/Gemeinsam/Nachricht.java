package Gemeinsam;

import java.io.Serializable;
import java.lang.reflect.Type;

public abstract class Nachricht implements Serializable
{
   public abstract Type getType();
   boolean gelesen;
   int gesendetAn;
   int bestaetigung;
   int hashCode;

   public void setGesendetAn(int gesendetAn)
   {
      this.gesendetAn = gesendetAn;
   }
   public int getHashCode()
   {
      return hashCode;
   }
   public void bestaetigungGelesen()
   {
      bestaetigung++;
   }
   public int getGesendetAn()
   {
      return gesendetAn;
   }
   public int getBestaetigungen()
   {
      return bestaetigung;
   }

}