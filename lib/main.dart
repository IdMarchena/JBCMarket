import 'package:flutter/material.dart';
import 'package:front_app_empleabilidad/src/pages/login.dart';
import 'package:front_app_empleabilidad/src/pages/register.dart';

void main() {
  runApp(MaterialApp(
    debugShowCheckedModeBanner: false,
    initialRoute: "/login",
    routes: {
      "/login": (context) => const LoginPage(),
      "/register": (context) => const RegisterPage(),
    },
  ));
}