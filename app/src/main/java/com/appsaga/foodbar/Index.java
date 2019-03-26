package com.appsaga.foodbar;

class Index {

    int image;
    int location;

    public Index(int image,int location)
    {
        this.image=image;
        this.location=location;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
}
