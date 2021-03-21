import OpenStreetMap.Statistics;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, XMLStreamException, CompressorException, JAXBException {
        CompressorInputStream cis = new CompressorStreamFactory().createCompressorInputStream(CompressorStreamFactory.BZIP2,
                new BufferedInputStream(new FileInputStream("RU-NVS.osm.bz2")));

        Statistics statistics = new Statistics(cis);

        System.out.println(statistics.getUsers());
        System.out.println(statistics.getTagNodes());
    }
}
