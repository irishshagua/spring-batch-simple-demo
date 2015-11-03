package com.mooneyserver.batch.util;

import java.io.IOException;

import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Source;

import lombok.SneakyThrows;

import org.springframework.batch.item.xml.stax.DefaultFragmentEventReader;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.util.xml.StaxUtils;

public class MySimpleUnmarshaller implements Unmarshaller {

  @Override
  public boolean supports(Class<?> clazz) {
    return false;
  }

  @SneakyThrows
  @Override
  public Object unmarshal(Source source) throws IOException,
      XmlMappingException {    
    DefaultFragmentEventReader reader = (DefaultFragmentEventReader) StaxUtils.getXMLEventReader(source);
    StringBuilder sb = new StringBuilder();
    while(reader.hasNext()) {
      XMLEvent event = reader.nextEvent();
      if (!event.isStartDocument() && !event.isEndDocument()) {
        sb.append(event.toString());
      }
    }    
    
    return sb.toString();
  }

}
