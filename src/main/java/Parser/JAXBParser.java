package Parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlType;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class JAXBParser {
    private static final Logger logger = LogManager.getLogger(JAXBParser.class);

    public <T> void parseXML(XMLStreamReader xmlStreamReader, Class<T> type, Consumer<T> onParseElement)
            throws XMLStreamException, JAXBException {
        String tagName = getTagName(type);
        logger.debug("Parsing tags named " + tagName);

        JAXBContext context = JAXBContext.newInstance(type);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        while(xmlStreamReader.hasNext()) {
            int event = xmlStreamReader.next();
            if(event == XMLEvent.START_ELEMENT && tagName.equals(xmlStreamReader.getLocalName())) {
                T element = unmarshaller.unmarshal(xmlStreamReader, type).getValue();
                onParseElement.accept(element);
            }
        }
    }

    public <T> List<T> parseXML(XMLStreamReader xmlStreamReader, Class<T> type) throws JAXBException, XMLStreamException {
        String tagName = getTagName(type);
        logger.debug("Parsing tags named " + tagName);

        JAXBContext context = JAXBContext.newInstance(type);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        List<T> result = new ArrayList<>();
        while(xmlStreamReader.hasNext()) {
            int event = xmlStreamReader.next();
            if(event == XMLEvent.START_ELEMENT && tagName.equals(xmlStreamReader.getLocalName())) {
                T element = unmarshaller.unmarshal(xmlStreamReader, type).getValue();
                result.add(element);
            }
        }

        return result;
    }

    private <T> String getTagName(Class<T> type) {
        XmlType xmlType = type.getAnnotation(XmlType.class);
        String tagName;
        if(xmlType == null) {
            tagName = type.getName();
        } else {
            tagName = xmlType.name();
        }

        return tagName;
    }
}
