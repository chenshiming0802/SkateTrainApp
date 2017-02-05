package com.sprcore.fosun.utils.depend;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class DateJsonValueProcessor  implements JsonValueProcessor{
    private String format = "yyyy-MM-dd HH:mm:ss";  //yyyy-MM-dd HH:mm:ss
    
    public DateJsonValueProcessor()  
    {
        System.out.println("DateJsonValueProcessor");
    }  
   
    public DateJsonValueProcessor(String format)  
    {
        //System.out.println("DateJsonValueProcessor(String format)");
   
        this.format = format;  
    }
     
    @Override
    public Object processArrayValue(Object value, JsonConfig jsonConfig)  
    {
         
        //System.out.println("processArrayValue");
   
        String[] obj = {};  
        if (value instanceof Date[])  
        {  
            SimpleDateFormat sf = new SimpleDateFormat(format);  
            Date[] dates = (Date[]) value;  
            obj = new String[dates.length];  
            for (int i = 0; i < dates.length; i++)  
            {  
                obj[i] = sf.format(dates[i]);  
            }  
        }else if (value instanceof Timestamp[])  
        {  
            SimpleDateFormat sf = new SimpleDateFormat(format);  
            Timestamp[] dates = (Timestamp[]) value;  
            obj = new String[dates.length];  
            for (int i = 0; i < dates.length; i++)  
            {  
                obj[i] = sf.format(dates[i]);  
            }  
        }
        return obj;  
    }
     
    @Override
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig)  
    {
         
        System.out.println("processObjectValue");
   
        if (value instanceof Date)  
        {  
            String str = new SimpleDateFormat(format).format((Date) value);  
            return str;  
        }else if (value instanceof Timestamp){
        	String str = new SimpleDateFormat(format).format((Timestamp) value); 
        	 return str;  
        }
        
        return value;  
    }  
   
    public String getFormat()  
    {  
   
        return format;  
    }  
   
    public void setFormat(String format)  
    {  
   
        this.format = format;  
    }

}
