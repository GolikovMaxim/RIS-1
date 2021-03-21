package OpenStreetMap;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "tag")
@Getter
public class Tag {
    @XmlAttribute(name = "k")
    private String key;

    @XmlAttribute(name = "v")
    private String value;
}
