package converters;


import java.io.*;
import java.util.HashMap;

import org.kabeja.parser.ParseException;
import org.kabeja.xml.SAXPrettyOutputter;
import org.xml.sax.ContentHandler;

import org.kabeja.dxf.DXFDocument;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;
import org.kabeja.svg.SVGGenerator;

public class Converter {

    public String parseFile(String sourceFile) {
        try {
            String pathToSvg = sourceFile.substring(0, sourceFile.length()-4)+".svg";
            FileOutputStream o=new FileOutputStream(pathToSvg);
            InputStream in = new FileInputStream(sourceFile);
            Parser dxfParser = ParserBuilder.createDefaultParser();
            dxfParser.parse(in, "");
            DXFDocument doc = dxfParser.getDocument();
            SVGGenerator generator = new SVGGenerator();

            final ContentHandler myhandler = new SAXPrettyOutputter(o);
            generator.generate(doc, myhandler, new HashMap());
            return pathToSvg;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        } catch (Exception ioe) {
            ioe.printStackTrace();
            return null;
        }

    }

}
