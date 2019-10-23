#!/bin/bash
# Purpose:
# build Keikai Spreadsheet with maven. Because the whole process requires many steps, therefore I write it as script instead of configuring in jenkins

# Usage:
# run the script on top of 3 folders checked out from 3 repositories (zsspoi, zkspreadsheet, zsscml)

# Command parameters:
# no parameter: freshly
# official: official EE
# eval: EE evaluation

bundleGoals='clean source:jar javadoc:jar repository:bundle-create -Dmaven.test.skip=true'
evalBundleGoals='clean repository:bundle-create -Dmaven.test.skip=true' # no need source javadoc

#zpoiVersion='unset'

function buildBundleInstall(){
   buildBundle $1 $2
    # install an artifact for resolving dependencies correctly
    mvn -pl $1 -am install -Dmaven.test.skip=true
}

# build a maven bundle file
function buildBundle(){
    mvn -B versions:set -DremoveSnapshot #remove '-SNAPSHOT' from project version
    if [[ $edition = "official" ]]
    then
        mvn -B -P $edition ${bundleGoals}
    else
        mvn -P $edition validate -N # set freshly version
        # http://maven.apache.org/plugins/maven-repository-plugin/usage.html
        mvn -B ${evalBundleGoals}
    fi
}

edition=$1
if [[ "official" = $edition ]] || [[ "eval" = $edition ]]
then
    echo "=== Build $edition ==="
else
    echo "=== Build freshly ==="
    edition="freshly"
fi


function buildBundleInstallAll(){
   buildBundle
    # install an artifact for resolving dependencies correctly
    mvn install -Dmaven.test.skip=true
}

buildBundleInstallAll
python3 ./bin/uploadMaven.py $edition