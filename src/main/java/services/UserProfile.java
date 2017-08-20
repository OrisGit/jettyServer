package services;

public class UserProfile {
    private String login;
    private String password;
    private String email;

    public UserProfile(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        return builder.append("Информация о пользователе: /nЛогин: ").append(login).append("/nПароль: ").append(password)
                .append("/nE-mail: ").append(email).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserProfile that = (UserProfile) o;

        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        return email != null ? email.equals(that.email) : that.email == null;
    }

    @Override
    public int hashCode() {
        return login.hashCode()^email.hashCode()^password.hashCode();
    }
}
