#!/bin/bash
# Purpose:
#   1. build Keikai Spreadsheet Official version
#   2. copy all released zip files to potix folders
#
# Usage:
#   Command parameters:
#     VERSION: same as pom.xml#revision verison
#   For example
#     $ ./bin/release.sh 5.0.0
version=$1
if [ "$version" = "" ] ; then
    echo "Version not found!"
    echo "For example,"
    echo "  $ ./bin/release.sh 5.0.0"
    exit 1
fi

python3 ./bin/read_pom_version.py './pom.xml' $verison
chmod +x ./bin/mavenBuild.sh

echo "Build with $version for Official version"
./bin/mavenBuild.sh official

python3 ./bin/build_binary.py $version