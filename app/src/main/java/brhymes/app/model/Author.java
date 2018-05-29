package brhymes.app.model;

import android.graphics.drawable.Drawable;


/**
 * Created by Braulio Cassule on 5/29/2018.
 **/

public class Author {

    private int id;
    private String name;
    private Drawable imageId;

    public Author(String name, Drawable imageId) {
        this.name = name;
        this.imageId = imageId;
    }
}
