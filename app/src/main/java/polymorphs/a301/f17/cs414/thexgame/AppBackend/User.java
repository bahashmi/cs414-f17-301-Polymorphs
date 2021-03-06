package polymorphs.a301.f17.cs414.thexgame.AppBackend;

/**
 * Created by athai on 10/18/17. edited and modified by Steven
 */

public class User {
    private String email;
    private String name;
    private String nickname;

    public User() {}

    public User(String name,String email,String nickname){
        this.name = name;
        this.email = email;
        this.nickname = nickname;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getEmail(){
        return email;
    }

    public String getName(){
        return name;
    }

    public String getNickname(){
        return nickname;
    }

    public boolean equals(Object o) {
        if (!(o instanceof User)) return false;
        User otherUser = (User)o;
        return (email.equals(otherUser.email) && nickname.equals(otherUser.nickname));
    }
}