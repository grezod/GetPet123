package iii.org.tw.getpet;

/**
 * Created by poloi on 2017/1/30.
 */

public class simplePetData {
    private String ImageUrl;
        private String title;
        private String content;
        public simplePetData(String p_ImageUrl, String p_title, String p_content){
            ImageUrl = p_ImageUrl;
        title=p_title;
        content = p_content;
    }
    //******

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.ImageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //*******

}
