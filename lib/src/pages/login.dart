import 'package:flutter/material.dart';
import '../services/auth_service.dart';
import 'package:url_launcher/url_launcher.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  LoginPageState createState() => LoginPageState();
}

class LoginPageState extends State<LoginPage> {
  final TextEditingController emailController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();
  bool loading = false;

  void login() async {
    setState(() => loading = true);

    final success = await AuthService.login(
      emailController.text,
      passwordController.text,
    );

    if(!mounted) return;

    setState(() => loading = false);

    if (success) {
      ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(content: Text("Login exitoso ✅")),
      );
      // Navegar a home
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(content: Text("Credenciales inválidas ❌")),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Login")),
      body: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          children: [
            TextField(controller: emailController, decoration: const InputDecoration(labelText: "Nombre de Usuario")),
            TextField(controller: passwordController, decoration: const InputDecoration(labelText: "Contraseña"), obscureText: true),
            const SizedBox(height: 20),
            loading
                ? const CircularProgressIndicator()
                : ElevatedButton(onPressed: login, child: const Text("Iniciar Sesión")),
            ElevatedButton(
              onPressed: () {
                Navigator.pushNamed(context, "/register");
              },
              child: const Text("Registrarme"),
            ),
            ElevatedButton(
              onPressed: () async {
                if(!await launchUrl(Uri.parse("http://localhost:9000/login"),mode: LaunchMode.inAppWebView)){
                  
                  throw Exception('No se pudo abrir');
                }
              },
              child: const Text("Acceder con Google"),
            ),
          ],
        ),
      ),
    );
  }
}
