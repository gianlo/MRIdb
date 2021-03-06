#!/bin/bash -eu

prog="mridb"

service $prog stop

cd /tmp
curl -LO http://cisbic.bioinformatics.ic.ac.uk/files/$prog/$prog.tar.bz2
tar xf $prog.tar.bz2 -C /opt
rm -f $prog.tar.bz2

service $prog start
