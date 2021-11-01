package ru.geekbrains.chat.server;


public interface AuthManager {
    String getNicknameByLoginAndPassword(String login, String password);
}
