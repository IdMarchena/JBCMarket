import 'package:flutter/material.dart';
import '../services/auth_service.dart';

class RegisterPage extends StatefulWidget {
  const RegisterPage({super.key});

  @override
  RegisterPageState createState() => RegisterPageState();
}

class RegisterPageState extends State<RegisterPage> {
  final TextEditingController usernameController = TextEditingController();
  final TextEditingController emailController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();
  bool loading = false;

  void register() async {
    setState(() => loading = true);

    final bool success = await AuthService.register(
      usernameController.text, 
      emailController.text, 
      passwordController.text
    );

    if(!mounted) return;

    setState(()=> loading = false);

    if (success) {
      ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(content: Text("Registro exitoso ✅")),
      );
      // Navegar a home
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(content: Text("Credenciales inválidas ❌")),
      );
    }
  }

  @override
  Widget build(BuildContext context){
    return Scaffold(
      appBar: AppBar(title: const Text("Register")),
      body: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          children: [
            TextField(controller: usernameController, decoration: const InputDecoration(labelText: "Nombre de Usuario")),
            TextField(controller: emailController, decoration: const InputDecoration(labelText: "Correo")),
            TextField(controller: passwordController, decoration: const InputDecoration(labelText: "Contraseña"), obscureText: true),
            const SizedBox(height: 20),
            loading
                ? const CircularProgressIndicator()
                : ElevatedButton(onPressed: register, child: const Text("Crear cuenta")),
          ],
        ),
      ),
    );
  }
}
