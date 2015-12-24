#!/bin/bash

while getopts f:k:v: option
do
	case "${option}"
		in
		f) FILE=${OPTARG};;
		k) KEY=${OPTARG};;
		v) VALUE=${OPTARG};;
	esac
done

if [ -z $FILE ] || [ -z $KEY ] ; then
	echo "Update properties failed - requires -f FILE -k KEY -v VALUE"
	exit 1
fi

# unamestr=`uname`
# if [[ "$unamestr" == 'Linux' ]]; then
#    sed -i "s|\($KEY = \)\(.*\)|\1$VALUE|" $FILE
# elif [[ "$unamestr" == 'Darwin' ]]; then
#    sed -i '' "s|\($KEY = \)\(.*\)|\1$VALUE|" $FILE
# fi

unamestr=`uname`
if [[ "$unamestr" == 'Linux' ]]; then
   sed -i "s|\($KEY[ ]*=[ ]*\)\(.*\)|\1$VALUE|" $FILE
elif [[ "$unamestr" == 'Darwin' ]]; then
   sed -i '' "s|\($KEY[ ]*=[ ]*\)\(.*\)|\1$VALUE|" $FILE
fi