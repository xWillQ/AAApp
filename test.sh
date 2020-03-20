#!/bin/sh
success=0
total=0

total=$(( $total + 1))
java -jar app.jar > /dev/null
res=$?
echo " T1.1 expected:1, actual:$res"
if [ $res -eq 1 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -h > /dev/null
res=$?
echo " T1.2 expected:1, actual:$res"
if [ $res -eq 1 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -q  > /dev/null #особый случай
res=$?
echo " T1.3 expected:0, actual:$res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 > /dev/null
res=$?
echo " T2.1 expected:0, actual:$res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -pass 123 -login vasya > /dev/null
res=$?
echo " T2.2 expected:0, actual:$res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login VASYA -pass 123 > /dev/null
res=$?
echo " T2.3 expected:2, actual:$res"
if [ $res -eq 2 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login asd -pass 123 > /dev/null
res=$?
echo " T2.4 expected:3, actual:$res"
if [ $res -eq 3 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login admin -pass 1234 > /dev/null
res=$?
echo " T2.5 expected:4, actual:$res"
if [ $res -eq 4 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login admin -pass admin > /dev/null
res=$?
echo " T2.6 expected:0, actual:$res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role READ -res A > /dev/null
res=$?
echo " T3.1 expected:0, actual:$res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role DELETE -res A > /dev/null
res=$?
echo " T3.2 expected:5, actual:$res"
if [ $res -eq 5 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role WRITE -res A > /dev/null
res=$?
echo " T3.3 expected:6, actual:$res"
if [ $res -eq 6 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role READ -res A.B > /dev/null
res=$?
echo " T3.4 expected:0, actual:$res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login admin -pass admin -role WRITE -res A.B.C > /dev/null
res=$?
echo " T3.5 expected:0, actual:$res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 1234 -role DELETE -res A > /dev/null
res=$?
echo " T3.6 expected:4, actual:$res"
if [ $res -eq 4 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role WRITE -res A.B.C > /dev/null
res=$?
echo " T3.7 expected:6, actual:$res"
if [ $res -eq 6 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login admin -pass admin -role READ  > /dev/null #удачная аутентификация
res=$?
echo " T3.8 expected:0, actual:$res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login admin -pass admin -role EXECUTE -res A > /dev/null
res=$?
echo " T3.9 expected:6, actual:$res"
if [ $res -eq 6 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login admin -pass admin -role WRITE -res A.A > /dev/null
res=$?
echo " T3.10 expected:6, actual:$res"
if [ $res -eq 6 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol 1024 > /dev/null
res=$?
echo " T4.1 expected:0, actual:$res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 20202-03-10 -de 2020-04-01 -vol 1024  > /dev/null #неверный год
res=$?
echo " T4.2 expected:7, actual:$res"
if [ $res -eq 7 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-12-10 -de 2020-13-01 -vol 1024  > /dev/null #несуществующий месяц
res=$?
echo " T4.3 expected:7, actual:$res"
if [ $res -eq 7 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-04-31 -de 2020-04-01 -vol 1024  > /dev/null #день которого нет в месяце
res=$?
echo " T4.4 expected:7, actual:$res"
if [ $res -eq 7 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-02-32 -de 2020-04-01 -vol 1024  > /dev/null #несуществующее число
res=$?
echo " T4.5 expected:7, actual:$res"
if [ $res -eq 7 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol -1024  > /dev/null #неверный объем
res=$?
echo " T4.6 expected:7, actual:$res"
if [ $res -eq 7 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol alot  > /dev/null #некорректный объем
res=$?
echo " T4.7 expected:7, actual:$res"
if [ $res -eq 7 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01  > /dev/null #удачная авторизация
res=$?
echo " T4.8 expected:0, actual:$res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login admin -pass admin -role WRITE -res A.B.C -ds 2020-03-10 -de 2020-01-01 -vol 1024  > /dev/null #проверка другого юзера
res=$?
echo " T4.9 expected:0, actual:$res"
if [ $res -eq 0 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi

total=$(( $total + 1))
java -jar app.jar -login vasya -pass 123 -role WRITE -res A.B.C -ds 2020-12-01 -de 2020-01-45 -vol 1024  > /dev/null #не проходит авторизация
res=$?
echo " T4.10 expected:6, actual:$res"
if [ $res -eq 6 ]; then
  success=$(( $success + 1))
  echo "Successed"
else
  echo "Failed"
fi


echo "Successed: $success"
echo "Total: $total"
