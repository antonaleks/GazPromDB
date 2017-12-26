package converters;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.kabeja.parser.ParseException;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;

import org.kabeja.dxf.DXFDocument;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;
import org.kabeja.svg.SVGGenerator;
import org.kabeja.xml.SAXGenerator;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class Converter {

    public String parseFile(String sourceFile) {
//        try {
//            String pathToPng = sourceFile.substring(0, sourceFile.length()-4)+".svg";
//            FileOutputStream o=new FileOutputStream(pathToPng);
//            InputStream in = new FileInputStream(sourceFile);//your stream from upload or somewhere
//            Parser dxfParser = ParserBuilder.createDefaultParser();
//            dxfParser.parse(in, "");
//            DXFDocument doc = dxfParser.getDocument();
//            SVGGenerator generator = new SVGGenerator();
//            SAXSerializer out = new SAXSerializer();
//            out.setOutput(o);
//            generator.generate(doc,out,new HashMap());
//            return pathToPng;
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return null;
//        } catch (Exception ioe) {
//            ioe.printStackTrace();
//            return null;
//        }
        Parser parser = ParserBuilder.createDefaultParser();

        try {
            parser.parse(new FileInputStream(sourceFile));

            DXFDocument doc = parser.getDocument();

            //the SVG will be emitted as SAX-Events
            //see org.xml.sax.ContentHandler for more information

            ContentHandler myhandler = new ContentHandler() {
                @Override
                public void setDocumentLocator(Locator locator) {

                }

                @Override
                public void startDocument() throws SAXException {

                }

                @Override
                public void endDocument() throws SAXException {

                }

                @Override
                public void startPrefixMapping(String prefix, String uri) throws SAXException {

                }

                @Override
                public void endPrefixMapping(String prefix) throws SAXException {

                }

                @Override
                public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {

                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {

                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {

                }

                @Override
                public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {

                }

                @Override
                public void processingInstruction(String target, String data) throws SAXException {

                }

                @Override
                public void skippedEntity(String name) throws SAXException {

                }
            };

            //the output - create first a SAXGenerator (SVG here)
            SAXGenerator generator = new SVGGenerator();

            //setup properties
            generator.setProperties(new HashMap());

            //start the output
            generator.generate(doc,myhandler,new HashMap() );


        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
