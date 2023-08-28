package org.construction.wms.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*Spring container provide a Converter interface that can convert one object type to another object type.
* The below converter converts String type to Date type.
* */
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String s) {
        try {
            if(s.equals("")){
                return null;
            }
            /*parse a string representation into a Date object using parse() method,
            use format() method to format the current date into a string representation*/
            return new SimpleDateFormat("yyyy-MM-dd").parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
