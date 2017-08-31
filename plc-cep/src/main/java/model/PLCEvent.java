package model;

import java.util.Date;

@org.kie.api.definition.type.Role(org.kie.api.definition.type.Role.Type.EVENT)
@org.kie.api.definition.type.Expires("2m")
public class PLCEvent implements java.io.Serializable
{

   static final long serialVersionUID = 1L;

   private java.lang.String id;
   private java.lang.Boolean input1;
   private java.lang.Double input2;
   private Date timestamp;

   public PLCEvent()
   {
   }

   @Override
   public String toString()
   {
      return String.format("PLCEvent [id=%s, input1=%s, input2=%s, timestamp=%s]", id, input1, input2, timestamp);
   }

   public java.lang.String getId()
   {
      return this.id;
   }

   public void setId(java.lang.String id)
   {
      this.id = id;
   }

   public java.lang.Boolean getInput1()
   {
      return this.input1;
   }

   public void setInput1(java.lang.Boolean input1)
   {
      this.input1 = input1;
   }

   public java.lang.Double getInput2()
   {
      return this.input2;
   }

   public void setInput2(java.lang.Double input2)
   {
      this.input2 = input2;
   }

   public Date getTimestamp()
   {
      return timestamp;
   }

   public void setTimestamp(Date timestamp)
   {
      this.timestamp = timestamp;
   }

   public PLCEvent(java.lang.String id, java.lang.Boolean input1,
         java.lang.Double input2, java.util.Date timestamp)
   {
      this.id = id;
      this.input1 = input1;
      this.input2 = input2;
      this.timestamp = timestamp;
   }

}