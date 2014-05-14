package com.mycompany.app;

import java.io.File;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jaxen.JaxenException;
import org.jaxen.XPath;
import org.jaxen.dom4j.Dom4jXPath;

public class App 
{
    public static void main( String[] args )
    {

    // XXXX: It must be an string.

  File xmlFile = new File("/home/vmr/second_future_id_abc_4_trust/p2abcengine-master/Code/core-abce/abce-services/tutorial-resources/future_id_to_abc4_trust/my-app/src/main/java/com/mycompany/app/att.xml");
  SAXReader reader = new SAXReader();
 
  Document dom4jDocument = null;
  XPath path = null;
  List<Element> results = null;
 
  try {
 
   dom4jDocument = reader.read(xmlFile);
   path = new Dom4jXPath("Attributes/Attribute/ID");
   results = path.selectNodes(dom4jDocument);
 
   for (Element element : results) {
    System.out.println("<abc:DisclosedAttribute AttributeType=\"" + element.getData() + "\" " + "DataHandlingPolicy=\"http://www.sweetdreamsuites.com/policies/creditcards\"/>");
   }
 
  } catch (JaxenException e) {
   e.printStackTrace();
  } catch (DocumentException e) {
   e.printStackTrace();
  }

// 1st part

/*

<?xml version="1.0" encoding="UTF-8" standalone="yes"?>

<!-- This is a sample ABC4Trust presentation policy for... -->

<abc:PresentationPolicyAlternatives
  xmlns:abc="http://abc4trust.eu/wp2/abcschemav1.0"
  Version="1.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://abc4trust.eu/wp2/abcschemav1.0 ../../../../../../../../abc4trust-xml/src/main/resources/xsd/schema.xsd">
  <abc:PresentationPolicy
    PolicyUID="http://MyFavoriteSoccerTeam/policies/match/842/vip">
    <abc:Message>
      <abc:ApplicationData>
        MyFavoriteSoccerTeam vs. OtherTeam
      </abc:ApplicationData>
    </abc:Message>
    <abc:Credential Alias="#ticket">
      <abc:CredentialSpecAlternatives>
        <abc:CredentialSpecUID>http://MyFavoriteSoccerTeam/tickets/vip</abc:CredentialSpecUID>
      </abc:CredentialSpecAlternatives>
      <abc:IssuerAlternatives>
        <abc:IssuerParametersUID>http://ticketcompany/MyFavoriteSoccerTeam/issuance:idemix</abc:IssuerParametersUID>
      </abc:IssuerAlternatives>

DISCLOSURES---
*/

/*

 2nd part

    </abc:Credential>
  </abc:PresentationPolicy>
</abc:PresentationPolicyAlternatives>

*/

   

    }
}
