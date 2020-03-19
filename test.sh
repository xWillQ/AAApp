#!/bin/sh
success=0
total=0

app.jar
total++
echo " T1.1 expected:1, actual:$?"
if $? == 1; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -h
total++
echo " T1.2 expected:1, actual:$?"
if $? == 1; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -q
#особый случай
total++
echo " T1.3 expected:0, actual:$?"
if $? == 0; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login vasya -pass 123
total++
echo " T2.1 expected:0, actual:$?"
if $? == 0; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -pass 123 -login vasya
total++
echo " T2.2 expected:0, actual:$?"
if $? == 0; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login VASYA -pass 123
total++
echo " T2.3 expected:2, actual:$?"
if $? == 2; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login asd -pass 123
total++
echo " T2.4 expected:3, actual:$?"
if $? == 3; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login admin -pass 1234
total++
echo " T2.5 expected:4, actual:$?"
if $? == 4; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login admin -pass admin
total++
echo " T2.6 expected:0, actual:$?"
if $? == 0; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login vasya -pass 123 -role READ -res A
total++
echo " T3.1 expected:0, actual:$?"
if $? == 0; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login vasya -pass 123 -role DELETE -res A
total++
echo " T3.2 expected:5, actual:$?"
if $? == 5; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login vasya -pass 123 -role WRITE -res A
total++
echo " T3.3 expected:6, actual:$?"
if $? == 6; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login vasya -pass 123 -role READ -res A.B
total++
echo " T3.4 expected:0, actual:$?"
if $? == 0; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login admin -pass admin -role WRITE -res A.B.C
total++
echo " T3.5 expected:0, actual:$?"
if $? == 0; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login vasya -pass 1234 -role DELETE -res A
total++
echo " T3.6 expected:4, actual:$?"
if $? == 4; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login vasya -pass 123 -role WRITE -res A.B.C
total++
echo " T3.7 expected:6, actual:$?"
if $? == 6; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login admin -pass admin -role READ
#удачная аутентификация
total++
echo " T3.8 expected:0, actual:$?"
if $? == 0; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login admin -pass admin -role EXECUTE -res A
total++
echo " T3.9 expected:6, actual:$?"
if $? == 6; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login admin -pass admin -role WRITE -res A.A
total++
echo " T3.10 expected:6, actual:$?"
if $? == 6; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol 1024
total++
echo " T4.1 expected:0, actual:$?"
if $? == 0; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login vasya -pass 123 -role READ -res A -ds 20202-03-10 -de 2020-04-01 -vol 1024
#неверный год
total++
echo " T4.2 expected:7, actual:$?"
if $? == 7; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login vasya -pass 123 -role READ -res A -ds 2020-12-10 -de 2020-13-01 -vol 1024
#несуществующий месяц
total++
echo " T4.3 expected:7, actual:$?"
if $? == 7; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login vasya -pass 123 -role READ -res A -ds 2020-04-31 -de 2020-04-01 -vol 1024
#день которого нет в месяце
total++
echo " T4.4 expected:7, actual:$?"
if $? == 7; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login vasya -pass 123 -role READ -res A -ds 2020-02-32 -de 2020-04-01 -vol 1024
#несуществующее число
total++
echo " T4.5 expected:7, actual:$?"
if $? == 7; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol -1024
#неверный объем
total++
echo " T4.6 expected:7, actual:$?"
if $? == 7; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol alot
#некорректный объем
total++
echo " T4.7 expected:7, actual:$?"
if $? == 7; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01
#удачная авторизация
total++
echo " T4.8 expected:0, actual:$?"
if $? == 0; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login admin -pass admin -role WRITE -res A.B.C -ds 2020-03-10 -de 2020-01-01 -vol 1024
#проверка другого юзера
total++
echo " T4.9 expected:0, actual:$?"
if $? == 0; then
  success++
  echo "Successed"
else
  echo "Failed"
fi

app.jar -login vasya -pass 123 -role WRITE -res A.B.C -ds 2020-12-01 -de 2020-01-45 -vol 1024
#не проходит авторизация
total++
echo " T4.10 expected:6, actual:$?"
if $? == 6; then
  success++
  echo "Successed"
else
  echo "Failed"
fi


echo "Successed: $success"
echo "Total: $total"
