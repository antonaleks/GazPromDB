package converters;

/*
import org.kabeja.dxf.DXFDocument;
import org.kabeja.parser.*;
import org.kabeja.parser.ParserBuilder;
import org.kabeja.svg.SVGGenerator;
import org.kabeja.xml.*;

public class Converter {
    private String pathToDFX = "src/main/resources/HydrotreaterUnitSimulation.dxf";

    public void parseFile(String sourceFile) {
        try {
            FileOutputStream o = new FileOutputStream(sourceFile.substring(0, sourceFile.length()-4)+".png");
            InputStream in = new FileInputStream(sourceFile);//your stream from upload or somewhere
            Parser dxfParser = ParserBuilder.createDefaultParser();
            dxfParser.parse(in, "");
            DXFDocument doc = dxfParser.getDocument();
            SVGGenerator generator = new SVGGenerator();
            SAXSerializer out = new org.kabeja.batik.tools.SAXPNGSerializer();
            out.setOutput(o);
            generator.generate(doc,out,new HashMap());

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }
}
*/