#!/usr/bin/env bash

# create bin directory if it doesn't exist
if [ ! -d "/Users/wenhui/dukeyduke/bin" ]
then
mkdir ../bin
fi

# delete output from previous run
if [ -e "./ACTUAL.txt" ]
then
rm ACTUAL.txt
fi

# compile the code into the bin folder, terminates if error occurred
if ! javac -cp /Users/wenhui/dukeyduke/src -Xlint:none -d /Users/wenhui/dukeyduke/bin /Users/wenhui/dukeyduke/src/main/java/*.java
then
echo "********** BUILD FAILURE **********"
exit 1
fi

# run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ../bin Duke < input.txt > ACTUAL.txt

# compare the output to the expected output
diff ACTUAL.TXT EXPECTED.TXT
if [ $? -eq 0 ]
then
echo "Test result: PASSED"
exit 0
else
echo "Test result: FAILED"
exit 1
fi
/Users/wenhui/dukeyduke/src
