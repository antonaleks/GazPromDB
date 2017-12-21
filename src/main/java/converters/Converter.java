package converters;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;

import org.kabeja.dxf.DXFDocument;
import org.kabeja.parser.*;
import org.kabeja.svg.SVGGenerator;
import org.kabeja.xml.*;

public class Converter {

    public String parseFile(String sourceFile) {
        try {
            String pathToPng = sourceFile.substring(0, sourceFile.length()-4)+".png";
            FileOutputStream o=new FileOutputStream(pathToPng);
            InputStream in = new FileInputStream(sourceFile);//your stream from upload or somewhere
            Parser dxfParser = ParserBuilder.createDefaultParser();
            dxfParser.parse(in, "");
            DXFDocument doc = dxfParser.getDocument();
            SVGGenerator generator = new SVGGenerator();
            SAXSerializer out = new org.kabeja.batik.tools.SAXPNGSerializer();
            out.setOutput(o);
            generator.generate(doc,out,new HashMap());
            return pathToPng;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        } catch (Exception ioe) {
            ioe.printStackTrace();
            return null;
        }
    }
}
