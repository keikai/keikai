#!/usr/bin/env python
# build binary release file

import subprocess
import read_pom_version
import uploadMaven
import shutil
import sys
import os
import logging

logger = logging.getLogger(sys.argv[0])

if len(sys.argv) < 2:
    raise Exception('version cannot be empty!')

MVN = 'mvn'
PHASE = 'process-resources'
PROFILE_OSE = '-P ose'
POM = 'pom.xml'

VERSION = sys.argv[1]

print('Release version: ' + VERSION);

def create_ose_zip():
    subprocess.check_call([MVN, PROFILE_OSE, 'package', PHASE, '-Dmaven.test.skip=true'])

def copyReleaseFiles():
    destinationFolder = uploadMaven.destination_path + 'keikaioss/releases/' + VERSION
    if os.path.exists('./dist'):
        src_files = os.listdir('./dist')
        for file_name in src_files:
            full_file_name = os.path.join('./dist', file_name)
            if os.path.isfile(full_file_name):
                shutil.copyfile(full_file_name, destinationFolder+"/"+file_name)
                logger.info("copied "+full_file_name + " to " + destinationFolder)

shutil.rmtree('./dist', ignore_errors=True)
subprocess.check_call([MVN, "clean"])
create_ose_zip()
uploadMaven.mountRemoteFolder()
copyReleaseFiles()