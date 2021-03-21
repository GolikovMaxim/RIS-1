package OpenStreetMap;

import Parser.JAXBParser;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.*;

public class Statistics {
    private static final Logger logger = LogManager.getLogger(Statistics.class);

    private XMLStreamReader xmlStreamReader;
    @Getter private List<User> users;
    @Getter private Map<String, Integer> tagNodes = new HashMap<>();

    public Statistics(InputStream inputStream) throws XMLStreamException, JAXBException {
        xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(inputStream);

        JAXBParser parser = new JAXBParser();
        Map<String, Integer> usersMap = new HashMap<>();

        logger.debug("Start parsing");
        parser.parseXML(xmlStreamReader, Node.class, node -> {
            usersMap.put(node.getUserName(), (usersMap.get(node.getUserName()) == null ? 0 : usersMap.get(node.getUserName())) + 1);

            List<Tag> tags = node.getTags();
            if(tags != null) {
                for(Tag tag : tags) {
                    tagNodes.put(tag.getKey(), (tagNodes.get(tag.getKey()) == null ? 0 : tagNodes.get(tag.getKey())) + 1);
                }
            }
        });
        logger.debug("Parsing finished successfully");

        users = new ArrayList<>();
        for(Map.Entry<String, Integer> pair : usersMap.entrySet()) {
            users.add(new User(pair.getKey(), pair.getValue()));
        }
        users.sort(Comparator.comparingInt(User::getChangeCount));
    }
}
