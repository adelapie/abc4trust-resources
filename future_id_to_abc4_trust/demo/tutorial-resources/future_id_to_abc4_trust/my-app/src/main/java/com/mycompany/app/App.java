package com.mycompany.app;

import java.io.File;
import java.util.List;
import java.io.FileWriter;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jaxen.JaxenException;
import org.jaxen.XPath;
import org.jaxen.dom4j.Dom4jXPath;

import java.lang.Process;

public class App {

  public static final String requestedAttributes = "/home/vmr/second_future_id_abc_4_trust/p2abcengine-master/Code/core-abce/abce-services/tutorial-resources/future_id_to_abc4_trust/my-app/src/main/java/com/mycompany/app/att.xml";
  public static final String presentationPolicyDest = "/home/vmr/second_future_id_abc_4_trust/p2abcengine-master/Code/core-abce/abce-services/tutorial-resources/future_id_to_abc4_trust/my-app/src/main/java/com/mycompany/app/presentationPolicyAlternatives.xml";
  //public static final String tokenGenerationScript = "/home/vmr/second_future_id_abc_4_trust/p2abcengine-master/Code/core-abce/abce-services/tutorial-resources/future_id_to_abc4_trust/my-app/src/main/java/com/mycompany/app/genPresentationToken.sh";
  //public static final String tokenGenerationScriptPath = "/home/vmr/second_future_id_abc_4_trust/p2abcengine-master/Code/core-abce/abce-services/tutorial-resources/future_id_to_abc4_trust/my-app/src/main/java/com/mycompany/app/";

  public static final String tokenGenerationScriptPath = "/home/vmr/second_future_id_abc_4_trust/p2abcengine-master/Code/core-abce/abce-services/tmp/";
  public static final String tokenGenerationScript = "/home/vmr/second_future_id_abc_4_trust/p2abcengine-master/Code/core-abce/abce-services/tmp/mini.sh";

  public static final String policyFirstPart = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                                               "<abc:PresentationPolicyAlternatives xmlns:abc=\"http://abc4trust.eu/wp2/abcschemav1.0\"" +
                                               " Version=\"1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
                                               " xsi:schemaLocation=\"http://abc4trust.eu/wp2/abcschemav1.0 ../../../../../../../../abc4trust-xml/src/main/resources/xsd/schema.xsd\">" +
                                               "<abc:PresentationPolicy PolicyUID=\"http://MyFavoriteSoccerTeam/policies/match/842/vip\">" +
                                               "<abc:Message><abc:ApplicationData>MyFavoriteSoccerTeam vs. OtherTeam</abc:ApplicationData></abc:Message>" +
                                               "<abc:Credential Alias=\"#ticket\"><abc:CredentialSpecAlternatives><abc:CredentialSpecUID>http://MyFavoriteSoccerTeam/tickets/vip</abc:CredentialSpecUID>" +
                                               "</abc:CredentialSpecAlternatives><abc:IssuerAlternatives><abc:IssuerParametersUID>http://ticketcompany/MyFavoriteSoccerTeam/issuance:idemix</abc:IssuerParametersUID></abc:IssuerAlternatives>";
                                              
  public static final String policySecondPart = "</abc:Credential></abc:PresentationPolicy></abc:PresentationPolicyAlternatives>";
  
  public static void main( String[] args ) {

   File xmlFile = new File(requestedAttributes);
   SAXReader reader = new SAXReader();
 
   Document dom4jDocument = null;
   XPath path = null;
   List<Element> results = null;

   StringWriter sw = new StringWriter();
   sw.write(policyFirstPart);
 
   try {
    dom4jDocument = reader.read(xmlFile);
    path = new Dom4jXPath("Attributes/Attribute/ID");
    results = path.selectNodes(dom4jDocument);
   
    for (Element element : results) {
     String disclosedAttribute = new String("<abc:DisclosedAttribute AttributeType=\"" + element.getData() + "\" " + "DataHandlingPolicy=\"http://www.sweetdreamsuites.com/policies/creditcards\"/>");
     sw.write(disclosedAttribute);
     sw.write("\n");
    }
 
   } catch (JaxenException e) {
     e.printStackTrace();
   } catch (DocumentException e) {
     e.printStackTrace();
   }

   sw.write(policySecondPart);
   System.out.println(sw.toString());

   try {
    FileWriter fw = new FileWriter(presentationPolicyDest);
    fw.write(sw.toString());
    fw.close();

    System.out.println("TST");

    Process pr = Runtime.getRuntime().exec(tokenGenerationScript, null, new File(tokenGenerationScriptPath));

   } catch (Exception e) {
    e.printStackTrace();
   }
  }
}
