mortgage-calculator project
===========================

Simple and Free Mortgage calculator where you can calculate your mortgage and configure basic stuff like monthly payment, number of years etc.
but also more advanced stuff like premature payments which you expect to pay after X years etc.


Steps to run Swing GUI client
=============================
(Tested on Oracle JDK6, Maven3, Ubuntu linux):

1) After cloning this project, simply run these commands from current directory:

mvn clean install

cd target

java -cp mortgage-calculator.jar:lib/TableLayout-20050920.jar cz.possoft.calculator.runner.swing.SwingAppRunner

This will execute Swing GUI client where you can choose language (English, Czech) and then configure client for your mortgage.

Steps to run console client
===========================
Console client is only in Czech and allows just very basic computation. Swing client is recommended way. Steps are same like for
GUI client, but only exception is last command:

java -cp mortgage-calculator.jar cz.possoft.calculator.runner.console.ConsoleRunner

Licence: LGPL 2.1

Email me on mposolda@gmail (dot) com for feedback :-)

