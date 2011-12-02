package model;

import com.orm.androrm.CharField;
import com.orm.androrm.Model;

public class Article extends Model {
	
	public CharField title;
	
    public Article() {
        super(true);
        
        title = new CharField();
    }
}
