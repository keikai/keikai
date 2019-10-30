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

# build a maven bundle file
function buildBundle(){
    if [[ $edition = "official" ]]
    then
        mvn -B -P $edition ${bundleGoals}
    else
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

STAMP=$(date +%Y%m%d)
VERSION=`sed -nre 's:^.*<version>(.*)</version>.*$:\1:p' pom.xml`
VERSION=(${VERSION[@]})
VERSION=${VERSION[0]}
NEW_VERSION=${VERSION%-SNAPSHOT}

if [ "freshly" = $edition ] ; then
  echo "=== Build $NEW_VERSION.FL.$STAMP ===="
  sed -i "/version>/,/<\//s/>$VERSION.*<\//>$NEW_VERSION.FL.$STAMP<\//" ./pom.xml
  echo "$1 pom.xml"
  grep -n --color=auto $NEW_VERSION.FL.$STAMP ./pom.xml
fi

## update all child modules
mvn -N versions:update-child-modules

buildBundleInstallAll
python3 ./bin/uploadMaven.py $edition
mvn versions:revert

## reset version to original version
if [ "freshly" = $edition ] ; then
  sed -i "/version>/,/<\//s/>$NEW_VERSION.FL.$STAMP<\//>$VERSION<\//" pom.xml
fi