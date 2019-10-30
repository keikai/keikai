#!/usr/bin/env python
# A script to read/write a project's version in pom.xml

import xml.etree.ElementTree as ElementTree
import os
import logging
import sys

logger = logging.getLogger( sys.argv[0])

def findProjectVersion(pom_file):
    namespaces = {'' : 'http://maven.apache.org/POM/4.0.0'}
    for prefix, uri in namespaces.items():
        ElementTree.register_namespace(prefix, uri)
    ns = '{http://maven.apache.org/POM/4.0.0}'
    tree = ElementTree.parse(pom_file)
    root = tree.getroot()
    version = root.find(ns+'version')
    if version is not None:
        return version.text
    return None

def findPropertyVersion(pom_file):
    namespaces = {'' : 'http://maven.apache.org/POM/4.0.0'}
    for prefix, uri in namespaces.items():
        ElementTree.register_namespace(prefix, uri)
    tree = ElementTree.parse(pom_file)
    root = tree.getroot()
    ns = '{http://maven.apache.org/POM/4.0.0}'
    return root.find(ns+"properties").find(ns+"revision").text

def modifyVersion(pom_file, version):
    namespaces = {'' : 'http://maven.apache.org/POM/4.0.0'}
    for prefix, uri in namespaces.items():
        ElementTree.register_namespace(prefix, uri)
    ns = '{http://maven.apache.org/POM/4.0.0}'
    tree = ElementTree.parse(pom_file)
    root = tree.getroot()
    root.find(ns+"version").text = version
    tree.write(pom_file)
    logger.info("set version " + (root.find(ns+"version").text))


def modifyReVersion(pom_file, version):
    namespaces = {'' : 'http://maven.apache.org/POM/4.0.0'}
    for prefix, uri in namespaces.items():
        ElementTree.register_namespace(prefix, uri)
    ns = '{http://maven.apache.org/POM/4.0.0}'
    tree = ElementTree.parse(pom_file)
    root = tree.getroot()
    root.find(ns+"properties").find(ns+"revision").text = version
    tree.write(pom_file)
    logger.info("set revision " + (root.find(ns+"properties").find(ns+"revision").text))


def validate(pom_file):
    return pom_file.endswith("pom.xml")


def main():
    logging.basicConfig(level='INFO')
    pom_file = sys.argv[1]
    if not validate(pom_file):
        logger.error('not a pom.xml')
        return 2
    version = findProjectVersion(pom_file)
    revision = None
    newVersion = None
    if version is None or version == '${revision}':
        revision = findPropertyVersion(pom_file)
        version = revision
    print('Version: ' + version)

    if len(sys.argv) > 2:
        newVersion = sys.argv[2]
    if newVersion is not None:
        if revision is not  None:
            modifyReVersion(pom_file, newVersion);
            print('update revision from ' + revision + ' to ' + newVersion)
        else:
            modifyVersion(pom_file, newVersion)
            print('update version from ' + version + ' to ' + newVersion)


if __name__== "__main__":
    main()