#!/bin/bash

docker build -f Dockerfile.deploytest -t xref-api:local-deploy-test .

docker run xref-api:local-deploy-test | tee testResults

if grep --quiet 'FAILED' testresults; then
    echo 'IT FAILED'
fi
