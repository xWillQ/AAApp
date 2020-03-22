#!/bin/sh
success=0
total=0

printResult() {
  echo "  expected: $2, actual: $1"
  if [ $1 -eq $2 ]; then
    echo "\033[32m  Passed \033[0m"
    return 1
  else
    echo "\033[31m  Failed \033[0m"
    return 0
  fi
}

echo
echo "Help" # ==========================================================
echo

echo " T1.1:"
echo "  java -jar app.jar "
java -jar app.jar > /dev/null
printResult $? 1                # 1 - expected return value
success=$(( $success + $?))
total=$(( $total + 1))


echo " T1.2:"
echo "  java -jar app.jar -h "
java -jar app.jar -h > /dev/null
printResult $? 0
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T1.3:"
echo "  java -jar app.jar -q  "
java -jar app.jar -q  > /dev/null
printResult $? 1
success=$(( $success + $? ))
total=$(( $total + 1))

echo
echo "Authentication" # =====================================================
echo

echo " T2.1:"
echo "  java -jar app.jar -login vasya -pass 123 "
java -jar app.jar -login vasya -pass 123 > /dev/null
printResult $? 0
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T2.2:"
echo "  java -jar app.jar -pass 123 -login vasya "
java -jar app.jar -pass 123 -login vasya > /dev/null
printResult $? 0
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T2.3:"
echo "  java -jar app.jar -login VASYA -pass 123 "
java -jar app.jar -login VASYA -pass 123 > /dev/null
printResult $? 2
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T2.4:"
echo "  java -jar app.jar -login asd -pass 123 "
java -jar app.jar -login asd -pass 123 > /dev/null
printResult $? 3
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T2.5:"
echo "  java -jar app.jar -login admin -pass 1234 "
java -jar app.jar -login admin -pass 1234 > /dev/null
printResult $? 4
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T2.6:"
echo "  java -jar app.jar -login admin -pass admin "
java -jar app.jar -login admin -pass admin > /dev/null
printResult $? 0
success=$(( $success + $? ))
total=$(( $total + 1))

echo
echo "Authorization" # =====================================================
echo

echo " T3.1:"
echo "  java -jar app.jar -login vasya -pass 123 -role READ -res A "
java -jar app.jar -login vasya -pass 123 -role READ -res A > /dev/null
printResult $? 0
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T3.2:"
echo "  java -jar app.jar -login vasya -pass 123 -role DELETE -res A "
java -jar app.jar -login vasya -pass 123 -role DELETE -res A > /dev/null
printResult $? 5
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T3.3:"
echo "  java -jar app.jar -login vasya -pass 123 -role WRITE -res A "
java -jar app.jar -login vasya -pass 123 -role WRITE -res A > /dev/null
printResult $? 6
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T3.4:"
echo "  java -jar app.jar -login vasya -pass 123 -role READ -res A.B "
java -jar app.jar -login vasya -pass 123 -role READ -res A.B > /dev/null
printResult $? 0
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T3.5:"
echo "  java -jar app.jar -login admin -pass admin -role WRITE -res A.B.C "
java -jar app.jar -login admin -pass admin -role WRITE -res A.B.C > /dev/null
printResult $? 0
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T3.6:"
echo "  java -jar app.jar -login vasya -pass 1234 -role DELETE -res A "
java -jar app.jar -login vasya -pass 1234 -role DELETE -res A > /dev/null
printResult $? 4
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T3.7:"
echo "  java -jar app.jar -login vasya -pass 123 -role WRITE -res A.B.C "
java -jar app.jar -login vasya -pass 123 -role WRITE -res A.B.C > /dev/null
printResult $? 0
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T3.8:"
echo "  java -jar app.jar -login admin -pass admin -role READ  "
java -jar app.jar -login admin -pass admin -role READ  > /dev/null #удачная аутентификация
printResult $? 0
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T3.9:"
echo "  java -jar app.jar -login admin -pass admin -role EXECUTE -res A "
java -jar app.jar -login admin -pass admin -role EXECUTE -res A > /dev/null
printResult $? 6
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T3.10:"
echo "  java -jar app.jar -login admin -pass admin -role WRITE -res A.A "
java -jar app.jar -login admin -pass admin -role WRITE -res A.A > /dev/null
printResult $? 6
success=$(( $success + $? ))
total=$(( $total + 1))

echo
echo "Accounting" # =====================================================
echo

echo " T4.1:"
echo "  java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol 1024 "
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol 1024 > /dev/null
printResult $? 0
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T4.2:"
echo "  java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 20202-03-10 -de 2020-04-01 -vol 1024  "
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 20202-03-10 -de 2020-04-01 -vol 1024  > /dev/null #неверный год
printResult $? 7
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T4.3:"
echo "  java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-12-10 -de 2020-13-01 -vol 1024  "
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-12-10 -de 2020-13-01 -vol 1024  > /dev/null #несуществующий месяц
printResult $? 7
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T4.4:"
echo "  java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-04-31 -de 2020-04-01 -vol 1024  "
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-04-31 -de 2020-04-01 -vol 1024  > /dev/null #день которого нет в месяце
printResult $? 7
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T4.5:"
echo "  java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-02-32 -de 2020-04-01 -vol 1024  "
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-02-32 -de 2020-04-01 -vol 1024  > /dev/null #несуществующее число
printResult $? 7
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T4.6:"
echo "  java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol -1024  "
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol -1024  > /dev/null #неверный объем
printResult $? 7
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T4.7:"
echo "  java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol alot  "
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol alot  > /dev/null #некорректный объем
printResult $? 7
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T4.8:"
echo "  java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01  "
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01  > /dev/null #удачная авторизация
printResult $? 0
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T4.9:"
echo "  java -jar app.jar -login admin -pass admin -role WRITE -res A.B.C -ds 2020-03-10 -de 2020-01-01 -vol 1024  "
java -jar app.jar -login admin -pass admin -role WRITE -res A.B.C -ds 2020-03-10 -de 2020-01-01 -vol 1024  > /dev/null #проверка другого юзера
printResult $? 0
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T4.10:"
echo "  java -jar app.jar -login vasya -pass 123 -role WRITE -res A.B.C -ds 2020-12-01 -de 2020-01-45 -vol 1024  "
java -jar app.jar -login vasya -pass 123 -role WRITE -res A.B.C -ds 2020-12-01 -de 2020-01-45 -vol 1024  > /dev/null #не проходит авторизация
printResult $? 7
success=$(( $success + $? ))
total=$(( $total + 1))

echo " T4.11:"
echo "  java -jar app.jar -login vasya -pass 123 -role EXECUTE -res A.B.C -ds 2020-12-01 -de 2020-01-45 -vol 1024  "
java -jar app.jar -login vasya -pass 123 -role EXECUTE -res A.B.C -ds 2020-12-01 -de 2020-01-45 -vol 1024  > /dev/null #не проходит авторизация
printResult $? 6
success=$(( $success + $? ))
total=$(( $total + 1))

echo
echo "Results:" # ==============================================================
echo " Passed: $success"
echo " Total: $total"
