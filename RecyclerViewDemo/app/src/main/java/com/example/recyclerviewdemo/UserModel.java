package com.example.recyclerviewdemo;

public class UserModel {
    private String id, name, mobile, profile_imge;

    public UserModel() {
    }

    public UserModel(String id, String name, String mobile, String profile_imge) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.profile_imge = profile_imge;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProfile_imge() {
        return profile_imge;
    }

    public void setProfile_imge(String profile_imge) {
        this.profile_imge = profile_imge;
    }
}
