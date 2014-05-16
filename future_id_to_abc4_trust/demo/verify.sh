#!/bin/sh

#Stop script if an error occurs.
set -e
  
# Verify presentation token against presentation policy.
echo "Verify presentation token against presentation policy"
# This method is not specified in H2.2.
curl -X POST --header 'Content-Type: text/xml' -d @presentationPolicyAlternativesAndPresentationToken.xml 'http://localhost:9300/verification/verifyTokenAgainstPolicy/' > presentationTokenDescription.xml


