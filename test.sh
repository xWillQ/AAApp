#!/bin/sh
success=0
total=0

echo
echo "Help" # ==========================================================
echo

total=$(( $total + 1))
java -jar app.jar > /dev/null
res=$?
echo " T1.1:"
echo "  java -jar app.jar "
echo "  expected: 1, actual: $res"
if [ $res -eq 1 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -h > /dev/null
res=$?
echo " T1.2:"
echo "  java -jar app.jar -h "
echo "  expected: 1, actual: $res"
if [ $res -eq 1 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -q  > /dev/null #особый случай
res=$?
echo " T1.3:"
echo "  java -jar app.jar -q  "
echo "  expected: 0, actual: $res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

echo
echo "Authentication" # =====================================================
echo

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 > /dev/null
res=$?
echo " T2.1:"
echo "  java -jar app.jar -login vasya -pass 123 "
echo "  expected: 0, actual: $res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -pass 123 -login vasya > /dev/null
res=$?
echo " T2.2:"
echo "  java -jar app.jar -pass 123 -login vasya "
echo "  expected: 0, actual: $res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login VASYA -pass 123 > /dev/null
res=$?
echo " T2.3:"
echo "  java -jar app.jar -login VASYA -pass 123 "
echo "  expected: 2, actual: $res"
if [ $res -eq 2 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login asd -pass 123 > /dev/null
res=$?
echo " T2.4:"
echo "  java -jar app.jar -login asd -pass 123 "
echo "  expected: 3, actual: $res"
if [ $res -eq 3 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login admin -pass 1234 > /dev/null
res=$?
echo " T2.5:"
echo "  java -jar app.jar -login admin -pass 1234 "
echo "  expected: 4, actual: $res"
if [ $res -eq 4 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login admin -pass admin > /dev/null
res=$?
echo " T2.6:"
echo "  java -jar app.jar -login admin -pass admin "
echo "  expected: 0, actual: $res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

echo
echo "Authorization" # =====================================================
echo

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role READ -res A > /dev/null
res=$?
echo " T3.1:"
echo "  java -jar app.jar -login vasya -pass 123 -role READ -res A "
echo "  expected: 0, actual: $res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role DELETE -res A > /dev/null
res=$?
echo " T3.2:"
echo "  java -jar app.jar -login vasya -pass 123 -role DELETE -res A "
echo "  expected: 5, actual: $res"
if [ $res -eq 5 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role WRITE -res A > /dev/null
res=$?
echo " T3.3:"
echo "  java -jar app.jar -login vasya -pass 123 -role WRITE -res A "
echo "  expected: 6, actual: $res"
if [ $res -eq 6 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role READ -res A.B > /dev/null
res=$?
echo " T3.4:"
echo "  java -jar app.jar -login vasya -pass 123 -role READ -res A.B "
echo "  expected: 0, actual: $res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login admin -pass admin -role WRITE -res A.B.C > /dev/null
res=$?
echo " T3.5:"
echo "  java -jar app.jar -login admin -pass admin -role WRITE -res A.B.C "
echo "  expected: 0, actual: $res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 1234 -role DELETE -res A > /dev/null
res=$?
echo " T3.6:"
echo "  java -jar app.jar -login vasya -pass 1234 -role DELETE -res A "
echo "  expected: 4, actual: $res"
if [ $res -eq 4 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role WRITE -res A.B.C > /dev/null
res=$?
echo " T3.7:"
echo "  java -jar app.jar -login vasya -pass 123 -role WRITE -res A.B.C "
echo "  expected: 6, actual: $res"
if [ $res -eq 6 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login admin -pass admin -role READ  > /dev/null #удачная аутентификация
res=$?
echo " T3.8:"
echo "  java -jar app.jar -login admin -pass admin -role READ  "
echo "  expected: 0, actual: $res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login admin -pass admin -role EXECUTE -res A > /dev/null
res=$?
echo " T3.9:"
echo "  java -jar app.jar -login admin -pass admin -role EXECUTE -res A "
echo "  expected: 6, actual: $res"
if [ $res -eq 6 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login admin -pass admin -role WRITE -res A.A > /dev/null
res=$?
echo " T3.10:"
echo "  java -jar app.jar -login admin -pass admin -role WRITE -res A.A "
echo "  expected: 6, actual: $res"
if [ $res -eq 6 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

echo
echo "Accounting" # =====================================================
echo

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol 1024 > /dev/null
res=$?
echo " T4.1:"
echo "  java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol 1024 "
echo "  expected: 0, actual: $res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 20202-03-10 -de 2020-04-01 -vol 1024  > /dev/null #неверный год
res=$?
echo " T4.2:"
echo "  java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 20202-03-10 -de 2020-04-01 -vol 1024  "
echo "  expected: 7, actual: $res"
if [ $res -eq 7 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-12-10 -de 2020-13-01 -vol 1024  > /dev/null #несуществующий месяц
res=$?
echo " T4.3:"
echo "  java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-12-10 -de 2020-13-01 -vol 1024  "
echo "  expected: 7, actual: $res"
if [ $res -eq 7 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-04-31 -de 2020-04-01 -vol 1024  > /dev/null #день которого нет в месяце
res=$?
echo " T4.4:"
echo "  java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-04-31 -de 2020-04-01 -vol 1024  "
echo "  expected: 7, actual: $res"
if [ $res -eq 7 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-02-32 -de 2020-04-01 -vol 1024  > /dev/null #несуществующее число
res=$?
echo " T4.5:"
echo "  java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-02-32 -de 2020-04-01 -vol 1024  "
echo "  expected: 7, actual: $res"
if [ $res -eq 7 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol -1024  > /dev/null #неверный объем
res=$?
echo " T4.6:"
echo "  java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol -1024  "
echo "  expected: 7, actual: $res"
if [ $res -eq 7 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol alot  > /dev/null #некорректный объем
res=$?
echo " T4.7:"
echo "  java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol alot  "
echo "  expected: 7, actual: $res"
if [ $res -eq 7 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01  > /dev/null #удачная авторизация
res=$?
echo " T4.8:"
echo "  java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01  "
echo "  expected: 0, actual: $res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login admin -pass admin -role WRITE -res A.B.C -ds 2020-03-10 -de 2020-01-01 -vol 1024  > /dev/null #проверка другого юзера
res=$?
echo " T4.9:"
echo "  java -jar app.jar -login admin -pass admin -role WRITE -res A.B.C -ds 2020-03-10 -de 2020-01-01 -vol 1024  "
echo "  expected: 0, actual: $res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role WRITE -res A.B.C -ds 2020-12-01 -de 2020-01-45 -vol 1024  > /dev/null #не проходит авторизация
res=$?
echo " T4.10:"
echo "  java -jar app.jar -login vasya -pass 123 -role WRITE -res A.B.C -ds 2020-12-01 -de 2020-01-45 -vol 1024  "
echo "  expected: 6, actual: $res"
if [ $res -eq 6 ]; then
  success=$(( $success + 1))
  echo "\033[32m  Passed \033[0m"
else
  echo "\033[31m  Failed \033[0m"
fi

echo
echo "Results:" # ==============================================================
echo " Passed: $success"
echo " Total: $total"
