package th.ac.ku.viewraidee.model;

public class Account{

    private String username;
    private String password;
    private String link;
    private String email;
    private String photo;
    private String aboutMe;
    private int countArticle;
    private int countHeart;
    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public int getCountArticle() {
        return countArticle;
    }

    public void setCountArticle(int countArticle) {
        this.countArticle = countArticle;
    }

    public int getCountHeart() {
        return countHeart;
    }

    public void setCountHeart(int countHeart) {
        this.countHeart = countHeart;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
