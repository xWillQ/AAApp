#!/bin/sh
success=0
total=0

printResult() {
  echo "  expected: $2, actual: $1"
  if [ "$1" -eq "$2" ]; then
    echo "\033[32m  Passed \033[0m"
    return 1
  else
    echo "\033[31m  Failed \033[0m"
    return 0
  fi
}

testAndPrint() {  # usage testAndPrint "test_number" "full_command" expected_return_value
  echo " $1:"
  echo "  $2"
  $2 > /dev/null
  printResult $? "$3"
  return $?
}

echo
echo "Help" # ==========================================================
echo

testAndPrint "T1.1" "java -jar app.jar" 1
success=$(( success + $?))
total=$(( total + 1))


testAndPrint "T1.2" "java -jar app.jar -h" 0
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T1.3" "java -jar app.jar -q" 1
success=$(( success + $? ))
total=$(( total + 1))

echo
echo "Authentication" # =====================================================
echo

testAndPrint "T2.1" "java -jar app.jar -login vasya -pass 123" 0
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T2.2" "java -jar app.jar -pass 123 -login vasya" 0
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T2.3" "java -jar app.jar -login VASYA -pass 123" 2
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T2.4" "java -jar app.jar -login asd -pass 123" 3
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T2.5" "java -jar app.jar -login admin -pass 1234" 4
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T2.6" "java -jar app.jar -login admin -pass admin" 0
success=$(( success + $? ))
total=$(( total + 1))

echo
echo "Authorization" # =====================================================
echo

testAndPrint "T3.1" "java -jar app.jar -login vasya -pass 123 -role READ -res A" 0
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T3.2" "java -jar app.jar -login vasya -pass 123 -role DELETE -res A" 5
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T3.3" "java -jar app.jar -login vasya -pass 123 -role WRITE -res A" 6
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T3.4" "java -jar app.jar -login vasya -pass 123 -role READ -res A.B" 0
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T3.5" "java -jar app.jar -login admin -pass admin -role WRITE -res A.B.C" 0
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T3.6" "java -jar app.jar -login vasya -pass 1234 -role DELETE -res A" 4
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T3.7" "java -jar app.jar -login vasya -pass 123 -role WRITE -res A.B.C" 0
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T3.8" "java -jar app.jar -login admin -pass admin -role READ" 0
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T3.9" "java -jar app.jar -login admin -pass admin -role EXECUTE -res A" 6
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T3.10" "java -jar app.jar -login admin -pass admin -role WRITE -res A.A" 6
success=$(( success + $? ))
total=$(( total + 1))

echo
echo "Accounting" # =====================================================
echo

testAndPrint "T4.1" "java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol 1024" 0
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T4.2" "java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 20202-03-10 -de 2020-04-01 -vol 1024" 7
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T4.3" "java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-12-10 -de 2020-13-01 -vol 1024" 7
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T4.4" "java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-04-31 -de 2020-04-01 -vol 1024" 7
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T4.5" "java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-02-32 -de 2020-04-01 -vol 1024" 7
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T4.6" "java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol -1024" 7
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T4.7" "java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol alot" 7
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T4.8" "java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01" 0
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T4.9" "java -jar app.jar -login admin -pass admin -role WRITE -res A.B.C -ds 2020-03-10 -de 2020-01-01 -vol 1024" 0
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T4.10" "java -jar app.jar -login vasya -pass 123 -role WRITE -res A.B.C -ds 2020-12-01 -de 2020-01-45 -vol 1024" 7
success=$(( success + $? ))
total=$(( total + 1))

testAndPrint "T4.11" "java -jar app.jar -login vasya -pass 123 -role EXECUTE -res A.B.C -ds 2020-12-01 -de 2020-01-45 -vol 1024" 6
success=$(( success + $? ))
total=$(( total + 1))



echo
if [ $success -eq $total ]; then
  echo "Results: \033[32m$success/$total\033[0m tests passed"
  return 0
else
  echo "Results: \033[31m$success/$total\033[0m tests passed"
  return 1
fi
