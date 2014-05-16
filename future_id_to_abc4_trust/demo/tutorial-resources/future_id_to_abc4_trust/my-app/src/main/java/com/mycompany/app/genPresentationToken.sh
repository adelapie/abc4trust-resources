#!/bin/sh
# Generation of Presentation Token for verifier.
#Stop script if an error occurs.
set -e

# Create presentation policy alternatives.
# This method is not specified in H2.2.
echo "Create presentation policy alternatives"
curl -X GET --header 'Content-Type: text/xml' -d @tutorial-resources/presentationPolicyAlternatives.xml 'http://localhost:9300/verification/createPresentationPolicy/' > presentationPolicyAlternatives.xml

# Create presentation UI return.
# This method is not specified in H2.2.
echo "Create presentation UI return"
curl -X POST --header 'Content-Type: text/xml' -d @presentationPolicyAlternatives.xml 'http://localhost:9200/user/createPresentationToken/' > presentationReturn.xml

# Setup uiPresentationReturn.xml.
UiContext=`cat presentationReturn.xml | sed 's/^.*<uiContext>//' | sed 's/<\/uiContext>.*//'`
#echo ${UiContext}
cat tutorial-resources/uiPresentationReturn.xml | sed "s#REPLACE-THIS-CONTEXT#${UiContext}#" > uiPresentationReturn.xml
  
# Create presentation token.
# This method is not specified in H2.2.
echo "Create presentation token"
curl -X POST --header 'Content-Type: text/xml' -d @uiPresentationReturn.xml 'http://localhost:9200/user/createPresentationTokenUi/' > presentationToken.xml

# Setup presentationPolicyAlternativesAndPresentationToken.xml.
presentationPolicy=`cat presentationPolicyAlternatives.xml | sed 's/^.*<PresentationPolicyAlternatives xmlns="http:\/\/abc4trust.eu\/wp2\/abcschemav1.0" Version="1.0">//' | sed 's/<\/PresentationPolicyAlternatives>.*//'`
presentationToken=`cat presentationToken.xml | sed 's/^.*<PresentationToken xmlns="http:\/\/abc4trust.eu\/wp2\/abcschemav1.0" Version="1.0">//' | sed 's/<\/PresentationToken>.*//'`
# echo "${presentationPolicy}"
# echo "${presentationToken}"
echo '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>' > presentationPolicyAlternativesAndPresentationToken.xml
echo '<PresentationPolicyAlternativesAndPresentationToken xmlns="http://abc4trust.eu/wp2/abcschemav1.0" Version="1.0"> <PresentationPolicyAlternatives>' >> presentationPolicyAlternativesAndPresentationToken.xml
echo "${presentationPolicy}" >> presentationPolicyAlternativesAndPresentationToken.xml
echo '</PresentationPolicyAlternatives>' >> presentationPolicyAlternativesAndPresentationToken.xml
echo '<PresentationToken>' >> presentationPolicyAlternativesAndPresentationToken.xml
echo "${presentationToken}" >> presentationPolicyAlternativesAndPresentationToken.xml
echo '</PresentationToken>' >> presentationPolicyAlternativesAndPresentationToken.xml
echo '</PresentationPolicyAlternativesAndPresentationToken>' >> presentationPolicyAlternativesAndPresentationToken.xml
  
# Verify presentation token against presentation policy.
echo "Verify presentation token against presentation policy"
# This method is not specified in H2.2.
curl -X POST --header 'Content-Type: text/xml' -d @presentationPolicyAlternativesAndPresentationToken.xml 'http://localhost:9300/verification/verifyTokenAgainstPolicy/' > presentationTokenDescription.xml


