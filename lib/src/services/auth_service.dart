import 'dart:convert';
import 'package:http/http.dart' as http;
import 'session_manager.dart' as session_manager;
import 'package:flutter/foundation.dart';

class AuthService {
  static const String baseUrl = "http://localhost:9000/api/v1/auth";

  static Future<bool> login(String username, String password) async {
    final url = Uri.parse("http://localhost:9000/api/v1/auth/login");
    final response = await http.post(url,
        headers: {"Content-Type": "application/json"},
        body: jsonEncode({"username": username, "password": password}));

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      await session_manager.SessionManager.saveToken(data["token"]);
      return true;
    }else{
      debugPrint("❌ Error ${response.statusCode}: ${response.body}");
    }
    return false;
  }

  static Future<bool> register(String username, String email, String password) async {
    final url = Uri.parse("http://localhost:9000/api/v1/auth/signup");
    final response = await http.post(url,
        headers: {"Content-Type": "application/json"},
        body: jsonEncode({"nombre" : username, "correo": email, "contrasenia": password}));

    if (response.statusCode == 200) {
      debugPrint("✅ Signup success: ${response.body}");
    } else {
      // Imprime el status y la respuesta del backend
      debugPrint("❌ Error ${response.statusCode}: ${response.body}");
    }
    return response.statusCode == 200;
  }
}
