#!/bin/bash
#prune jars from the big coat java jar so that I can depend on projects

SCRIPTDIR=`dirname $0`
CLARA_SERVICES=$SCRIPTDIR/../lib/services ; export CLARA_SERVICES
CLAS12DIR=$SCRIPTDIR/../ ; export CLAS12DIR

#***********************************************************************
# CLEAN UP CLARA_SERVICES DIRECTORY
#***********************************************************************
echo '---> Cleaning CLARA_SERVICES directory'
rm -rf $CLARA_SERVICES/*

#***********************************************************************
# UNPACK SERVICES
#***********************************************************************
echo '---> Unpacking services into directory'
cd $CLARA_SERVICES
#for i in `ls -a $CLARA_SERVICES/../plugins/*.jar`
for i in `ls -a ../plugins/*.jar`
do  
echo "---> unpacking : $i"
jar -xf $i
done 
echo '---> done unpacking\n\n' 
cd -
#***********************************************************************
# UNPACK CLARA DISTRIBUTION
#***********************************************************************
cd $CLARA_SERVICES
jar -xf ../clas/cnuphys-2.0-SNAPSHOT.jar
jar -xf ../clas/coat-libs-2.0-SNAPSHOT.jar 
#tar -xf $CLARA_SERVICES/../clas/coat-libs-2.0-SNAPSHOT.jar --directory $CLARA_SERVICES
cd -
cp $CLARA_SERVICES/../../etc/configs/Evio*yaml $CLARA_SERVICES/std/services/convertors/.

#***********************************************************************
# 
#***********************************************************************

CLASSPATH="$CLARA_SERVICES/.:$CLAS12DIR/lib/plugins/*:$CLAS12DIR/lib/clas/*"
MAIN=org.jlab.coda.clara.platform.CPlatform

java $JVMOPTIONS -cp $CLASSPATH $MAIN "$@"

