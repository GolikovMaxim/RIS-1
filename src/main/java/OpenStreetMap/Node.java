package OpenStreetMap;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "node")
@Getter
public class Node {
    @XmlAttribute(name = "user")
    private String userName;

    @XmlElement(name = "tag")
    private List<Tag> tags;
}
