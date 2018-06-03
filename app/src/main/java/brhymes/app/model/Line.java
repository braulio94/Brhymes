package brhymes.app.model;

import java.util.Date;

/**
 * Created by Braulio Cassule on 6/3/2018.
 **/

public interface Line {
    int getId();
    String getText();
    Form getForm();
    int getAuthorId();
    String getPart();
    Date getWrittenAt();
}
