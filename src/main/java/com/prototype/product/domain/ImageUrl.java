package com.prototype.product.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.NamedEntityGraph;

@Embeddable
@NoArgsConstructor
public class ImageUrl {

    String imageUrl;

    private ImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static ImageUrl create(String imageUrl){
        checkUrlValidation(imageUrl);
        return new ImageUrl(imageUrl);
    }

    public static void checkUrlValidation(String imageUrl){
        if(imageUrl.length()==0 || imageUrl.length()>2048){
            throw new IllegalArgumentException();
        }
    }

    public String showValue(){
        return this.imageUrl;
    }
}
