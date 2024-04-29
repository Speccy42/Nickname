package fr.speccy.nickname;

public class CustomPlayer {
    public String name;
    public String newName;
    public String newSkin;

    public CustomPlayer(String name, String newName, String newSkin) {
        this.name = name;
        this.newName = newName;
        this.newSkin = newSkin;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewName() {
        return this.newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getNewSkin() {
        return this.newSkin;
    }

    public void setNewSkin(String newSkin) {
        this.newSkin = newSkin;
    }
}
