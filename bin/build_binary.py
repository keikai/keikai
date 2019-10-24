#!/usr/bin/env python
# build binary release file

import subprocess
import read_pom_version

MVN = 'mvn'
PHASE = 'process-resources'
PROFILE_EE = '-P ee'
PROFILE_EVAL = '-P eval'
PROFILE_OSE = '-P ose'
POM = 'pom.xml'
VERSION = '5.0.0'

def create_ose_zip():
    subprocess.check_call([MVN, PROFILE_OSE, PHASE])

subprocess.check_call([MVN, "clean"])
create_ose_zip()